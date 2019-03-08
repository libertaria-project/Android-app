package com.stocksexchange.android.database.tables.new

import com.stocksexchange.android.api.model.newapi.CandleStick
import com.stocksexchange.android.api.model.newapi.PriceChartDataParameters
import com.stocksexchange.android.database.daos.newd.CandleStickDao
import com.stocksexchange.android.database.tables.base.BaseTable
import com.stocksexchange.android.database.tables.exceptions.NotFoundException
import com.stocksexchange.android.mappings.new.mapToCandleStickList
import com.stocksexchange.android.mappings.new.mapToDatabaseCandleStickList
import com.stocksexchange.android.model.Result
import org.koin.standalone.inject

/**
 * A database table holding candle sticks related functionality.
 */
object CandleSticksTable : BaseTable() {


    private val mCandleStickDao: CandleStickDao by inject()




    /**
     * Saves candle sticks inside the database.
     *
     * @param params The parameters for saving the candle sticks
     * @param candleSticks The candle sticks to save
     */
    fun save(params: PriceChartDataParameters,
             candleSticks: List<CandleStick>) {
        mCandleStickDao.insert(candleSticks.mapToDatabaseCandleStickList(params))
    }


    /**
     * Deletes candle sticks from the database.
     *
     * @param params The parameters for deleting the candle sticks
     */
    fun delete(params: PriceChartDataParameters) {
        mCandleStickDao.delete(
            currencyPairId = params.currencyPairId,
            interval = params.interval.interval
        )
    }


    /**
     * Retrieves candle sticks from the database.
     *
     * @param params The parameters for retrieving candle sticks
     *
     * @return The candle sticks or [NotFoundException] error packaged
     * inside an instance of [Result] class
     */
    fun get(params: PriceChartDataParameters): Result<List<CandleStick>> {
        return mCandleStickDao.get(
            currencyPairId = params.currencyPairId,
            interval = params.interval.interval,
            count = params.count
        ).mapToCandleStickList()
        .takeIf { it.isNotEmpty() }
        ?.let { Result.Success(it) }
        ?: Result.Failure(NotFoundException())
    }


}