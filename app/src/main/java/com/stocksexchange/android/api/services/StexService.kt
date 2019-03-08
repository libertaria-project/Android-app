package com.stocksexchange.android.api.services

import com.stocksexchange.android.api.model.newapi.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

/**
 * A service for a STEX API.
 */
interface StexService {


    /**
     *
     * PUBLIC ENDPOINTS
     *
     */


    /**
     * Retrieves all available currencies.
     */
    @GET("public/currencies")
    fun getCurrencies(): Call<ApiResponse<List<Currency>>>


    /**
     * Retrieves all available markets.
     */
    @GET("public/markets")
    fun getMarkets(): Call<ApiResponse<List<Market>>>


    /**
     * Retrieves currency pairs.
     *
     * @param code The code of the currency pairs to retrieve.
     * Can be "ALL" to retrieve all pairs or one of the values
     * returned by the [getMarkets] method.
     */
    @GET("public/currency_pairs/list/{code}")
    fun getCurrencyPairs(
        @Path("code") code: String
    ) : Call<ApiResponse<List<CurrencyPair>>>


    /**
     * Retrieves ticker of the coins at the moment of making this request.
     */
    @GET("public/ticker")
    fun getTickerItems(): Call<ApiResponse<List<TickerItem>>>


    /**
     * Retrieves trades of the particular currency pair.
     *
     * @param currencyPairId The ID of the currency pair to retrieve the trades for.
     * @param sortOrder The order of the sort. The fetched trades are sorted
     * based on the timestamp. Two possible values to use for this parameter:
     * ASC (ascending) or DESC (descending) order. Default value is DESC.
     * @param count The count of the trades to retrieve. Default value is 100.
     */
    @GET("public/trades/{currencyPairId}")
    fun getTrades(
        @Path("currencyPairId") currencyPairId: Int,
        @Query("sort") sortOrder: String,
        @Query("limit") count: Int
    ) : Call<ApiResponse<List<Trade>>>


    /**
     * Retrieves orderbook of the particular currency pair.
     *
     * @param currencyPairId The ID of the currency pair to retrieve
     * the orderbook for.
     * @param bidOrdersCount The count of the bid orders to retrieve.
     * Default value is 100.
     * @param askOrdersCount The count of the ask orders to retrieve.
     * Default value is 100.
     */
    @GET("public/orderbook/{currencyPairId}")
    fun getOrderbook(
        @Path("currencyPairId") currencyPairId: Int,
        @Query("limit_bids") bidOrdersCount: Int,
        @Query("limit_asks") askOrdersCount: Int
    ) : Call<ApiResponse<Orderbook>>


    /**
     * Retrieves candle sticks of the particular currency pair.
     *
     * @param currencyPairId The ID of the currency pair to retrieve
     * the candle sticks for
     * @param interval The interval of the candle sticks to fetch.
     * Can be one of the following constants:
     * - 1 (1 minute)
     * - 5 (5 minutes)
     * - 30 (30 minutes)
     * - 60 (60 minutes)
     * - 240 (240 minutes or 4 hours)
     * - 720 (720 minutes or 12 hours)
     * - 1D (1 day)
     * Default value is 1D.
     * @param startTimestamp The start timestamp (in milliseconds) of the
     * period we want to retrieve the candle sticks for
     * @param endTimestamp The end timestamp (in milliseconds) of the
     * period we want to retrieve the candle sticks for
     * @param count The count of the candle sticks to retrieve.
     * Default value is 100.
     */
    @GET("public/chart/{currencyPairId}/{candlesType}")
    fun getCandleSticks(
        @Path("currencyPairId") currencyPairId: Int,
        @Path("candlesType") interval: String,
        @Query("timeStart") startTimestamp: Long,
        @Query("timeEnd") endTimestamp: Long,
        @Query("limit") count: Int
    ) : Call<ApiResponse<List<CandleStick>>>


