package com.stocksexchange.android.mappings.new

import com.stocksexchange.android.api.model.newapi.Wallet
import com.stocksexchange.android.database.model.newd.DatabaseWallet

fun Wallet.mapToDatabaseWallet(): DatabaseWallet {
    return DatabaseWallet(
        id = id,
        currencyId = currencyId,
        isDelisted = isDelisted,
        isDisabled = isDisabled,
        isDepositingDisabled = isDepositingDisabled,
        currencyName = currencyName,
        currencyCode = currencyCode,
        currentBalance = currentBalance,
        frozenBalance = frozenBalance,
        bonusBalance = bonusBalance,
        totalBalance = totalBalance,
        depositAddress = depositAddress
    )
}


fun List<Wallet>.mapToDatabaseWalletList(): List<DatabaseWallet> {
    return map { it.mapToDatabaseWallet() }
}


fun DatabaseWallet.mapToWallet(): Wallet {
    return Wallet(
        id = id,
        currencyId = currencyId,
        isDelisted = isDelisted,
        isDisabled = isDisabled,
        isDepositingDisabled = isDepositingDisabled,
        currencyName = currencyName,
        currencyCode = currencyCode,
        currentBalance = currentBalance,
        frozenBalance = frozenBalance,
        bonusBalance = bonusBalance,
        totalBalance = totalBalance,
        depositAddress = depositAddress
    )
}


fun List<DatabaseWallet>.mapToWalletList(): List<Wallet> {
    return map { it.mapToWallet() }
}