package com.stocksexchange.android.database.converters.newd

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.stocksexchange.android.api.model.newapi.OrderbookOrder
import com.stocksexchange.android.api.model.newapi.Order
import com.stocksexchange.android.api.model.newapi.TransactionAddress
import com.stocksexchange.android.api.utils.fromJson
import com.stocksexchange.android.database.converters.base.BaseConverter
import com.stocksexchange.android.model.*
import com.stocksexchange.android.theming.factories.ThemeFactory
import com.stocksexchange.android.theming.model.Theme
import com.stocksexchange.android.utils.EncryptionUtil
import org.koin.standalone.get

/**
 * Converters used for converting different type of data
 * from and to the database.
 */
object Converters : BaseConverter() {


    /**
     * Converts an [AuthenticationSessionDurations] object to a string.
     *
     * @param authenticationSessionDuration The [AuthenticationSessionDurations] object
     * to convert to a string
     *
     * @return The string representation of the [AuthenticationSessionDurations] object
     */
    @TypeConverter
    @JvmStatic
    fun fromAuthenticationSessionDuration(authenticationSessionDuration: AuthenticationSessionDurations): String {
        return authenticationSessionDuration.name
    }


    /**
     * Converts a string into an [AuthenticationSessionDurations] object.
     *
     * @param name The name string of [AuthenticationSessionDurations] object
     *
     * @return The [AuthenticationSessionDurations] object constructed from
     * the string
     */
    @TypeConverter
    @JvmStatic
    fun toAuthenticationSessionDuration(name: String): AuthenticationSessionDurations {
        return AuthenticationSessionDurations.valueOf(name)
    }


    /**
     * Converts a [CandleStickStyles] object to a string.
     *
     * @param candleStickStyle The [CandleStickStyles] object
     * to convert to a string
     *
     * @return The string representation of the [CandleStickStyles]
     * object
     */
    @TypeConverter
    @JvmStatic
    fun fromCandleStickStyle(candleStickStyle: CandleStickStyles): String {
        return candleStickStyle.name
    }


    /**
     * Converts a string into a [CandleStickStyles] object.
     *
     * @param name The name string of the [CandleStickStyles] object
     *
     * @return The [CandleStickStyles] object constructed from
     * the string
     */
    @TypeConverter
    @JvmStatic
    fun toCandleStickStyle(name: String): CandleStickStyles {
        return CandleStickStyles.valueOf(name)
    }


    /**
     * Converts a [DecimalSeparators] object to a string.
     *
     * @param decimalSeparator The [DecimalSeparators] object
     * to convert to a string
     *
     * @return The string representation of the [DecimalSeparators]
     * object
     */
    @TypeConverter
    @JvmStatic
    fun fromDecimalSeparator(decimalSeparator: DecimalSeparators): String {
        return decimalSeparator.name
    }


    /**
     * Converts a string into an [DecimalSeparators] object.
     *
     * @param name The name string of the [DecimalSeparators] object
     *
     * @return The [DecimalSeparators] object constructed from
     * the string
     */
    @TypeConverter
    @JvmStatic
    fun toDecimalSeparator(name: String): DecimalSeparators {
        return DecimalSeparators.valueOf(name)
    }


    /**
     * Converts a [DepthChartLineStyles] object to a string.
     *
     * @param depthChartLineStyle The [DepthChartLineStyles] object
     * to convert to a string
     *
     * @return The string representation of the [DepthChartLineStyles]
     * object
     */
    @TypeConverter
    @JvmStatic
    fun fromDepthChartLineStyle(depthChartLineStyle: DepthChartLineStyles): String {
        return depthChartLineStyle.name
    }


    /**
     * Converts a string into an [DepthChartLineStyles] object.
     *
     * @param name The name string of the [DepthChartLineStyles] object
     *
     * @return The [DepthChartLineStyles] object constructed from
     * the string
     */
    @TypeConverter
    @JvmStatic
    fun toDepthChartLineStyle(name: String): DepthChartLineStyles {
        return DepthChartLineStyles.valueOf(name)
    }


    /**
     * Converts a [GroupingSeparators] object to a string.
     *
     * @param groupingSeparator The [GroupingSeparators] object
     * to convert to a string
     *
     * @return The string representation of the [GroupingSeparators]
     * object
     */
    @TypeConverter
    @JvmStatic
    fun fromGroupingSeparator(groupingSeparator: GroupingSeparators): String {
        return groupingSeparator.name
    }


