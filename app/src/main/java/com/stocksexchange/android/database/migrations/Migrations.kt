package com.stocksexchange.android.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

private val MIGRATION_1_2: Migration = object : Migration(1, 2) {

    override fun migrate(database: SupportSQLiteDatabase) {
        with(database) {
            execSQL(
                "CREATE TABLE IF NOT EXISTS orders_new " +
                "(id INTEGER NOT NULL, market_name TEXT NOT NULL, currency TEXT NOT NULL, " +
                "market TEXT NOT NULL, type TEXT NOT NULL, trade_type TEXT NOT NULL, " +
                "amount REAL NOT NULL, buy_amount REAL NOT NULL, sell_amount REAL NOT NULL, " +
                "rate REAL NOT NULL, timestamp INTEGER NOT NULL, PRIMARY KEY(id))"
            )
            execSQL(
                "INSERT INTO orders_new " +
                "(id, market_name, currency, market, type, " +
                "trade_type, amount, buy_amount, sell_amount, rate, timestamp) " +
                "SELECT id, market_name, \"\", \"\", type, trade_type, amount, " +
                "buy_amount, sell_amount, rate, timestamp FROM orders"
            )
            execSQL("DROP TABLE orders")
            execSQL("ALTER TABLE orders_new RENAME TO orders")
        }
    }

}


private val MIGRATION_2_3: Migration = object : Migration(2, 3) {

    override fun migrate(database: SupportSQLiteDatabase) {
        with(database) {
            execSQL(
                "CREATE TABLE IF NOT EXISTS currencies_new " +
                "(name TEXT NOT NULL, long_name TEXT NOT NULL, minimum_withdrawal_amount REAL NOT NULL, " +
                "minimum_deposit_amount REAL NOT NULL, deposit_fee_currency TEXT NOT NULL, " +
                "deposit_fee REAL NOT NULL, withdrawal_fee_currency TEXT NOT NULL, withdrawal_fee REAL NOT NULL, " +
                "block_explorer_url TEXT NOT NULL, is_active INTEGER NOT NULL, PRIMARY KEY(name))"
            )
            execSQL(
                "INSERT INTO currencies_new " +
                "(name, long_name, minimum_withdrawal_amount, minimum_deposit_amount, deposit_fee_currency," +
                "deposit_fee, withdrawal_fee_currency, withdrawal_fee, block_explorer_url, is_active) " +
                "SELECT name, long_name, minimum_withdrawal_amount, minimum_deposit_amount, deposit_fee_currency," +
                "deposit_fee, withdrawal_fee_currency, withdrawal_fee, \"\", is_active FROM currencies"
            )
            execSQL("DROP TABLE currencies")
            execSQL("ALTER TABLE currencies_new RENAME TO currencies")
        }
    }

}


private val MIGRATION_3_4: Migration = object : Migration(3, 4) {

    override fun migrate(database: SupportSQLiteDatabase) {
        with(database) {
            execSQL(
                "CREATE TABLE IF NOT EXISTS orders_new " +
                "(id INTEGER NOT NULL, market_name TEXT NOT NULL, currency TEXT NOT NULL, " +
                "market TEXT NOT NULL, type TEXT NOT NULL, trade_type TEXT NOT NULL, " +
                "amount REAL NOT NULL, original_amount REAL NOT NULL, buy_amount REAL NOT NULL, " +
                "sell_amount REAL NOT NULL, rate REAL NOT NULL, rates_map TEXT NOT NULL, " +
                "timestamp INTEGER NOT NULL, PRIMARY KEY(id))"
            )
            execSQL(
                "INSERT INTO orders_new " +
                "(id, market_name, currency, market, type, trade_type, amount, " +
                "original_amount, buy_amount, sell_amount, rate, rates_map, timestamp) " +
                "SELECT id, market_name, currency, market, type, trade_type, amount, " +
                "0.0, buy_amount, sell_amount, rate, \"\", timestamp FROM orders"
            )
            execSQL("DROP TABLE orders")
            execSQL("ALTER TABLE orders_new RENAME TO orders")
        }
    }

}


private val MIGRATION_4_5: Migration = object : Migration(4, 5) {

    override fun migrate(database: SupportSQLiteDatabase) {
        with(database) {
            execSQL(
                "CREATE TABLE IF NOT EXISTS orders_new " +
                "(id INTEGER NOT NULL, market_name TEXT NOT NULL, currency TEXT NOT NULL, " +
                "market TEXT NOT NULL, type TEXT NOT NULL, trade_type TEXT NOT NULL, " +
                "amount REAL NOT NULL, original_amount REAL NOT NULL, buy_amount REAL NOT NULL, " +
                "sell_amount REAL NOT NULL, rate REAL NOT NULL, rates_map TEXT NOT NULL, " +
                "timestamp INTEGER NOT NULL, PRIMARY KEY(id, type))"
            )
            execSQL(
                "INSERT INTO orders_new " +
                "(id, market_name, currency, market, type, trade_type, amount, " +
                "original_amount, buy_amount, sell_amount, rate, rates_map, timestamp) " +
                "SELECT id, market_name, currency, market, type, trade_type, amount, " +
                "original_amount, buy_amount, sell_amount, rate, rates_map, timestamp FROM orders"
            )
            execSQL("DROP TABLE orders")
            execSQL("ALTER TABLE orders_new RENAME TO orders")
        }
    }

}


private val MIGRATION_5_6: Migration = object : Migration(5, 6) {

    override fun migrate(database: SupportSQLiteDatabase) {
        with(database) {
            execSQL(
                "CREATE TABLE IF NOT EXISTS transactions_new " +
                "(id INTEGER NOT NULL, transaction_id TEXT NOT NULL, type TEXT NOT NULL, " +
                "currency TEXT NOT NULL, status TEXT NOT NULL, amount REAL NOT NULL, " +
                "fee TEXT NOT NULL, address TEXT NOT NULL, timestamp INTEGER NOT NULL, " +
                "PRIMARY KEY(id))"
            )
            execSQL(
                "INSERT INTO transactions_new " +
                "(id, transaction_id, type, currency, status, amount, fee, address, timestamp) " +
                "SELECT id, IFNULL(transaction_id, \"\"), type, currency, status, amount, fee, " +
                "address, timestamp FROM transactions"
            )
            execSQL("DROP TABLE transactions")
            execSQL("ALTER TABLE transactions_new RENAME TO transactions")
        }
    }

}


private val MIGRATION_6_7: Migration = object : Migration(6, 7) {

    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE IF NOT EXISTS coins " +
            "(id INTEGER NOT NULL, name TEXT NOT NULL, symbol TEXT NOT NULL, slug TEXT NOT NULL, " +
            "rank INTEGER NOT NULL, circulating_supply REAL NOT NULL, total_supply REAL NOT NULL, " +
            "max_supply REAL NOT NULL, quotes TEXT NOT NULL, PRIMARY KEY(id))"
        )
    }

}


