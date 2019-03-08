package com.stocksexchange.android.ui.help

import com.stocksexchange.android.model.HelpItemModel

interface HelpContract {


    interface View {

        fun showPopupMenu()

        fun hidePopupMenu()

        fun showSocialMediaDialog()

        fun hideSocialMediaDialog()

        fun launchFeedbackActivity()

        fun launchAboutActivity()

        fun launchBrowser(url: String)

        fun finishActivity()

        fun setItems(items: List<HelpItemModel>)

        fun isDataSetEmpty(): Boolean

    }


    interface ActionListener {

        fun onActionButtonClicked()

        fun onFeedbackMenuItemClicked()

        fun onAboutMenuItemClicked()

        fun onHelpItemActionButtonClicked(model: HelpItemModel)

        fun onSocialMediaPicked(title: String)

    }


}