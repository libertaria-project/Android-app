package com.stocksexchange.android.di

import com.stocksexchange.android.data.stores.candlesticks.CandleSticksDataStore
import com.stocksexchange.android.data.stores.candlesticks.CandleSticksDatabaseDataStore
import com.stocksexchange.android.data.stores.candlesticks.CandleSticksServerDataStore
import com.stocksexchange.android.data.stores.currencypairs.CurrencyPairsDataStore
import com.stocksexchange.android.data.stores.currencypairs.CurrencyPairsDatabaseDataStore
import com.stocksexchange.android.data.stores.currencypairs.CurrencyPairsServerDataStore
import com.stocksexchange.android.data.stores.orderbooks.OrderbooksDataStore
import com.stocksexchange.android.data.stores.orderbooks.OrderbooksDatabaseDataStore
import com.stocksexchange.android.data.stores.orderbooks.OrderbooksServerDataStore
import com.stocksexchange.android.data.stores.profileinfos.ProfileInfosDataStore
import com.stocksexchange.android.data.stores.profileinfos.ProfileInfosDatabaseDataStore
import com.stocksexchange.android.data.stores.profileinfos.ProfileInfosServerDataStore
import com.stocksexchange.android.data.stores.tickeritems.TickerItemsDataStore
import com.stocksexchange.android.data.stores.tickeritems.TickerItemsDatabaseDataStore
import com.stocksexchange.android.data.stores.tickeritems.TickerItemsServerDataStore
import com.stocksexchange.android.data.stores.useradmission.UserAdmissionDataStore
import com.stocksexchange.android.data.stores.useradmission.UserAdmissionServerDataStore
import com.stocksexchange.android.data.stores.wallets.WalletsDataStore
import com.stocksexchange.android.data.stores.wallets.WalletsDatabaseDataStore
import com.stocksexchange.android.data.stores.wallets.WalletsServerDataStore
import com.stocksexchange.android.data.stores.withdrawals.WithdrawalsDataStore
import com.stocksexchange.android.data.stores.withdrawals.WithdrawalsDatabaseDataStore
import com.stocksexchange.android.data.stores.withdrawals.WithdrawalsServerDataStore
import com.stocksexchange.android.datastores.pricechartdata.PriceChartDataDataStore
import com.stocksexchange.android.datastores.pricechartdata.PriceChartDataDatabaseDataStore
import com.stocksexchange.android.datastores.pricechartdata.PriceChartDataServerDataStore
import com.stocksexchange.android.datastores.currencies.CurrenciesDataStore
import com.stocksexchange.android.datastores.currencies.CurrenciesDatabaseDataStore
import com.stocksexchange.android.datastores.currencies.CurrenciesServerDataStore
import com.stocksexchange.android.datastores.currencymarkets.CurrencyMarketsDataStore
import com.stocksexchange.android.datastores.currencymarkets.CurrencyMarketsDatabaseDataStore
import com.stocksexchange.android.datastores.currencymarkets.CurrencyMarketsServerDataStore
import com.stocksexchange.android.datastores.deposits.DepositsDataStore
import com.stocksexchange.android.datastores.deposits.DepositsDatabaseDataStore
import com.stocksexchange.android.datastores.deposits.DepositsServerDataStore
import com.stocksexchange.android.datastores.favoritecurrencymarkets.FavoriteCurrencyMarketsDataStore
import com.stocksexchange.android.datastores.favoritecurrencymarkets.FavoriteCurrencyMarketsDatabaseDataStore
import com.stocksexchange.android.datastores.orderbook.OrderbookDataStore
import com.stocksexchange.android.datastores.orderbook.OrderbookDatabaseDataStore
import com.stocksexchange.android.datastores.orderbook.OrderbookServerDataStore
import com.stocksexchange.android.datastores.orders.OrdersDataStore
import com.stocksexchange.android.datastores.orders.OrdersDatabaseDataStore
import com.stocksexchange.android.datastores.orders.OrdersServerDataStore
import com.stocksexchange.android.datastores.settings.SettingsDataStore
import com.stocksexchange.android.datastores.settings.SettingsDatabaseDataStore
import com.stocksexchange.android.datastores.trades.TradesDataStore
import com.stocksexchange.android.datastores.trades.TradesDatabaseDataStore
import com.stocksexchange.android.datastores.trades.TradesServerDataStore
import com.stocksexchange.android.datastores.transactions.TransactionsDataStore
import com.stocksexchange.android.datastores.transactions.TransactionsDatabaseDataStore
import com.stocksexchange.android.datastores.transactions.TransactionsServerDataStore
import com.stocksexchange.android.datastores.users.UsersDataStore
import com.stocksexchange.android.datastores.users.UsersDatabaseDataStore
import com.stocksexchange.android.datastores.users.UsersServerDataStore
import org.koin.dsl.module.applicationContext

