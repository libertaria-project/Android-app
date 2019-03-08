package com.stocksexchange.android.ui.deeplinkhandler

import com.stocksexchange.android.R
import com.stocksexchange.android.model.DeepLinkData
import com.stocksexchange.android.model.DeepLinkTypes
import com.stocksexchange.android.ui.base.mvp.presenters.BasePresenter
import com.stocksexchange.android.utils.providers.StringProvider
import org.koin.standalone.inject

class DeepLinkHandlerPresenter(
    model: DeepLinkHandlerModel,
    view: DeepLinkHandlerContract.View
) : BasePresenter<DeepLinkHandlerModel, DeepLinkHandlerContract.View>(model, view), DeepLinkHandlerContract.ActionListener {


    private val mStringProvider: StringProvider by inject()




    constructor(view: DeepLinkHandlerContract.View): this(DeepLinkHandlerModel(), view)


    @Suppress("UNCHECKED_CAST")
    override fun onUriRetrieved(uri: String) {
        val deepLinkData: DeepLinkData = mModel.getLinkDataForUri(uri)
        val errorHandler = { throwable: Throwable ->
            mView.finishActivityWithError(mStringProvider.getString(R.string.error_something_went_wrong))
        }

        when(deepLinkData.deepLinkType) {

            DeepLinkTypes.INVALID -> {
                mView.finishActivityWithError(mStringProvider.getString(R.string.error_invalid_deep_link))
            }

            DeepLinkTypes.MARKET_PREVIEW -> {
                val symbolsPair: Pair<String, String> = (deepLinkData.data as Pair<String, String>)

                mModel.getCurrencyMarket(symbolsPair.first, symbolsPair.second, errorHandler) {
                    mView.launchCurrencyMarketPreviewActivity(it)
                    mView.finishActivity()
                }
            }

        }
    }


}