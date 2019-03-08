package com.stocksexchange.android.ui.help

import com.stocksexchange.android.Constants.DEFAULT_LOCALE
import com.stocksexchange.android.R
import com.stocksexchange.android.model.HelpItemModel
import com.stocksexchange.android.model.SocialMediaTypes
import com.stocksexchange.android.ui.base.mvp.model.BaseModel
import com.stocksexchange.android.utils.providers.StringProvider
import org.koin.standalone.inject
import java.lang.IllegalStateException

class HelpModel : BaseModel() {


    companion object {

        const val HELP_ITEM_ID_PRICE_CHART_DATA_INTERVALS = 0
        const val HELP_ITEM_ID_DEPTH_CHART_TAB_TITLES = 1
        const val HELP_ITEM_ID_WITHDRAWALS_DISABLED = 2
        const val HELP_ITEM_ID_CURRENCY_DISABLED = 3
        const val HELP_ITEM_ID_ASSETS_DIVIDENDS = 4
        const val HELP_ITEM_ID_NO_ANSWER = 5

    }


    private val mStringProvider: StringProvider by inject()




    /**
     * Returns a list of help item model classes.
     *
     * @return The list of model classes
     */
    fun getHelpItems(): List<HelpItemModel> {
        val items = mutableListOf<HelpItemModel>()

        items.add(HelpItemModel(
            id = HELP_ITEM_ID_PRICE_CHART_DATA_INTERVALS,
            questionText = mStringProvider.getString(
                R.string.help_item_price_chart_data_intervals_question_text,
                arrayOf(
                    mStringProvider.getStringByLocale(R.string.chart_interval_one_day, DEFAULT_LOCALE),
                    mStringProvider.getStringByLocale(R.string.chart_interval_one_week, DEFAULT_LOCALE),
                    mStringProvider.getStringByLocale(R.string.chart_interval_one_month, DEFAULT_LOCALE),
                    mStringProvider.getStringByLocale(R.string.chart_interval_three_months, DEFAULT_LOCALE)
                )
            ),
            answerText = mStringProvider.getString(R.string.help_item_price_chart_data_intervals_answer_text)
        ))
        items.add(HelpItemModel(
            id = HELP_ITEM_ID_DEPTH_CHART_TAB_TITLES,
            questionText = mStringProvider.getString(R.string.help_item_depth_chart_tab_titles_question_text),
            answerText = mStringProvider.getString(
                R.string.help_item_depth_chart_tab_titles_answer_text,
                mStringProvider.getStringByLocale(R.string.depth_chart_all_tab_title, DEFAULT_LOCALE)
            )
        ))
        items.add(HelpItemModel(
            id = HELP_ITEM_ID_WITHDRAWALS_DISABLED,
            questionText = mStringProvider.getString(R.string.help_item_no_withdrawals_question_text),
            answerText = mStringProvider.getString(R.string.help_item_no_withdrawals_answer_text)
        ))
        items.add(HelpItemModel(
            id = HELP_ITEM_ID_CURRENCY_DISABLED,
            questionText = mStringProvider.getString(R.string.help_item_currency_disabled_question_text),
            answerText = mStringProvider.getString(R.string.help_item_currency_disabled_answer_text),
            buttonText = mStringProvider.getString(R.string.help_item_currency_disabled_button_text)
        ))
        items.add(HelpItemModel(
            id = HELP_ITEM_ID_ASSETS_DIVIDENDS,
            questionText = mStringProvider.getString(R.string.help_item_assets_dividends_question_text),
            answerText = mStringProvider.getString(R.string.help_item_assets_dividends_answer_text)
        ))
        items.add(HelpItemModel(
            id = HELP_ITEM_ID_NO_ANSWER,
            questionText = mStringProvider.getString(R.string.help_item_no_answer_question_text),
            answerText = mStringProvider.getString(R.string.help_item_no_answer_answer_text),
            buttonText = mStringProvider.getString(R.string.help_item_no_answer_button_text)
        ))

        return items
    }


    /**
     * Gets an instance of [SocialMediaTypes] enumeration for its title.
     *
     * @param title The title to get an instance for
     *
     * @return An instance of [SocialMediaTypes] class
     */
    fun getSocialMediaTypeForTitleString(title: String): SocialMediaTypes {
        return when(title) {
            mStringProvider.getString(R.string.twitter) -> SocialMediaTypes.TWITTER
            mStringProvider.getString(R.string.facebook) -> SocialMediaTypes.FACEBOOK
            mStringProvider.getString(R.string.telegram) -> SocialMediaTypes.TELEGRAM

            else -> throw IllegalStateException("Please provide an implementation for \"$title\" title.")
        }
    }


}