package com.stocksexchange.android.model.comparators

import com.stocksexchange.android.api.model.CurrencyMarket
import kotlinx.android.parcel.Parcelize

/**
 * A comparator for [CurrencyMarket] model class.
 */
@Parcelize
data class CurrencyMarketComparator(
    val id: Int,
    val column: Columns,
    val order: Orders
) : ParcelableComparator<CurrencyMarket> {


    companion object {

        val NAME_ASCENDING_COMPARATOR = CurrencyMarketComparator(1, Columns.NAME, Orders.ASC)
        val NAME_DESCENDING_COMPARATOR = CurrencyMarketComparator(2, Columns.NAME, Orders.DESC)
        val VOLUME_ASCENDING_COMPARATOR = CurrencyMarketComparator(3, Columns.VOLUME, Orders.ASC)
        val VOLUME_DESCENDING_COMPARATOR = CurrencyMarketComparator(4, Columns.VOLUME, Orders.DESC)
        val LAST_PRICE_ASCENDING_COMPARATOR = CurrencyMarketComparator(5, Columns.LAST_PRICE, Orders.ASC)
        val LAST_PRICE_DESCENDING_COMPARATOR = CurrencyMarketComparator(6, Columns.LAST_PRICE, Orders.DESC)
        val DAILY_PRICE_CHANGE_ASCENDING_COMPARATOR = CurrencyMarketComparator(7, Columns.DAILY_PRICE_CHANGE, Orders.ASC)
        val DAILY_PRICE_CHANGE_DESCENDING_COMPARATOR = CurrencyMarketComparator(8, Columns.DAILY_PRICE_CHANGE, Orders.DESC)

    }


    /**
     * An enumeration of all available columns to sort by.
     */
    enum class Columns {

        NAME,
        VOLUME,
        LAST_PRICE,
        DAILY_PRICE_CHANGE

    }


    /**
     * An enumeration of all possible sorting orders.
     */
    enum class Orders {

        ASC,
        DESC

    }




    /**
     * Returns an opposite sort config.
     *
     * @return The opposite sort config
     */
    operator fun not(): CurrencyMarketComparator {
        return when(order) {

            Orders.ASC -> when(column) {
                Columns.NAME -> NAME_DESCENDING_COMPARATOR
                Columns.VOLUME -> VOLUME_DESCENDING_COMPARATOR
                Columns.LAST_PRICE -> LAST_PRICE_DESCENDING_COMPARATOR
                Columns.DAILY_PRICE_CHANGE -> DAILY_PRICE_CHANGE_DESCENDING_COMPARATOR
            }

            Orders.DESC -> when(column) {
                Columns.NAME -> NAME_ASCENDING_COMPARATOR
                Columns.VOLUME -> VOLUME_ASCENDING_COMPARATOR
                Columns.LAST_PRICE -> LAST_PRICE_ASCENDING_COMPARATOR
                Columns.DAILY_PRICE_CHANGE -> DAILY_PRICE_CHANGE_ASCENDING_COMPARATOR
            }

        }
    }


    override fun compare(o1: CurrencyMarket, o2: CurrencyMarket): Int {
        return when(order) {

            Orders.ASC -> when(column) {
                Columns.NAME -> o1.name.compareTo(o2.name)
                Columns.VOLUME -> o1.volume.compareTo(o2.volume)
                Columns.LAST_PRICE -> o1.lastPrice.compareTo(o2.lastPrice)
                Columns.DAILY_PRICE_CHANGE -> o1.dailyPriceChange.compareTo(o2.dailyPriceChange)
            }

            Orders.DESC -> when(column) {
                Columns.NAME -> o2.name.compareTo(o1.name)
                Columns.VOLUME -> o2.volume.compareTo(o1.volume)
                Columns.LAST_PRICE -> o2.lastPrice.compareTo(o1.lastPrice)
                Columns.DAILY_PRICE_CHANGE -> o2.dailyPriceChange.compareTo(o1.dailyPriceChange)
            }

        }
    }


}