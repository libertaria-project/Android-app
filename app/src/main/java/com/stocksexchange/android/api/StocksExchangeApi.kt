package com.stocksexchange.android.api

import com.stocksexchange.android.api.exceptions.*
import com.stocksexchange.android.api.model.*
import com.stocksexchange.android.api.services.StocksExchangeService
import com.stocksexchange.android.api.utils.*
import com.stocksexchange.android.mappings.mapToOrderList
import com.stocksexchange.android.mappings.mapToTransactionList
import com.stocksexchange.android.model.*
import com.stocksexchange.android.utils.handlers.CredentialsHandler
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

/**
 * An implementation of StocksExchange API.
 */
object StocksExchangeApi : KoinComponent {


    private val credentialsHandler: CredentialsHandler by inject()

    private val stocksExchangeService: StocksExchangeService by inject()




    /**
     * Retrieves information about a signed-in user.
     *
     * @return The container holding a user object
     * or an exception if an error has occurred
     */
    fun getUser(): Result<User> {
        if(!credentialsHandler.hasCredentials()) {
            return Result.Failure(MissingCredentialsException())
        }

        val params = mapOf(
            PrivateApiParameters.METHOD.name to PrivateApiMethods.GET_INFO.methodName,
            PrivateApiParameters.NONCE.name to generateNonce()
        )

        return stocksExchangeService.getUser(
            getPublicKey(),
            getSignature(params),
            params[PrivateApiParameters.METHOD.name]!!,
            params[PrivateApiParameters.NONCE.name]!!
        ).extractResultDirectly {
            when(it.error) {
                ApiErrors.INVALID_CREDENTIALS.message -> InvalidCredentialsException()

                else -> ApiException(it.toString())
            }
        }
    }


    /**
     * Creates an order with the specified parameters.
     *
     * @param parameters The parameters for order creation
     *
     * @return The container holding an order creation response
     * or an exception of an error has occurred
     */
    fun createOrder(parameters: OrderCreationParameters): Result<OrderResponse> {
        if(!credentialsHandler.hasCredentials()) {
            return Result.Failure(MissingCredentialsException())
        }

        val params = mapOf(
            PrivateApiParameters.METHOD.name to PrivateApiMethods.TRADE.methodName,
            PrivateApiParameters.NONCE.name to generateNonce(),
            PrivateApiParameters.PAIR.name to parameters.marketName,
            PrivateApiParameters.TYPE.name to parameters.type,
            PrivateApiParameters.AMOUNT.name to parameters.amount,
            PrivateApiParameters.RATE.name to parameters.rate
        )

        return stocksExchangeService.createOrder(
            getPublicKey(),
            getSignature(params),
            params[PrivateApiParameters.METHOD.name]!!,
            params[PrivateApiParameters.NONCE.name]!!,
            parameters.marketName,
            parameters.type,
            parameters.amount,
            parameters.rate
        ).extractResultDirectly {
            when(it.error) {
                ApiErrors.AMOUNT_TOO_SMALL.message -> AmountTooSmallException()
                ApiErrors.MAX_NUM_OF_OPEN_ORDERS.message -> MaxNumOfOpenOrdersException()

                else -> ApiException(it.toString())
            }
        }
    }


    /**
     * Cancels an order with the specified order ID.
     *
     * @param orderId The ID of the order to cancel
     *
     * @return The container holding an order cancellation response
     * or an exception if an error has occurred
     */
    fun cancelOrder(orderId: Long): Result<OrderResponse> {
        if(!credentialsHandler.hasCredentials()) {
            return Result.Failure(MissingCredentialsException())
        }

        val params = mapOf(
            PrivateApiParameters.METHOD.name to PrivateApiMethods.CANCEL_ORDER.methodName,
            PrivateApiParameters.NONCE.name to generateNonce(),
            PrivateApiParameters.ORDER_ID.name to orderId.toString()
        )

        return stocksExchangeService.cancelOrder(
            getPublicKey(),
            getSignature(params),
            params[PrivateApiParameters.METHOD.name]!!,
            params[PrivateApiParameters.NONCE.name]!!,
            orderId
        ).extractResultDirectly()
    }