private val MIGRATION_7_8: Migration = object : Migration(7, 8) {

    override fun migrate(database: SupportSQLiteDatabase) {
        with(database) {
            execSQL(
                "CREATE TABLE IF NOT EXISTS settings_new " +
                "(id INTEGER NOT NULL, is_sound_enabled INTEGER NOT NULL, " +
                "is_vibration_enabled INTEGER NOT NULL, is_phone_led_enabled INTEGER NOT NULL, " +
                "notification_ringtone TEXT NOT NULL, decimal_separator TEXT NOT NULL, " +
                "coins_pricing_currency TEXT NOT NULL, PRIMARY KEY(id))"
            )
            execSQL(
                "INSERT INTO settings_new " +
                "(id, is_sound_enabled, is_vibration_enabled, is_phone_led_enabled," +
                "notification_ringtone, decimal_separator, coins_pricing_currency) " +
                "SELECT id, is_sound_enabled, is_vibration_enabled, is_phone_led_enabled," +
                "notification_ringtone, decimal_separator, \"USD\" FROM settings"
            )
            execSQL("DROP TABLE settings")
            execSQL("ALTER TABLE settings_new RENAME TO settings")
        }
    }

}


private val MIGRATION_8_9: Migration = object : Migration(8, 9) {

    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE IF NOT EXISTS alerts " +
            "(name TEXT NOT NULL, symbol TEXT NOT NULL, price REAL NOT NULL, " +
            "direction TEXT NOT NULL, is_enabled INTEGER NOT NULL, " +
            "creation_time INTEGER NOT NULL, PRIMARY KEY(name, price))"
        )
    }

}


private val MIGRATION_9_10: Migration = object : Migration(9, 10) {

    override fun migrate(database: SupportSQLiteDatabase) {
        with(database) {
            execSQL(
                "CREATE TABLE IF NOT EXISTS alerts_new " +
                "(coin_id INTEGER NOT NULL, coin_name TEXT NOT NULL, " +
                "coin_symbol TEXT NOT NULL, price REAL NOT NULL, " +
                "price_currency TEXT NOT NULL, direction TEXT NOT NULL, " +
                "is_enabled INTEGER NOT NULL, creation_time INTEGER NOT NULL, " +
                "PRIMARY KEY(coin_id, price))"
            )
            execSQL(
                "INSERT INTO alerts_new " +
                "(coin_id, coin_name, coin_symbol, price, price_currency, direction, " +
                "is_enabled, creation_time) SELECT -1, name, symbol, price, \"\", direction," +
                "is_enabled, creation_time FROM alerts"
            )
            execSQL("DROP TABLE alerts")
            execSQL("ALTER TABLE alerts_new RENAME TO alerts")
        }
    }

}


private val MIGRATION_10_11: Migration = object : Migration(10, 11) {

    override fun migrate(database: SupportSQLiteDatabase) {
        with(database) {
            execSQL(
                "CREATE TABLE IF NOT EXISTS settings_new " +
                "(id INTEGER NOT NULL, is_sound_enabled INTEGER NOT NULL, is_vibration_enabled INTEGER NOT NULL, " +
                "is_phone_led_enabled INTEGER NOT NULL, notification_ringtone TEXT NOT NULL, " +
                "decimal_separator TEXT NOT NULL, coins_pricing_currency TEXT NOT NULL, " +
                "background_sync_interval TEXT NOT NULL, PRIMARY KEY(id))"
            )
            execSQL(
                "INSERT INTO settings_new " +
                "(id, is_sound_enabled, is_vibration_enabled, is_phone_led_enabled, " +
                "notification_ringtone, decimal_separator, coins_pricing_currency, " +
                "background_sync_interval) SELECT id, is_sound_enabled, is_vibration_enabled, " +
                "is_phone_led_enabled, notification_ringtone, decimal_separator, coins_pricing_currency, " +
                "\"FIFTEEN_MINUTES\" FROM settings"
            )
            execSQL("DROP TABLE settings")
            execSQL("ALTER TABLE settings_new RENAME TO settings")
        }
    }

}


private val MIGRATION_11_12: Migration = object : Migration(11, 12) {

    override fun migrate(database: SupportSQLiteDatabase) {
        with(database) {
            execSQL(
                "CREATE TABLE IF NOT EXISTS settings_new " +
                "(id INTEGER NOT NULL, is_sound_enabled INTEGER NOT NULL, " +
                "is_vibration_enabled INTEGER NOT NULL, is_phone_led_enabled INTEGER NOT NULL, " +
                "is_grouping_enabled INTEGER NOT NULL, notification_ringtone TEXT NOT NULL, " +
                "decimal_separator TEXT NOT NULL, grouping_separator TEXT NOT NULL, " +
                "coins_pricing_currency TEXT NOT NULL, background_sync_interval TEXT NOT NULL, " +
                "PRIMARY KEY(id))"
            )
            execSQL(
                "INSERT INTO settings_new " +
                "(id, is_sound_enabled, is_vibration_enabled, is_phone_led_enabled, " +
                "is_grouping_enabled, notification_ringtone, decimal_separator, grouping_separator, " +
                "coins_pricing_currency, background_sync_interval) SELECT id, is_sound_enabled, " +
                "is_vibration_enabled, is_phone_led_enabled, 1, notification_ringtone, decimal_separator, " +
                "\"COMMA\", coins_pricing_currency, background_sync_interval FROM settings"
            )
            execSQL("DROP TABLE settings")
            execSQL("ALTER TABLE settings_new RENAME TO settings")
        }
    }

}


private val MIGRATION_12_13: Migration = object : Migration(12, 13) {

    override fun migrate(database: SupportSQLiteDatabase) {
        with(database) {
            execSQL(
                "CREATE TABLE IF NOT EXISTS settings_new " +
                "(id INTEGER NOT NULL, is_sound_enabled INTEGER NOT NULL, " +
                "is_vibration_enabled INTEGER NOT NULL, is_phone_led_enabled INTEGER NOT NULL, " +
                "is_grouping_enabled INTEGER NOT NULL, notification_ringtone TEXT NOT NULL, " +
                "decimal_separator TEXT NOT NULL, grouping_separator TEXT NOT NULL, " +
                "coins_pricing_currency TEXT NOT NULL, background_sync_interval TEXT NOT NULL, " +
                "theme_id INTEGER NOT NULL, PRIMARY KEY(id))"
            )
            execSQL(
                "INSERT INTO settings_new " +
                "(id, is_sound_enabled, is_vibration_enabled, is_phone_led_enabled, " +
                "is_grouping_enabled, notification_ringtone, decimal_separator, grouping_separator, " +
                "coins_pricing_currency, background_sync_interval, theme_id) SELECT id, is_sound_enabled, " +
                "is_vibration_enabled, is_phone_led_enabled, is_grouping_enabled, notification_ringtone, " +
                "decimal_separator, grouping_separator, coins_pricing_currency, background_sync_interval, 1 " +
                "FROM settings"
            )
            execSQL("DROP TABLE settings")
            execSQL("ALTER TABLE settings_new RENAME TO settings")
        }
    }

}


