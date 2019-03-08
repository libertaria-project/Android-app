package com.stocksexchange.android.mappings.new

import com.stocksexchange.android.api.model.newapi.Deposit
import com.stocksexchange.android.database.model.newd.DatabaseDeposit

fun Deposit.mapToDatabaseDeposit(): DatabaseDeposit {
    return DatabaseDeposit(
        id = id,
        currencyId = currencyId,
        currencyCode = currencyCode,
        amount = amount,
        fee = fee,
        statusStr = statusStr,
        timestamp = timestamp,
        transactionId = transactionId,
        confirmations = confirmations
    )
}


fun List<Deposit>.mapToDatabaseDepositList(): List<DatabaseDeposit> {
    return map { it.mapToDatabaseDeposit() }
}


fun DatabaseDeposit.mapToDeposit(): Deposit {
    return Deposit(
        id = id,
        currencyId = currencyId,
        currencyCode = currencyCode,
        amount = amount,
        fee = fee,
        statusStr = statusStr,
        timestamp = timestamp,
        transactionId = transactionId,
        confirmations = confirmations
    )
}


fun List<DatabaseDeposit>.mapToDepositList(): List<Deposit> {
    return map { it.mapToDeposit() }
}