    /**
     * Retrieves active orders of the signed-in user.
     *
     * @param parameters The parameters for loading active orders
     *
     * @return The container holding active orders or an exception
     * if an error has occurred
     */
    fun getActiveOrders(parameters: OrderParameters): Result<List<Order>> {
        if(!credentialsHandler.hasCredentials()) {
            return Result.Failure(MissingCredentialsException())
        }

        val params = mapOf(
            PrivateApiParameters.METHOD.name to PrivateApiMethods.ACTIVE_ORDERS.methodName,
            PrivateApiParameters.NONCE.name to generateNonce(),
            PrivateApiParameters.PAIR.name to parameters.marketName,
            PrivateApiParameters.TYPE.name to parameters.tradeType.name,
            PrivateApiParameters.OWNER.name to parameters.ownerType.name,
            PrivateApiParameters.ORDER.name to parameters.sortType.name,
            PrivateApiParameters.COUNT.name to parameters.count.toString()
        )

        return stocksExchangeService.getActiveOrders(
            getPublicKey(),
            getSignature(params),
            params[PrivateApiParameters.METHOD.name]!!,
            params[PrivateApiParameters.NONCE.name]!!,
            parameters.marketName,
            parameters.tradeType.name,
            parameters.ownerType.name,
            parameters.sortType.name,
            parameters.count
        ).extractResult { it.mapToOrderList(parameters.type.name) }
    }


    /**
     * Retrieves history (cancelled and completed) orders of the signed-in user.
     *
     * @param parameters The parameters for loading history orders
     *
     * @return The container holding history orders or an exception if
     * an error has occurred
     */
    fun getHistoryOrders(parameters: OrderParameters): Result<List<Order>> {
        if(!credentialsHandler.hasCredentials()) {
            return Result.Failure(MissingCredentialsException())
        }

        val params = mapOf(
            PrivateApiParameters.METHOD.name to PrivateApiMethods.TRADE_HISTORY.methodName,
            PrivateApiParameters.NONCE.name to generateNonce(),
            PrivateApiParameters.PAIR.name to parameters.marketName,
            PrivateApiParameters.STATUS.name to parameters.status.id.toString(),
            PrivateApiParameters.OWNER.name to parameters.ownerType.name,
            PrivateApiParameters.ORDER.name to parameters.sortType.name,
            PrivateApiParameters.COUNT.name to parameters.count.toString()
        )

        return stocksExchangeService.getHistoryOrders(
            getPublicKey(),
            getSignature(params),
            params[PrivateApiParameters.METHOD.name]!!,
            params[PrivateApiParameters.NONCE.name]!!,
            parameters.marketName,
            parameters.status.id,
            parameters.ownerType.name,
            parameters.sortType.name,
            parameters.count
        ).extractResult { it.mapToOrderList(parameters.type.name) }
    }


    /**
     * Retrieves transactions (deposits and withdrawals) of the signed-in user.
     *
     * @param parameters The parameters for loading transactions
     *
     * @return The container holding transactions or an exception if an
     * error has occurred
     */
    fun getTransactions(parameters: TransactionParameters): Result<List<Transaction>> {
        if(!credentialsHandler.hasCredentials()) {
            return Result.Failure(MissingCredentialsException())
        }

        val params = mapOf(
            PrivateApiParameters.METHOD.name to PrivateApiMethods.TRANSACTION_HISTORY.methodName,
            PrivateApiParameters.NONCE.name to generateNonce(),
            PrivateApiParameters.CURRENCY.name to parameters.currency,
            PrivateApiParameters.OPERATION.name to parameters.operation.name,
            PrivateApiParameters.STATUS.name to parameters.status.name,
            PrivateApiParameters.ORDER.name to parameters.sortType.name,
            PrivateApiParameters.COUNT.name to parameters.count.toString()
        )

        return stocksExchangeService.getTransactions(
            getPublicKey(),
            getSignature(params),
            params[PrivateApiParameters.METHOD.name]!!,
            params[PrivateApiParameters.NONCE.name]!!,
            parameters.currency,
            parameters.operation.name,
            parameters.status.name,
            parameters.sortType.name,
            parameters.count
        ).extractResult { it.mapToTransactionList(parameters.type.name) }
    }