private val MIGRATION_13_14: Migration = object : Migration(13, 14) {

    override fun migrate(database: SupportSQLiteDatabase) {
        with(database) {
            execSQL(
                "CREATE TABLE IF NOT EXISTS settings_new " +
                "(id INTEGER NOT NULL, is_sound_enabled INTEGER NOT NULL, " +
                "is_vibration_enabled INTEGER NOT NULL, is_phone_led_enabled INTEGER NOT NULL, " +
                "is_grouping_enabled INTEGER NOT NULL, is_fingerprint_unlock_enabled INTEGER NOT NULL, " +
                "notification_ringtone TEXT NOT NULL, pin_code TEXT NOT NULL, " +
                "decimal_separator TEXT NOT NULL, grouping_separator TEXT NOT NULL, " +
                "coins_pricing_currency TEXT NOT NULL, background_sync_interval TEXT NOT NULL, " +
                "theme_id INTEGER NOT NULL, PRIMARY KEY(id))"
            )
            execSQL(
                "INSERT INTO settings_new " +
                "(id, is_sound_enabled, is_vibration_enabled, is_phone_led_enabled, " +
                "is_grouping_enabled, is_fingerprint_unlock_enabled, notification_ringtone, " +
                "pin_code, decimal_separator, grouping_separator, coins_pricing_currency, " +
                "background_sync_interval, theme_id) SELECT id, is_sound_enabled, " +
                "is_vibration_enabled, is_phone_led_enabled, is_grouping_enabled, 0, " +
                "notification_ringtone, \"\", decimal_separator, grouping_separator, " +
                "coins_pricing_currency, background_sync_interval, theme_id FROM settings"
            )
            execSQL("DROP TABLE settings")
            execSQL("ALTER TABLE settings_new RENAME TO settings")
        }
    }

}


private val MIGRATION_14_15: Migration = object : Migration(14, 15) {

    override fun migrate(database: SupportSQLiteDatabase) {
        with(database) {
            execSQL(
                "CREATE TABLE IF NOT EXISTS settings_new " +
                "(id INTEGER NOT NULL, is_sound_enabled INTEGER NOT NULL, " +
                "is_vibration_enabled INTEGER NOT NULL, is_phone_led_enabled INTEGER NOT NULL, " +
                "is_grouping_enabled INTEGER NOT NULL, is_fingerprint_unlock_enabled INTEGER NOT NULL, " +
                "is_force_authentication_on_app_startup_is_enabled INTEGER NOT NULL, " +
                "notification_ringtone TEXT NOT NULL, pin_code TEXT NOT NULL, " +
                "decimal_separator TEXT NOT NULL, grouping_separator TEXT NOT NULL, " +
                "coins_pricing_currency TEXT NOT NULL, background_sync_interval TEXT NOT NULL, " +
                "session_expiration_timeout TEXT NOT NULL, theme_id INTEGER NOT NULL, PRIMARY KEY(id))"
            )
            execSQL(
                "INSERT INTO settings_new " +
                "(id, is_sound_enabled, is_vibration_enabled, is_phone_led_enabled, " +
                "is_grouping_enabled, is_fingerprint_unlock_enabled, is_force_authentication_on_app_startup_is_enabled, " +
                "notification_ringtone, pin_code, decimal_separator, grouping_separator, coins_pricing_currency, " +
                "background_sync_interval, session_expiration_timeout, theme_id) SELECT id, is_sound_enabled, " +
                "is_vibration_enabled, is_phone_led_enabled, is_grouping_enabled, is_fingerprint_unlock_enabled, " +
                "0, notification_ringtone, pin_code, decimal_separator, grouping_separator, " +
                "coins_pricing_currency, background_sync_interval, \"FIVE_MINUTES\", theme_id " +
                "FROM settings"
            )
            execSQL("DROP TABLE settings")
            execSQL("ALTER TABLE settings_new RENAME TO settings")
        }
    }

}


private val MIGRATION_15_16: Migration = object : Migration(15, 16) {

    override fun migrate(database: SupportSQLiteDatabase) {
        with(database) {
            execSQL(
                "CREATE TABLE IF NOT EXISTS currency_markets_new " +
                "(id INTEGER NOT NULL, name TEXT NOT NULL, currency_symbol TEXT NOT NULL, " +
                "market_symbol TEXT NOT NULL, min_order_amount REAL NOT NULL, " +
                "daily_buy_max_price REAL NOT NULL, daily_sell_min_price REAL NOT NULL, " +
                "last_price REAL NOT NULL, last_price_day_ago REAL NOT NULL, " +
                "daily_volume REAL NOT NULL, spread REAL NOT NULL, " +
                "buy_fee_percent REAL NOT NULL, sell_fee_percent REAL NOT NULL, PRIMARY KEY(id))"
            )
            execSQL(
                "INSERT INTO currency_markets_new " +
                "(id, name, currency_symbol, market_symbol, min_order_amount, daily_buy_max_price, " +
                "daily_sell_min_price, last_price, last_price_day_ago, daily_volume, spread, " +
                "buy_fee_percent, sell_fee_percent) SELECT id, name, currency, market, min_order_amount, " +
                "daily_buy_max_price, daily_sell_min_price, last_price, last_price_day_ago, daily_volume, " +
                "spread, buy_fee_percent, sell_fee_percent FROM currency_markets"
            )
            execSQL("DROP TABLE currency_markets")
            execSQL("ALTER TABLE currency_markets_new RENAME TO currency_markets")

            execSQL(
                "CREATE TABLE IF NOT EXISTS orders_new " +
                "(id INTEGER NOT NULL, market_name TEXT NOT NULL, currency_symbol TEXT NOT NULL, " +
                "market_symbol TEXT NOT NULL, type TEXT NOT NULL, trade_type TEXT NOT NULL, " +
                "amount REAL NOT NULL, original_amount REAL NOT NULL, buy_amount REAL NOT NULL, " +
                "sell_amount REAL NOT NULL, rate REAL NOT NULL, rates_map TEXT NOT NULL, " +
                "timestamp INTEGER NOT NULL, PRIMARY KEY(id, type))"
            )
            execSQL(
                "INSERT INTO orders_new " +
                "(id, market_name, currency_symbol, market_symbol, type, trade_type, amount, original_amount, " +
                "buy_amount, sell_amount, rate, rates_map, timestamp) SELECT id, market_name, currency, " +
                "market, type, trade_type, amount, original_amount, buy_amount, sell_amount, rate, rates_map, " +
                "timestamp FROM orders"
            )
            execSQL("DROP TABLE orders")
            execSQL("ALTER TABLE orders_new RENAME TO orders")
        }
    }

}


private val MIGRATION_16_17: Migration = object : Migration(16, 17) {

    override fun migrate(database: SupportSQLiteDatabase) {
        with(database) {
            execSQL(
                "CREATE TABLE IF NOT EXISTS currencies_new " +
                "(name TEXT NOT NULL, long_name TEXT NOT NULL, minimum_withdrawal_amount REAL NOT NULL, " +
                "minimum_deposit_amount REAL NOT NULL, deposit_fee_currency TEXT NOT NULL, " +
                "deposit_fee REAL NOT NULL, withdrawal_fee_currency TEXT NOT NULL, " +
                "withdrawal_fee REAL NOT NULL, block_explorer_url TEXT NOT NULL, " +
                "is_active INTEGER NOT NULL, is_depositing_disabled INTEGER NOT NULL, PRIMARY KEY(name))"
            )
            execSQL(
                "INSERT INTO currencies_new " +
                "(name, long_name, minimum_withdrawal_amount, minimum_deposit_amount, deposit_fee_currency, " +
                "deposit_fee, withdrawal_fee_currency, withdrawal_fee, block_explorer_url, is_active, is_depositing_disabled) " +
                "SELECT name, long_name, minimum_withdrawal_amount, minimum_deposit_amount, deposit_fee_currency, " +
                "deposit_fee, withdrawal_fee_currency, withdrawal_fee, block_explorer_url, is_active, 0 FROM " +
                "currencies"
            )
            execSQL("DROP TABLE currencies")
            execSQL("ALTER TABLE currencies_new RENAME TO currencies")
        }
    }

}


