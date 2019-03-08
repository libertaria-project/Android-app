package com.stocksexchange.android.utils.handlers

import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.graphics.drawable.Icon
import com.stocksexchange.android.Constants
import com.stocksexchange.android.R
import com.stocksexchange.android.ui.currencymarkets.search.CurrencyMarketsSearchActivity
import com.stocksexchange.android.ui.favoritecurrencymarkets.FavoriteCurrencyMarketsActivity
import com.stocksexchange.android.ui.orders.OrdersActivity
import com.stocksexchange.android.ui.splash.SplashActivity
import com.stocksexchange.android.ui.utils.extensions.getShortcutManager
import com.stocksexchange.android.ui.wallets.WalletsActivity
import java.util.Arrays

/**
 * A handler used for interacting with shortcuts.
 */
class ShortcutsHandler(context: Context) {


    private val mContext: Context = context.applicationContext




    /**
     * Setups the application's shortcuts.
     */
    @SuppressWarnings("NewApi")
    fun setupShortcuts() {
        if(!Constants.AT_LEAST_NOUGAT_V2) {
            return
        }

        // Wallets
        val walletsIntent: Intent = SplashActivity.newInstanceWithWalletsDestination(mContext).apply {
            action = WalletsActivity.TAG
        }

        val walletsShortcut = ShortcutInfo.Builder(mContext, walletsIntent.action)
            .setShortLabel(mContext.getString(R.string.shortcut_wallets_short_label))
            .setLongLabel(mContext.getString(R.string.shortcut_wallets_long_label))
            .setIcon(Icon.createWithResource(mContext, R.mipmap.ic_wallets_shortcut_icon))
            .setIntent(walletsIntent)
            .build()

        // Orders
        val ordersIntent: Intent = SplashActivity.newInstanceWithOrdersDestination(mContext).apply {
            action = OrdersActivity.TAG
        }

        val ordersShortcut = ShortcutInfo.Builder(mContext, ordersIntent.action)
            .setShortLabel(mContext.getString(R.string.shortcut_orders_short_label))
            .setLongLabel(mContext.getString(R.string.shortcut_orders_long_label))
            .setIcon(Icon.createWithResource(mContext, R.mipmap.ic_orders_shortcut_icon))
            .setIntent(ordersIntent)
            .build()

        // Favorite markets
        val favoriteMarketsIntent: Intent = SplashActivity.newInstanceWithFavoriteMarketsDestination(mContext).apply {
            action = FavoriteCurrencyMarketsActivity.TAG
        }

        val favoriteMarketsShortcut = ShortcutInfo.Builder(mContext, favoriteMarketsIntent.action)
            .setShortLabel(mContext.getString(R.string.shortcut_favorite_markets_short_label))
            .setLongLabel(mContext.getString(R.string.shortcut_favorite_markets_long_label))
            .setIcon(Icon.createWithResource(mContext, R.mipmap.ic_favorite_markets_shortcut_icon))
            .setIntent(favoriteMarketsIntent)
            .build()

        // Search markets
        val searchMarketsIntent: Intent = SplashActivity.newInstanceWithMarketsSearchDestination(mContext).apply {
            action = CurrencyMarketsSearchActivity.TAG
        }

        val searchMarketsShortcut = ShortcutInfo.Builder(mContext, searchMarketsIntent.action)
            .setShortLabel(mContext.getString(R.string.shortcut_search_markets_short_label))
            .setLongLabel(mContext.getString(R.string.shortcut_search_markets_long_label))
            .setIcon(Icon.createWithResource(mContext, R.mipmap.ic_search_markets_shortcut_icon))
            .setIntent(searchMarketsIntent)
            .build()

        // Setting the shortcuts
        mContext.getShortcutManager().dynamicShortcuts = Arrays.asList(
            walletsShortcut,
            ordersShortcut,
            favoriteMarketsShortcut,
            searchMarketsShortcut
        )
    }


}