    /**
     * Retrieves a deposit for a specific currency.
     *
     * @param parameters The parameters for loading deposit data
     *
     * @return The container holding a deposit or an exception
     * if an error has occurred
     */
    fun getDeposit(parameters: DepositParameters): Result<Deposit> {
        if(!credentialsHandler.hasCredentials()) {
            return Result.Failure(MissingCredentialsException())
        }

        val params = mapOf(
            PrivateApiParameters.METHOD.name to PrivateApiMethods.DEPOSIT.methodName,
            PrivateApiParameters.NONCE.name to generateNonce(),
            PrivateApiParameters.CURRENCY.name to parameters.currency
        )

        return stocksExchangeService.getDeposit(
            getPublicKey(),
            getSignature(params),
            params[PrivateApiParameters.METHOD.name]!!,
            params[PrivateApiParameters.NONCE.name]!!,
            parameters.currency
        ).extractResultDirectly {
            when(it.error) {
                ApiErrors.NO_WALLET.message, ApiErrors.NO_WALLET_ADDRESS.message -> NoWalletAddressException()
                ApiErrors.INVALID_CURRENCY_CODE.message -> InvalidCurrencyCodeException()

                else -> ApiException(it.toString())
            }
        }
    }


    /**
     * Generates a wallet address for the specific currency.
     *
     * @param parameters The parameters for generating a wallet address
     *
     * @return The container holding a deposit or an exception
     * if an error has occurred
     */
    fun generateWalletAddress(parameters: DepositParameters): Result<Deposit> {
        if(!credentialsHandler.hasCredentials()) {
            return Result.Failure(MissingCredentialsException())
        }

        val params = mapOf(
            PrivateApiParameters.METHOD.name to PrivateApiMethods.GENERATE_WALLET.methodName,
            PrivateApiParameters.NONCE.name to generateNonce(),
            PrivateApiParameters.CURRENCY.name to parameters.currency
        )

        return stocksExchangeService.generateWalletAddress(
            getPublicKey(),
            getSignature(params),
            params[PrivateApiParameters.METHOD.name]!!,
            params[PrivateApiParameters.NONCE.name]!!,
            parameters.currency
        ).extractResultDirectly {
            when(it.error) {
                ApiErrors.CURRENCY_DISABLED.message -> CurrencyDisabledException()

                else -> ApiException(it.toString())
            }
        }
    }


    /**
     * Retrieves a list of currency markets.
     *
     * @return The container holding a list of currency markets
     * or an exception if an error has occurred
     */
    fun getCurrencyMarkets(): Result<List<CurrencyMarket>> {
        return stocksExchangeService
            .getCurrencyMarkets()
            .toResult()
    }


    /**
     * Retrieves price chart data for the specific parameters.
     *
     * @param parameters The parameters for loading price chart data
     *
     * @return The container holding price chart data or an exception
     * if an error has occurred
     */
    fun getPriceChartData(parameters: PriceChartParameters): Result<PriceChartData> {
        return stocksExchangeService.getPriceChartData(
            parameters.marketName,
            parameters.interval.intervalName,
            parameters.sortType.name,
            parameters.count,
            parameters.page
        ).extractResultDirectly()
    }


    /**
     * Retrieves orderbook for the specific currency market.
     *
     * @param parameters The parameters for loading orderbook
     *
     * @return The container holding orderbook data or an exception
     * if an error has occurred
     */
    fun getOrderbook(parameters: OrderbookParameters): Result<Orderbook> {
        return stocksExchangeService.getOrderbook(
            parameters.marketName
        ).extractResultDirectly()
    }


    /**
     * Retrieves trade history (trades) for the specific market.
     *
     * @param parameters The parameters for loading trades
     *
     * @return The container holding trades or an exception if an
     * error has occurred
     */
    fun getTrades(parameters: TradeParameters): Result<List<Trade>> {
        return stocksExchangeService.getTrades(
            parameters.marketName,
            parameters.count
        ).extractResultDirectly()
    }


    /**
     * Retrieves a list of currencies.
     *
     * @return The container holding a list of currencies
     * or an exception of an error has occurred
     */
    fun getCurrencies(): Result<List<Currency>> {
        return stocksExchangeService
            .getCurrencies()
            .toResult()
    }


    private fun getSignature(params: Map<String, String?>): String {
        return generateSignature(getSecretKey(), params)
    }


    private fun getPublicKey(): String {
        return credentialsHandler.getPublicKey()
            .takeIf { it.isNotBlank() }
            ?: throw IllegalArgumentException("The public key is empty.")
    }


    private fun getSecretKey(): String {
        return credentialsHandler.getSecretKey()
            .takeIf { it.isNotBlank() }
            ?: throw IllegalArgumentException("The secret key is empty.")
    }


}