private val MIGRATION_17_18: Migration = object : Migration(17, 18) {

    override fun migrate(database: SupportSQLiteDatabase) {
        with(database) {
            execSQL(
                "CREATE TABLE IF NOT EXISTS currency_markets_new " +
                "(id INTEGER NOT NULL, name TEXT NOT NULL, currency_symbol TEXT NOT NULL, " +
                "market_symbol TEXT NOT NULL, min_order_amount REAL NOT NULL, " +
                "daily_buy_max_price REAL NOT NULL, daily_sell_min_price REAL NOT NULL, " +
                "last_price REAL NOT NULL, last_price_day_ago REAL NOT NULL, " +
                "daily_volume REAL NOT NULL, spread REAL NOT NULL, buy_fee_percent REAL NOT NULL, " +
                "sell_fee_percent REAL NOT NULL, is_active INTEGER NOT NULL, PRIMARY KEY(id))"
            )
            execSQL(
                "INSERT INTO currency_markets_new " +
                "(id, name, currency_symbol, market_symbol, min_order_amount, daily_buy_max_price, " +
                "daily_sell_min_price, last_price, last_price_day_ago, daily_volume, spread, " +
                "buy_fee_percent, sell_fee_percent, is_active) " +
                "SELECT id, name, currency_symbol, market_symbol, min_order_amount, daily_buy_max_price, " +
                "daily_sell_min_price, last_price, last_price_day_ago, daily_volume, spread, " +
                "buy_fee_percent, sell_fee_percent, 1 FROM currency_markets"
            )
            execSQL("DROP TABLE currency_markets")
            execSQL("ALTER TABLE currency_markets_new RENAME TO currency_markets")
        }
    }

}


private val MIGRATION_18_19: Migration = object : Migration(18, 19) {

    override fun migrate(database: SupportSQLiteDatabase) {
        with(database) {
            execSQL(
                "CREATE TABLE IF NOT EXISTS deposits_new " +
                "(currency TEXT NOT NULL, address TEXT NOT NULL, public_key TEXT NOT NULL, " +
                "payment_id TEXT NOT NULL, destination_tag TEXT NOT NULL, PRIMARY KEY(currency))"
            )
            execSQL(
                "INSERT INTO deposits_new " +
                "(currency, address, public_key, payment_id, destination_tag) " +
                "SELECT currency, address, public_key, payment_id, \"\" FROM deposits"
            )
            execSQL("DROP TABLE deposits")
            execSQL("ALTER TABLE deposits_new RENAME TO deposits")
        }
    }

}


private val MIGRATION_19_20: Migration = object : Migration(19, 20) {

    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE IF NOT EXISTS orderbook " +
            "(market_name TEXT NOT NULL, buy_orders TEXT NOT NULL, " +
            "sell_orders TEXT NOT NULL, PRIMARY KEY(market_name))"
        )
    }

}


private val MIGRATION_20_21: Migration = object : Migration(20, 21) {

    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE charts_data RENAME TO price_chart_data")
    }

}


private val MIGRATION_21_22: Migration = object : Migration(21, 22) {

    override fun migrate(database: SupportSQLiteDatabase) {
        with(database) {
            execSQL("DROP TABLE IF EXISTS alerts")
            execSQL("DROP TABLE IF EXISTS coins")
            execSQL("DROP TABLE IF EXISTS tickets")
            execSQL("DROP TABLE IF EXISTS currency_market_summaries")

            execSQL(
                "CREATE TABLE IF NOT EXISTS settings_new " +
                "(id INTEGER NOT NULL, is_sound_enabled INTEGER NOT NULL, is_vibration_enabled INTEGER NOT NULL, " +
                "is_phone_led_enabled INTEGER NOT NULL, is_grouping_enabled INTEGER NOT NULL, " +
                "is_fingerprint_unlock_enabled INTEGER NOT NULL, " +
                "is_force_authentication_on_app_startup_is_enabled INTEGER NOT NULL, notification_ringtone TEXT NOT NULL, " +
                "pin_code TEXT NOT NULL, decimal_separator TEXT NOT NULL, grouping_separator TEXT NOT NULL, " +
                "session_expiration_timeout TEXT NOT NULL, theme_id INTEGER NOT NULL, PRIMARY KEY(id))"
            )
            execSQL(
                "INSERT INTO settings_new " +
                "(id, is_sound_enabled, is_vibration_enabled, is_phone_led_enabled, is_grouping_enabled, " +
                "is_fingerprint_unlock_enabled, is_force_authentication_on_app_startup_is_enabled, notification_ringtone, " +
                "pin_code, decimal_separator, grouping_separator, session_expiration_timeout, theme_id) " +
                "SELECT id, is_sound_enabled, is_vibration_enabled, is_phone_led_enabled, is_grouping_enabled, " +
                "is_fingerprint_unlock_enabled, is_force_authentication_on_app_startup_is_enabled, notification_ringtone, " +
                "pin_code, decimal_separator, grouping_separator, session_expiration_timeout, theme_id FROM settings"
            )
            execSQL("DROP TABLE settings")
            execSQL("ALTER TABLE settings_new RENAME TO settings")
        }
    }

}


private val MIGRATION_22_23: Migration = object : Migration(22, 23) {

    override fun migrate(database: SupportSQLiteDatabase) {
        with(database) {
            execSQL(
                "CREATE TABLE IF NOT EXISTS currency_markets_new " +
                "(id INTEGER NOT NULL, name TEXT NOT NULL, currency_name TEXT NOT NULL, " +
                "market_name TEXT NOT NULL, currency_symbol TEXT NOT NULL, market_symbol TEXT NOT NULL, " +
                "min_order_amount REAL NOT NULL, daily_buy_max_price REAL NOT NULL, " +
                "daily_sell_min_price REAL NOT NULL, last_price REAL NOT NULL, " +
                "last_price_day_ago REAL NOT NULL, daily_volume REAL NOT NULL, spread REAL NOT NULL, " +
                "buy_fee_percent REAL NOT NULL, sell_fee_percent REAL NOT NULL, is_active INTEGER NOT NULL, " +
                "PRIMARY KEY(id))"
            )
            execSQL(
                "INSERT INTO currency_markets_new " +
                "(id, name, currency_name, market_name, currency_symbol, market_symbol, min_order_amount, " +
                "daily_buy_max_price, daily_sell_min_price, last_price, last_price_day_ago, daily_volume, spread, " +
                "buy_fee_percent, sell_fee_percent, is_active) SELECT id, name, \"\", \"\", currency_symbol, " +
                "market_symbol, min_order_amount, daily_buy_max_price, daily_sell_min_price, last_price, " +
                "last_price_day_ago, daily_volume, spread, buy_fee_percent, sell_fee_percent, is_active FROM currency_markets"
            )
            execSQL("DROP TABLE currency_markets")
            execSQL("ALTER TABLE currency_markets_new RENAME TO currency_markets")
        }
    }

}


