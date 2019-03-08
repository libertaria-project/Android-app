package com.stocksexchange.android.di

import com.stocksexchange.android.data.repositories.candlesticks.CandleSticksRepository
import com.stocksexchange.android.data.repositories.candlesticks.CandleSticksRepositoryImpl
import com.stocksexchange.android.data.repositories.currencypairs.CurrencyPairsRepository
import com.stocksexchange.android.data.repositories.currencypairs.CurrencyPairsRepositoryImpl
import com.stocksexchange.android.data.repositories.orderbooks.OrderbooksRepository
import com.stocksexchange.android.data.repositories.orderbooks.OrderbooksRepositoryImpl
import com.stocksexchange.android.data.repositories.profileinfos.ProfileInfosRepository
import com.stocksexchange.android.data.repositories.profileinfos.ProfileInfosRepositoryImpl
import com.stocksexchange.android.data.repositories.tickeritems.TickerItemsRepository
import com.stocksexchange.android.data.repositories.tickeritems.TickerItemsRepositoryImpl
import com.stocksexchange.android.data.repositories.useradmission.UserAdmissionRepository
import com.stocksexchange.android.data.repositories.useradmission.UserAdmissionRepositoryImpl
import com.stocksexchange.android.data.repositories.wallets.WalletsRepository
import com.stocksexchange.android.data.repositories.wallets.WalletsRepositoryImpl
import com.stocksexchange.android.data.repositories.withdrawals.WithdrawalsRepository
import com.stocksexchange.android.data.repositories.withdrawals.WithdrawalsRepositoryImpl
import com.stocksexchange.android.repositories.pricechartdata.PriceChartDataCache
import com.stocksexchange.android.repositories.pricechartdata.PriceChartDataRepository
import com.stocksexchange.android.repositories.pricechartdata.PriceChartDataRepositoryImpl
import com.stocksexchange.android.repositories.currencies.CurrenciesCache
import com.stocksexchange.android.repositories.currencies.CurrenciesRepository
import com.stocksexchange.android.repositories.currencies.CurrenciesRepositoryImpl
import com.stocksexchange.android.repositories.currencymarkets.CurrencyMarketsCache
import com.stocksexchange.android.repositories.currencymarkets.CurrencyMarketsRepository
import com.stocksexchange.android.repositories.currencymarkets.CurrencyMarketsRepositoryImpl
import com.stocksexchange.android.repositories.deposits.DepositsCache
import com.stocksexchange.android.repositories.deposits.DepositsRepository
import com.stocksexchange.android.repositories.deposits.DepositsRepositoryImpl
import com.stocksexchange.android.repositories.orderbook.OrderbookCache
import com.stocksexchange.android.repositories.orderbook.OrderbookRepository
import com.stocksexchange.android.repositories.orderbook.OrderbookRepositoryImpl
import com.stocksexchange.android.repositories.orders.OrdersCache
import com.stocksexchange.android.repositories.orders.OrdersRepository
import com.stocksexchange.android.repositories.orders.OrdersRepositoryImpl
import com.stocksexchange.android.repositories.settings.SettingsCache
import com.stocksexchange.android.repositories.settings.SettingsRepository
import com.stocksexchange.android.repositories.settings.SettingsRepositoryImpl
import com.stocksexchange.android.repositories.trades.TradesCache
import com.stocksexchange.android.repositories.trades.TradesRepository
import com.stocksexchange.android.repositories.trades.TradesRepositoryImpl
import com.stocksexchange.android.repositories.transactions.TransactionsCache
import com.stocksexchange.android.repositories.transactions.TransactionsRepository
import com.stocksexchange.android.repositories.transactions.TransactionsRepositoryImpl
import com.stocksexchange.android.repositories.users.UsersCache
import com.stocksexchange.android.repositories.users.UsersRepository
import com.stocksexchange.android.repositories.users.UsersRepositoryImpl
import org.koin.dsl.module.applicationContext