    /**
     * Converts a string into an [GroupingSeparators] object.
     *
     * @param name The name string of the [GroupingSeparators] object
     *
     * @return The [GroupingSeparators] object constructed from
     * the string
     */
    @TypeConverter
    @JvmStatic
    fun toGroupingSeparator(name: String): GroupingSeparators {
        return GroupingSeparators.valueOf(name)
    }


    /**
     * Converts a [PinCode] object to a string.
     *
     * @param pinCode The [PinCode] object
     * to convert to a string
     *
     * @return The string representation of the [PinCode]
     * object
     */
    @TypeConverter
    @JvmStatic
    fun fromPinCode(pinCode: PinCode): String {
        return EncryptionUtil.getInstance().encrypt(pinCode.code)
    }


    /**
     * Converts a string into a [PinCode] object.
     *
     * @param code The code of the [PinCode] object
     *
     * @return The [PinCode] object constructed from the code
     */
    @TypeConverter
    @JvmStatic
    fun toPinCode(code: String): PinCode {
        return PinCode(EncryptionUtil.getInstance().decrypt(code))
    }


    /**
     * Converts a [Theme] object to a string.
     *
     * @param theme The [Theme] object
     * to convert to a string
     *
     * @return The string representation of the [Theme]
     * object
     */
    @TypeConverter
    @JvmStatic
    fun fromTheme(theme: Theme): Int {
        return theme.id
    }


    /**
     * Converts a string into an [Theme] object.
     *
     * @param id The id of the [Theme] object
     *
     * @return The [Theme] object constructed from
     * the string
     */
    @TypeConverter
    @JvmStatic
    fun toTheme(id: Int): Theme {
        return ThemeFactory.getThemeForId(id)
    }


    /**
     * Converts a [TransactionAddress] object to a JSON string.
     *
     * @param transactionAddress The [TransactionAddress] object
     * to convert to JSON
     *
     * @return The JSON string of the [TransactionAddress] object
     */
    @TypeConverter
    @JvmStatic
    fun fromTransactionAddress(transactionAddress: TransactionAddress?): String {
        return get<Gson>().toJson(transactionAddress)
    }


    /**
     * Converts a JSON string into a [TransactionAddress] object.
     *
     * @param json The JSON string of the [TransactionAddress] object
     *
     * @return The [TransactionAddress] object constructed from
     * the JSON string
     */
    @TypeConverter
    @JvmStatic
    fun toTransactionAddress(json: String?): TransactionAddress? {
        return json?.let { get<Gson>().fromJson(it) }
    }


    /**
     * Converts a list of [Order.Trade] objects to a JSON string.
     *
     * @param list The list of [Order.Trade] objects to convert
     * to JSON
     *
     * @return The JSON string of the list of [Order.Trade] objects
     */
    @TypeConverter
    @JvmStatic
    fun fromOrderTradeList(list: List<Order.Trade>): String {
        return get<Gson>().toJson(list)
    }


    /**
     * Converts a list of [Order.Trade] objects to a JSON string.
     *
     * @param json The JSON string of the list of [Order.Trade] objects
     *
     * @return The list of [Order.Trade] objects constructed from the
     * JSON string
     */
    @TypeConverter
    @JvmStatic
    fun toOrderTradeList(json: String?): List<Order.Trade> {
        return json?.let { get<Gson>().fromJson<List<Order.Trade>>(it) } ?: listOf()
    }


    /**
     * Converts a list of [Order.Fee] objects to a JSON string.
     *
     * @param list The list of [Order.Fee] objects to convert
     * to JSON
     *
     * @return The JSON string of the list of [Order.Fee] objects
     */
    @TypeConverter
    @JvmStatic
    fun fromOrderFeeList(list: List<Order.Fee>): String {
        return get<Gson>().toJson(list)
    }


    /**
     * Converts a list of [Order.Fee] objects to a JSON string.
     *
     * @param json The JSON string of the list of [Order.Fee] objects
     *
     * @return The list of [Order.Fee] objects constructed from the
     * JSON string
     */
    @TypeConverter
    @JvmStatic
    fun toOrderFeeList(json: String?): List<Order.Fee> {
        return json?.let { get<Gson>().fromJson<List<Order.Fee>>(it) } ?: listOf()
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
    fun toOrderbookOrderList(json: String?): List<OrderbookOrder> {
        return json?.let { get<Gson>().fromJson<List<OrderbookOrder>>(it) } ?: listOf()
    }


}