private val MIGRATION_23_24: Migration = object : Migration(23, 24) {

    override fun migrate(database: SupportSQLiteDatabase) {
        with(database) {
            execSQL(
                "CREATE TABLE IF NOT EXISTS currency_markets_new " +
                "(id INTEGER NOT NULL, name TEXT NOT NULL, base_currency_name TEXT NOT NULL, " +
                "quote_currency_name TEXT NOT NULL, base_currency_symbol TEXT NOT NULL, quote_currency_symbol TEXT NOT NULL, " +
                "min_order_amount REAL NOT NULL, daily_buy_max_price REAL NOT NULL, " +
                "daily_sell_min_price REAL NOT NULL, last_price REAL NOT NULL, " +
                "last_price_day_ago REAL NOT NULL, daily_volume REAL NOT NULL, spread REAL NOT NULL, " +
                "buy_fee_percent REAL NOT NULL, sell_fee_percent REAL NOT NULL, is_active INTEGER NOT NULL, " +
                "PRIMARY KEY(id))"
            )
            execSQL(
                "INSERT INTO currency_markets_new " +
                "(id, name, base_currency_name, quote_currency_name, base_currency_symbol, quote_currency_symbol, min_order_amount, " +
                "daily_buy_max_price, daily_sell_min_price, last_price, last_price_day_ago, daily_volume, spread, " +
                "buy_fee_percent, sell_fee_percent, is_active) SELECT id, name, currency_name, market_name, currency_symbol, " +
                "market_symbol, min_order_amount, daily_buy_max_price, daily_sell_min_price, last_price, " +
                "last_price_day_ago, daily_volume, spread, buy_fee_percent, sell_fee_percent, is_active FROM currency_markets"
            )
            execSQL("DROP TABLE currency_markets")
            execSQL("ALTER TABLE currency_markets_new RENAME TO currency_markets")
        }
    }

}


private val MIGRATION_24_25: Migration = object : Migration(24, 25) {

    override fun migrate(database: SupportSQLiteDatabase) {
        with(database) {
            execSQL(
                "CREATE TABLE IF NOT EXISTS orders_new " +
                "(id INTEGER NOT NULL, market_name TEXT NOT NULL, base_currency_symbol TEXT NOT NULL, " +
                "quote_currency_symbol TEXT NOT NULL, type TEXT NOT NULL, trade_type TEXT NOT NULL, " +
                "amount REAL NOT NULL, original_amount REAL NOT NULL, buy_amount REAL NOT NULL, " +
                "sell_amount REAL NOT NULL, rate REAL NOT NULL, rates_map TEXT NOT NULL, timestamp INTEGER NOT NULL, " +
                "PRIMARY KEY(id, type))"
            )
            execSQL(
                "INSERT INTO orders_new " +
                "(id, market_name, base_currency_symbol, quote_currency_symbol, type, trade_type, amount, " +
                "original_amount, buy_amount, sell_amount, rate, rates_map, timestamp) SELECT " +
                "id, market_name, currency_symbol, market_symbol, type, trade_type, amount, " +
                "original_amount, buy_amount, sell_amount, rate, rates_map, timestamp FROM orders"
            )
            execSQL("DROP TABLE orders")
            execSQL("ALTER TABLE orders_new RENAME TO orders")
        }
    }

}


private val MIGRATION_25_26: Migration = object : Migration(25, 26) {

    override fun migrate(database: SupportSQLiteDatabase) {
        with(database) {
            execSQL(
                "CREATE TABLE IF NOT EXISTS currency_markets_new " +
                "(id INTEGER NOT NULL, name TEXT NOT NULL, base_currency_name TEXT NOT NULL, " +
                "quote_currency_name TEXT NOT NULL, base_currency_symbol TEXT NOT NULL, " +
                "quote_currency_symbol TEXT NOT NULL, min_order_amount REAL NOT NULL, " +
                "daily_min_price REAL NOT NULL, daily_max_price REAL NOT NULL, last_price REAL NOT NULL, " +
                "last_price_day_ago REAL NOT NULL, daily_volume REAL NOT NULL, spread REAL NOT NULL, " +
                "buy_fee_percent REAL NOT NULL, sell_fee_percent REAL NOT NULL, is_active INTEGER NOT NULL, " +
                "PRIMARY KEY(id))"
            )
            execSQL(
                "INSERT INTO currency_markets_new " +
                "(id, name, base_currency_name, quote_currency_name, base_currency_symbol, " +
                "quote_currency_symbol, min_order_amount, daily_min_price, daily_max_price, last_price, " +
                "last_price_day_ago, daily_volume, spread, buy_fee_percent, sell_fee_percent, is_active) " +
                "SELECT id, name, base_currency_name, quote_currency_name, base_currency_symbol, " +
                "quote_currency_symbol, min_order_amount, daily_sell_min_price, daily_buy_max_price, last_price, " +
                "last_price_day_ago, daily_volume, spread, buy_fee_percent, sell_fee_percent, is_active FROM currency_markets"
            )
            execSQL("DROP TABLE currency_markets")
            execSQL("ALTER TABLE currency_markets_new RENAME TO currency_markets")
        }
    }

}


private val MIGRATION_26_27: Migration = object : Migration(26, 27) {

    override fun migrate(database: SupportSQLiteDatabase) {
        with(database) {
            execSQL(
                "CREATE TABLE IF NOT EXISTS currency_markets_new " +
                "(id INTEGER NOT NULL, name TEXT NOT NULL, base_currency_name TEXT NOT NULL, " +
                "quote_currency_name TEXT NOT NULL, base_currency_symbol TEXT NOT NULL, " +
                "quote_currency_symbol TEXT NOT NULL, min_order_amount REAL NOT NULL, " +
                "daily_min_price REAL NOT NULL, daily_max_price REAL NOT NULL, last_price REAL NOT NULL, " +
                "last_price_day_ago REAL NOT NULL, daily_price_change REAL NOT NULL, daily_volume REAL NOT NULL, " +
                "volume REAL NOT NULL, buy_fee_percent REAL NOT NULL, sell_fee_percent REAL NOT NULL, " +
                "is_active INTEGER NOT NULL, PRIMARY KEY(id))"
            )
            execSQL(
                "INSERT INTO currency_markets_new " +
                "(id, name, base_currency_name, quote_currency_name, base_currency_symbol, " +
                "quote_currency_symbol, min_order_amount, daily_min_price, daily_max_price, last_price, " +
                "last_price_day_ago, daily_price_change, daily_volume, volume, buy_fee_percent, sell_fee_percent, " +
                "is_active) SELECT id, name, base_currency_name, quote_currency_name, base_currency_symbol, " +
                "quote_currency_symbol, min_order_amount, daily_min_price, daily_max_price, last_price, " +
                "last_price_day_ago, spread, daily_volume, 0.0, buy_fee_percent, sell_fee_percent, is_active " +
                "FROM currency_markets"
            )
            execSQL("DROP TABLE currency_markets")
            execSQL("ALTER TABLE currency_markets_new RENAME TO currency_markets")
        }
    }

}


private val MIGRATION_27_28: Migration = object : Migration(27, 28) {

    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE IF NOT EXISTS trades " +
            "(id INTEGER NOT NULL, market_name TEXT NOT NULL, type TEXT NOT NULL, " +
            "timestamp INTEGER NOT NULL, price REAL NOT NULL, amount REAL NOT NULL, " +
            "PRIMARY KEY(id))"
        )
    }

}


