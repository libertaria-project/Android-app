package com.stocksexchange.android.database.tables

import com.stocksexchange.android.api.model.Trade
import com.stocksexchange.android.api.model.TradeParameters
import com.stocksexchange.android.database.daos.TradeDao
import com.stocksexchange.android.database.tables.base.BaseTable
import com.stocksexchange.android.database.tables.exceptions.NotFoundException
import com.stocksexchange.android.mappings.mapToDatabaseTrade
import com.stocksexchange.android.mappings.mapToDatabaseTradeList
import com.stocksexchange.android.mappings.mapToTradeList
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.model.SortTypes
import org.koin.standalone.inject

/**
 * A database table holding trades related functionality.
 */
object TradesTable : BaseTable() {


    private val mTradeDao: TradeDao by inject()




    /**
     * Saves a trade within the database.
     *
     * @param params The trade parameters
     * @param trade The trade to save
     */
    fun save(params: TradeParameters, trade: Trade) {
        mTradeDao.insert(trade.mapToDatabaseTrade(params.marketName))
    }


    /**
     * Deletes all trades with a specific market name.
     *
     * @param marketName The name of the market which trades to delete
     */
    fun delete(marketName: String) {
        mTradeDao.delete(marketName)
    }


    /**
     * Saves trades within the database.
     *
     * @param params The trade parameters
     * @param trades The trades to save
     */
    fun save(params: TradeParameters, trades: List<Trade>) {
        mTradeDao.insert(trades.mapToDatabaseTradeList(params.marketName))
    }


    /**
     * Retrieves trades based on the passed parameters.
     *
     * @param params The trade parameters for data loading
     *
     * @return The trades or [NotFoundException] error
     * packaged inside an instance of [Result] class
     */
    fun get(params: TradeParameters): Result<List<Trade>> {
        val marketName = params.marketName
        val count = params.count

        return when(params.sortType) {
            SortTypes.ASC -> mTradeDao.getAsc(marketName, count)
            SortTypes.DESC -> mTradeDao.getDesc(marketName, count)
        }.mapToTradeList()
            .takeIf { it.isNotEmpty() }
            ?.let { Result.Success(it) }
            ?: Result.Failure(NotFoundException())
    }


}