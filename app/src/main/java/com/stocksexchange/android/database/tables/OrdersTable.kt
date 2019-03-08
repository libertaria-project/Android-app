package com.stocksexchange.android.database.tables

import com.stocksexchange.android.api.model.Order
import com.stocksexchange.android.api.model.OrderParameters
import com.stocksexchange.android.database.daos.OrderDao
import com.stocksexchange.android.mappings.mapToDatabaseOrder
import com.stocksexchange.android.mappings.mapToDatabaseOrderList
import com.stocksexchange.android.mappings.mapToOrderList
import com.stocksexchange.android.database.tables.base.BaseTable
import com.stocksexchange.android.database.tables.exceptions.NotFoundException
import com.stocksexchange.android.model.Result
import com.stocksexchange.android.model.SortTypes
import org.koin.standalone.inject

/**
 * A database table holding orders related functionality.
 */
object OrdersTable : BaseTable() {


    private val mOrderDao: OrderDao by inject()




    /**
     * Saves an order within the database.
     *
     * @param order The order to save
     */
    fun save(order: Order) {
        mOrderDao.insert(order.mapToDatabaseOrder())
    }


    /**
     * Saves orders within the database.
     *
     * @param orders The orders to save
     */
    fun save(orders: List<Order>) {
        mOrderDao.insert(orders.mapToDatabaseOrderList())
    }


    /**
     * Deletes a specific order from the database.
     *
     * @param order The order to delete
     */
    fun delete(order: Order) {
        mOrderDao.delete(order.mapToDatabaseOrder())
    }


    /**
     * Deletes all orders with a specific type.
     *
     * @param type The type of orders to delete
     */
    fun delete(type: String) {
        mOrderDao.delete(type)
    }


    /**
     * Deletes all orders within the database.
     */
    fun deleteAll() {
        mOrderDao.deleteAll()
    }


    /**
     * Searches orders within the database based on the passed
     * parameters.
     *
     * @param params The order parameters for data loading
     *
     * @return The searched orders or [NotFoundException] error
     * packaged inside an instance of [Result] class
     */
    fun search(params: OrderParameters): Result<List<Order>> {
        val query = params.lowercasedSearchQuery
        val type = params.type.name
        val count = params.count

        return when(params.sortType) {
            SortTypes.ASC -> mOrderDao.searchAsc(query, type, count)
            SortTypes.DESC -> mOrderDao.searchDesc(query, type, count)
        }.mapToOrderList()
            .takeIf { it.isNotEmpty() }
            ?.let { Result.Success(it) }
            ?: Result.Failure(NotFoundException())
    }


    /**
     * Retrieves user orders based on the passed parameters.
     *
     * @param params The order parameters for data loading
     *
     * @return The users orders or [NotFoundException] error
     * packaged inside an instance of [Result] class
     */
    fun getUserOrders(params: OrderParameters): Result<List<Order>> {
        val type = params.type.name
        val count = params.count

        return when(params.sortType) {
            SortTypes.ASC -> mOrderDao.getAsc(type, count)
            SortTypes.DESC -> mOrderDao.getDesc(type, count)
        }.mapToOrderList()
            .takeIf { it.isNotEmpty() }
            ?.let { Result.Success(it) }
            ?: Result.Failure(NotFoundException())
    }


}