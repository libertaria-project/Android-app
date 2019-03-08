package com.stocksexchange.android.database.model.newd

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.stocksexchange.android.api.model.newapi.Deposit
import com.stocksexchange.android.database.model.newd.DatabaseDeposit.Companion.TABLE_NAME

/**
 * A Room database model for [Deposit] class.
 */
@Entity(tableName = TABLE_NAME)
data class DatabaseDeposit(
    @PrimaryKey @ColumnInfo(name = ID) var id: Long,
    @ColumnInfo(name = CURRENCY_ID) var currencyId: Int,
    @ColumnInfo(name = CURRENCY_CODE) var currencyCode: String,
    @ColumnInfo(name = AMOUNT) var amount: Double,
    @ColumnInfo(name = FEE) var fee: Double,
    @ColumnInfo(name = STATUS_STR) var statusStr: String,
    @ColumnInfo(name = TIMESTAMP) var timestamp: Long,
    @ColumnInfo(name = TRANSACTION_ID) var transactionId: String?,
    @ColumnInfo(name = CONFIRMATIONS) var confirmations: String
) {

    companion object {

        const val TABLE_NAME = "new_deposits"

        const val ID = "id"
        const val CURRENCY_ID = "currency_id"
        const val CURRENCY_CODE = "currency_code"
        const val AMOUNT = "amount"
        const val FEE = "fee"
        const val STATUS_STR = "status_str"
        const val TIMESTAMP = "timestamp"
        const val TRANSACTION_ID = "transaction_id"
        const val CONFIRMATIONS = "confirmations"

    }


    constructor(): this(
        id = -1L,
        currencyId = -1,
        currencyCode = "",
        amount = -1.0,
        fee = -1.0,
        statusStr = "",
        timestamp = -1L,
        transactionId = null,
        confirmations = ""
    )

}