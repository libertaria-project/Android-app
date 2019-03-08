package com.stocksexchange.android.ui.views.marketpreview.base

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.annotation.CallSuper
import androidx.annotation.ColorInt
import com.stocksexchange.android.R
import com.stocksexchange.android.model.Attributes
import com.stocksexchange.android.model.DataActionItem
import com.stocksexchange.android.ui.utils.extensions.*
import com.stocksexchange.android.ui.views.InfoView

/**
 * A base market preview view to extend from to create custom views.
 */
@Suppress("LeakingThis")
abstract class BaseMarketPreviewView<Data : Any, DataItem: Any, Params: Parcelable> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {


    companion object {

        internal const val ATTRIBUTE_KEY_PROGRESS_BAR_COLOR = "progress_bar_color"
        internal const val ATTRIBUTE_KEY_INFO_VIEW_COLOR = "info_view_color"
        internal const val ATTRIBUTE_KEY_INFO_VIEW_EMPTY_CAPTION = "info_view_empty_caption"
        internal const val ATTRIBUTE_KEY_INFO_VIEW_ERROR_CAPTION = "info_view_error_caption"
        internal const val ATTRIBUTE_KEY_INFO_VIEW_ICON = "info_view_icon"

        internal const val DEFAULT_PROGRESS_BAR_COLOR = Color.GREEN
        internal const val DEFAULT_INFO_VIEW_COLOR = Color.GRAY

        internal const val DEFAULT_INFO_VIEW_CAPTION = ""

        private const val MAIN_VIEW_ANIMATION_DURATION = 300L

        internal val CLASS_LOADER = BaseMarketPreviewView::class.java.classLoader

    }


    protected var mProgressBarColor: Int = 0
    protected var mInfoViewColor: Int = 0

    protected var mInfoViewEmptyCaption: String = ""
    protected var mInfoViewErrorCaption: String = ""

    protected var mInfoViewIcon: Drawable? = null

    protected var mDataParameters: Params = getDefaultParameters()

    protected var mAttributes: Attributes = Attributes()

    protected var mData: Data? = null

    /**
     * An instance of [OnMainViewShowListener].
     */
    var onMainViewShowingListener: OnMainViewShowListener? = null




    init {
        View.inflate(context, getLayoutResourceId(), this)
        saveAttributes(attrs, defStyleAttr)
    }


    /**
     * Should save custom view's attributes (if any).
     *
     * @param attrs The attribute set to fetch from
     * @param defStyleAttr The style of the attributes
     */
    protected open fun saveAttributes(attrs: AttributeSet? = null, defStyleAttr: Int = 0) {
        // Stub
    }


    override fun onFinishInflate() {
        super.onFinishInflate()

        init()

        restoreAttributes()
        applyAttributes()

        mAttributes.recycle()
    }


    /**
     * Initializes the views.
     */
    @CallSuper
    protected open fun init() {
        initProgressBar()
        initInfoView()
    }


    /**
     * Initializes the progress bar.
     */
    protected open fun initProgressBar() {
        hideProgressBar()
    }


    /**
     * Initializes the info view.
     */
    protected open fun initInfoView() {
        getInfoView().setPadding(0)
        hideInfoView()
    }


    /**
     * Should restore the previously saved attributes.
     */
    @CallSuper
    protected open fun restoreAttributes() {
        with(mAttributes) {
            mProgressBarColor = get(ATTRIBUTE_KEY_PROGRESS_BAR_COLOR, DEFAULT_PROGRESS_BAR_COLOR)
            mInfoViewColor = get(ATTRIBUTE_KEY_INFO_VIEW_COLOR, DEFAULT_INFO_VIEW_COLOR)
            mInfoViewEmptyCaption = get(ATTRIBUTE_KEY_INFO_VIEW_EMPTY_CAPTION, DEFAULT_INFO_VIEW_CAPTION)
            mInfoViewErrorCaption = get(ATTRIBUTE_KEY_INFO_VIEW_ERROR_CAPTION, DEFAULT_INFO_VIEW_CAPTION)
            mInfoViewIcon = get(ATTRIBUTE_KEY_INFO_VIEW_ICON, context.getCompatDrawable(getDefaultInfoViewIconResourceId()))
        }
    }


    /**
     * Should apply the previously fetched attributes (if any).
     */
    @CallSuper
    protected open fun applyAttributes() {
        setProgressBarColor(mProgressBarColor)
        setInfoViewColor(mInfoViewColor)
        setInfoViewEmptyCaption(mInfoViewEmptyCaption)
        setInfoViewErrorCaption(mInfoViewErrorCaption)
        setInfoViewIcon(mInfoViewIcon)
    }