    /**
     * Performs a first step of the sign in process: sending the user's credentials.
     * If the credentials are valid, the next step is to send a security code
     * retrieved in one of the possible ways (see [SignInConfirmationTypes] for
     * possible ways to retrieve a security code).
     *
     * @param email The email of the user trying to sign in
     * @param password The password of the user trying to sign in
     * @param key The key the purpose of which is to denote the sign in process.
     * As mentioned above that the sign in is a two-step process, this key parameter
     * will be used later when performing a second step using [confirmSignIn] method.
     * The key itself should be a random nonce encrypted using SHA-1 hash function.
     */
    @FormUrlEncoded
    @POST("signin")
    fun signIn(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("key") key: String
    ) : Call<ResponseBody>


    /**
     * Performs a second step of the sign in process: providing a security code
     * received by one of the ways specified by in [SignInConfirmationTypes] class.
     * If everything is performed successfully, the response of this method
     * should return OAuth related tokens used for authentication in future.
     *
     * @param code The security code received by one of the ways specified in
     * [SignInConfirmationTypes] class
     * @param key The key used in the first step of the sign in process.
     * Unlike in the first step, the key provided for this method should
     * be in plaintext that was used and transformed by SHA-1 hash function
     * in the first step
     */
    @FormUrlEncoded
    @POST("confirm_signin")
    fun confirmSignIn(
        @Field("code") code: String,
        @Field("key") key: String
    ) : Call<ResponseBody>


    /**
     * Registers a new user.
     *
     * @param email The email of the user who wants to register
     * @param password The password of the user who wants to register. Must be
     * between 6 and 32 characters.
     * @param confirmedPassword The password one more time. Must be between
     * 6 and 32 characters.
     */
    @FormUrlEncoded
    @POST("signup")
    fun signUp(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("password_confirmation") confirmedPassword: String
    ) : Call<ApiResponse<SignUpResponse>>


    /**
     *
     * PRIVATE ENDPOINTS
     *
     */


    /**
     * Retrieves all active orders.
     *
     * @param authorizationHeader The authorization header
     */
    @GET("trading/orders")
    fun getAllActiveOrders(
        @Header("Authorization") authorizationHeader: String
    ) : Call<ApiResponse<List<Order>>>


    /**
     * Retrieves active orders of the particular currency pair.
     *
     * @param authorizationHeader The authorization header
     * @param currencyPairId The ID of the currency pair to return the active
     * orders for
     */
    @GET("trading/orders/{currencyPairId}")
    fun getActiveOrders(
        @Header("Authorization") authorizationHeader: String,
        @Path("currencyPairId") currencyPairId: Int
    ) : Call<ApiResponse<List<Order>>>


    /**
     * Retrieves an active order by its ID.
     *
     * @param authorizationHeader The authorization header
     * @param orderId The ID of the order
     */
    @GET("trading/order/{orderId}")
    fun getActiveOrder(
        @Header("Authorization") authorizationHeader: String,
        @Path("orderId") orderId: Long
    ) : Call<ApiResponse<Order>>


    /**
     * Cancels all active orders.
     *
     * @param authorizationHeader The authorization header
     */
    @DELETE("trading/orders")
    fun cancelAllActiveOrders(
        @Header("Authorization") authorizationHeader: String
    ) : Call<ApiResponse<OrdersCancellationResponse>>


    /**
     * Cancels active orders of the particular currency pair.
     *
     * @param authorizationHeader The authorization header
     * @param currencyPairId The ID of the currency pair to cancel
     * active orders with
     */
    @DELETE("trading/orders/{currencyPairId}")
    fun cancelActiveOrders(
        @Header("Authorization") authorizationHeader: String,
        @Path("currencyPairId") currencyPairId: Int
    ) : Call<ApiResponse<OrdersCancellationResponse>>


