package com.stocksexchange.android.database.daos.newd

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.stocksexchange.android.database.model.newd.DatabaseWallet

/**
 * A Room data access object used for interacting
 * with a [DatabaseWallet] model class.
 */
@Dao
interface WalletDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(wallets: List<DatabaseWallet>)


    @Query("DELETE FROM ${DatabaseWallet.TABLE_NAME}")
    fun deleteAll()


    @Query(
        "SELECT * FROM ${DatabaseWallet.TABLE_NAME} " +
        "ORDER BY :sortColumn ASC"
    )
    fun getAllAsc(sortColumn: String): List<DatabaseWallet>


    @Query(
        "SELECT * FROM ${DatabaseWallet.TABLE_NAME} " +
        "ORDER BY :sortColumn DESC"
    )
    fun getAllDesc(sortColumn: String): List<DatabaseWallet>


}