const val CANDLE_STICKS_DATABASE_DATA_STORE = "candle_sticks_database_data_store"
const val CANDLE_STICKS_SERVER_DATA_STORE = "candle_sticks_server_data_store"

const val CURRENCIES_DATABASE_DATA_STORE = "currencies_database_data_store"
const val CURRENCIES_SERVER_DATA_STORE = "currencies_server_data_store"

const val CURRENCY_PAIRS_DATABASE_DATA_STORE = "currency_pairs_database_data_store"
const val CURRENCY_PAIRS_SERVER_DATA_STORE = "currency_pairs_server_data_store"

const val DEPOSITS_DATABASE_DATA_STORE = "deposits_database_data_store"
const val DEPOSITS_SERVER_DATA_STORE = "deposits_server_data_store"

const val ORDERBOOKS_DATABASE_DATA_STORE = "orderbooks_database_data_store"
const val ORDERBOOKS_SERVER_DATA_STORE = "orderbooks_server_data_store"

const val ORDERS_DATABASE_DATA_STORE = "orders_database_data_store"
const val ORDERS_SERVER_DATA_STORE = "orders_server_data_store"

const val PROFILE_INFOS_DATABASE_DATA_STORE = "profile_infos_database_data_store"
const val PROFILE_INFOS_SERVER_DATA_STORE = "profile_infos_server_data_store"

const val TICKER_ITEMS_DATABASE_DATA_STORE = "ticker_items_database_data_store"
const val TICKER_ITEMS_SERVER_DATA_STORE = "ticker_items_server_data_store"

const val TRADES_DATABASE_DATA_STORE = "trades_database_data_store"
const val TRADES_SERVER_DATA_STORE = "trades_server_data_store"

const val USER_ADMISSION_SERVER_DATA_STORE = "user_admission_server_data_store"

const val WALLETS_DATABASE_DATA_STORE = "wallets_database_data_store"
const val WALLETS_SERVER_DATA_STORE = "wallets_server_data_store"

const val WITHDRAWALS_DATABASE_DATA_STORE = "withdrawals_database_data_store"
const val WITHDRAWALS_SERVER_DATA_STORE = "withdrawals_server_data_store"