private val MIGRATION_28_29: Migration = object : Migration(28, 29) {

    override fun migrate(database: SupportSQLiteDatabase) {
        with(database) {
            execSQL(
                "CREATE TABLE IF NOT EXISTS settings_new " +
                "(id INTEGER NOT NULL, is_sound_enabled INTEGER NOT NULL, is_vibration_enabled INTEGER NOT NULL, " +
                "is_phone_led_enabled INTEGER NOT NULL, is_grouping_enabled INTEGER NOT NULL, " +
                "is_fingerprint_unlock_enabled INTEGER NOT NULL, " +
                "is_force_authentication_on_app_startup_is_enabled INTEGER NOT NULL, " +
                "is_real_time_data_streaming_enabled INTEGER NOT NULL, " +
                "is_orderbook_real_time_updates_highlighting_enabled INTEGER NOT NULL, " +
                "is_new_trades_real_time_addition_highlighting_enabled INTEGER NOT NULL, " +
                "notification_ringtone TEXT NOT NULL, pin_code TEXT NOT NULL, decimal_separator TEXT NOT NULL, " +
                "grouping_separator TEXT NOT NULL, session_expiration_timeout TEXT NOT NULL, " +
                "theme_id INTEGER NOT NULL, PRIMARY KEY(id))"
            )
            execSQL(
                "INSERT INTO settings_new " +
                "(id, is_sound_enabled, is_vibration_enabled, is_phone_led_enabled, is_grouping_enabled, " +
                "is_fingerprint_unlock_enabled, is_force_authentication_on_app_startup_is_enabled, " +
                "is_real_time_data_streaming_enabled, is_orderbook_real_time_updates_highlighting_enabled, " +
                "is_new_trades_real_time_addition_highlighting_enabled, notification_ringtone, pin_code, " +
                "decimal_separator, grouping_separator, session_expiration_timeout, theme_id) SELECT id, " +
                "is_sound_enabled, is_vibration_enabled, is_phone_led_enabled, is_grouping_enabled, " +
                "is_fingerprint_unlock_enabled, is_force_authentication_on_app_startup_is_enabled, 1, 1, 1, " +
                "notification_ringtone, pin_code, decimal_separator, grouping_separator, session_expiration_timeout, " +
                "theme_id FROM settings"
            )
            execSQL("DROP TABLE settings")
            execSQL("ALTER TABLE settings_new RENAME TO settings")
        }
    }

}


private val MIGRATION_29_30: Migration = object : Migration(29, 30) {

    override fun migrate(database: SupportSQLiteDatabase) {
        with(database) {
            execSQL(
                "CREATE TABLE IF NOT EXISTS settings_new " +
                "(id INTEGER NOT NULL, is_sound_enabled INTEGER NOT NULL, is_vibration_enabled INTEGER NOT NULL, " +
                "is_phone_led_enabled INTEGER NOT NULL, is_grouping_enabled INTEGER NOT NULL, " +
                "is_fingerprint_unlock_enabled INTEGER NOT NULL, " +
                "is_force_authentication_on_app_startup_is_enabled INTEGER NOT NULL, " +
                "is_real_time_data_streaming_enabled INTEGER NOT NULL, " +
                "is_orderbook_real_time_updates_highlighting_enabled INTEGER NOT NULL, " +
                "is_new_trades_real_time_addition_highlighting_enabled INTEGER NOT NULL, " +
                "should_animate_charts INTEGER NOT NULL, notification_ringtone TEXT NOT NULL, " +
                "pin_code TEXT NOT NULL, bullish_candle_stick_style TEXT NOT NULL, " +
                "bearish_candle_stick_style TEXT NOT NULL, depth_chart_line_style TEXT NOT NULL, " +
                "decimal_separator TEXT NOT NULL, grouping_separator TEXT NOT NULL, " +
                "session_expiration_timeout TEXT NOT NULL, theme_id INTEGER NOT NULL, PRIMARY KEY(id))"
            )
            execSQL(
                "INSERT INTO settings_new " +
                "(id, is_sound_enabled, is_vibration_enabled, is_phone_led_enabled, is_grouping_enabled, " +
                "is_fingerprint_unlock_enabled, is_force_authentication_on_app_startup_is_enabled, " +
                "is_real_time_data_streaming_enabled, is_orderbook_real_time_updates_highlighting_enabled, " +
                "is_new_trades_real_time_addition_highlighting_enabled, should_animate_charts, " +
                "notification_ringtone, pin_code, bullish_candle_stick_style, bearish_candle_stick_style," +
                "depth_chart_line_style, decimal_separator, grouping_separator, session_expiration_timeout, " +
                "theme_id) SELECT id, is_sound_enabled, is_vibration_enabled, is_phone_led_enabled, is_grouping_enabled, " +
                "is_fingerprint_unlock_enabled, is_force_authentication_on_app_startup_is_enabled, " +
                "is_real_time_data_streaming_enabled, is_orderbook_real_time_updates_highlighting_enabled, " +
                "is_new_trades_real_time_addition_highlighting_enabled, 1, notification_ringtone, pin_code, " +
                "\"SOLID\", \"SOLID\", \"LINEAR\", decimal_separator, grouping_separator, session_expiration_timeout, " +
                "theme_id FROM settings"
            )
            execSQL("DROP TABLE settings")
            execSQL("ALTER TABLE settings_new RENAME TO settings")
        }
    }

}


private val MIGRATION_30_31: Migration = object : Migration(30, 31) {

    override fun migrate(database: SupportSQLiteDatabase) {
        with(database) {
            execSQL(
                "CREATE TABLE IF NOT EXISTS settings_new " +
                "(id INTEGER NOT NULL, is_sound_enabled INTEGER NOT NULL, is_vibration_enabled INTEGER NOT NULL, " +
                "is_phone_led_enabled INTEGER NOT NULL, is_grouping_enabled INTEGER NOT NULL, " +
                "is_fingerprint_unlock_enabled INTEGER NOT NULL, " +
                "is_force_authentication_on_app_startup_is_enabled INTEGER NOT NULL, " +
                "is_real_time_data_streaming_enabled INTEGER NOT NULL, " +
                "is_orderbook_real_time_updates_highlighting_enabled INTEGER NOT NULL, " +
                "is_new_trades_real_time_addition_highlighting_enabled INTEGER NOT NULL, " +
                "should_animate_charts INTEGER NOT NULL, notification_ringtone TEXT NOT NULL, " +
                "pin_code TEXT NOT NULL, bullish_candle_stick_style TEXT NOT NULL, " +
                "bearish_candle_stick_style TEXT NOT NULL, depth_chart_line_style TEXT NOT NULL, " +
                "decimal_separator TEXT NOT NULL, grouping_separator TEXT NOT NULL, " +
                "session_expiration_timeout TEXT NOT NULL, theme_id INTEGER NOT NULL, PRIMARY KEY(id))"
            )
            execSQL(
                "INSERT INTO settings_new " +
                "(id, is_sound_enabled, is_vibration_enabled, is_phone_led_enabled, is_grouping_enabled, " +
                "is_fingerprint_unlock_enabled, is_force_authentication_on_app_startup_is_enabled, " +
                "is_real_time_data_streaming_enabled, is_orderbook_real_time_updates_highlighting_enabled, " +
                "is_new_trades_real_time_addition_highlighting_enabled, should_animate_charts, " +
                "notification_ringtone, pin_code, bullish_candle_stick_style, bearish_candle_stick_style, " +
                "depth_chart_line_style, decimal_separator, grouping_separator, session_expiration_timeout, " +
                "theme_id) SELECT id, is_sound_enabled, is_vibration_enabled, is_phone_led_enabled, is_grouping_enabled, " +
                "is_fingerprint_unlock_enabled, is_force_authentication_on_app_startup_is_enabled, " +
                "is_real_time_data_streaming_enabled, is_orderbook_real_time_updates_highlighting_enabled, " +
                "is_new_trades_real_time_addition_highlighting_enabled, should_animate_charts, notification_ringtone, pin_code, " +
                "bullish_candle_stick_style, bearish_candle_stick_style, depth_chart_line_style, decimal_separator, " +
                "CASE WHEN grouping_separator=\"UNDERSCORE\" THEN \"SPACE\" ELSE grouping_separator END, " +
                "session_expiration_timeout, theme_id FROM settings"
            )
            execSQL("DROP TABLE settings")
            execSQL("ALTER TABLE settings_new RENAME TO settings")
        }
    }

}


