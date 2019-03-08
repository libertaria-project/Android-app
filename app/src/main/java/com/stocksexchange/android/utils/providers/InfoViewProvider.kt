package com.stocksexchange.android.utils.providers

import android.content.Context
import android.graphics.drawable.Drawable
import com.stocksexchange.android.R
import com.stocksexchange.android.api.model.*
import com.stocksexchange.android.model.*
import com.stocksexchange.android.ui.utils.extensions.getCompatDrawable

/**
 * A helper class used for providing captions and icons for info views.
 */
class InfoViewProvider(context: Context) {


    private val context: Context = context.applicationContext




    /**
     * Retrieves a default error caption.
     *
     * @return The error caption
     */
    fun getDefaultErrorCaption(): String {
        return context.getString(R.string.error_something_went_wrong)
    }


    /**
     * Retrieves an empty caption for currency markets.
     *
     * @param parameters The parameters holding currency markets information
     *
     * @return The empty caption
     */
    fun getCurrencyMarketsEmptyCaption(parameters: CurrencyMarketParameters): String {
        return when(parameters.currencyMarketType) {

            CurrencyMarketTypes.SEARCH -> {
                val searchQuery = parameters.searchQuery

                if(searchQuery.isNotBlank()) {
                    context.getString(R.string.currency_markets_fragment_search_no_markets_found_template, searchQuery)
                } else {
                    context.getString(R.string.currency_markets_fragment_search_initial_message)
                }
            }

            CurrencyMarketTypes.FAVORITES -> {
                context. getString(R.string.error_no_favorite_markets_available)
            }

            else -> {
                context.getString(
                    R.string.error_no_markets_available,
                    parameters.currencyMarketType.marketName
                )
            }

        }
    }


    /**
     * Retrieves a currency markets icon to display whenever
     * no currency markets data is available.
     *
     * @param parameters The parameters holding currency
     * markets information
     *
     * @return The drawable for currency markets
     */
    fun getCurrencyMarketsIcon(parameters: CurrencyMarketParameters): Drawable? {
        return context.getCompatDrawable(when(parameters.currencyMarketType) {

            CurrencyMarketTypes.BTC -> R.drawable.ic_btc
            CurrencyMarketTypes.USDT -> R.drawable.ic_usdt
            CurrencyMarketTypes.NXT -> R.drawable.ic_nxt
            CurrencyMarketTypes.LTC -> R.drawable.ic_ltc
            CurrencyMarketTypes.ETH -> R.drawable.ic_eth
            CurrencyMarketTypes.USD -> R.drawable.ic_currency_usd
            CurrencyMarketTypes.EUR -> R.drawable.ic_currency_eur
            CurrencyMarketTypes.JPY -> R.drawable.ic_currency_jpy
            CurrencyMarketTypes.RUB -> R.drawable.ic_currency_rub
            CurrencyMarketTypes.FAVORITES -> R.drawable.ic_star
            CurrencyMarketTypes.SEARCH -> R.drawable.ic_search

        })
    }


    /**
     * Retrieves an empty caption for a deposit.
     *
     * @return The empty caption
     */
    fun getDepositEmptyCaption(): String {
        return context.getString(R.string.error_no_data_available)
    }


    /**
     * Retrieves a deposit icon to display whenever no deposit
     * data is available.
     *
     * @return The drawable for a deposit
     */
    fun getDepositIcon(): Drawable? {
        return context.getCompatDrawable(R.drawable.ic_deposit)
    }


    /**
     * Retrieves an empty caption for orders.
     *
     * @param parameters The parameters holding orders information
     *
     * @return The empty caption
     */
    fun getOrdersEmptyCaption(parameters: OrderParameters): String {
        return when(parameters.mode) {

            OrderModes.SEARCH -> {
                val searchQuery = parameters.searchQuery

                if(searchQuery.isNotBlank()) {
                    context.getString(R.string.orders_fragment_search_no_orders_found_template, searchQuery)
                } else {
                    context.getString(R.string.orders_fragment_search_initial_message)
                }
            }

            OrderModes.STANDARD -> {
                context.getString(when(parameters.type) {
                    OrderTypes.ACTIVE -> R.string.error_no_active_orders_available
                    OrderTypes.COMPLETED -> R.string.error_no_completed_orders_available
                    OrderTypes.CANCELLED -> R.string.error_no_cancelled_orders_available
                })
            }

        }
    }


    /**
     * Retrieves an orders icon to display whenever no orders
     * data is available.
     *
     * @return The drawable for orders
     */
    fun getOrdersIcon(): Drawable? {
        return context.getCompatDrawable(R.drawable.ic_swap_horizontal)
    }


