package com.stocksexchange.android.data.stores.deposits

import com.stocksexchange.android.api.model.newapi.Deposit
import com.stocksexchange.android.data.base.DepositsData
import com.stocksexchange.android.model.Result

interface DepositsDataStore : DepositsData<Result<List<Deposit>>>