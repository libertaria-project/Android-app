package com.stocksexchange.android.ui.currencymarketpreview

import com.stocksexchange.android.api.model.*
import com.stocksexchange.android.api.model.PriceChartParameters
import com.stocksexchange.android.api.model.CurrencyMarket
import com.stocksexchange.android.model.CurrencyMarketPreviewDataSources
import com.stocksexchange.android.model.CurrencyMarketPreviewDataSources.*
import com.stocksexchange.android.model.DataLoadingStates
import com.stocksexchange.android.model.DataTypes
import com.stocksexchange.android.model.utils.log
import com.stocksexchange.android.model.utils.onFailure
import com.stocksexchange.android.model.utils.onSuccess
import com.stocksexchange.android.repositories.pricechartdata.PriceChartDataRepository
import com.stocksexchange.android.repositories.currencymarkets.CurrencyMarketsRepository
import com.stocksexchange.android.repositories.orderbook.OrderbookRepository
import com.stocksexchange.android.repositories.trades.TradesRepository
import com.stocksexchange.android.ui.base.mvp.model.BaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import org.koin.standalone.inject

class CurrencyMarketPreviewModel : BaseModel() {


    var isDataLoadingMap: MutableMap<CurrencyMarketPreviewDataSources, Boolean>
        private set

    var isDataLoadingCancelledMap: MutableMap<CurrencyMarketPreviewDataSources, Boolean>
        private set

    private var mDataLoadingJobsMap: MutableMap<CurrencyMarketPreviewDataSources, Job?>

    private val mCurrencyMarketsRepository: CurrencyMarketsRepository by inject()
    private val mPriceChartDataRepository: PriceChartDataRepository by inject()
    private val mOrderbookRepository: OrderbookRepository by inject()
    private val mTradesRepository: TradesRepository by inject()

    private var mActionListener: ActionListener? = null




    init {
        isDataLoadingMap = mutableMapOf()
        isDataLoadingCancelledMap = mutableMapOf()
        mDataLoadingJobsMap = mutableMapOf()
    }


    override fun stop() {
        cancelAllDataLoading()

        super.stop()
    }


    fun cancelAllDataLoading() {
        for(dataSource in CurrencyMarketPreviewDataSources.values()) {
            cancelDataLoading(dataSource)
        }
    }


    fun cancelDataLoading(dataSource: CurrencyMarketPreviewDataSources): Boolean {
        return if(mDataLoadingJobsMap[dataSource]?.isActive == true) {
            isDataLoadingCancelledMap[dataSource] = true

            mDataLoadingJobsMap[dataSource]?.cancel()
            onDataLoadingEnded(dataSource)

            true
        } else {
            false
        }
    }


    fun cancelDataLoadingAndWait(dataSource: CurrencyMarketPreviewDataSources,
                                 block: () -> Unit) {
        if(cancelDataLoading(dataSource)) {
            mDataLoadingJobsMap[dataSource]?.invokeOnCompletion {
                block()
            }
        } else {
            block()
        }
    }


    private fun onDataLoadingStarted(dataSource: CurrencyMarketPreviewDataSources) {
        isDataLoadingMap[dataSource] = true

        mActionListener?.onDataLoadingStarted(dataSource)
        mActionListener?.onDataLoadingStateChanged(DataLoadingStates.ACTIVE, dataSource)
    }


    private fun onDataLoadingEnded(dataSource: CurrencyMarketPreviewDataSources) {
        isDataLoadingMap[dataSource] = false

        mActionListener?.onDataLoadingEnded(dataSource)
        mActionListener?.onDataLoadingStateChanged(DataLoadingStates.IDLE, dataSource)
    }


    private fun onDataLoadingSucceeded(data: Any, dataSource: CurrencyMarketPreviewDataSources) {
        if(isDataLoadingCancelledMap[dataSource] == true) {
            return
        }

        mActionListener?.onDataLoadingSucceeded(data, dataSource)
        onDataLoadingEnded(dataSource)
    }


