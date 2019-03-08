package com.stocksexchange.android.database.tables

import com.stocksexchange.android.api.model.Orderbook
import com.stocksexchange.android.api.model.OrderbookParameters
import com.stocksexchange.android.database.daos.OrderbookDao
import com.stocksexchange.android.database.tables.base.BaseTable
import com.stocksexchange.android.database.tables.exceptions.NotFoundException
import com.stocksexchange.android.mappings.mapToDatabaseOrderbook
import com.stocksexchange.android.mappings.mapToOrderbook
import com.stocksexchange.android.model.Result
import org.koin.standalone.inject

/**
 * A database table holding orderbook related functionality.
 */
object OrderbookTable : BaseTable() {


    private val mOrderbookDao: OrderbookDao by inject()




    /**
     * Saves orderbook within the database.
     *
     * @param params The parameters of the orderbook
     * @param orderbook The orderbook data to save
     */
    fun save(params: OrderbookParameters, orderbook: Orderbook) {
        mOrderbookDao.insert(orderbook.mapToDatabaseOrderbook(params.marketName))
    }


    /**
     * Retrieves orderbook data from the database.
     *
     * @param params The orderbook parameters for data loading
     *
     * @return The orderbook data or [NotFoundException] error
     * packaged inside an instance of [Result] class
     */
    fun get(params: OrderbookParameters): Result<Orderbook> {
        return mOrderbookDao.get(params.marketName)
            ?.mapToOrderbook()
            ?.let { Result.Success(it) }
            ?: Result.Failure(NotFoundException())
    }


}