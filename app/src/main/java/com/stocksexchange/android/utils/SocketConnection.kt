package com.stocksexchange.android.utils

import android.app.Application
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import com.google.gson.Gson
import com.stocksexchange.android.Constants
import com.stocksexchange.android.api.model.*
import com.stocksexchange.android.api.model.websocket.CurrencyMarketSocketData
import com.stocksexchange.android.api.model.websocket.OrderbookOrderSocketData
import com.stocksexchange.android.api.model.websocket.TradeHistorySocketData
import com.stocksexchange.android.api.utils.fromJson
import com.stocksexchange.android.di.STOCKS_EXCHANGE_OK_HTTP_CLIENT
import com.stocksexchange.android.events.*
import com.stocksexchange.android.model.*
import com.stocksexchange.android.receivers.NetworkStateReceiver
import com.stocksexchange.android.repositories.currencymarkets.CurrencyMarketsRepository
import com.stocksexchange.android.repositories.orderbook.OrderbookRepository
import com.stocksexchange.android.repositories.trades.TradesRepository
import com.stocksexchange.android.utils.extensions.binarySearch
import com.stocksexchange.android.ui.utils.extensions.isNetworkAvailable
import com.stocksexchange.android.utils.helpers.createBgLaunchGlobalCoroutine
import com.stocksexchange.android.utils.managers.BackgroundManager
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import io.socket.engineio.client.transports.WebSocket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import okhttp3.OkHttpClient
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.json.JSONObject
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import timber.log.Timber

/**
 * Contains socket-related functionality and a few helper methods.
 */
