package com.stocksexchange.android.ui.deeplinkhandler

import com.stocksexchange.android.api.model.CurrencyMarket
import com.stocksexchange.android.model.DeepLinkData
import com.stocksexchange.android.model.DeepLinkTypes
import com.stocksexchange.android.model.utils.onFailure
import com.stocksexchange.android.model.utils.onSuccess
import com.stocksexchange.android.repositories.currencymarkets.CurrencyMarketsRepository
import com.stocksexchange.android.ui.base.mvp.model.BaseModel
import org.koin.standalone.inject

class DeepLinkHandlerModel : BaseModel() {


    companion object {

        private const val DEEP_LINK_MARKET_PREVIEW_SYMBOLS_PRECURSOR = "pair"

    }


    private val mCurrencyMarketsRepository: CurrencyMarketsRepository by inject()




    /**
     * Fetches the data of the provided deep link.
     *
     * @param uri The URI of the deep link
     *
     * @return The instance of [DeepLinkData] class
     */
    fun getLinkDataForUri(uri: String): DeepLinkData {
        if(uri.isBlank()) {
            return DeepLinkData(DeepLinkTypes.INVALID)
        }

        if(uri.contains(DEEP_LINK_MARKET_PREVIEW_SYMBOLS_PRECURSOR)) {
            val uriParts = uri.split('/')
            var baseCurrencySymbol = ""
            var quoteCurrencySymbol = ""
            var precursorFound = false

            for(uriPart in uriParts) {
                if(uriPart == DEEP_LINK_MARKET_PREVIEW_SYMBOLS_PRECURSOR) {
                    precursorFound = true
                } else if(precursorFound) {
                    if(quoteCurrencySymbol.isBlank()) {
                        quoteCurrencySymbol = uriPart
                    } else {
                        baseCurrencySymbol = uriPart

                        break
                    }
                }
            }

            if(baseCurrencySymbol.isNotBlank() && quoteCurrencySymbol.isNotBlank()) {
                return DeepLinkData(DeepLinkTypes.MARKET_PREVIEW, Pair(baseCurrencySymbol, quoteCurrencySymbol))
            }
        }

        return DeepLinkData(DeepLinkTypes.INVALID)
    }


    /**
     * Retrieves a currency market with a given base currency symbol and a quote currency symbol.
     *
     * @param baseCurrencySymbol The base currency symbol
     * @param quoteCurrencySymbol The quote currency symbol
     * @param onFailure The callback to invoke on failure
     * @param onSuccess The callback to invoke on success
     */
    fun getCurrencyMarket(baseCurrencySymbol: String, quoteCurrencySymbol: String,
                          onFailure: ((Throwable) -> Unit), onSuccess: ((CurrencyMarket) -> Unit)) {
        createUiLaunchCoroutine {
            mCurrencyMarketsRepository.getCurrencyMarket(baseCurrencySymbol, quoteCurrencySymbol)
                .onSuccess { onSuccess(it) }
                .onFailure { onFailure(it) }
        }
    }




}