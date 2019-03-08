package com.stocksexchange.android.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.stocksexchange.android.api.model.*
import com.stocksexchange.android.api.utils.fromJson
import com.stocksexchange.android.database.converters.base.BaseConverter
import org.koin.standalone.get

/**
 * Converters used for converting different type of data
 * from and to the database.
 */
object Converters : BaseConverter() {


    /**
     * Converts an [AssetsPortfolio] object to a JSON string.
     *
     * @param assetsPortfolio The [AssetsPortfolio] object
     * to convert to JSON
     *
     * @return The JSON string of the [AssetsPortfolio] object
     */
    @TypeConverter
    @JvmStatic
    fun fromAssetsPortfolio(assetsPortfolio: AssetsPortfolio?): String {
        return get<Gson>().toJson(assetsPortfolio)
    }


    /**
     * Converts a JSON string into an [AssetsPortfolio] object.
     *
     * @param json The JSON string of the [AssetsPortfolio] object
     *
     * @return The [AssetsPortfolio] object constructed from
     * the JSON string
     */
    @TypeConverter
    @JvmStatic
    fun toAssetsPortfolio(json: String?): AssetsPortfolio? {
        return json?.let { get<Gson>().fromJson(it) }
    }


    /**
     * Converts a list of [CandleStick] objects to a JSON string.
     *
     * @param list The list of [CandleStick] objects
     * to convert to JSON
     *
     * @return The JSON string of the list of [CandleStick] objects
     */
    @TypeConverter
    @JvmStatic
    fun fromCandleStickList(list: List<CandleStick>): String {
        return get<Gson>().toJson(list)
    }


    /**
     * Converts a JSON string into a list of [CandleStick] objects.
     *
     * @param json The JSON string of the list of [CandleStick] objects
     *
     * @return The list of [CandleStick] objects constructed from
     * the JSON string
     */
    @TypeConverter
    @JvmStatic
    fun toCandleStickList(json: String?): List<CandleStick>? {
        return json?.let { get<Gson>().fromJson(it) }
    }


    /**
     * Converts a list of [OrderbookOrder] objects to a JSON string.
     *
     * @param list The list of [OrderbookOrder] objects
     * to convert to JSON
     *
     * @return The JSON string of the list of [OrderbookOrder] objects
     */
    @TypeConverter
    @JvmStatic
    fun fromOrderbookOrderList(list: List<OrderbookOrder>): String {
        return get<Gson>().toJson(list)
    }


    /**
     * Converts a JSON string into a list of [OrderbookOrder] objects.
     *
     * @param json The JSON string of the list of [OrderbookOrder] objects
     *
     * @return The list of [OrderbookOrder] objects constructed from
     * the JSON string
     */
    @TypeConverter
    @JvmStatic
    fun toOrderbookOrderList(json: String?): List<OrderbookOrder>? {
        return json?.let { get<Gson>().fromJson(it) }
    }


    /**
     * Converts a list of [UserSession] objects to a JSON string.
     *
     * @param list The list of [UserSession] objects
     * to convert to JSON
     *
     * @return The JSON string of the list of [UserSession] objects
     */
    @TypeConverter
    @JvmStatic
    fun fromUserSessionList(list: List<UserSession>?): String {
        return get<Gson>().toJson(list)
    }


    /**
     * Converts a JSON string into a list of [UserSession] objects.
     *
     * @param json The JSON string of the list of [UserSession] objects
     *
     * @return The list of [UserSession] objects constructed from
     * the JSON string
     */
    @TypeConverter
    @JvmStatic
    fun toUserSessionList(json: String?): List<UserSession>? {
        return json?.let { get<Gson>().fromJson(it) }
    }


    /**
     * Converts a map of [String] objects to a JSON string.
     *
     * @param map The map of [String] objects
     * to convert to JSON
     *
     * @return The JSON string of the map of [String] objects
     */
    @TypeConverter
    @JvmStatic
    fun fromStringMap(map: Map<String, String>?): String {
        return get<Gson>().toJson(map)
    }


    /**
     * Converts a JSON string into a map of [String] objects.
     *
     * @param json The JSON string of the map of [String] objects
     *
     * @return The map of [String] objects constructed from
     * the JSON string
     */
    @TypeConverter
    @JvmStatic
    fun toStringMap(json: String?): Map<String, String>? {
        return json?.let { get<Gson>().fromJson(json) }
    }


    /**
     * Converts a map of [OrderAmount] objects to a JSON string.
     *
     * @param map The map of [OrderAmount] objects
     * to convert to JSON
     *
     * @return The JSON string of the map of [OrderAmount] objects
     */
    @TypeConverter
    @JvmStatic
    fun fromOrderAmountMap(map: Map<Double, OrderAmount>?): String {
        return get<Gson>().toJson(map)
    }


    /**
     * Converts a JSON string into a map of [OrderAmount] objects.
     *
     * @param json The JSON string of the map of [OrderAmount] objects
     *
     * @return The map of [OrderAmount] objects constructed from
     * the JSON string
     */
    @TypeConverter
    @JvmStatic
    fun toOrderAmountMap(json: String?): Map<Double, OrderAmount>? {
        return json?.let { get<Gson>().fromJson(json) }
    }


}