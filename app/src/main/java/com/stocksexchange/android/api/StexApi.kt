package com.stocksexchange.android.api

import com.google.gson.Gson
import com.stocksexchange.android.api.exceptions.MissingCredentialsException
import com.stocksexchange.android.api.model.newapi.*
import com.stocksexchange.android.api.services.StexService
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.utils.handlers.CredentialsHandler
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

/**
 * An implementation of Stex API.
 */
object StexApi : KoinComponent {


    private val credentialsHandler: CredentialsHandler by inject()

    private val gson: Gson by inject()

    private val stexService: StexService by inject()




    /**
     * See [StexService.getCurrencies] for documentation.
     *
     * @return The result container holding currencies or an exception
     * if an error has occurred
     */
    fun getCurrencies(): Result<List<Currency>> {
        return stexService.getCurrencies().extractResultDirectly()
    }


    /**
     * See [StexService.getMarkets] for documentation.
     *
     * @return The result container holding markets or an exception
     * if an error has occurred
     */
    fun getMarkets(): Result<List<Market>> {
        return stexService.getMarkets().extractResultDirectly()
    }


    /**
     * See [StexService.getCurrencyPairs] for documentation.
     *
     * @return The result container holding currency pairds or an exception
     * if an error has occurred
     */
    fun getAllCurrencyPairs(): Result<List<CurrencyPair>> {
        return stexService
            .getCurrencyPairs(CurrencyPair.CURRENCY_PAIR_ALL_CODE)
            .extractResultDirectly()
    }


    /**
     * See [StexService.getTickerItems] for documentation.
     *
     * @return The result container holding ticker items or an exception
     * if an error has occurred
     */
    fun getTickerItems(): Result<List<TickerItem>> {
        return stexService.getTickerItems().extractResultDirectly()
    }


    /**
     * See [StexService.getTrades] for documentation.
     *
     * @param parameters The parameters for loading trades
     *
     * @return The result container holding trades or an exception
     * if an error has occurred
     */
    fun getTrades(parameters: TradeParameters): Result<List<Trade>> {
        return stexService.getTrades(
            currencyPairId = parameters.currencyPairId,
            sortOrder = parameters.sortOrder.name,
            count = parameters.count
        ).extractResultDirectly()
    }


    /**
     * See [StexService.getOrderbook] for documentation.
     *
     * @param parameters The parameters for loading orderbook
     *
     * @return The result container holding orderbook or an exception
     * if an error has occurred
     */
    fun getOrderbook(parameters: OrderbookParameters): Result<Orderbook> {
        return stexService.getOrderbook(
            currencyPairId = parameters.currencyPairId,
            bidOrdersCount = parameters.bidOrdersCount,
            askOrdersCount = parameters.askOrdersCount
        ).extractResultDirectly()
    }


    /**
     * See [StexService.getCandleSticks] for documentation.
     *
     * @param parameters The parameters for loading candle sticks
     *
     * @return The result container holding candle sticks or an exception
     * if an error has occurred
     */
    fun getCandleSticks(parameters: PriceChartDataParameters): Result<List<CandleStick>> {
        return stexService.getCandleSticks(
            currencyPairId = parameters.currencyPairId,
            interval = parameters.interval.interval,
            startTimestamp = parameters.startTimestamp,
            endTimestamp = parameters.endTimestamp,
            count = parameters.count
        ).extractResultDirectly()
    }


    /**
     * See [StexService.signIn] for documentation.
     *
     * @param parameters The parameters holding data for login
     *
     * @return The result container holding login response or an exception
     * if an error has occurred
     */
    fun signIn(parameters: SignInParameters): Result<SignInConfirmation> {
        return stexService.signIn(
            email = parameters.email,
            password = parameters.password,
            key = parameters.hashedKey
        ).extractSignInResponse(gson)
    }


    /**
     * See [StexService.confirmSignIn] for documentation.
     *
     * @param parameters The parameters holding data for login confirmation
     *
     * @return The result container holding OAuth credentials or an exception
     * if an error has occurred
     */
    fun confirmSignIn(parameters: SignInParameters): Result<SignInConfirmationResponse> {
        return stexService.confirmSignIn(
            code = parameters.code,
            key = parameters.key
        ).extractSignInConfirmationResponse(gson)
    }


    /**
     * See [StexService.signUp] for documentation.
     *
     * @param parameters The parameters holding data for registration
     *
     * @return The result container holding registration response or
     * an exception if an error has occurred
     */
    fun signUp(parameters: SignUpParameters): Result<SignUpResponse> {
        return stexService.signUp(
            email = parameters.email,
            password = parameters.password,
            confirmedPassword = parameters.password
        ).extractResultDirectly()
    }


