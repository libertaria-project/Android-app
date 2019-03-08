package com.stocksexchange.android.database.model.newd

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.stocksexchange.android.api.model.newapi.TransactionAddress
import com.stocksexchange.android.api.model.newapi.Withdrawal
import com.stocksexchange.android.database.model.newd.DatabaseWithdrawal.Companion.TABLE_NAME

/**
 * A Room database model for [Withdrawal] class.
 */
@Entity(tableName = TABLE_NAME)
data class DatabaseWithdrawal(
    @PrimaryKey @ColumnInfo(name = ID) var id: Long,
    @ColumnInfo(name = CURRENCY_ID) var currencyId: Int,
    @ColumnInfo(name = CURRENCY_CODE) var currencyCode: String,
    @ColumnInfo(name = AMOUNT) var amount: Double,
    @ColumnInfo(name = FEE) var fee: Double,
    @ColumnInfo(name = FEE_CURRENCY_ID) var feeCurrencyId: Int,
    @ColumnInfo(name = FEE_CURRENCY_CODE) var feeCurrencyCode: String,
    @ColumnInfo(name = STATUS_STR) var statusStr: String,
    @ColumnInfo(name = CREATION_TIMESTAMP) var creationTimestamp: Long,
    @ColumnInfo(name = UPDATE_TIMESTAMP) var updateTimestamp: Long,
    @ColumnInfo(name = TRANSACTION_ID) var transactionId: String?,
    @ColumnInfo(name = ADDRESS) var address: TransactionAddress?
) {

    companion object {

        const val TABLE_NAME = "withdrawals"

        const val ID = "id"
        const val CURRENCY_ID = "currency_id"
        const val CURRENCY_CODE = "currency_code"
        const val AMOUNT = "amount"
        const val FEE = "fee"
        const val FEE_CURRENCY_ID = "fee_currency_id"
        const val FEE_CURRENCY_CODE = "fee_currency_code"
        const val STATUS_STR = "status_str"
        const val CREATION_TIMESTAMP = "creation_timestamp"
        const val UPDATE_TIMESTAMP = "update_timestamp"
        const val TRANSACTION_ID = "transaction_id"
        const val ADDRESS = "address"

    }


    constructor(): this(
        id = -1L,
        currencyId = -1,
        currencyCode = "",
        amount = -1.0,
        fee = -1.0,
        feeCurrencyId = -1,
        feeCurrencyCode = "",
        statusStr = "",
        creationTimestamp = -1L,
        updateTimestamp = -1L,
        transactionId = null,
        address = null
    )

}