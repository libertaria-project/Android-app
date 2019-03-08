package com.stocksexchange.android.database.tables

import com.stocksexchange.android.api.model.PriceChartData
import com.stocksexchange.android.api.model.PriceChartParameters
import com.stocksexchange.android.database.daos.PriceChartDataDao
import com.stocksexchange.android.mappings.mapToPriceChartData
import com.stocksexchange.android.mappings.mapToDatabasePriceChartData
import com.stocksexchange.android.database.tables.base.BaseTable
import com.stocksexchange.android.database.tables.exceptions.NotFoundException
import com.stocksexchange.android.model.Result
import org.koin.standalone.inject

/**
 * A database table holding charts data related functionality.
 */
object PriceChartDataTable : BaseTable() {


    private val mPriceChartDataDao: PriceChartDataDao by inject()




    /**
     * Saves price chart data within the database.
     *
     * @param priceChartData The price chart data to save
     */
    fun save(priceChartData: PriceChartData) {
        mPriceChartDataDao.insert(priceChartData.mapToDatabasePriceChartData())
    }


    /**
     * Retrieves price chart data from the database.
     *
     * @param params The price chart parameters for data loading
     *
     * @return The price chart data or [NotFoundException] error packaged
     * inside an instance of [Result] class
     */
    fun get(params: PriceChartParameters): Result<PriceChartData> {
        return mPriceChartDataDao.get(
            params.marketName,
            params.interval.intervalName,
            params.sortType.name,
            params.count,
            params.page
        )?.mapToPriceChartData()
            ?.let { Result.Success(it) }
            ?: Result.Failure(NotFoundException())
    }


}