    /**
     * Retrieves an empty caption for transactions.
     *
     * @param parameters The parameters holding transactions information
     *
     * @return The empty caption
     */
    fun getTransactionsEmptyCaption(parameters: TransactionParameters): String {
        return when(parameters.mode) {

            TransactionModes.SEARCH -> {
                val query = parameters.searchQuery

                if(query.isNotBlank()) {
                    context.getString(when(parameters.type) {
                        TransactionTypes.DEPOSITS -> R.string.transactions_fragment_search_no_deposits_found_template
                        TransactionTypes.WITHDRAWALS -> R.string.transactions_fragment_search_no_withdrawals_found_template
                    }, query)
                } else {
                    context.getString(when(parameters.type) {
                        TransactionTypes.DEPOSITS -> R.string.transactions_fragment_search_deposits_initial_message
                        TransactionTypes.WITHDRAWALS -> R.string.transactions_fragment_search_withdrawals_initial_message
                    })
                }
            }

            TransactionModes.STANDARD -> {
                context.getString(when(parameters.type) {
                    TransactionTypes.DEPOSITS -> R.string.error_no_deposits_available
                    TransactionTypes.WITHDRAWALS -> R.string.error_no_withdrawals_available
                })
            }

        }
    }


    /**
     * Retrieves a transactions icon to display whenever no transactions
     * data is available.
     *
     * @param parameters The parameters holding transactions information
     *
     * @return The drawable for transactions
     */
    fun getTransactionsIcon(parameters: TransactionParameters): Drawable? {
        return when(parameters.mode) {

            TransactionModes.STANDARD -> {
                when(parameters.type) {
                    TransactionTypes.DEPOSITS -> context.getCompatDrawable(R.drawable.ic_deposit)
                    TransactionTypes.WITHDRAWALS -> context.getCompatDrawable(R.drawable.ic_withdrawal)
                }
            }

            TransactionModes.SEARCH -> {
                context.getCompatDrawable(R.drawable.ic_search)
            }

        }
    }


    /**
     * Retrieves an empty caption for wallets.
     *
     * @param parameters The parameters holding wallets information
     *
     * @return The empty caption
     */
    fun getWalletsEmptyCaption(parameters: WalletParameters): String {
        return when(parameters.walletMode) {

            WalletModes.SEARCH -> {
                val searchQuery = parameters.searchQuery

                if(searchQuery.isNotBlank()) {
                    context.getString(R.string.wallets_fragment_search_no_wallets_found_template, searchQuery)
                } else {
                    context.getString(R.string.wallets_fragment_search_initial_message)
                }
            }

            WalletModes.STANDARD -> {
                context.getString(R.string.error_no_wallets_available)
            }

        }
    }


    /**
     * Retrieves a wallets icon to display whenever no wallets
     * data is available.
     *
     * @param parameters The parameters holding wallets information
     *
     * @return The drawable for wallets
     */
    fun getWalletsIcon(parameters: WalletParameters): Drawable? {
        return when(parameters.walletMode) {
            WalletModes.STANDARD -> context.getCompatDrawable(R.drawable.ic_wallet)
            WalletModes.SEARCH -> context.getCompatDrawable(R.drawable.ic_search)
        }
    }


    /**
     * Retrieves an empty caption for the price chart view.
     *
     * @return The empty caption
     */
    fun getPriceChartViewEmptyCaption(): String {
        return context.getString(R.string.error_no_chart_data_available)
    }


    /**
     * Retrieves a price chart view icon.
     *
     * @return The drawable for the price chart view
     */
    fun getPriceChartViewIcon(): Drawable? {
        return context.getCompatDrawable(R.drawable.ic_candlestick_chart)
    }


    /**
     * Retrieves an empty caption for the depth chart view.
     *
     * @return The empty caption
     */
    fun getDepthChartViewEmptyCaption(): String {
        return getPriceChartViewEmptyCaption()
    }


    /**
     * Retrieves a depth chart view icon.
     *
     * @return The drawable for the depth chart view
     */
    fun getDepthChartViewIcon(): Drawable? {
        return context.getCompatDrawable(R.drawable.ic_bar_chart)
    }


    /**
     * Retrieves an empty caption for the orderbook view.
     *
     * @return The empty caption
     */
    fun getOrderbookViewEmptyCaption(): String {
        return context.getString(R.string.error_no_orderbook_available)
    }


    /**
     * Retrieves an orderbook view icon.
     *
     * @return The drawabble for the orderbook view
     */
    fun getOrderbookViewIcon(): Drawable? {
        return context.getCompatDrawable(R.drawable.ic_doc_with_two_columns)
    }


    /**
     * Retrieves an empty caption for the trades view.
     *
     * @return The empty caption
     */
    fun getTradesViewEmptyCaption(): String {
        return context.getString(R.string.error_no_trades_available)
    }


    /**
     * Retrieves a trades view icon.
     *
     * @return The drawable for the trades view
     */
    fun getTradesViewIcon(): Drawable? {
        return context.getCompatDrawable(R.drawable.ic_coin_exchange)
    }


    /**
     * Retrieves an empty caption of the orderbook details view.
     *
     * @return The empty caption
     */
    fun getOrderbookDetailsViewEmptyCaption(): String {
        return context.getString(R.string.error_no_information_available)
    }


}