    /**
     * See [StexService.getAllActiveOrders] for documentation.
     *
     * @return The result container holding active orders or an exception
     * if an error has occurred
     */
    fun getAllActiveOrders(): Result<List<Order>> {
        val credentials = if(credentialsHandler.hasOAuthCredentials()) {
            credentialsHandler.getCredentials()
        } else {
            return Result.Failure(MissingCredentialsException())
        }

        return stexService.getAllActiveOrders(
            authorizationHeader = credentials.authorizationHeader
        ).extractResultDirectly()
    }


    /**
     * See [StexService.getActiveOrders] for documentation.
     *
     * @param currencyPairId The ID of the currency to retrieve active orders for
     *
     * @return The result container holding active orders or an exception
     * if an error has occurred.
     */
    fun getActiveOrders(currencyPairId: Int): Result<List<Order>> {
        val credentials = if(credentialsHandler.hasOAuthCredentials()) {
            credentialsHandler.getCredentials()
        } else {
            return Result.Failure(MissingCredentialsException())
        }

        return stexService.getActiveOrders(
            authorizationHeader = credentials.authorizationHeader,
            currencyPairId =  currencyPairId
        ).extractResultDirectly()
    }


    /**
     * See [StexService.getActiveOrder] for documentation.
     *
     * @param orderId The ID of the order to retrieve
     *
     * @return The result container holding active order or an exception
     * if an error has occurred.
     */
    fun getActiveOrder(orderId: Long): Result<Order> {
        val credentials = if(credentialsHandler.hasOAuthCredentials()) {
            credentialsHandler.getCredentials()
        } else {
            return Result.Failure(MissingCredentialsException())
        }

        return stexService.getActiveOrder(
            authorizationHeader = credentials.authorizationHeader,
            orderId = orderId
        ).extractResultDirectly()
    }


    /**
     * See [StexService.cancelAllActiveOrders] for documentation.
     *
     * @return The result container holding response of cancelling active
     * orders or an exception if an error has occurred
     */
    fun cancelAllActiveOrders(): Result<OrdersCancellationResponse> {
        val credentials = if(credentialsHandler.hasOAuthCredentials()) {
            credentialsHandler.getCredentials()
        } else {
            return Result.Failure(MissingCredentialsException())
        }

        return stexService.cancelAllActiveOrders(
            authorizationHeader = credentials.authorizationHeader
        ).extractResultDirectly()
    }


    /**
     * See [StexService.cancelActiveOrders] for documentation.
     *
     * @param currencyPairId The ID of the currency pair to cancel
     * active orders with
     *
     * @return The result container holding response of cancelling active
     * orders or an exception if an error has occurred
     */
    fun cancelActiveOrders(currencyPairId: Int): Result<OrdersCancellationResponse> {
        val credentials = if(credentialsHandler.hasOAuthCredentials()) {
            credentialsHandler.getCredentials()
        } else {
            return Result.Failure(MissingCredentialsException())
        }

        return stexService.cancelActiveOrders(
            authorizationHeader = credentials.authorizationHeader,
            currencyPairId = currencyPairId
        ).extractResultDirectly()
    }


    /**
     * See [StexService.cancelActiveOrder] for documentation.
     *
     * @param order The order to cancel
     *
     * @return The result container holding response of cancelling active
     * order or an exception if an error has occurred
     */
    fun cancelActiveOrder(order: Order): Result<OrdersCancellationResponse> {
        val credentials = if(credentialsHandler.hasOAuthCredentials()) {
            credentialsHandler.getCredentials()
        } else {
            return Result.Failure(MissingCredentialsException())
        }

        return stexService.cancelActiveOrder(
            authorizationHeader = credentials.authorizationHeader,
            orderId = order.id
        ).extractResultDirectly()
    }


    /**
     * See [StexService.createOrder] for documentation.
     *
     * @param parameters The parameters for creating an order
     *
     * @return The result container holding a result of order creation or an
     * exception if an error has occurred
     */
    fun createOrder(parameters: OrderCreationParameters): Result<Order> {
        val credentials = if(credentialsHandler.hasOAuthCredentials()) {
            credentialsHandler.getCredentials()
        } else {
            return Result.Failure(MissingCredentialsException())
        }

        return stexService.createOrder(
            authorizationHeader = credentials.authorizationHeader,
            currencyPairId = parameters.currencyPairId,
            type = parameters.type.name,
            amount = parameters.amount,
            price = parameters.price
        ).extractResultDirectly()
    }


