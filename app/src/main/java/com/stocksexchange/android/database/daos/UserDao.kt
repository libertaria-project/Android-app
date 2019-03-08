package com.stocksexchange.android.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import androidx.room.Query
import com.stocksexchange.android.database.model.DatabaseUser

/**
 * A Room data access object used for interacting
 * with [DatabaseUser] model class.
 */
@Dao
interface UserDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg users: DatabaseUser)


    @Query("SELECT * FROM ${DatabaseUser.TABLE_NAME}")
    fun getAll(): List<DatabaseUser>


    @Query(
        "SELECT * FROM ${DatabaseUser.TABLE_NAME} " +
        "WHERE ${DatabaseUser.USER_NAME} = :userName"
    )
    fun get(userName: String): DatabaseUser?


    @Update
    fun update(vararg users: DatabaseUser)


    @Delete
    fun delete(vararg users: DatabaseUser)


    @Query(
        "DELETE FROM ${DatabaseUser.TABLE_NAME} " +
        "WHERE ${DatabaseUser.USER_NAME} = :userName"
    )
    fun deleteUser(userName: String)


}