    private fun onDataLoadingFailed(error: Throwable, dataSource: CurrencyMarketPreviewDataSources) {
        if(isDataLoadingCancelledMap[dataSource] == true) {
            return
        }

        onDataLoadingEnded(dataSource)
        mActionListener?.onDataLoadingFailed(error, dataSource)
    }


    fun loadData(dataType: DataTypes, parameters: Any,
                 dataSource: CurrencyMarketPreviewDataSources) {
        isDataLoadingCancelledMap[dataSource] = false

        if(dataType == DataTypes.NEW_DATA) {
            refreshData(parameters, dataSource)
        }

        mDataLoadingJobsMap[dataSource] = createBgLaunchCoroutine {
            val repositoryResult = when(dataSource) {

                PRICE_CHART -> mPriceChartDataRepository.get(parameters as PriceChartParameters).apply {
                    log("mPriceChartDataRepository.get()")
                }

                DEPTH_CHART, ORDERBOOK -> mOrderbookRepository.get(parameters as OrderbookParameters).apply {
                    log("mOrderbookRepository.get()")
                }

                TRADES -> mTradesRepository.get(parameters as TradeParameters).apply {
                    log("mTradesRepository.get()")
                }

            }

            withContext(Dispatchers.Main) {
                repositoryResult
                    .onSuccess { onDataLoadingSucceeded(it, dataSource) }
                    .onFailure { onDataLoadingFailed(it, dataSource) }
            }
        }

        onDataLoadingStarted(dataSource)
    }


    private fun refreshData(parameters: Any,
                            dataSource: CurrencyMarketPreviewDataSources) {
        when(dataSource) {
            PRICE_CHART -> mPriceChartDataRepository.refresh(parameters as PriceChartParameters)
            DEPTH_CHART, ORDERBOOK -> mOrderbookRepository.refresh(parameters as OrderbookParameters)
            TRADES -> mTradesRepository.refresh(parameters as TradeParameters)
        }
    }


    /**
     * Handles the favorite state of the specified currency market.
     *
     * @param currencyMarket The currency market to handle (either favorite
     * or unfavorite)
     */
    fun handleFavoriteAction(currencyMarket: CurrencyMarket) {
        createUiLaunchCoroutine {
            if(mCurrencyMarketsRepository.isCurrencyMarketFavorite(currencyMarket)) {
                unfavoriteCurrencyMarket(currencyMarket)
            } else {
                favoriteCurrencyMarket(currencyMarket)
            }
        }
    }


    private suspend fun unfavoriteCurrencyMarket(currencyMarket: CurrencyMarket) {
        mCurrencyMarketsRepository.unfavorite(currencyMarket)

        mActionListener?.onCurrencyMarketUnfavorited(currencyMarket)
    }


    private suspend fun favoriteCurrencyMarket(currencyMarket: CurrencyMarket) {
        mCurrencyMarketsRepository.favorite(currencyMarket)

        mActionListener?.onCurrencyMarketFavorited(currencyMarket)
    }


    /**
     * Sets the action listener.
     *
     * @param listener The listener to set
     */
    fun setActionListener(listener: ActionListener) {
        mActionListener = listener
    }


    interface ActionListener {

        fun onDataLoadingStarted(dataSource: CurrencyMarketPreviewDataSources)

        fun onDataLoadingEnded(dataSource: CurrencyMarketPreviewDataSources)

        fun onDataLoadingStateChanged(state: DataLoadingStates, dataSource: CurrencyMarketPreviewDataSources)

        fun onDataLoadingSucceeded(data: Any, dataSource: CurrencyMarketPreviewDataSources)

        fun onDataLoadingFailed(error: Throwable, dataSource: CurrencyMarketPreviewDataSources)

        fun onCurrencyMarketFavorited(currencyMarket: CurrencyMarket)

        fun onCurrencyMarketUnfavorited(currencyMarket: CurrencyMarket)

    }


}