    /**
     * See [StexService.getHistoryOrders] for documentation.
     *
     * @param parameters The parameters for loading orders
     *
     * @return The result container holding history orders or an exception
     * if an error has occurred.
     */
    fun getHistoryOrders(parameters: OrderParameters): Result<List<Order>> {
        val credentials = if(credentialsHandler.hasOAuthCredentials()) {
            credentialsHandler.getCredentials()
        } else {
            return Result.Failure(MissingCredentialsException())
        }

        return stexService.getHistoryOrders(
            authorizationHeader = credentials.authorizationHeader,
            status = parameters.status.name,
            limit = parameters.limit,
            offset = parameters.offset
        ).extractResultDirectly()
    }


    /**
     * See [StexService.getHistoryOrder] for documentation.
     *
     * @param orderId The ID of the order to retrieve
     *
     * @return The result container holding history order or an exception
     * if an error has occurred.
     */
    fun getHistoryOrder(orderId: Long): Result<Order> {
        val credentials = if(credentialsHandler.hasOAuthCredentials()) {
            credentialsHandler.getCredentials()
        } else {
            return Result.Failure(MissingCredentialsException())
        }

        return stexService.getHistoryOrder(
            authorizationHeader = credentials.authorizationHeader,
            orderId = orderId
        ).extractResultDirectly()
    }


    /**
     * See [StexService.getProfileInfo] for documentation.
     *
     * @return The result container holding profile info or an exception
     * if an error has occurred.
     */
    fun getProfileInfo(): Result<ProfileInfo> {
        val credentials = if(credentialsHandler.hasOAuthCredentials()) {
            credentialsHandler.getCredentials()
        } else {
            return Result.Failure(MissingCredentialsException())
        }

        return stexService.getProfileInfo(
            authorizationHeader = credentials.authorizationHeader
        ).extractResultDirectly()
    }


    /**
     * See [StexService.getWallets] for documentation.
     *
     * @param parameters The parameters for loading wallets
     *
     * @return The result container holding wallets or an exception
     * if an error has occurred
     */
    fun getWallets(parameters: WalletParameters): Result<List<Wallet>> {
        val credentials = if(credentialsHandler.hasOAuthCredentials()) {
            credentialsHandler.getCredentials()
        } else {
            return Result.Failure(MissingCredentialsException())
        }

        return stexService.getWallets(
            authorizationHeader = credentials.authorizationHeader,
            sortOrder = parameters.sortOrder.name,
            sortColumn = parameters.sortColumn.type
        ).extractResultDirectly()
    }


    /**
     * See [StexService.getWallet] for documentation.
     *
     * @param walletId The ID of the wallet to fetch
     *
     * @return The result container holding wallet or an exception
     * if an error has occurred
     */
    fun getWallet(walletId: Long): Result<Wallet> {
        val credentials = if(credentialsHandler.hasOAuthCredentials()) {
            credentialsHandler.getCredentials()
        } else {
            return Result.Failure(MissingCredentialsException())
        }

        return stexService.getWallet(
            authorizationHeader = credentials.authorizationHeader,
            walletId = walletId
        ).extractResultDirectly()
    }


    /**
     * See [StexService.createWallet] for documentation.
     *
     * @param currencyId The ID of the currency to create a wallet for
     *
     * @return The result container holding wallet or an exception
     * if an error has occurred
     */
    fun createWallet(currencyId: Int): Result<Wallet> {
        val credentials = if(credentialsHandler.hasOAuthCredentials()) {
            credentialsHandler.getCredentials()
        } else {
            return Result.Failure(MissingCredentialsException())
        }

        return stexService.createWallet(
            authorizationHeader = credentials.authorizationHeader,
            currencyId = currencyId
        ).extractResultDirectly()
    }


    /**
     * See [StexService.getDepositAddress] for documentation.
     *
     * @param walletId The ID of the wallet to return the deposit address of
     *
     * @return The result container holding deposit address or an exception
     * if an error has occurred
     */
    fun getDepositAddress(walletId: Long): Result<TransactionAddress> {
        val credentials = if(credentialsHandler.hasOAuthCredentials()) {
            credentialsHandler.getCredentials()
        } else {
            return Result.Failure(MissingCredentialsException())
        }

        return stexService.getDepositAddress(
            authorizationHeader = credentials.authorizationHeader,
            walletId = walletId
        ).extractResultDirectly()
    }


