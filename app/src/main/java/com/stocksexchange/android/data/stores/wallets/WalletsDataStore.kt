package com.stocksexchange.android.data.stores.wallets

import com.stocksexchange.android.api.model.newapi.Wallet
import com.stocksexchange.android.data.base.WalletsData
import com.stocksexchange.android.model.Result

interface WalletsDataStore : WalletsData<Result<List<Wallet>>>