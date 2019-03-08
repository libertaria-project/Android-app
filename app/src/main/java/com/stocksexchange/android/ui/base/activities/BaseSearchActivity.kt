package com.stocksexchange.android.ui.base.activities

import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.View.OnClickListener
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import com.stocksexchange.android.ui.base.fragments.BaseFragment
import com.stocksexchange.android.ui.base.mvp.presenters.Presenter
import com.stocksexchange.android.theming.ThemingUtil
import com.stocksexchange.android.ui.utils.listeners.QueryListener
import com.stocksexchange.android.ui.views.SearchToolbar

/**
 * A base search activity to extend from.
 */
abstract class BaseSearchActivity<T : BaseFragment<*>, P : Presenter> : BaseFragmentActivity<T, P>() {


    companion object {

        private const val SAVED_STATE_IS_QUERY_INPUT_EMPTY = "is_query_input_empty"

    }


    private var mIsQueryInputEmpty: Boolean = true




    override fun init() {
        super.init()

        initSearchToolbar()
    }


    private fun initSearchToolbar() {
        val searchToolbar = getSearchToolbar()

        with(searchToolbar) {
            setOnLeftButtonClickListener {
                onBackPressed()
            }

            setHintText(getInputHint())
            setInputType(getInputType())
            addTextWatcher(QueryListener(mQueryListenerCallback))
            setOnEditorActionListener(mOnEditorActionListener)

            if(mIsQueryInputEmpty) {
                hideClearInputButton(false)
            } else {
                showClearInputButton(false)
            }

            setOnClearInputButtonClickListener(mOnClearInputBtnClickListener)

            ThemingUtil.Main.searchToolbar(this, getAppTheme())
        }
    }


    private fun showClearInputButton() {
        getSearchToolbar().showClearInputButton(true)
    }


    private fun hideClearInputButton() {
        getSearchToolbar().hideClearInputButton(true)
    }


    private fun showKeyboard(shouldDelay: Boolean) {
        getSearchToolbar().showKeyboard(shouldDelay)
    }


    private fun hideKeyboard() {
        getSearchToolbar().hideKeyboard()
    }


    /**
     * Should perform a search on the passed in query.
     */
    protected abstract fun performSearch(query: String)


    /**
     * Should cancel the search.
     */
    protected abstract fun cancelSearch()


    private fun setSearchQuery(query: String) {
        getSearchToolbar().setText(query)
    }


    /**
     * Should return a hint for the EditText.
     */
    protected abstract fun getInputHint(): String


    /**
     * Should return an input type for the EditText.
     */
    protected open fun getInputType(): Int {
        return InputType.TYPE_CLASS_TEXT
    }


    /**
     * Should return a reference to an instance of [SearchToolbar].
     */
    protected abstract fun getSearchToolbar(): SearchToolbar


    override fun onResume() {
        super.onResume()

        if(mIsQueryInputEmpty) {
            showKeyboard(true)
        } else {
            hideKeyboard()
        }
    }


    private val mQueryListenerCallback: QueryListener.Callback = object : QueryListener.Callback {

        override fun onQueryEntered(query: String) {
            if(mIsQueryInputEmpty) {
                getSearchToolbar().showClearInputButton(true)
                mIsQueryInputEmpty = false
            }
        }

        override fun onQueryRemoved() {
            if(!mIsQueryInputEmpty) {
                getSearchToolbar().hideClearInputButton(true)
                mIsQueryInputEmpty = true
            }
        }

    }


    private val mOnEditorActionListener: OnEditorActionListener = OnEditorActionListener { view, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            hideKeyboard()

            val query = view.text.toString()

            if(query.isNotBlank()) {
                setSearchQuery(query)
                performSearch(query)
            }
        }

        true
    }


    private val mOnClearInputBtnClickListener: ((View) -> Unit) = {
        cancelSearch()
        setSearchQuery("")
        showKeyboard(false)
    }


    override fun onRestoreState(savedState: Bundle?) {
        super.onRestoreState(savedState)

        if(savedState != null) {
            mIsQueryInputEmpty = savedState.getBoolean(SAVED_STATE_IS_QUERY_INPUT_EMPTY, true)
        }
    }


    override fun onSaveState(savedState: Bundle) {
        super.onSaveState(savedState)

        savedState.putBoolean(SAVED_STATE_IS_QUERY_INPUT_EMPTY, mIsQueryInputEmpty)
    }


    override fun onRecycle(isChangingConfigurations: Boolean) {
        super.onRecycle(isChangingConfigurations)

        getSearchToolbar().recycle()
    }


}