    /**
     * Cancels an active order by its ID.
     *
     * @param authorizationHeader The authorization header
     * @param orderId The ID of the order to cancel
     */
    @DELETE("trading/orders/{orderId}")
    fun cancelActiveOrder(
        @Header("Authorization") authorizationHeader: String,
        @Path("orderId") orderId: Long
    ) : Call<ApiResponse<OrdersCancellationResponse>>


    /**
     * Creates an order.
     *
     * @param authorizationHeader The authorization header
     * @param currencyPairId The ID of the currency pair to create an
     * order for
     * @param type The type of the order. Either "BUY" or "SELL".
     * @param amount The amount to buy or sell
     * @param price The price to buy or sell at
     */
    @FormUrlEncoded
    @DELETE("trading/orders/{currencyPairId}")
    fun createOrder(
        @Header("Authorization") authorizationHeader: String,
        @Path("currencyPairId") currencyPairId: Int,
        @Field("type") type: String,
        @Field("amount") amount: String,
        @Field("price") price: String
    ) : Call<ApiResponse<Order>>


    /**
     * Retrieves history orders.
     *
     * @param authorizationHeader The authorization header
     * @param status The status of the history orders. Can be one of the following:
     * [OrderStatuses.ALL], [OrderStatuses.FINISHED], [OrderStatuses.CANCELED],
     * [OrderStatuses.PARTIAL].
     * @param limit The count of the orders to fetch. Default is 100.
     * @param offset The offset of the orders data set to fetch items from
     */
    @GET("reports/order")
    fun getHistoryOrders(
        @Header("Authorization") authorizationHeader: String,
        @Query("orderStatus") status: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ) : Call<ApiResponse<List<Order>>>


    /**
     * Retrieves a history order by its ID.
     *
     * @param authorizationHeader The authorization header
     * @param orderId The ID of the order to retrieve
     */
    @GET("reports/order/{orderId}")
    fun getHistoryOrder(
        @Header("Authorization") authorizationHeader: String,
        @Path("orderId") orderId: Long
    ) : Call<ApiResponse<Order>>


    /**
     * Retrieves profile information of the user.
     *
     * @param authorizationHeader The authorization header
     */
    @GET("profile/info")
    fun getProfileInfo(
        @Header("Authorization") authorizationHeader: String
    ) : Call<ApiResponse<ProfileInfo>>


    /**
     * Retrieves user's wallets.
     *
     * @param authorizationHeader The authorization header
     * @param sortOrder The order of the sort. Two possible values to use
     * for this parameter: ASC (ascending) or DESC (descending) order.
     * Default value is DESC.
     * @param sortBy The column by which to sort the data set.
     * The value can be one of the following: "BALANCE", "FROZEN",
     * "BONUS", or "TOTAL". Default is "BALANCE".
     */
    @GET("profile/wallets")
    fun getWallets(
        @Header("Authorization") authorizationHeader: String,
        @Query("sort") sortOrder: String,
        @Query("sortBy") sortColumn: String
    ) : Call<ApiResponse<List<Wallet>>>


    /**
     * Retrieves a single wallet by its ID.
     *
     * @param authorizationHeader The authorization header
     * @param walletId The ID of the wallet to fetch
     */
    @GET("profile/wallets/{walletId}")
    fun getWallet(
        @Header("Authorization") authorizationHeader: String,
        @Path("walletId") walletId: Long
    ) : Call<ApiResponse<Wallet>>


    /**
     * Creates a wallet for the particular currency.
     *
     * @param authorizationHeader The authorization header
     * @param currencyId The ID of the currency to create wallet for
     */
    @POST("profile/wallets/{currencyId}")
    fun createWallet(
        @Header("Authorization") authorizationHeader: String,
        @Path("currencyId") currencyId: Int
    ) : Call<ApiResponse<Wallet>>


    /**
     * Retrieves a deposit address of the wallet.
     *
     * @param authorizationHeader The authorization header
     * @param walletId The ID of the wallet to return the deposit address of
     */
    @GET("profile/wallets/address/{walletId}")
    fun getDepositAddress(
        @Header("Authorization") authorizationHeader: String,
        @Path("walletId") walletId: Long
    ) : Call<ApiResponse<TransactionAddress>>