val repositoriesModule = applicationContext {

    bean { UsersRepositoryImpl(get(), get(), get(), get()) as UsersRepository }
    bean { UsersCache() }

    bean { CurrencyMarketsRepositoryImpl(get(), get(), get(), get()) as CurrencyMarketsRepository }
    bean { CurrencyMarketsCache() }

    bean { PriceChartDataRepositoryImpl(get(), get(), get()) as PriceChartDataRepository }
    bean { PriceChartDataCache() }

    bean { OrdersRepositoryImpl(get(), get(), get()) as OrdersRepository }
    bean { OrdersCache() }

    bean { CurrenciesRepositoryImpl(get(), get(), get()) as CurrenciesRepository }
    bean { CurrenciesCache() }

    bean { TransactionsRepositoryImpl(get(), get(), get()) as TransactionsRepository }
    bean { TransactionsCache() }

    bean { DepositsRepositoryImpl(get(), get(), get()) as DepositsRepository }
    bean { DepositsCache() }

    bean { SettingsRepositoryImpl(get()) as SettingsRepository }
    bean { SettingsCache() }

    bean { OrderbookRepositoryImpl(get(), get(), get()) as OrderbookRepository }
    bean { OrderbookCache() }

    bean { TradesRepositoryImpl(get(), get(), get()) as TradesRepository }
    bean { TradesCache() }

    // New
    bean {
        CandleSticksRepositoryImpl(
            get(CANDLE_STICKS_SERVER_DATA_STORE),
            get(CANDLE_STICKS_DATABASE_DATA_STORE),
            get()
        ) as CandleSticksRepository
    }

    bean {
        com.stocksexchange.android.data.repositories.currencies.CurrenciesRepositoryImpl(
            get(CURRENCIES_SERVER_DATA_STORE),
            get(CURRENCIES_DATABASE_DATA_STORE),
            get()
        ) as com.stocksexchange.android.data.repositories.currencies.CurrenciesRepository
    }

    bean {
        CurrencyPairsRepositoryImpl(
            get(CURRENCY_PAIRS_SERVER_DATA_STORE),
            get(CURRENCY_PAIRS_DATABASE_DATA_STORE),
            get()
        ) as CurrencyPairsRepository
    }

    bean {
        com.stocksexchange.android.data.repositories.deposits.DepositsRepositoryImpl(
            get(DEPOSITS_SERVER_DATA_STORE),
            get(DEPOSITS_DATABASE_DATA_STORE),
            get()
        ) as com.stocksexchange.android.data.repositories.deposits.DepositsRepository
    }

    bean {
        OrderbooksRepositoryImpl(
            get(ORDERBOOKS_SERVER_DATA_STORE),
            get(ORDERBOOKS_DATABASE_DATA_STORE),
            get()
        ) as OrderbooksRepository
    }

    bean {
        com.stocksexchange.android.data.repositories.orders.OrdersRepositoryImpl(
            get(ORDERS_SERVER_DATA_STORE),
            get(ORDERS_DATABASE_DATA_STORE),
            get()
        ) as com.stocksexchange.android.data.repositories.orders.OrdersRepository
    }

    bean {
        ProfileInfosRepositoryImpl(
            get(PROFILE_INFOS_SERVER_DATA_STORE),
            get(PROFILE_INFOS_DATABASE_DATA_STORE),
            get()
        ) as ProfileInfosRepository
    }

    bean {
        TickerItemsRepositoryImpl(
            get(TICKER_ITEMS_SERVER_DATA_STORE),
            get(TICKER_ITEMS_DATABASE_DATA_STORE),
            get()
        ) as TickerItemsRepository
    }

    bean {
        com.stocksexchange.android.data.repositories.trades.TradesRepositoryImpl(
            get(TRADES_SERVER_DATA_STORE),
            get(TRADES_DATABASE_DATA_STORE),
            get()
        ) as com.stocksexchange.android.data.repositories.trades.TradesRepository
    }

    bean {
        UserAdmissionRepositoryImpl(
            get(USER_ADMISSION_SERVER_DATA_STORE),
            get()
        ) as UserAdmissionRepository
    }

    bean {
        WalletsRepositoryImpl(
            get(WALLETS_SERVER_DATA_STORE),
            get(WALLETS_DATABASE_DATA_STORE),
            get()
        ) as WalletsRepository
    }

    bean {
        WithdrawalsRepositoryImpl(
            get(WITHDRAWALS_SERVER_DATA_STORE),
            get(WITHDRAWALS_DATABASE_DATA_STORE),
            get()
        ) as WithdrawalsRepository
    }

}