package com.stocksexchange.android.api.services

import com.stocksexchange.android.api.model.*
import retrofit2.Call
import retrofit2.http.*

interface StocksExchangeService {


    /**
     * Gets information about an account.
     */
    @FormUrlEncoded
    @POST("./")
    fun getUser(
        @Header("Key") publicKey: String,
        @Header("Sign") signature: String,
        @Field("method") method: String,
        @Field("nonce") nonce: String
    ): Call<ApiResponse<User>>


    /**
     * Creates an order with the specified
     * parameters.
     */
    @FormUrlEncoded
    @POST("./")
    fun createOrder(
        @Header("Key") publicKey: String,
        @Header("Sign") signature: String,
        @Field("method") method: String,
        @Field("nonce") nonce: String,
        @Field("pair") marketName: String,
        @Field("type") tradeType: String,
        @Field("amount") amount: String,
        @Field("rate") rate: String
    ): Call<ApiResponse<OrderResponse>>


    /**
     * Cancels a specific order specified by
     * order_id parameter.
     */
    @FormUrlEncoded
    @POST("./")
    fun cancelOrder(
        @Header("Key") publicKey: String,
        @Header("Sign") signature: String,
        @Field("method") method: String,
        @Field("nonce") nonce: String,
        @Field("order_id") orderId: Long
    ): Call<ApiResponse<OrderResponse>>


    /**
     * Retrieves user's active orders.
     */
    @FormUrlEncoded
    @POST("./")
    fun getActiveOrders(
        @Header("Key") publicKey: String,
        @Header("Sign") signature: String,
        @Field("method") method: String,
        @Field("nonce") nonce: String,
        @Field("pair") marketName: String,
        @Field("type") tradeType: String,
        @Field("owner") owner: String,
        @Field("order") order: String,
        @Field("count") count: Int
    ): Call<ApiResponse<Map<String, Order>>>


    /**
     * Retrieves user's history orders
     * (such as completed or cancelled orders).
     */
    @FormUrlEncoded
    @POST("./")
    fun getHistoryOrders(
        @Header("Key") publicKey: String,
        @Header("Sign") signature: String,
        @Field("method") method: String,
        @Field("nonce") nonce: String,
        @Field("pair") marketName: String,
        @Field("status") statusId: Int,
        @Field("owner") owner: String,
        @Field("order") order: String,
        @Field("count") count: Int
    ): Call<ApiResponse<Map<String, Order>>>


    /**
     * Retrieves user's transactions (deposits
     * and withdrawals).
     */
    @FormUrlEncoded
    @POST("./")
    fun getTransactions(
        @Header("Key") publicKey: String,
        @Header("Sign") signature: String,
        @Field("method") method: String,
        @Field("nonce") nonce: String,
        @Field("currency") currency: String,
        @Field("operation") operation: String,
        @Field("status") status: String,
        @Field("order") order: String,
        @Field("count") count: Int
    ): Call<ApiResponse<Map<String, Map<Long, Transaction>>>>


    /**
     * Retrieves detailed information about a specific currency deposit.
     */
    @FormUrlEncoded
    @POST("./")
    fun getDeposit(
        @Header("Key") publicKey: String,
        @Header("Sign") signature: String,
        @Field("method") method: String,
        @Field("nonce") nonce: String,
        @Field("currency") currency: String
    ): Call<ApiResponse<Deposit>>


    /**
     * Generates a wallet address for a specific currency.
     */
    @FormUrlEncoded
    @POST("./")
    fun generateWalletAddress(
        @Header("Key") publicKey: String,
        @Header("Sign") signature: String,
        @Field("method") method: String,
        @Field("nonce") nonce: String,
        @Field("currency") currency: String
    ): Call<ApiResponse<Deposit>>


    /**
     * Retrieves the recommended retail exchange rates
     * for all currency pairs.
     */
    @GET("ticker")
    fun getCurrencyMarkets(): Call<List<CurrencyMarket>>


    /**
     * Retrieves data for price charts.
     */
    @GET("grafic_public")
    fun getPriceChartData(
        @Query("pair") marketName: String,
        @Query("interval") interval: String,
        @Query("order") order: String,
        @Query("count") count: Int,
        @Query("page") page: Int
    ): Call<ApiResponse<PriceChartData>>


    /**
     * Retrieves orderbook data for a specific currency market.
     */
    @GET("orderbook")
    fun getOrderbook(
        @Query("pair") marketName: String
    ): Call<ApiResponse<Orderbook>>


    /**
     * Retrieves trades for a specific currency market.
     */
    @GET("trades")
    fun getTrades(
        @Query("pair") marketName: String,
        @Query("count") count: Int
    ): Call<ApiResponse<List<Trade>>>


    /**
     * Retrieves all available currencies.
     */
    @GET("currencies")
    fun getCurrencies(): Call<List<Currency>>


}