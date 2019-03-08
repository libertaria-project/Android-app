package com.stocksexchange.android.ui.feedback

import com.stocksexchange.android.R
import com.stocksexchange.android.ui.base.mvp.model.StubModel
import com.stocksexchange.android.ui.base.mvp.presenters.BasePresenter
import com.stocksexchange.android.ui.utils.listeners.QueryListener
import com.stocksexchange.android.utils.helpers.composeFeedbackEmailSubject
import com.stocksexchange.android.utils.providers.StringProvider
import org.koin.standalone.inject

class FeedbackPresenter(
    model: StubModel,
    view: FeedbackContract.View
) : BasePresenter<StubModel, FeedbackContract.View>(model, view), FeedbackContract.ActionListener,
    QueryListener.Callback {


    private val mStringProvider: StringProvider by inject()




    constructor(view: FeedbackContract.View): this(StubModel(), view)


    override fun onQueryEntered(query: String) {
        mView.enableSendButton()
    }


    override fun onQueryRemoved() {
        mView.disableSendButton()
    }


    override fun onSendButtonClicked() {
        mView.sendEmail(
            mView.getRecipientEmailAddress(),
            composeFeedbackEmailSubject(mStringProvider.getString(R.string.app_name)),
            mView.getFeedbackText()
        )
    }


    override fun onContentContainerClicked() {
        mView.showKeyboard()
    }


}