    /**
     * See [StexService.createDepositAddress] for documentation.
     *
     * @param walletId The ID of the wallet to create or recreate a deposit
     * address of
     *
     * @return The result container holding creating or recreating
     * a deposit address or an exception if an error has occurred
     */
    fun createDepositAddress(walletId: Long): Result<TransactionAddress> {
        val credentials = if(credentialsHandler.hasOAuthCredentials()) {
            credentialsHandler.getCredentials()
        } else {
            return Result.Failure(MissingCredentialsException())
        }

        return stexService.createDepositAddress(
            authorizationHeader = credentials.authorizationHeader,
            walletId = walletId
        ).extractResultDirectly()
    }


    /**
     * See [StexService.getDeposits] for documentation.
     *
     * @param parameters The parameters for loading deposits
     *
     * @return The result container holding deposits or an exception
     * if an error has occurred
     */
    fun getDeposits(parameters: TransactionParameters): Result<List<Deposit>> {
        val credentials = if(credentialsHandler.hasOAuthCredentials()) {
            credentialsHandler.getCredentials()
        } else {
            return Result.Failure(MissingCredentialsException())
        }

        return stexService.getDeposits(
            authorizationHeader = credentials.authorizationHeader,
            sortOrder = parameters.sortOrder.name,
            limit = parameters.limit,
            offset = parameters.offset
        ).extractResultDirectly()
    }


    /**
     * See [StexService.getDeposit] for documentation.
     *
     * @param depositId The ID of the deposit to fetch
     *
     * @return The result container holding deposit or an exception
     * if an error has occurred
     */
    fun getDeposit(depositId: Long): Result<Deposit> {
        val credentials = if(credentialsHandler.hasOAuthCredentials()) {
            credentialsHandler.getCredentials()
        } else {
            return Result.Failure(MissingCredentialsException())
        }

        return stexService.getDeposit(
            authorizationHeader = credentials.authorizationHeader,
            depositId = depositId
        ).extractResultDirectly()
    }


    /**
     * See [StexService.getWithdrawals] for documentation.
     *
     * @param parameters The parameters for loading withdrawals
     *
     * @return The result container holding withdrawals or an exception
     * if an error has occurred
     */
    fun getWithdrawals(parameters: TransactionParameters): Result<List<Withdrawal>> {
        val credentials = if(credentialsHandler.hasOAuthCredentials()) {
            credentialsHandler.getCredentials()
        } else {
            return Result.Failure(MissingCredentialsException())
        }

        return stexService.getWithdrawals(
            authorizationHeader = credentials.authorizationHeader,
            sortOrder = parameters.sortOrder.name,
            limit = parameters.limit,
            offset = parameters.offset
        ).extractResultDirectly()
    }


    /**
     * See [StexService.getWithdrawal] for documentation.
     *
     * @param withdrawalId The ID of the withdrawal to fetch
     *
     * @return The result container holding withdrawal or an exception
     * if an error has occurred
     */
    fun getWithdrawal(withdrawalId: Long): Result<Withdrawal> {
        val credentials = if(credentialsHandler.hasOAuthCredentials()) {
            credentialsHandler.getCredentials()
        } else {
            return Result.Failure(MissingCredentialsException())
        }

        return stexService.getWithdrawal(
            authorizationHeader = credentials.authorizationHeader,
            withdrawalId = withdrawalId
        ).extractResultDirectly()
    }


    /**
     * See [StexService.createWithdrawal] for documentation.
     *
     * @param parameters The parameters for creating a withdrawal
     *
     * @return The result container holding a newly created withdrawal
     * or an exception if an error has occurred
     */
    fun createWithdrawal(parameters: WithdrawalCreationParameters): Result<Withdrawal> {
        val credentials = if(credentialsHandler.hasOAuthCredentials()) {
            credentialsHandler.getCredentials()
        } else {
            return Result.Failure(MissingCredentialsException())
        }

        return stexService.createWithdrawal(
            authorizationHeader = credentials.authorizationHeader,
            currencyId = parameters.currencyId,
            amount = parameters.amount,
            address = parameters.address,
            additionalAddress = parameters.additionalAddress
        ).extractResultDirectly()
    }


    /**
     * See [StexService.cancelWithdrawal] for documentation.
     *
     * @param withdrawalId The ID of the withdrawal to cancel
     *
     * @return The result container holding a result of cancelling a
     * withdrawal or an exception if an error has occurred
     */
    fun cancelWithdrawal(withdrawalId: Long): Result<String> {
        val credentials = if(credentialsHandler.hasOAuthCredentials()) {
            credentialsHandler.getCredentials()
        } else {
            return Result.Failure(MissingCredentialsException())
        }

        return stexService.cancelWithdrawal(
            authorizationHeader = credentials.authorizationHeader,
            withdrawalId = withdrawalId
        ).extractResultDirectly()
    }


}