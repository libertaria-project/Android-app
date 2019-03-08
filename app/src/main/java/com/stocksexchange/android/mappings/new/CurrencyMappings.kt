package com.stocksexchange.android.mappings.new

import com.stocksexchange.android.api.model.newapi.Currency
import com.stocksexchange.android.database.model.newd.DatabaseCurrency

fun Currency.mapToDatabaseCurrency(): DatabaseCurrency {
    return DatabaseCurrency(
        id = id,
        code = code,
        name = name,
        isActive = isActive,
        isDelisted = isDelisted,
        precision = precision,
        minimumWithdrawalAmount = minimumWithdrawalAmount,
        minimumDepositAmount = minimumDepositAmount,
        depositFeeCurrencyId = depositFeeCurrencyId,
        depositFee = depositFee,
        depositFeeInPercentage = depositFeeInPercentage,
        withdrawalFeeCurrencyId = withdrawalFeeCurrencyId,
        withdrawalFee = withdrawalFee,
        withdrawalFeeInPercentage = withdrawalFeeInPercentage,
        blockExplorerUrl = blockExplorerUrl
    )
}


fun List<Currency>.mapToDatabaseCurrencyList(): List<DatabaseCurrency> {
    return map { it.mapToDatabaseCurrency() }
}


fun DatabaseCurrency.mapToCurrency(): Currency {
    return Currency(
        id = id,
        code = code,
        name = name,
        isActive = isActive,
        isDelisted = isDelisted,
        precision = precision,
        minimumWithdrawalAmount = minimumWithdrawalAmount,
        minimumDepositAmount = minimumDepositAmount,
        depositFeeCurrencyId = depositFeeCurrencyId,
        depositFee = depositFee,
        depositFeeInPercentage = depositFeeInPercentage,
        withdrawalFeeCurrencyId = withdrawalFeeCurrencyId,
        withdrawalFee = withdrawalFee,
        withdrawalFeeInPercentage = withdrawalFeeInPercentage,
        blockExplorerUrl = blockExplorerUrl
    )
}


fun List<DatabaseCurrency>.mapToCurrencyList(): List<Currency> {
    return map { it.mapToCurrency() }
}