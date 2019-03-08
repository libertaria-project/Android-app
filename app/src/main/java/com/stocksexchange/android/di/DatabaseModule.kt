package com.stocksexchange.android.di

import androidx.room.Room
import com.stocksexchange.android.database.StocksExchangeDatabase
import com.stocksexchange.android.database.migrations.MIGRATIONS
import com.stocksexchange.android.database.tables.*
import com.stocksexchange.android.database.tables.CurrenciesTable
import com.stocksexchange.android.database.tables.DepositsTable
import com.stocksexchange.android.database.tables.OrdersTable
import com.stocksexchange.android.database.tables.TradesTable
import com.stocksexchange.android.database.tables.new.*
import org.koin.dsl.module.applicationContext

val databaseModule = applicationContext {

    bean {
        Room.databaseBuilder(
            get(),
            StocksExchangeDatabase::class.java,
            StocksExchangeDatabase.NAME
        ).addMigrations(*MIGRATIONS)
        .allowMainThreadQueries().build()
    }

    bean { get<StocksExchangeDatabase>().userDao }
    bean { get<StocksExchangeDatabase>().currencyMarketDao }
    bean { get<StocksExchangeDatabase>().favoriteCurrencyMarketDao }
    bean { get<StocksExchangeDatabase>().priceChartDataDao }
    bean { get<StocksExchangeDatabase>().orderDao }
    bean { get<StocksExchangeDatabase>().currencyDao }
    bean { get<StocksExchangeDatabase>().transactionDao }
    bean { get<StocksExchangeDatabase>().depositDao }
    bean { get<StocksExchangeDatabase>().settingsDao }
    bean { get<StocksExchangeDatabase>().orderbookDao }
    bean { get<StocksExchangeDatabase>().tradeDao }

    bean { get<StocksExchangeDatabase>().candleStickDao }
    bean { get<StocksExchangeDatabase>().newCurrencyDao }
    bean { get<StocksExchangeDatabase>().currencyPairDao }
    bean { get<StocksExchangeDatabase>().newDepositDao }
    bean { get<StocksExchangeDatabase>().newOrderbookDao }
    bean { get<StocksExchangeDatabase>().newOrderDao }
    bean { get<StocksExchangeDatabase>().profileInfoDao }
    bean { get<StocksExchangeDatabase>().tickerItemDao }
    bean { get<StocksExchangeDatabase>().newTradeDao }
    bean { get<StocksExchangeDatabase>().walletDao }
    bean { get<StocksExchangeDatabase>().withdrawalDao }

    bean { PriceChartDataTable }
    bean { CurrenciesTable }
    bean { CurrencyMarketsTable }
    bean { DepositsTable }
    bean { FavoriteCurrencyMarketsTable }
    bean { OrdersTable }
    bean { SettingsTable }
    bean { TransactionsTable }
    bean { UsersTable }
    bean { OrderbookTable }
    bean { TradesTable }

    // New
    bean { CandleSticksTable }
    bean { com.stocksexchange.android.database.tables.new.CurrenciesTable }
    bean { CurrencyPairsTable }
    bean { com.stocksexchange.android.database.tables.new.DepositsTable }
    bean { OrderbooksTable }
    bean { com.stocksexchange.android.database.tables.new.OrdersTable }
    bean { ProfileInfosTable }
    bean { TickerItemsTable }
    bean { com.stocksexchange.android.database.tables.new.TradesTable }
    bean { WalletsTable }
    bean { WithdrawalsTable }

}