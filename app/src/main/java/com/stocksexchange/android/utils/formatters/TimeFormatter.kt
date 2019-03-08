package com.stocksexchange.android.utils.formatters

import android.content.Context
import com.stocksexchange.android.ui.utils.extensions.getLocale
import java.text.SimpleDateFormat
import java.util.*

/**
 * A singleton class containing all the goodies for formatting
 * time data. Requires a locale for proper formatting across
 * different countries/regions.
 */
class TimeFormatter private constructor(context: Context) {


    companion object {

        @Volatile
        private var INSTANCE : TimeFormatter? = null


        fun getInstance(context: Context): TimeFormatter {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildTimeFormatter(context).also { INSTANCE = it }
            }
        }


        private fun buildTimeFormatter(context: Context): TimeFormatter {
            return TimeFormatter(context)
        }

    }


    private val mLocale: Locale = context.getLocale()

    private val mPriceChartDateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", mLocale)

    private val mDateFormatter = SimpleDateFormat(
        "yyyy-MM-dd ${if(isTwelveHourTimeZone(context)) "hh:mm:ss aa" else "HH:mm:ss"}",
        mLocale
    )

    private val mTradeTimeFormatter = SimpleDateFormat(
        if(isTwelveHourTimeZone(context)) "hh:mm:ss aa" else "HH:mm:ss",
        mLocale
    )

    private val mCalendar = Calendar.getInstance(mLocale)




    /**
     * Formats the date string of the candle stick.
     *
     * @param date The date string to parse
     *
     * @return The formatted date
     */
    fun formatCandleStickDate(date: String): String {
        return formatDate(mPriceChartDateFormatter.parse(date).time)
    }


    /**
     * A generic method for formatting time in the following format: MM:SS AM/PM (in countries
     * that support AM/PM) or MM:SS (in countries that do support 24 hour format).
     *
     * @param context The context
     * @param hours The hours to format
     * @param minutes The minutes to format
     * @param appendDayPeriod Whether or not to append day period (AM / PM). Only applicable
     * to countries that support such format.
     *
     * @return The formatted time
     */
    fun formatTime(context: Context, hours: Int, minutes: Int, appendDayPeriod: Boolean): String {
        return if(isTwelveHourTimeZone(context)) {
            val adjustedHours: Int = if(hours >= 12) {
                (hours - 12)
            } else {
                hours
            }

            var result = when {
                (adjustedHours == 0) -> "12"
                else -> adjustedHours.toString()
            } + ":" + when {
                (minutes < 10) -> "0$minutes"
                else -> minutes.toString()
            }

            if(appendDayPeriod) {
                result += (if (isAm(context, hours)) " AM" else " PM")
            }

            return result
        } else {
            when {
                (hours < 10) -> "0$hours"
                else -> hours.toString()
            } + ":" + when {
                (minutes < 10) -> "0$minutes"
                else -> minutes.toString()
            }
        }
    }


    /**
     * Formats the passed in timestamp in the following format: 2018-04-19 14:06:32.
     * If the device's time zone is in 12 hour time zone, then the time will be
     * 2018-04-19 02:06:32 PM.
     *
     * @param timestamp The unix time to format
     *
     * @return The timestamp formatted in the aforementioned way
     */
    fun formatDate(timestamp: Long): String {
        return mDateFormatter.format(Date(timestamp))
    }


    /**
     * Formats the passed in timestamp in the following format: 14:06:32.
     * If the device's time zone is in 12 hour time zone, then the time will be
     * 02:06:32 PM.
     *
     * @param timestamp The unix time to format
     *
     * @return The timestamp formatted in the aforementioned way
     */
    fun formatTradeTime(timestamp: Long): String {
        return mTradeTimeFormatter.format(Date(timestamp))
    }


    /**
     * Fetches the year from the timestamp.
     *
     * @param timeInMillis The time in milliseconds
     *
     * @return The year from the timestamp
     */
    fun getYear(timeInMillis: Long): Int {
        mCalendar.timeInMillis = timeInMillis

        return mCalendar.get(Calendar.YEAR)
    }


    /**
     * Fetches the month from the timestamp.
     *
     * @param timeInMillis The time in milliseconds
     *
     * @return The month from the timestamp
     */
    fun getMonth(timeInMillis: Long): Int {
        mCalendar.timeInMillis = timeInMillis

        return mCalendar.get(Calendar.MONTH)
    }


    /**
     * Fetches the day of month from the timestamp.
     *
     * @param timeInMillis The time in milliseconds
     *
     * @return The day of month from the timestamp
     */
    fun getDayOfMonth(timeInMillis: Long): Int {
        mCalendar.timeInMillis = timeInMillis

        return mCalendar.get(Calendar.DAY_OF_MONTH)
    }


    /**
     * Fetches the day of week from the timestamp.
     *
     * @param timeInMillis The time in milliseconds
     *
     * @return The day of week from the timestamp
     */
    fun getDayOfWeek(timeInMillis: Long): Int {
        mCalendar.timeInMillis = timeInMillis

        return mCalendar.get(Calendar.DAY_OF_WEEK)
    }


    /**
     * Fetches the hour from the timestamp.
     *
     * @param timeInMillis The time in milliseconds
     *
     * @return The hour from the timestamp
     */
    fun getHour(context: Context, timeInMillis: Long): Int {
        mCalendar.timeInMillis = timeInMillis

        return mCalendar.get(if(isTwelveHourTimeZone(context)) {
            Calendar.HOUR
        } else {
            Calendar.HOUR_OF_DAY
        })
    }


    /**
     * Fetches the hour of day from the timestamp.
     *
     * @param timeInMillis The time in milliseconds
     *
     * @return The hour of day from the timestamp
     */
    fun getHourOfDay(timeInMillis: Long): Int {
        mCalendar.timeInMillis = timeInMillis

        return mCalendar.get(Calendar.HOUR_OF_DAY)
    }


    /**
     * Fetches the minute from the timestamp.
     *
     * @param timeInMillis The time in milliseconds
     *
     * @return The minute from the timestamp
     */
    fun getMinute(timeInMillis: Long): Int {
        mCalendar.timeInMillis = timeInMillis

        return mCalendar.get(Calendar.MINUTE)
    }


    /**
     * Fetches the second from the timestamp.
     *
     * @param timeInMillis The time in milliseconds
     *
     * @return The second from the timestamp
     */
    fun getSecond(timeInMillis: Long): Int {
        mCalendar.timeInMillis = timeInMillis

        return mCalendar.get(Calendar.SECOND)
    }


    /**
     * Fetches the millisecond from the timestamp.
     *
     * @param timeInMillis The time in milliseconds
     *
     * @return The millisecond from the timestamp
     */
    fun getMillisecond(timeInMillis: Long): Int {
        mCalendar.timeInMillis = timeInMillis

        return mCalendar.get(Calendar.MILLISECOND)
    }


    /**
     * Determines whether the device supports 12 hour time zone or not.
     *
     * @param context The context
     *
     * @return true if supports; false otherwise
     */
    fun isTwelveHourTimeZone(context: Context): Boolean {
        return !android.text.format.DateFormat.is24HourFormat(context)
    }


    /**
     * Determines whether the hour of day is in the AM range according
     * to the 12 hour time zone.
     *
     * @param context The context
     * @param hourOfDay The hour of day
     *
     * @return true if the hour of day resides in the AM range; false otherwise
     */
    fun isAm(context: Context, hourOfDay: Int): Boolean {
        return if(isTwelveHourTimeZone(context)) {
            (hourOfDay < 12)
        } else {
            false
        }
    }


    /**
     * Determines whether the hour of day is in the PM range according
     * to the 12 hour time zone.
     *
     * @param context The context
     * @param hourOfDay The hour of day
     *
     * @return true if the hour of day resides in the PM range; false otherwise
     */
    fun isPm(context: Context, hourOfDay: Int): Boolean {
        return if(isTwelveHourTimeZone(context)) {
            (hourOfDay >= 12)
        } else {
            false
        }
    }


}