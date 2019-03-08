package com.stocksexchange.android.mappings.new

import com.stocksexchange.android.api.model.newapi.Withdrawal
import com.stocksexchange.android.database.model.newd.DatabaseWithdrawal

fun Withdrawal.mapToDatabaseWithdrawal(): DatabaseWithdrawal {
    return DatabaseWithdrawal(
        id = id,
        currencyId = currencyId,
        currencyCode = currencyCode,
        amount = amount,
        fee = fee,
        feeCurrencyId = feeCurrencyId,
        feeCurrencyCode = feeCurrencyCode,
        statusStr = statusStr,
        creationTimestamp = creationTimestamp,
        updateTimestamp = updateTimestamp,
        transactionId = transactionId,
        address = address
    )
}


fun List<Withdrawal>.mapToDatabaseWithdrawalList(): List<DatabaseWithdrawal> {
    return map { it.mapToDatabaseWithdrawal() }
}


fun DatabaseWithdrawal.mapToWithdrawal(): Withdrawal {
    return Withdrawal(
        id = id,
        currencyId = currencyId,
        currencyCode = currencyCode,
        amount = amount,
        fee = fee,
        feeCurrencyId = feeCurrencyId,
        feeCurrencyCode = feeCurrencyCode,
        statusStr = statusStr,
        creationTimestamp = creationTimestamp,
        updateTimestamp = updateTimestamp,
        transactionId = transactionId,
        address = address
    )
}


fun List<DatabaseWithdrawal>.mapToWithdrawalList(): List<Withdrawal> {
    return map { it.mapToWithdrawal() }
}