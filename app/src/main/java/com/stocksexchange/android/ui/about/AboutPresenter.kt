package com.stocksexchange.android.ui.about

import com.stocksexchange.android.Constants
import com.stocksexchange.android.ui.base.mvp.model.StubModel
import com.stocksexchange.android.ui.base.mvp.presenters.BasePresenter

class AboutPresenter(
    model: StubModel,
    view: AboutContract.View
) : BasePresenter<StubModel, AboutContract.View>(model, view), AboutContract.ActionListener {


    constructor(view: AboutContract.View): this(StubModel(), view)


    override fun stop() {
        super.stop()

        mView.hidePopupMenu()
    }


    override fun onActionButtonClicked() {
        mView.showPopupMenu()
    }


    override fun onTwitterMenuItemClicked() {
        launchBrowser(Constants.STEX_TWITTER_URL)
    }


    override fun onFacebookMenuItemClicked() {
        launchBrowser(Constants.STEX_FACEBOOK_URL)
    }


    override fun onTelegramMenuItemClicked() {
        launchBrowser(Constants.STEX_TELEGRAM_URL)
    }


    override fun onTermsOfUseMenuItemClicked() {
        launchBrowser(Constants.STEX_TERMS_OF_USE_URL)
    }


    override fun onVisitOurWebsiteButtonClicked() {
        launchBrowser(Constants.STEX_WEBSITE_URL)
    }


    private fun launchBrowser(url: String) {
        mView.launchBrowser(url)
    }


}