class SocketConnection private constructor(
    application: Application,
    settings: Settings
) : KoinComponent {


    companion object {

        @Volatile
        private var INSTANCE: SocketConnection? = null

        private const val MAX_RECONNECTION_ATTEMPTS = 3


        fun getInstance(application: Application, settings: Settings): SocketConnection {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildSocketConnection(application, settings).also { INSTANCE = it }
            }
        }


        private fun buildSocketConnection(application: Application, settings: Settings): SocketConnection {
            return SocketConnection(application, settings)
        }

    }


    private var mIsNetworkAvailable: Boolean = application.isNetworkAvailable()
    private var mIsInForeground: Boolean = true

    private var mStatus: Statuses = Statuses.INVALID

    private var mSettings: Settings = settings

    private val mEmittingEventChannelsSet: MutableSet<String> = mutableSetOf()
    private val mEmittingEventChannelsCacheSet: MutableSet<String> = mutableSetOf()

    private val mOrderbookOrdersActionItems: MutableList<DataActionItem<OrderbookOrder>> = mutableListOf()
    private val mTradeActionItems: MutableList<DataActionItem<Trade>> = mutableListOf()

    private val mAsyncJobsSet: LinkedHashSet<Job> = linkedSetOf()

    private val mPerformedCurrencyMarketActions = PerformedCurrencyMarketActions()



    private val mGson: Gson by inject()

    private val mOkHttpClient: OkHttpClient by inject(STOCKS_EXCHANGE_OK_HTTP_CLIENT)

    private val mCurrencyMarketsRepository: CurrencyMarketsRepository by inject()
    private val mOrderbookRepository: OrderbookRepository by inject()
    private val mTradesRepository: TradesRepository by inject()

    private var mSocket: Socket? = null




    init {
        registerEventBusSubscriber()

        initNetworkReceiver(application)
        initBackgroundManager(application)
        initSocket()
    }


    private fun registerEventBusSubscriber() {
        EventBus.getDefault().register(this)
    }


    private fun initNetworkReceiver(context: Context) {
        val networkListener = object : NetworkStateReceiver.Listener {

            override fun onConnected() {
                mIsNetworkAvailable = true

                if(shouldConnect()) {
                    connect()
                }
            }

            override fun onDisconnected() {
                mIsNetworkAvailable = false
            }

        }

        context.registerReceiver(
            NetworkStateReceiver(context, networkListener),
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }


    private fun initBackgroundManager(application: Application) {
        val backgroundManagerListener = object : BackgroundManager.Listener {

            override fun onBecameForeground() {
                mIsInForeground = true

                if(shouldConnect()) {
                    connect()
                }
            }

            override fun onBecameBackground() {
                mIsInForeground = false

                if(shouldDisconnect()) {
                    disconnect()
                }
            }

        }

        BackgroundManager.getInstance(application).registerListener(backgroundManagerListener)
    }


    private fun initSocket() {
        IO.setDefaultOkHttpCallFactory(mOkHttpClient)
        IO.setDefaultOkHttpWebSocketFactory(mOkHttpClient)

        val options = IO.Options().apply {
            reconnectionAttempts = MAX_RECONNECTION_ATTEMPTS
            transports = arrayOf(WebSocket.NAME)
            callFactory = mOkHttpClient
            webSocketFactory = mOkHttpClient
        }

        mSocket = IO.socket(Constants.STEX_SOCKET_URL, options)?.apply {
            on(InputEvents.CONNECT, Listener(InputEvents.CONNECT))
            on(InputEvents.CONNECT_ERROR, Listener(InputEvents.CONNECT_ERROR))
            on(InputEvents.ERROR, Listener(InputEvents.ERROR))
            on(InputEvents.DISCONNECT, Listener(InputEvents.DISCONNECT))
            on(InputEvents.CURRENCY_MARKET_CHANGED, Listener(InputEvents.CURRENCY_MARKET_CHANGED))
            on(InputEvents.PRICE_CHART_DATA_CHANGED, Listener(InputEvents.PRICE_CHART_DATA_CHANGED))
            on(InputEvents.ORDERBOOK_DATA_CHANGED, Listener(InputEvents.ORDERBOOK_DATA_CHANGED))
            on(InputEvents.TRADE_CREATED, Listener(InputEvents.TRADE_CREATED))
        }

        if(shouldConnect()) {
            connect()
        }
    }


    private fun shouldConnect(): Boolean {
        return (mIsInForeground && mIsNetworkAvailable && mSettings.isRealTimeDataStreamingEnabled)
    }


    private fun shouldDisconnect(): Boolean {
        return ((!mIsInForeground || !mSettings.isRealTimeDataStreamingEnabled) && mIsNetworkAvailable)
    }


    /**
     * Tries to establish a web socket connection. Does nothing if the
     * connection is already established.
     */
    fun connect() {
        if((mStatus == Statuses.CONNECTED) || (mStatus == Statuses.SENT_CONNECTION_REQUEST)) {
            return
        }

        mSocket?.connect()
        mStatus = Statuses.SENT_CONNECTION_REQUEST

        Timber.i("Sent a socket connection request.")
    }


    /**
     * Closes a web socket connection. Does nothing if the connection is not established.
     */
    fun disconnect() {
        if((mStatus == Statuses.DISCONNECTED) || (mStatus == Statuses.SENT_DISCONNECTION_REQUEST)) {
            return
        }

        mSocket?.disconnect()
        mStatus = Statuses.SENT_DISCONNECTION_REQUEST

        Timber.i("Sent a socket disconnection request.")
    }


    /**
     * Starts listening to currency markets real-time updates.
     */
    fun startListeningToCurrencyMarketsUpdates() {
        sendSubscribingEvent(OutputEventsData.getValueChannelRate())
    }


    /**
     * Stops listening to currency markets real-time updates.
     */
    fun stopListeningToCurrencyMarketsUpdates() {
        sendUnsubscribingEvent(OutputEventsData.getValueChannelRate())
    }


    /**
     * Starts listening to market preview real-time updates.
     *
     * @param priceChartInterval The data interval of the price chart
     * @param marketId The id of the market
     */
    fun startListeningToMarketPreviewUpdates(priceChartInterval: String, marketId: String) {
        startListeningToPriceChartDataUpdates(priceChartInterval, marketId)

        sendSubscribingEvent(OutputEventsData.getValueChannelOrderbookBidData(marketId))
        sendSubscribingEvent(OutputEventsData.getValueChannelOrderbookAskData(marketId))
        sendSubscribingEvent(OutputEventsData.getValueChannelTradeHistoryData(marketId))
    }


    /**
     * Stops listening to market preview real-time updates.
     *
     * @param marketId The id of the market
     */
    fun stopListeningToMarketPreviewUpdates(marketId: String) {
        stopListeningToPriceChartDataUpdates(marketId)

        sendUnsubscribingEvent(OutputEventsData.getValueChannelOrderbookBidData(marketId))
        sendUnsubscribingEvent(OutputEventsData.getValueChannelOrderbookAskData(marketId))
        sendUnsubscribingEvent(OutputEventsData.getValueChannelTradeHistoryData(marketId))
    }


    /**
     * Starts listening to price chart real-time updates.
     *
     * @param interval The data interval of the price chart
     * @param marketId The id of the market
     */
    fun startListeningToPriceChartDataUpdates(interval: String, marketId: String) {
        sendSubscribingEvent(OutputEventsData.getValueChannelPriceChartData(interval, marketId))
    }


    /**
     * Stops listening to price chart real-time updates.
     *
     * @param marketId The id of the market
     */
    fun stopListeningToPriceChartDataUpdates(marketId: String) {
        // Creating a list to prevent concurrent modification
        val emittingChannelsList = if(isInBlackZone()) {
            mEmittingEventChannelsCacheSet
        } else {
            mEmittingEventChannelsSet
        }.toMutableList()

        for(channel in emittingChannelsList) {
            if(OutputEventsData.isPriceChartDataChannel(channel, marketId)) {
                sendUnsubscribingEvent(channel)
            }
        }
    }


    private fun sendSubscribingEvent(channel: String) {
        if(!shouldSendSubscribingEvent(channel)) {
            return
        }

        mEmittingEventChannelsSet.add(channel)

        val jsonObject = getEmittingEventJsonObject(channel)

        mSocket?.emit(OutputEvents.SUBSCRIBE, jsonObject)

        Timber.i("Sent subscribing event. Content: $jsonObject")
    }


    private fun sendUnsubscribingEvent(channel: String) {
        if(!shouldSendUnsubscribingEvent(channel)) {
            return
        }

        mEmittingEventChannelsSet.remove(channel)

        val jsonObject = getEmittingEventJsonObject(channel)

        mSocket?.emit(OutputEvents.UNSUBSCRIBE, jsonObject)

        Timber.i("Sent unsubscribing event. Content: $jsonObject")
    }


    private fun shouldSendSubscribingEvent(channel: String): Boolean {
        return if(isInBlackZone()) {
            if(!mEmittingEventChannelsCacheSet.contains(channel)) {
                mEmittingEventChannelsCacheSet.add(channel)
            }

            false
        } else {
            !mEmittingEventChannelsSet.contains(channel)
        }
    }


    private fun shouldSendUnsubscribingEvent(channel: String): Boolean {
        return if(isInBlackZone()) {
            if(mEmittingEventChannelsCacheSet.contains(channel)) {
                mEmittingEventChannelsCacheSet.remove(channel)
            }

            false
        } else {
            mEmittingEventChannelsSet.contains(channel)
        }
    }


    private fun isInBlackZone(): Boolean {
        return (!mIsNetworkAvailable || !mIsInForeground)
    }


    private fun getEmittingEventJsonObject(channel: String): JSONObject {
        return JSONObject().apply {
            put(OutputEventsData.KEY_CHANNEL, channel)
            put(OutputEventsData.KEY_AUTH, JSONObject())
        }
    }


    private fun saveEmittingEventsState() {
        mEmittingEventChannelsCacheSet.addAll(mEmittingEventChannelsSet)
        mEmittingEventChannelsSet.clear()
    }


    private fun restoreEmittingEventsState() {
        if(mEmittingEventChannelsCacheSet.isEmpty()) {
            return
        }

        for(channel in mEmittingEventChannelsCacheSet) {
            sendSubscribingEvent(channel)
        }

        mEmittingEventChannelsCacheSet.clear()
    }


    @Suppress("NON_EXHAUSTIVE_WHEN")
    @Subscribe
    fun onEvent(event: SettingsEvent) {
        if(event.isOriginatedFrom(this)) {
            return
        }

        mSettings = event.attachment

        when(event.action) {
            SettingsEvent.Actions.REAL_TIME_DATA_STREAMING_STATE_CHANGED -> {
                when {
                    shouldConnect() -> connect()
                    shouldDisconnect() -> disconnect()
                }
            }
        }
    }


    private fun performAsync(block: suspend CoroutineScope.() -> Unit) {
        val job = createBgLaunchGlobalCoroutine(startOption = CoroutineStart.LAZY, block = block)
        job.invokeOnCompletion {
            synchronized(mAsyncJobsSet) {
                mAsyncJobsSet.remove(job)

                if(mAsyncJobsSet.isNotEmpty()) {
                    mAsyncJobsSet.first().start()
                }
            }
        }

        synchronized(mAsyncJobsSet) {
            mAsyncJobsSet.add(job)

            if((mAsyncJobsSet.size - 1) == 0) {
                job.start()
            }
        }
    }


    private fun isValidSocketResponse(args: Array<out Any>): Boolean {
        if(args.size < 2) {
            return false
        }

        if((args[0] !is String) ||
            (!mEmittingEventChannelsSet.contains(args[0])) ||
            (args[1] !is JSONObject)) {
            return false
        }

        return true
    }


    private fun onConnected() {
        mStatus = Statuses.CONNECTED
        Timber.i("onConnected")

        restoreEmittingEventsState()
    }


    private fun onConnectionError() {
        mStatus = Statuses.CONNECTION_ERROR
        Timber.i("onConnectionError")
    }


    private fun onError(args: Array<out Any>) {
        mStatus = Statuses.ERROR
        Timber.i("onError")

        if(args.isEmpty() || args[0] !is Throwable) {
            return
        }

        Timber.e(args[0] as Throwable, "A web socket error has occurred.")
    }


    private fun onDisconnected() {
        mStatus = Statuses.DISCONNECTED
        Timber.i("onDisconnected")

        saveEmittingEventsState()
    }


    private fun onCurrencyMarketChanged(args: Array<out Any>) {
        if(!isValidSocketResponse(args)) {
            return
        }

        val currencyMarketSocketData: CurrencyMarketSocketData = mGson.fromJson(
            (args[1] as JSONObject).toString()
        )

        Timber.i("onCurrencyMarketChanged(data: $currencyMarketSocketData)")

        updateCurrencyMarket(currencyMarketSocketData)
    }


    private fun updateCurrencyMarket(socketData: CurrencyMarketSocketData) {
        performAsync {
            val currencyMarketResult = mCurrencyMarketsRepository.getCurrencyMarket(socketData.id)

            if(currencyMarketResult.isErroneous()) {
                return@performAsync
            }

            val originalCurrencyMarket = currencyMarketResult.getSuccessfulResultValue()
            val updatedCurrencyMarket = originalCurrencyMarket.copy(
                lastPrice = socketData.lastPrice,
                lastPriceDayAgo = socketData.lastPriceDayAgo,
                dailyMinPrice = socketData.dailyMinPrice,
                dailyMaxPrice = socketData.dailyMaxPrice,
                dailyPriceChange = socketData.dailyPriceChange,
                dailyVolume = socketData.dailyVolume,
                volume = (socketData.lastPriceDayAgo * socketData.dailyVolume)
            ) // calculating volume manually until the web socket starts sending calculated field

            mCurrencyMarketsRepository.save(updatedCurrencyMarket)

            val currencyMarketEvent = CurrencyMarketEvent.update(
                updatedCurrencyMarket,
                this@SocketConnection
            )

            if(EventBus.getDefault().hasSubscriberForEvent(currencyMarketEvent.javaClass)) {
                EventBus.getDefault().post(currencyMarketEvent)
            } else {
                mPerformedCurrencyMarketActions.addUpdatedCurrencyMarket(updatedCurrencyMarket)

                EventBus.getDefault().postSticky(PerformedCurrencyMarketsActionsEvent.init(
                    mPerformedCurrencyMarketActions,
                    this@SocketConnection,
                    object : BaseEvent.OnConsumeListener {

                        override fun onConsumed() {
                            mPerformedCurrencyMarketActions.removeAllUpdatedCurrencyMarkets()
                        }

                    }
                ))
            }
        }
    }


    private fun onPriceChartDataChanged(args: Array<out Any>) {
        if(!isValidSocketResponse(args)) {
            return
        }

        Timber.i("onPriceChartDataChanged(args: $args)")
        //todo
    }


    private fun onOrderbookDataChanged(args: Array<out Any>) {
        if(!isValidSocketResponse(args)) {
            return
        }

        val channel: String = (args[0] as String)
        val orderbookOrderSocketData: OrderbookOrderSocketData = mGson.fromJson(
            (args[1] as JSONObject).toString()
        )

        Timber.i("onOrderbookDataChanged(channel: $channel, data: $orderbookOrderSocketData)")

        updateOrderbookOrder(channel, orderbookOrderSocketData)
    }


    private fun updateOrderbookOrder(channel: String, socketData: OrderbookOrderSocketData) {
        performAsync {
            val currencyMarketResult = mCurrencyMarketsRepository.getCurrencyMarket(socketData.marketId)

            if(currencyMarketResult.isErroneous()) {
                return@performAsync
            }

            val currencyMarket = currencyMarketResult.getSuccessfulResultValue()
            val orderbookParameters = OrderbookParameters(currencyMarket.name)
            val orderbookResult = mOrderbookRepository.get(orderbookParameters)

            if(orderbookResult.isErroneous()) {
                return@performAsync
            }

            val orderbook = orderbookResult.getSuccessfulResultValue()
            val buyOrders = orderbook.buyOrders.toMutableList()
            val sellOrders = orderbook.sellOrders.toMutableList()
            val newOrder = socketData.toOrderbookOrder()
            val isRemoval = (newOrder.amount == 0.0)
            val type = if(OutputEventsData.isOrderbookBidData(channel)) {
                OrderbookOrderTypes.BID
            } else if(OutputEventsData.isOrderbookAskData(channel)) {
                OrderbookOrderTypes.ASK
            } else {
                return@performAsync
            }
            val orders = when(type) {
                OrderbookOrderTypes.BID -> buyOrders
                OrderbookOrderTypes.ASK -> sellOrders
            }
            var isOperationPerformed = false
            var orderbookDataActionItem: DataActionItem<OrderbookOrder>? = null

            orders.binarySearch(
                sortType = when(type) {
                    OrderbookOrderTypes.BID -> SortTypes.DESC
                    OrderbookOrderTypes.ASK -> SortTypes.ASC
                },
                onPresent = {
                    if(isRemoval) {
                        orderbookDataActionItem = DataActionItem.remove(newOrder)
                        orders.removeAt(it)
                    } else {
                        orderbookDataActionItem = DataActionItem.update(newOrder)
                        orders[it] = newOrder
                    }

                    isOperationPerformed = true
                },
                onAbsent = {
                    if(!isRemoval) {
                        orderbookDataActionItem = DataActionItem.insert(newOrder)
                        orders.add(it, newOrder)

                        isOperationPerformed = true
                    }
                },
                compare = {
                    when {
                        (it.price < newOrder.price) -> -1
                        (it.price > newOrder.price) -> 1
                        else -> 0
                    }
                }
            )

            if(!isOperationPerformed) {
                return@performAsync
            }

            val newOrderbook = Orderbook(buyOrders, sellOrders)

            mOrderbookRepository.save(orderbookParameters, newOrderbook)

            mOrderbookOrdersActionItems.add(orderbookDataActionItem!!)

            EventBus.getDefault().postSticky(OrderbookDataUpdateEvent.newInstance(
                newData = newOrderbook,
                dataActionItems = mOrderbookOrdersActionItems,
                source = this@SocketConnection,
                onConsumeListener = object : BaseEvent.OnConsumeListener {

                    override fun onConsumed() {
                        if(mOrderbookOrdersActionItems.isNotEmpty()) {
                            mOrderbookOrdersActionItems.clear()
                        }
                    }

                }
            ))
        }
    }


    private fun onTradeCreated(args: Array<out Any>) {
        if(!isValidSocketResponse(args)) {
            return
        }

        val tradeHistorySocketData: TradeHistorySocketData = mGson.fromJson(
            (args[1] as JSONObject).toString()
        )

        Timber.i("onTradeCreated(data: $tradeHistorySocketData)")

        addNewTrade(tradeHistorySocketData)
    }


    private fun addNewTrade(socketData: TradeHistorySocketData) {
        performAsync {
            val currencyMarketResult = mCurrencyMarketsRepository.getCurrencyMarket(socketData.marketId)

            if(currencyMarketResult.isErroneous()) {
                return@performAsync
            }

            val currencyMarket = currencyMarketResult.getSuccessfulResultValue()
            val trade = socketData.toTrade()
            val params = TradeParameters.getDefaultParameters(currencyMarket.name)

            mTradesRepository.save(params, trade)

            val tradesResult = mTradesRepository.get(params)

            if(tradesResult.isErroneous()) {
                return@performAsync
            }

            val trades = tradesResult.getSuccessfulResultValue()

            mTradeActionItems.add(DataActionItem.insert(trade))

            EventBus.getDefault().postSticky(TradesDataUpdateEvent.newInstance(
                newData = trades,
                dataActionItems = mTradeActionItems,
                source = this@SocketConnection,
                onConsumeListener = object : BaseEvent.OnConsumeListener {

                    override fun onConsumed() {
                        if(mTradeActionItems.isNotEmpty()) {
                            mTradeActionItems.clear()
                        }
                    }

                }
            ))
        }
    }


    private inner class Listener(val event: String) : Emitter.Listener {

        override fun call(vararg args: Any) {
            when(event) {
                InputEvents.CONNECT -> onConnected()
                InputEvents.CONNECT_ERROR -> onConnectionError()
                InputEvents.ERROR -> onError(args)
                InputEvents.DISCONNECT -> onDisconnected()
                InputEvents.CURRENCY_MARKET_CHANGED -> onCurrencyMarketChanged(args)
                InputEvents.PRICE_CHART_DATA_CHANGED -> onPriceChartDataChanged(args)
                InputEvents.ORDERBOOK_DATA_CHANGED -> onOrderbookDataChanged(args)
                InputEvents.TRADE_CREATED -> onTradeCreated(args)
            }
        }

    }


    /**
     * An enumeration of all possible statuses this connection can have.
     */
    enum class Statuses {

        INVALID,
        SENT_CONNECTION_REQUEST,
        SENT_DISCONNECTION_REQUEST,
        CONNECTED,
        CONNECTION_ERROR,
        ERROR,
        DISCONNECTED,

    }


    /**
     * A singleton holding input events for the socket.
     */
    object InputEvents {

        // Socket related
        internal const val CONNECT = Socket.EVENT_CONNECT
        internal const val CONNECT_ERROR = Socket.EVENT_CONNECT_ERROR
        internal const val ERROR = Socket.EVENT_ERROR
        internal const val DISCONNECT = Socket.EVENT_DISCONNECT

        // Custom
        internal const val CURRENCY_MARKET_CHANGED = "App\\Events\\Ticker"
        internal const val PRICE_CHART_DATA_CHANGED = "App\\Events\\CandleChanged"
        internal const val ORDERBOOK_DATA_CHANGED = "App\\Events\\GlassRowChanged"
        internal const val TRADE_CREATED = "App\\Events\\OrderFillCreated"

    }


    /**
     * A singleton holding output (emitting) events for the socket.
     */
    object OutputEvents {

        internal const val SUBSCRIBE = "subscribe"
        internal const val UNSUBSCRIBE = "unsubscribe"

    }


    /**
     * A singleton holding output events data (keys and values).
     */
    object OutputEventsData {

        internal const val KEY_CHANNEL = "channel"
        internal const val KEY_AUTH = "auth"

        private const val VALUE_CHANNEL_RATE = "rate"
        private const val VALUE_CHANNEL_PREFIX_PRICE_CHART_DATA = "stats_data_"
        private const val VALUE_CHANNEL_PREFIX_ORDERBOOK_BID_DATA = "buy_data"
        private const val VALUE_CHANNEL_PREFIX_ORDERBOOK_ASK_DATA = "sell_data"
        private const val VALUE_CHANNEL_PREFIX_TRADE_HISTORY_DATA = "trade_c"


        internal fun isPriceChartDataChannel(channel: String, marketId: String): Boolean {
            return (channel.startsWith(VALUE_CHANNEL_PREFIX_PRICE_CHART_DATA) && channel.endsWith(marketId))
        }


        internal fun isOrderbookBidData(channel: String): Boolean {
            return channel.startsWith(VALUE_CHANNEL_PREFIX_ORDERBOOK_BID_DATA)
        }


        internal fun isOrderbookAskData(channel: String): Boolean {
            return channel.startsWith(VALUE_CHANNEL_PREFIX_ORDERBOOK_ASK_DATA)
        }


        internal fun getValueChannelRate(): String {
            return VALUE_CHANNEL_RATE
        }


        internal fun getValueChannelPriceChartData(interval: String, marketId: String): String {
            return (VALUE_CHANNEL_PREFIX_PRICE_CHART_DATA + interval + "_" + marketId)
        }


        internal fun getValueChannelOrderbookBidData(marketId: String): String {
            return (VALUE_CHANNEL_PREFIX_ORDERBOOK_BID_DATA + marketId)
        }


        internal fun getValueChannelOrderbookAskData(marketId: String): String {
            return (VALUE_CHANNEL_PREFIX_ORDERBOOK_ASK_DATA + marketId)
        }


        internal fun getValueChannelTradeHistoryData(marketId: String): String {
            return (VALUE_CHANNEL_PREFIX_TRADE_HISTORY_DATA + marketId)
        }

    }


}