private val MIGRATION_31_32: Migration = object : Migration(31, 32) {

    override fun migrate(database: SupportSQLiteDatabase) {
        with(database) {
            execSQL(
                "CREATE TABLE IF NOT EXISTS settings_new " +
                "(id INTEGER NOT NULL, is_sound_enabled INTEGER NOT NULL, is_vibration_enabled INTEGER NOT NULL, " +
                "is_phone_led_enabled INTEGER NOT NULL, is_grouping_enabled INTEGER NOT NULL, " +
                "is_fingerprint_unlock_enabled INTEGER NOT NULL, " +
                "is_force_authentication_on_app_startup_is_enabled INTEGER NOT NULL, " +
                "is_real_time_data_streaming_enabled INTEGER NOT NULL, " +
                "is_orderbook_real_time_updates_highlighting_enabled INTEGER NOT NULL, " +
                "is_new_trades_real_time_addition_highlighting_enabled INTEGER NOT NULL, " +
                "should_animate_charts INTEGER NOT NULL, notification_ringtone TEXT NOT NULL, " +
                "pin_code TEXT NOT NULL, bullish_candle_stick_style TEXT NOT NULL, " +
                "bearish_candle_stick_style TEXT NOT NULL, depth_chart_line_style TEXT NOT NULL, " +
                "decimal_separator TEXT NOT NULL, grouping_separator TEXT NOT NULL, " +
                "authentication_session_duration TEXT NOT NULL, theme_id INTEGER NOT NULL, PRIMARY KEY(id))"
            )
            execSQL(
                "INSERT INTO settings_new " +
                "(id, is_sound_enabled, is_vibration_enabled, is_phone_led_enabled, is_grouping_enabled, " +
                "is_fingerprint_unlock_enabled, is_force_authentication_on_app_startup_is_enabled, " +
                "is_real_time_data_streaming_enabled, is_orderbook_real_time_updates_highlighting_enabled, " +
                "is_new_trades_real_time_addition_highlighting_enabled, should_animate_charts, " +
                "notification_ringtone, pin_code, bullish_candle_stick_style, bearish_candle_stick_style, " +
                "depth_chart_line_style, decimal_separator, grouping_separator, authentication_session_duration, " +
                "theme_id) SELECT id, is_sound_enabled, is_vibration_enabled, is_phone_led_enabled, is_grouping_enabled, " +
                "is_fingerprint_unlock_enabled, is_force_authentication_on_app_startup_is_enabled, " +
                "is_real_time_data_streaming_enabled, is_orderbook_real_time_updates_highlighting_enabled, " +
                "is_new_trades_real_time_addition_highlighting_enabled, should_animate_charts, notification_ringtone, pin_code, " +
                "bullish_candle_stick_style, bearish_candle_stick_style, depth_chart_line_style, decimal_separator, " +
                "grouping_separator, session_expiration_timeout, theme_id FROM settings"
            )
            execSQL("DROP TABLE settings")
            execSQL("ALTER TABLE settings_new RENAME TO settings")
        }
    }

}


private val MIGRATION_32_33: Migration = object : Migration(32, 33) {

    override fun migrate(database: SupportSQLiteDatabase) {
        with(database) {
            execSQL(
                "CREATE TABLE IF NOT EXISTS settings_new " +
                "(id INTEGER NOT NULL, is_sound_enabled INTEGER NOT NULL, is_vibration_enabled INTEGER NOT NULL, " +
                "is_phone_led_enabled INTEGER NOT NULL, is_grouping_enabled INTEGER NOT NULL, " +
                "is_fingerprint_unlock_enabled INTEGER NOT NULL, " +
                "is_force_authentication_on_app_startup_is_enabled INTEGER NOT NULL, " +
                "is_real_time_data_streaming_enabled INTEGER NOT NULL, " +
                "is_orderbook_real_time_updates_highlighting_enabled INTEGER NOT NULL, " +
                "is_new_trades_real_time_addition_highlighting_enabled INTEGER NOT NULL, " +
                "is_price_chart_zoom_in_enabled INTEGER NOT NULL, should_animate_charts INTEGER NOT NULL, " +
                "notification_ringtone TEXT NOT NULL, pin_code TEXT NOT NULL, " +
                "bullish_candle_stick_style TEXT NOT NULL, bearish_candle_stick_style TEXT NOT NULL, " +
                "depth_chart_line_style TEXT NOT NULL, decimal_separator TEXT NOT NULL, " +
                "grouping_separator TEXT NOT NULL, authentication_session_duration TEXT NOT NULL, " +
                "theme_id INTEGER NOT NULL, PRIMARY KEY(id))"
            )
            execSQL(
                "INSERT INTO settings_new " +
                "(id, is_sound_enabled, is_vibration_enabled, is_phone_led_enabled, is_grouping_enabled, " +
                "is_fingerprint_unlock_enabled, is_force_authentication_on_app_startup_is_enabled, " +
                "is_real_time_data_streaming_enabled, is_orderbook_real_time_updates_highlighting_enabled, " +
                "is_new_trades_real_time_addition_highlighting_enabled, is_price_chart_zoom_in_enabled, " +
                "should_animate_charts, notification_ringtone, pin_code, bullish_candle_stick_style, " +
                "bearish_candle_stick_style, depth_chart_line_style, decimal_separator, grouping_separator, " +
                "authentication_session_duration, theme_id) SELECT id, is_sound_enabled, is_vibration_enabled, " +
                "is_phone_led_enabled, is_grouping_enabled, is_fingerprint_unlock_enabled, " +
                "is_force_authentication_on_app_startup_is_enabled, is_real_time_data_streaming_enabled, " +
                "is_orderbook_real_time_updates_highlighting_enabled, " +
                "is_new_trades_real_time_addition_highlighting_enabled, 0, should_animate_charts, notification_ringtone, " +
                "pin_code, bullish_candle_stick_style, bearish_candle_stick_style, depth_chart_line_style, " +
                "decimal_separator, grouping_separator, authentication_session_duration, theme_id FROM settings"
            )
            execSQL("DROP TABLE settings")
            execSQL("ALTER TABLE settings_new RENAME TO settings")
        }
    }

}


