package com.stocksexchange.android.ui.feedback

interface FeedbackContract {


    interface View {

        fun showToast(message: String)

        fun showKeyboard()

        fun enableSendButton()

        fun disableSendButton()

        fun sendEmail(emailAddress: String, subject: String, text: String)

        fun getFeedbackText(): String

        fun getRecipientEmailAddress(): String

    }


    interface ActionListener {

        fun onSendButtonClicked()

        fun onContentContainerClicked()

    }


}