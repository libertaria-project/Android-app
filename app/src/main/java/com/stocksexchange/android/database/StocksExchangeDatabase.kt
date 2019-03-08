package com.stocksexchange.android.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.stocksexchange.android.database.StocksExchangeDatabase.Companion.VERSION
import com.stocksexchange.android.database.converters.*
import com.stocksexchange.android.database.daos.*
import com.stocksexchange.android.database.daos.CurrencyDao
import com.stocksexchange.android.database.daos.DepositDao
import com.stocksexchange.android.database.daos.OrderDao
import com.stocksexchange.android.database.daos.OrderbookDao
import com.stocksexchange.android.database.daos.TradeDao
import com.stocksexchange.android.database.daos.newd.*
import com.stocksexchange.android.database.model.*
import com.stocksexchange.android.database.model.DatabaseCurrency
import com.stocksexchange.android.database.model.DatabaseDeposit
import com.stocksexchange.android.database.model.DatabaseOrder
import com.stocksexchange.android.database.model.DatabaseOrderbook
import com.stocksexchange.android.database.model.DatabaseTrade
import com.stocksexchange.android.database.model.newd.*

/**
 * Main Room database this application uses to store data.
 */
@Database(entities = [
    DatabaseUser::class,
    DatabaseCurrencyMarket::class,
    DatabaseFavoriteCurrencyMarket::class,
    DatabasePriceChartData::class,
    DatabaseOrder::class,
    DatabaseCurrency::class,
    DatabaseTransaction::class,
    DatabaseDeposit::class,
    DatabaseSettings::class,
    DatabaseOrderbook::class,
    DatabaseTrade::class,
    DatabaseCandleStick::class,//new starting from this line
    com.stocksexchange.android.database.model.newd.DatabaseCurrency::class,
    DatabaseCurrencyPair::class,
    com.stocksexchange.android.database.model.newd.DatabaseDeposit::class,
    com.stocksexchange.android.database.model.newd.DatabaseOrderbook::class,
    com.stocksexchange.android.database.model.newd.DatabaseOrder::class,
    DatabaseProfileInfo::class,
    DatabaseTickerItem::class,
    com.stocksexchange.android.database.model.newd.DatabaseTrade::class,
    DatabaseWallet::class,
    DatabaseWithdrawal::class
], version = VERSION)
@TypeConverters(Converters::class, com.stocksexchange.android.database.converters.newd.Converters::class)
abstract class StocksExchangeDatabase : RoomDatabase() {


    companion object {

        const val NAME = "stocksexchange_database.db"
        const val VERSION = 34

    }


    abstract val userDao: UserDao
    abstract val currencyMarketDao: CurrencyMarketDao
    abstract val favoriteCurrencyMarketDao: FavoriteCurrencyMarketDao
    abstract val priceChartDataDao: PriceChartDataDao
    abstract val orderDao: OrderDao
    abstract val currencyDao: CurrencyDao
    abstract val transactionDao: TransactionDao
    abstract val depositDao: DepositDao
    abstract val settingsDao: SettingsDao
    abstract val orderbookDao: OrderbookDao
    abstract val tradeDao: TradeDao

    // New
    abstract val candleStickDao: CandleStickDao
    abstract val newCurrencyDao: com.stocksexchange.android.database.daos.newd.CurrencyDao
    abstract val currencyPairDao: CurrencyPairDao
    abstract val newDepositDao: com.stocksexchange.android.database.daos.newd.DepositDao
    abstract val newOrderbookDao: com.stocksexchange.android.database.daos.newd.OrderbookDao
    abstract val newOrderDao: com.stocksexchange.android.database.daos.newd.OrderDao
    abstract val profileInfoDao: ProfileInfoDao
    abstract val tickerItemDao: TickerItemDao
    abstract val newTradeDao: com.stocksexchange.android.database.daos.newd.TradeDao
    abstract val walletDao: WalletDao
    abstract val withdrawalDao: WithdrawalDao


}

