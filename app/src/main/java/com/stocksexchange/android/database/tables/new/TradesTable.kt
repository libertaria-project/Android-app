package com.stocksexchange.android.database.tables.new

import com.stocksexchange.android.api.model.newapi.SortOrders
import com.stocksexchange.android.api.model.newapi.Trade
import com.stocksexchange.android.api.model.newapi.TradeParameters
import com.stocksexchange.android.database.daos.newd.TradeDao
import com.stocksexchange.android.database.tables.base.BaseTable
import com.stocksexchange.android.database.tables.exceptions.NotFoundException
import com.stocksexchange.android.mappings.new.mapToDatabaseTrade
import com.stocksexchange.android.mappings.new.mapToDatabaseTradeList
import com.stocksexchange.android.mappings.new.mapToTradeList
import com.stocksexchange.android.model.Result
import org.koin.standalone.inject

/**
 * A database table holding trades related functionality.
 */
object TradesTable : BaseTable() {


    private val mTradeDao: TradeDao by inject()




    /**
     * Saves a trade inside the database.
     *
     * @param params The parameters for saving a trade
     * @param trade The trade to save
     */
    fun save(params: TradeParameters,
             trade: Trade) {
        mTradeDao.insert(trade.mapToDatabaseTrade(params))
    }


    /**
     * Saves trades inside the database.
     *
     * @param params The parameters for saving trades
     * @param trades The trades to save
     */
    fun save(params: TradeParameters,
             trades: List<Trade>) {
        mTradeDao.insert(trades.mapToDatabaseTradeList(params))
    }


    /**
     * Delete trades with a particular currency pair ID from the database.
     *
     * @param currencyPairId The currency pair ID of trades to delete
     */
    fun delete(currencyPairId: Int) {
        mTradeDao.delete(currencyPairId)
    }


    /**
     * Retrieves trades from the database.
     *
     * @param params The parameters for fetching trades
     *
     * @return The trades or [NotFoundException] error packaged
     * inside an instance of [Result] class
     */
    fun get(params: TradeParameters): Result<List<Trade>> {
        val currencyPairId = params.currencyPairId
        val count = params.count

        return when(params.sortOrder) {
            SortOrders.ASC -> mTradeDao.getAsc(currencyPairId, count)
            SortOrders.DESC -> mTradeDao.getDesc(currencyPairId, count)
        }.mapToTradeList()
            .takeIf { it.isNotEmpty() }
            ?.let { Result.Success(it) }
            ?: Result.Failure(NotFoundException())
    }


}