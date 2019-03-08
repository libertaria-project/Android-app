package com.stocksexchange.android.database.tables.new

import com.stocksexchange.android.api.model.newapi.Orderbook
import com.stocksexchange.android.api.model.newapi.OrderbookParameters
import com.stocksexchange.android.database.daos.newd.OrderbookDao
import com.stocksexchange.android.database.tables.base.BaseTable
import com.stocksexchange.android.database.tables.exceptions.NotFoundException
import com.stocksexchange.android.mappings.new.mapToDatabaseOrderbook
import com.stocksexchange.android.mappings.new.mapToOrderbook
import com.stocksexchange.android.model.Result
import org.koin.standalone.inject

/**
 * A database table holding orderbook related functionality.
 */
object OrderbooksTable : BaseTable() {


    private val mOrderbookDao: OrderbookDao by inject()




    /**
     * Saves orderbook inside the database.
     *
     * @param params The parameters for saving the orderbook
     * @param orderbook The orderbook to save
     */
    fun save(params: OrderbookParameters,
             orderbook: Orderbook) {
        mOrderbookDao.insert(orderbook.mapToDatabaseOrderbook(params))
    }


    /**
     * Retrieves orderbook from the database.
     *
     * @param params The parameters for fetching the orderbook
     *
     * @return The orderbook or [NotFoundException] error packaged
     * inside an instance of [Result] class
     */
    fun get(params: OrderbookParameters): Result<Orderbook> {
        return mOrderbookDao.get(currencyPairId = params.currencyPairId)
            ?.mapToOrderbook()
            ?.takeIf { !it.isEmpty }
            ?.let { Result.Success(it) }
            ?: Result.Failure(NotFoundException())
    }


}