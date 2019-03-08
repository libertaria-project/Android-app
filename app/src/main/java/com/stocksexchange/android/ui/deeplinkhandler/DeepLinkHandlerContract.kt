package com.stocksexchange.android.ui.deeplinkhandler

import com.stocksexchange.android.api.model.CurrencyMarket

interface DeepLinkHandlerContract {


    interface View {

        fun launchCurrencyMarketPreviewActivity(currencyMarket: CurrencyMarket)

        fun finishActivity()

        fun finishActivityWithError(error: String)

    }


    interface ActionListener {

        fun onUriRetrieved(uri: String)

    }


}