    /**
     * Shows the main view.
     */
    open fun showMainView() {
        getMainView().alpha = 0f
        getMainView()
            .animate()
            .alpha(1f)
            .setInterpolator(LinearInterpolator())
            .setDuration(MAIN_VIEW_ANIMATION_DURATION)
            .setListener(mMainViewShowingAnimationListener)
            .start()
    }


    /**
     * Hides the main view.
     */
    open fun hideMainView() {
        getMainView().animate().cancel()

        if(getMainView().alpha != 0f) {
            getMainView().alpha = 0f
        }
    }


    /**
     * Shows the progress bar.
     */
    open fun showProgressBar() {
        getProgressBar().makeVisible()
    }


    /**
     * Hides the progress bar.
     */
    open fun hideProgressBar() {
        getProgressBar().makeGone()
    }


    /**
     * Shows the empty view.
     */
    open fun showEmptyView() {
        setInfoViewCaption(mInfoViewEmptyCaption)
        getInfoView().makeVisible()
    }


    /**
     * Shows the error view.
     */
    open fun showErrorView() {
        setInfoViewCaption(mInfoViewErrorCaption)
        getInfoView().makeVisible()
    }


    /**
     * Hides the info view.
     */
    open fun hideInfoView() {
        getInfoView().makeGone()
    }


    /**
     * Sets a color of the progress bar.
     *
     * @param color The color to set
     */
    fun setProgressBarColor(@ColorInt color: Int) {
        mProgressBarColor = color

        getProgressBar().setColor(color)
    }


    /**
     * Sets a color of the info view (icon and caption).
     *
     * @param color The color to set
     */
    fun setInfoViewColor(@ColorInt color: Int) {
        mInfoViewColor = color

        getInfoView().setColor(color)
    }


    /**
     * Sets a caption of the info view.
     *
     * @param text The text to set
     */
    fun setInfoViewCaption(text: String) {
        getInfoView().setCaption(text)
    }


    /**
     * Sets an icon of the info view.
     *
     * @param drawable The icon to set
     */
    fun setInfoViewIcon(drawable: Drawable?) {
        getInfoView().setIcon(drawable)
    }


    /**
     * Sets an empty caption of the info view.
     *
     * @param caption The empty caption to set
     */
    fun setInfoViewEmptyCaption(caption: String) {
        mInfoViewEmptyCaption = caption
    }


    /**
     * Sets an error caption of the info view.
     *
     * @param caption The error caption to set
     */
    fun setInfoViewErrorCaption(caption: String) {
        mInfoViewErrorCaption = caption
    }


    private fun setDataParameters(dataParameters: Params) {
        mDataParameters = dataParameters
    }


    /**
     * Sets the data.
     *
     * @param data The data to set
     * @param shouldBindData Whether the data should be bound or not
     */
    fun setData(data: Data?, shouldBindData: Boolean) {
        mData = truncateData(data)

        if(isDataEmpty() && isDataAlreadyBound()) {
            clearData()
        }

        if(shouldBindData) {
            bindData()
        }
    }


    /**
     * Updates the data. By default calls [setData] method
     * and binds the new data.
     *
     * Should be overridden to provide custom implementation.
     *
     * @param data The updated data
     * @param dataActionItems The data action items that were changed
     */
    open fun updateData(data: Data, dataActionItems: List<DataActionItem<DataItem>>) {
        setData(data, true)
    }


    /**
     * Should truncate the passed in data to the appropriate size.
     *
     * @param data The data to truncate
     *
     * @return The truncated data
     */
    protected open fun truncateData(data: Data?): Data? {
        return data
    }


    /**
     * Binds the previously set data to the main view.
     */
    abstract fun bindData()


    /**
     * Clears the data.
     */
    @CallSuper
    open fun clearData() {
        mData = null
    }


    /**
     * Returns a flag indicating whether the data is empty or not.
     *
     * @return true if the data is empty; false otherwise
     */
    abstract fun isDataEmpty(): Boolean


    /**
     * Returns a flag indicating whether the data has already been
     * bound to the main view, meaning whether [bindData] has already
     * run.
     *
     * @return true if already bound; false otherwise
     */
    abstract fun isDataAlreadyBound(): Boolean


    /**
     * Should return a drawable resource ID of a default info view icon.
     *
     * @return The drawable resource ID
     */
    protected open fun getDefaultInfoViewIconResourceId(): Int {
        return R.drawable.ic_information
    }


    /**
     * Should return a layout resource ID of the custom view.
     *
     * @return The layout resource ID
     */
    protected abstract fun getLayoutResourceId(): Int


    /**
     * Returns data parameters.
     *
     * @return The parameters for loading the data
     */
    fun getDataParameters(): Params {
        return mDataParameters
    }


