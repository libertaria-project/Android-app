package com.stocksexchange.android.database.tables.new

import com.stocksexchange.android.api.model.newapi.Order
import com.stocksexchange.android.api.model.newapi.OrderParameters
import com.stocksexchange.android.api.model.newapi.SortOrders
import com.stocksexchange.android.database.daos.newd.OrderDao
import com.stocksexchange.android.database.tables.base.BaseTable
import com.stocksexchange.android.database.tables.exceptions.NotFoundException
import com.stocksexchange.android.mappings.new.mapToDatabaseOrder
import com.stocksexchange.android.mappings.new.mapToDatabaseOrderList
import com.stocksexchange.android.mappings.new.mapToOrderList
import com.stocksexchange.android.model.Result
import org.koin.standalone.inject

/**
 * A database table holding orders related functionality.
 */
object OrdersTable : BaseTable() {


    private val mOrderDao: OrderDao by inject()




    /**
     * Saves orders inside the database.
     *
     * @param order The order to save
     */
    fun save(order: Order) {
        mOrderDao.insert(order.mapToDatabaseOrder())
    }


    /**
     * Saves orders inside the database.
     *
     * @param orders The orders to save
     */
    fun save(orders: List<Order>) {
        mOrderDao.insert(orders.mapToDatabaseOrderList())
    }


    /**
     * Deletes an order inside the database.
     *
     * @param order The order to delete
     */
    fun delete(order: Order) {
        mOrderDao.delete(order.mapToDatabaseOrder())
    }


    /**
     * Deletes orders with a particular status from the database.
     *
     * @param status The status of orders to delete
     */
    fun delete(status: String) {
        mOrderDao.delete(status)
    }


    /**
     * Deletes orders from the database.
     */
    fun deleteAll() {
        mOrderDao.deleteAll()
    }


    /**
     * Retrieves orders from the database.
     *
     * @param params The parameters for loading orders
     *
     * @return The orders or [NotFoundException] error packaged
     * inside an instance of [Result] class
     */
    fun get(params: OrderParameters): Result<List<Order>> {
        val status = params.status.name
        val limit = params.limit
        val offset = params.offset

        return when(params.sortOrder) {
            SortOrders.ASC -> mOrderDao.getAsc(status, limit, offset)
            SortOrders.DESC -> mOrderDao.getDesc(status, limit, offset)
        }.mapToOrderList()
            .takeIf { it.isNotEmpty() }
            ?.let { Result.Success(it) }
            ?: Result.Failure(NotFoundException())
    }


}