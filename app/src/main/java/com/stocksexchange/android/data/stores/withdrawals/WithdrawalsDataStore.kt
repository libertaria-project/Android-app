package com.stocksexchange.android.data.stores.withdrawals

import com.stocksexchange.android.api.model.newapi.Withdrawal
import com.stocksexchange.android.data.base.WithdrawalsData
import com.stocksexchange.android.model.Result

interface WithdrawalsDataStore : WithdrawalsData<Result<List<Withdrawal>>>