    /**
     * Creates or recreates a deposit address of the particular wallet.
     *
     * @param authorizationHeader The authorization header
     * @param walletId The ID of the wallet to create or recreate
     * the deposit address of
     */
    @FormUrlEncoded
    @POST("profile/wallets/address/{walletId}")
    fun createDepositAddress(
        @Header("Authorization") authorizationHeader: String,
        @Path("walletId") walletId: Long
    ) : Call<ApiResponse<TransactionAddress>>


    /**
     * Retrieves deposits of the user.
     *
     * @param authorizationHeader The authorization header
     * @param sortOrder The order of the sort. The fetched deposits are sorted
     * based on the timestamp. Two possible values to use for this parameter:
     * ASC (ascending) or DESC (descending) order. Default value is DESC.
     * @param limit The count of the deposits to fetch. Default is 100.
     * @param offset The offset of the deposits data set to fetch items from
     */
    @GET("profile/deposits")
    fun getDeposits(
        @Header("Authorization") authorizationHeader: String,
        @Query("sort") sortOrder: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ) : Call<ApiResponse<List<Deposit>>>


    /**
     * Retrieves a deposit by its ID.
     *
     * @param authorizationHeader The authorization header
     * @param depositId The ID of the deposit to fetch
     */
    @GET("profile/deposits/{depositId}")
    fun getDeposit(
        @Header("Authorization") authorizationHeader: String,
        @Path("depositId") depositId: Long
    ) : Call<ApiResponse<Deposit>>


    /**
     * Retrieves withdrawals of the user.
     *
     * @param authorizationHeader The authorization header
     * @param sortOrder The order of the sort. The fetched withdrawals are sorted
     * based on the timestamp. Two possible values to use for this parameter:
     * ASC (ascending) or DESC (descending) order. Default value is DESC.
     * @param limit The count of the withdrawals to fetch. Default is 100.
     * @param offset The offset of the withdrawals data set to fetch items from
     */
    @GET("profile/withdrawals")
    fun getWithdrawals(
        @Header("Authorization") authorizationHeader: String,
        @Query("sort") sortOrder: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ) : Call<ApiResponse<List<Withdrawal>>>


    /**
     * Retrieves a withdrawal by its ID.
     *
     * @param authorizationHeader The authorization header
     * @param withdrawalId The ID of the withdrawal to fetch
     */
    @GET("profile/withdrawals/{withdrawalId}")
    fun getWithdrawal(
        @Header("Authorization") authorizationHeader: String,
        @Path("withdrawalId") withdrawalId: Long
    ) : Call<ApiResponse<Withdrawal>>


    /**
     * Creates a withdrawal.
     *
     * @param authorizationHeader The authorization header
     * @param currencyId The ID of the currency to create a withdrawal of
     * @param amount The amount to withdraw
     * @param address The address of the wallet to withdraw funds into
     * @param additionalAddress The additional address for a withdrawal.
     * For example, if withdrawing funds requires additional address
     * (like payment ID or destination tag), it should be passed as
     * a value for this parameter.
     */
    @FormUrlEncoded
    @POST("profile/withdraw")
    fun createWithdrawal(
        @Header("Authorization") authorizationHeader: String,
        @Field("currency_id") currencyId: Int,
        @Field("amount") amount: String,
        @Field("address") address: String,
        @Field("additional_address") additionalAddress: String
    ) : Call<ApiResponse<Withdrawal>>


    /**
     * Cancels a withdrawal.
     *
     * @param authorizationHeader The authorization header
     * @param withdrawalId The ID of the withdrawal to cancel
     */
    @DELETE("profile/withdraw/{withdrawalId}")
    fun cancelWithdrawal(
        @Header("Authorization") authorizationHeader: String,
        @Path("withdrawalId") withdrawalId: Long
    ) : Call<ApiResponse<String>>


}