val dataStoresModule = applicationContext {

    bean { UsersDatabaseDataStore(get()) }
    bean { UsersServerDataStore(get()) as UsersDataStore }

    bean { CurrencyMarketsDatabaseDataStore(get()) }
    bean { CurrencyMarketsServerDataStore(get()) as CurrencyMarketsDataStore }

    bean { FavoriteCurrencyMarketsDatabaseDataStore(get()) as FavoriteCurrencyMarketsDataStore }

    bean { PriceChartDataDatabaseDataStore(get()) }
    bean { PriceChartDataServerDataStore(get()) as PriceChartDataDataStore }

    bean { OrdersDatabaseDataStore(get()) }
    bean { OrdersServerDataStore(get()) as OrdersDataStore }

    bean { CurrenciesDatabaseDataStore(get()) }
    bean { CurrenciesServerDataStore(get()) as CurrenciesDataStore }

    bean { TransactionsDatabaseDataStore(get()) }
    bean { TransactionsServerDataStore(get()) as TransactionsDataStore }

    bean { DepositsDatabaseDataStore(get()) }
    bean { DepositsServerDataStore(get()) as DepositsDataStore }

    bean { SettingsDatabaseDataStore(get()) as SettingsDataStore }

    bean { OrderbookDatabaseDataStore(get()) }
    bean { OrderbookServerDataStore(get()) as OrderbookDataStore }

    bean { TradesDatabaseDataStore(get()) }
    bean { TradesServerDataStore(get()) as TradesDataStore }

    // New
    bean(CANDLE_STICKS_DATABASE_DATA_STORE) { CandleSticksDatabaseDataStore(get()) as CandleSticksDataStore }
    bean(CANDLE_STICKS_SERVER_DATA_STORE) { CandleSticksServerDataStore(get()) as CandleSticksDataStore }

    bean(CURRENCIES_DATABASE_DATA_STORE) { com.stocksexchange.android.data.stores.currencies.CurrenciesDatabaseDataStore(get()) as com.stocksexchange.android.data.stores.currencies.CurrenciesDataStore }
    bean(CURRENCIES_SERVER_DATA_STORE) { com.stocksexchange.android.data.stores.currencies.CurrenciesServerDataStore(get()) as com.stocksexchange.android.data.stores.currencies.CurrenciesDataStore }

    bean(CURRENCY_PAIRS_DATABASE_DATA_STORE) { CurrencyPairsDatabaseDataStore(get()) as CurrencyPairsDataStore }
    bean(CURRENCY_PAIRS_SERVER_DATA_STORE) { CurrencyPairsServerDataStore(get()) as CurrencyPairsDataStore }

    bean(DEPOSITS_DATABASE_DATA_STORE) { com.stocksexchange.android.data.stores.deposits.DepositsDatabaseDataStore(get()) as com.stocksexchange.android.data.stores.deposits.DepositsDataStore }
    bean(DEPOSITS_SERVER_DATA_STORE) { com.stocksexchange.android.data.stores.deposits.DepositsServerDataStore(get()) as com.stocksexchange.android.data.stores.deposits.DepositsDataStore }

    bean(ORDERBOOKS_DATABASE_DATA_STORE) { OrderbooksDatabaseDataStore(get()) as OrderbooksDataStore }
    bean(ORDERBOOKS_SERVER_DATA_STORE) { OrderbooksServerDataStore(get()) as OrderbooksDataStore }

    bean(ORDERS_DATABASE_DATA_STORE) { com.stocksexchange.android.data.stores.orders.OrdersDatabaseDataStore(get()) as com.stocksexchange.android.data.stores.orders.OrdersDataStore }
    bean(ORDERS_SERVER_DATA_STORE) { com.stocksexchange.android.data.stores.orders.OrdersServerDataStore(get()) as com.stocksexchange.android.data.stores.orders.OrdersDataStore }

    bean(PROFILE_INFOS_DATABASE_DATA_STORE) { ProfileInfosDatabaseDataStore(get()) as ProfileInfosDataStore }
    bean(PROFILE_INFOS_SERVER_DATA_STORE) { ProfileInfosServerDataStore(get()) as ProfileInfosDataStore }

    bean(TICKER_ITEMS_DATABASE_DATA_STORE) { TickerItemsDatabaseDataStore(get()) as TickerItemsDataStore }
    bean(TICKER_ITEMS_SERVER_DATA_STORE) { TickerItemsServerDataStore(get()) as TickerItemsDataStore }

    bean(TRADES_DATABASE_DATA_STORE) { com.stocksexchange.android.data.stores.trades.TradesDatabaseDataStore(get()) as com.stocksexchange.android.data.stores.trades.TradesDataStore }
    bean(TRADES_SERVER_DATA_STORE) { com.stocksexchange.android.data.stores.trades.TradesServerDataStore(get()) as com.stocksexchange.android.data.stores.trades.TradesDataStore }

    bean(USER_ADMISSION_SERVER_DATA_STORE) { UserAdmissionServerDataStore(get()) as UserAdmissionDataStore }

    bean(WALLETS_DATABASE_DATA_STORE) { WalletsDatabaseDataStore(get()) as WalletsDataStore }
    bean(WALLETS_SERVER_DATA_STORE) { WalletsServerDataStore(get()) as WalletsDataStore }

    bean(WITHDRAWALS_DATABASE_DATA_STORE) { WithdrawalsDatabaseDataStore(get()) as WithdrawalsDataStore }
    bean(WITHDRAWALS_SERVER_DATA_STORE) { WithdrawalsServerDataStore(get()) as WithdrawalsDataStore }

}