    /**
     * Should return default data parameters.
     *
     * @return The default data parameters
     */
    protected abstract fun getDefaultParameters(): Params


    /**
     * Should return a progress bar.
     *
     * @return The progress bar
     */
    protected abstract fun getProgressBar(): ProgressBar


    /**
     * Should return an info view.
     *
     * @return The info view
     */
    protected abstract fun getInfoView(): InfoView


    /**
     * Should return a main view.
     *
     * @return The main view
     */
    protected abstract fun getMainView(): View


    private val mMainViewShowingAnimationListener = object : AnimatorListenerAdapter() {

        override fun onAnimationStart(animation: Animator) {
            onMainViewShowingListener?.onMainViewShowingStarted()
        }

        override fun onAnimationEnd(animation: Animator) {
            onMainViewShowingListener?.onMainViewShowingEnded()
        }

    }


    /**
     * A listener to invoke to get notified when main view
     * showing has started and ended.
     */
    interface OnMainViewShowListener {

        fun onMainViewShowingStarted() {
            // Stub
        }

        fun onMainViewShowingEnded() {
            // Stub
        }

    }


    @Suppress("UNCHECKED_CAST")
    override fun onRestoreInstanceState(state: Parcelable?) {
        super.onRestoreInstanceState(state.fetchProperState())

        if(state is BaseMarketPreviewSavedState<*>) {
            setProgressBarColor(state.progressBarColor)
            setInfoViewColor(state.infoViewColor)
            setInfoViewEmptyCaption(state.infoViewEmptyCaption)
            setInfoViewErrorCaption(state.infoViewErrorCaption)
            setDataParameters(state.dataParameters as Params)
        }
    }


    /**
     * Method used for saving instance state.
     *
     * @param savedState The instance of [BaseMarketPreviewSavedState]
     */
    @CallSuper
    protected open fun onSaveInstanceState(savedState: BaseMarketPreviewSavedState<Params>) {
        with(savedState) {
            progressBarColor = mProgressBarColor
            infoViewColor = mInfoViewColor
            infoViewEmptyCaption = mInfoViewEmptyCaption
            infoViewErrorCaption = mInfoViewErrorCaption
            dataParameters = mDataParameters
        }
    }


    /**
     * A base class used for storing & restoring saved state.
     */
    abstract class BaseMarketPreviewSavedState<Params: Parcelable> : BaseSavedState {

        companion object {

            private const val KEY_PROGRESS_BAR_COLOR = "progress_bar_color"
            private const val KEY_INFO_VIEW_COLOR = "info_view_color"
            private const val KEY_INFO_VIEW_EMPTY_CAPTION = "info_view_empty_caption"
            private const val KEY_INFO_VIEW_ERROR_CAPTION = "info_view_error_caption"
            private const val KEY_DATA_PARAMETERS = "data_parameters"

        }


        internal var progressBarColor: Int = 0
        internal var infoViewColor: Int = 0

        internal var infoViewEmptyCaption: String = ""
        internal var infoViewErrorCaption: String = ""

        internal lateinit var dataParameters: Params


        constructor(parcel: Parcel): super(parcel) {
            parcel.readBundle(CLASS_LOADER)?.run {
                progressBarColor = getInt(KEY_PROGRESS_BAR_COLOR, DEFAULT_PROGRESS_BAR_COLOR)
                infoViewColor = getInt(KEY_INFO_VIEW_COLOR, DEFAULT_INFO_VIEW_COLOR)
                infoViewEmptyCaption = getString(KEY_INFO_VIEW_EMPTY_CAPTION, DEFAULT_INFO_VIEW_CAPTION)
                infoViewErrorCaption = getString(KEY_INFO_VIEW_ERROR_CAPTION, DEFAULT_INFO_VIEW_CAPTION)
                dataParameters = getParcelable(KEY_DATA_PARAMETERS)
            }
        }


        constructor(superState: Parcelable?): super(superState)


        override fun writeToParcel(parcel: Parcel, flags: Int) {
            super.writeToParcel(parcel, flags)

            val bundle = Bundle(CLASS_LOADER).apply {
                putInt(KEY_PROGRESS_BAR_COLOR, progressBarColor)
                putInt(KEY_INFO_VIEW_COLOR, infoViewColor)
                putString(KEY_INFO_VIEW_EMPTY_CAPTION, infoViewEmptyCaption)
                putString(KEY_INFO_VIEW_ERROR_CAPTION, infoViewErrorCaption)
                putParcelable(KEY_DATA_PARAMETERS, dataParameters)
            }

            parcel.writeBundle(bundle)
        }

    }


}