private val MIGRATION_33_34: Migration = object : Migration(33, 34) {

    override fun migrate(database: SupportSQLiteDatabase) {
        with(database) {
            // Candle sticks
            execSQL(
                "CREATE TABLE IF NOT EXISTS candle_sticks " +
                "(currency_pair_id INTEGER NOT NULL, interval TEXT NOT NULL, open_price REAL NOT NULL, " +
                "high_price REAL NOT NULL, low_price REAL NOT NULL, close_price REAL NOT NULL, volume REAL NOT NULL, " +
                "timestamp INTEGER NOT NULL, PRIMARY KEY(currency_pair_id, interval, timestamp))"
            )

            // New currencies
            execSQL(
                "CREATE TABLE IF NOT EXISTS new_currencies " +
                "(id INTEGER NOT NULL, code TEXT NOT NULL, name TEXT NOT NULL, is_active INTEGER NOT NULL, " +
                "is_delisted INTEGER NOT NULL, precision INTEGER NOT NULL, minimum_withdrawal_amount REAL NOT NULL, " +
                "minimum_deposit_amount REAL NOT NULL, deposit_fee_currency_id INTEGER NOT NULL, deposit_fee REAL NOT NULL, " +
                "deposit_fee_in_percentage REAL NOT NULL, withdrawal_fee_currency_id INTEGER NOT NULL, withdrawal_fee REAL NOT NULL, " +
                "withdrawal_fee_in_percentage REAL NOT NULL, block_explorer_url TEXT NOT NULL, PRIMARY KEY(id))"
            )

            // Currency pairs
            execSQL(
                "CREATE TABLE IF NOT EXISTS currency_pairs " +
                "(id INTEGER NOT NULL, base_currency_id INTEGER NOT NULL, base_currency_code TEXT NOT NULL, " +
                "base_currency_name TEXT NOT NULL, quote_currency_id INTEGER NOT NULL, quote_currency_code TEXT NOT NULL, " +
                "quote_currency_name TEXT NOT NULL, symbol TEXT NOT NULL, group_name TEXT NOT NULL, group_id INTEGER NOT NULL, " +
                "min_order_amount REAL NOT NULL, min_buy_price REAL NOT NULL, min_sell_price REAL NOT NULL, " +
                "buy_fee_in_percentage REAL NOT NULL, sell_fee_in_percentage REAL NOT NULL, is_active INTEGER NOT NULL, " +
                "is_delisted INTEGER NOT NULL, message TEXT, base_currency_precision INTEGER NOT NULL, " +
                "quote_currency_precision INTEGER NOT NULL, PRIMARY KEY(id))"
            )

            // New deposits
            execSQL(
                "CREATE TABLE IF NOT EXISTS new_deposits " +
                "(id INTEGER NOT NULL, currency_id INTEGER NOT NULL, currency_code TEXT NOT NULL, amount REAL NOT NULL, " +
                "fee REAL NOT NULL, status_str TEXT NOT NULL, timestamp INTEGER NOT NULL, transaction_id TEXT, " +
                "confirmations TEXT NOT NULL, PRIMARY KEY(id))"
            )

            // Orderbooks
            execSQL(
                "CREATE TABLE IF NOT EXISTS orderbooks " +
                "(currency_pair_id INTEGER NOT NULL, buy_orders TEXT NOT NULL, sell_orders TEXT NOT NULL, PRIMARY KEY(currency_pair_id))"
            )

            // New orders
            execSQL(
                "CREATE TABLE IF NOT EXISTS new_orders " +
                "(id INTEGER NOT NULL, currency_pair_id INTEGER NOT NULL, price REAL NOT NULL, trigger_price REAL NOT NULL, " +
                "initial_amount REAL NOT NULL, processed_amount REAL NOT NULL, type_str TEXT NOT NULL, original_type_str TEXT NOT NULL, " +
                "timestamp INTEGER NOT NULL, status_str TEXT NOT NULL, trades TEXT NOT NULL, fees TEXT NOT NULL, PRIMARY KEY(id))"
            )

            // Profile infos
            execSQL(
                "CREATE TABLE IF NOT EXISTS profile_infos (email TEXT NOT NULL, user_name TEXT NOT NULL, PRIMARY KEY(email))"
            )

            // Ticker items
            execSQL(
                "CREATE TABLE IF NOT EXISTS ticker_items " +
                "(id INTEGER NOT NULL, base_currency_code TEXT NOT NULL, base_currency_name TEXT NOT NULL, quote_currency_code TEXT NOT NULL, " +
                "quote_currency_name TEXT NOT NULL, symbol TEXT NOT NULL, ask_price REAL NOT NULL, bid_price REAL NOT NULL, last_price REAL, " +
                "open_price REAL, low_price REAL, high_price REAL, volume REAL, volume_quote REAL, timestamp INTEGER NOT NULL, PRIMARY KEY(id))"
            )

            // New trades
            execSQL(
                "CREATE TABLE IF NOT EXISTS new_trades " +
                "(id INTEGER NOT NULL, currency_pair_id INTEGER NOT NULL, price REAL NOT NULL, amount REAL NOT NULL, type_str TEXT NOT NULL, " +
                "timestamp INTEGER NOT NULL, PRIMARY KEY(id))"
            )

            // Wallets
            execSQL(
                "CREATE TABLE IF NOT EXISTS wallets " +
                "(id INTEGER, currency_id INTEGER NOT NULL, is_delisted INTEGER NOT NULL, is_disabled INTEGER NOT NULL, " +
                "is_depositing_disabled INTEGER NOT NULL, currency_name TEXT NOT NULL, currency_code TEXT NOT NULL, " +
                "current_balance REAL NOT NULL, frozen_balance REAL NOT NULL, bonus_balance REAL NOT NULL, total_balance REAL NOT NULL, " +
                "deposit_address TEXT, PRIMARY KEY(currency_id))"
            )

            // Withdrawals
            execSQL(
                "CREATE TABLE IF NOT EXISTS withdrawals " +
                "(id INTEGER NOT NULL, currency_id INTEGER NOT NULL, currency_code TEXT NOT NULL, amount REAL NOT NULL, fee REAL NOT NULL, " +
                "fee_currency_id INTEGER NOT NULL, fee_currency_code TEXT NOT NULL, status_str TEXT NOT NULL, creation_timestamp INTEGER NOT NULL, " +
                "update_timestamp INTEGER NOT NULL, transaction_id TEXT, address TEXT, PRIMARY KEY(id))"
            )
        }
    }

}


val MIGRATIONS: Array<Migration> = arrayOf(
    MIGRATION_1_2,          MIGRATION_2_3,          MIGRATION_3_4,
    MIGRATION_4_5,          MIGRATION_5_6,          MIGRATION_6_7,
    MIGRATION_7_8,          MIGRATION_8_9,          MIGRATION_9_10,
    MIGRATION_10_11,        MIGRATION_11_12,        MIGRATION_12_13,
    MIGRATION_13_14,        MIGRATION_14_15,        MIGRATION_15_16,
    MIGRATION_16_17,        MIGRATION_17_18,        MIGRATION_18_19,
    MIGRATION_19_20,        MIGRATION_20_21,        MIGRATION_21_22,
    MIGRATION_22_23,        MIGRATION_23_24,        MIGRATION_24_25,
    MIGRATION_25_26,        MIGRATION_26_27,        MIGRATION_27_28,
    MIGRATION_28_29,        MIGRATION_29_30,        MIGRATION_30_31,
    MIGRATION_31_32,        MIGRATION_32_33,        MIGRATION_33_34
)