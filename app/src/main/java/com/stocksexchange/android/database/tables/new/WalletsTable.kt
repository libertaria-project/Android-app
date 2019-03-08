package com.stocksexchange.android.database.tables.new

import com.stocksexchange.android.api.model.newapi.SortOrders
import com.stocksexchange.android.api.model.newapi.Wallet
import com.stocksexchange.android.api.model.newapi.WalletBalanceTypes
import com.stocksexchange.android.api.model.newapi.WalletParameters
import com.stocksexchange.android.database.daos.newd.WalletDao
import com.stocksexchange.android.database.model.newd.DatabaseWallet
import com.stocksexchange.android.database.tables.base.BaseTable
import com.stocksexchange.android.database.tables.exceptions.NotFoundException
import com.stocksexchange.android.mappings.new.mapToDatabaseWalletList
import com.stocksexchange.android.mappings.new.mapToWalletList
import com.stocksexchange.android.model.Result
import org.koin.standalone.inject

/**
 * A database table holding wallets related functionality.
 */
object WalletsTable : BaseTable() {


    private val mWalletDao: WalletDao by inject()




    /**
     * Saves wallets inside the database.
     *
     * @param wallets The wallets to save
     */
    fun save(wallets: List<Wallet>) {
        mWalletDao.insert(wallets.mapToDatabaseWalletList())
    }


    /**
     * Deletes all wallets from the database.
     */
    fun deleteAll() {
        mWalletDao.deleteAll()
    }


    /**
     * Retrieves all wallets from the database.
     *
     * @param parameters The parameters for fetching wallets
     *
     * @return The wallets or [NotFoundException] error packaged
     * inside an instance of [Result] class
     */
    fun getAll(parameters: WalletParameters): Result<List<Wallet>> {
        val sortColumn = when(parameters.sortColumn) {
            WalletBalanceTypes.CURRENT -> DatabaseWallet.CURRENT_BALANCE
            WalletBalanceTypes.FROZEN -> DatabaseWallet.FROZEN_BALANCE
            WalletBalanceTypes.BONUS -> DatabaseWallet.BONUS_BALANCE
            WalletBalanceTypes.TOTAL -> DatabaseWallet.TOTAL_BALANCE
        }

        return when(parameters.sortOrder) {
            SortOrders.ASC -> mWalletDao.getAllAsc(sortColumn)
            SortOrders.DESC -> mWalletDao.getAllDesc(sortColumn)
        }.mapToWalletList()
            .takeIf { it.isNotEmpty() }
            ?.let { Result.Success(it) }
            ?: Result.Failure(NotFoundException())
    }


}