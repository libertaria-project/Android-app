package com.stocksexchange.android.ui.help

import com.stocksexchange.android.Constants
import com.stocksexchange.android.model.HelpItemModel
import com.stocksexchange.android.model.SocialMediaTypes
import com.stocksexchange.android.ui.base.mvp.presenters.BasePresenter

class HelpPresenter(
    model: HelpModel,
    view: HelpContract.View
) : BasePresenter<HelpModel, HelpContract.View>(model, view), HelpContract.ActionListener {


    constructor(view: HelpContract.View): this(HelpModel(), view)


    override fun start() {
        super.start()

        if(mView.isDataSetEmpty()) {
            mView.setItems(mModel.getHelpItems())
        }
    }


    override fun stop() {
        super.stop()

        mView.hidePopupMenu()
        mView.hideSocialMediaDialog()
    }


    override fun onActionButtonClicked() {
        mView.showPopupMenu()
    }


    override fun onFeedbackMenuItemClicked() {
        mView.launchFeedbackActivity()
    }


    override fun onAboutMenuItemClicked() {
        mView.launchAboutActivity()
    }


    override fun onHelpItemActionButtonClicked(model: HelpItemModel) {
        when(model.id) {
            HelpModel.HELP_ITEM_ID_CURRENCY_DISABLED -> onFollowUsButtonClicked()
            HelpModel.HELP_ITEM_ID_NO_ANSWER -> onContactUsButtonClicked()
        }
    }


    private fun onFollowUsButtonClicked() {
        mView.showSocialMediaDialog()
    }


    override fun onSocialMediaPicked(title: String) {
        mView.launchBrowser(when(mModel.getSocialMediaTypeForTitleString(title)) {
            SocialMediaTypes.TWITTER -> Constants.STEX_TWITTER_URL
            SocialMediaTypes.FACEBOOK -> Constants.STEX_FACEBOOK_URL
            SocialMediaTypes.TELEGRAM -> Constants.STEX_TELEGRAM_URL
        })
    }


    private fun onContactUsButtonClicked() {
        mView.finishActivity()
        mView.launchFeedbackActivity()
    }


}