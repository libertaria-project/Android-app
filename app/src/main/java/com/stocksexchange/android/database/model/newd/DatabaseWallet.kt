package com.stocksexchange.android.database.model.newd

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.stocksexchange.android.api.model.newapi.TransactionAddress
import com.stocksexchange.android.api.model.newapi.Wallet
import com.stocksexchange.android.database.model.newd.DatabaseWallet.Companion.CURRENCY_ID
import com.stocksexchange.android.database.model.newd.DatabaseWallet.Companion.ID
import com.stocksexchange.android.database.model.newd.DatabaseWallet.Companion.TABLE_NAME

/**
 * A Room database model for [Wallet] class.
 */
@Entity(tableName = TABLE_NAME)
data class DatabaseWallet(
    @ColumnInfo(name = ID) var id: Long?,
    @PrimaryKey @ColumnInfo(name = CURRENCY_ID) var currencyId: Int,
    @ColumnInfo(name = IS_DELISTED) var isDelisted: Boolean,
    @ColumnInfo(name = IS_DISABLED) var isDisabled: Boolean,
    @ColumnInfo(name = IS_DEPOSITING_DISABLED) var isDepositingDisabled: Boolean,
    @ColumnInfo(name = CURRENCY_NAME) var currencyName: String,
    @ColumnInfo(name = CURRENCY_CODE) var currencyCode: String,
    @ColumnInfo(name = CURRENT_BALANCE) var currentBalance: Double,
    @ColumnInfo(name = FROZEN_BALANCE) var frozenBalance: Double,
    @ColumnInfo(name = BONUS_BALANCE) var bonusBalance: Double,
    @ColumnInfo(name = TOTAL_BALANCE) var totalBalance: Double,
    @ColumnInfo(name = DEPOSIT_ADDRESS) var depositAddress: TransactionAddress?
) {

    companion object {

        const val TABLE_NAME = "wallets"

        const val ID = "id"
        const val CURRENCY_ID = "currency_id"
        const val IS_DELISTED = "is_delisted"
        const val IS_DISABLED = "is_disabled"
        const val IS_DEPOSITING_DISABLED = "is_depositing_disabled"
        const val CURRENCY_NAME = "currency_name"
        const val CURRENCY_CODE = "currency_code"
        const val CURRENT_BALANCE = "current_balance"
        const val FROZEN_BALANCE = "frozen_balance"
        const val BONUS_BALANCE = "bonus_balance"
        const val TOTAL_BALANCE = "total_balance"
        const val DEPOSIT_ADDRESS = "deposit_address"

    }


    constructor(): this(
        id = null,
        currencyId = -1,
        isDelisted = false,
        isDisabled = false,
        isDepositingDisabled = false,
        currencyName = "",
        currencyCode = "",
        currentBalance = -1.0,
        frozenBalance = -1.0,
        bonusBalance = -1.0,
        totalBalance = -1.0,
        depositAddress = null
    )

}