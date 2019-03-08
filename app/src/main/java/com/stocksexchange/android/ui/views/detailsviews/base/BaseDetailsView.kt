package com.stocksexchange.android.ui.views.detailsviews.base

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.CallSuper
import androidx.annotation.ColorInt
import com.stocksexchange.android.model.Attributes
import com.stocksexchange.android.ui.utils.extensions.*
import com.stocksexchange.android.ui.views.DottedMapView

/**
 * A base details view to extend from to create custom views.
 */
@Suppress("LeakingThis")
abstract class BaseDetailsView<Data : Parcelable> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {


    companion object {

        internal const val ATTRIBUTE_KEY_ITEM_TITLE_COLOR = "item_title_color"
        internal const val ATTRIBUTE_KEY_ITEM_VALUE_COLOR = "item_value_color"
        internal const val ATTRIBUTE_KEY_ITEM_SEPARATOR_COLOR = "item_separator_color"
        internal const val ATTRIBUTE_KEY_PROGRESS_BAR_COLOR = "progress_bar_color"
        internal const val ATTRIBUTE_KEY_INFO_VIEW_CAPTION_COLOR = "info_view_caption_color"
        internal const val ATTRIBUTE_KEY_INFO_VIEW_EMPTY_CAPTION = "info_view_empty_caption"
        internal const val ATTRIBUTE_KEY_INFO_VIEW_ERROR_CAPTION = "info_view_error_caption"

        internal const val DEFAULT_ITEM_TITLE_COLOR = Color.LTGRAY
        internal const val DEFAULT_ITEM_VALUE_COLOR = Color.LTGRAY
        internal const val DEFAULT_ITEM_SEPARATOR_COLOR = Color.LTGRAY

        internal const val DEFAULT_PROGRESS_BAR_COLOR = Color.GREEN
        internal const val DEFAULT_INFO_VIEW_CAPTION_COLOR = Color.GRAY

        internal const val DEFAULT_INFO_VIEW_CAPTION = ""

        internal const val MAIN_VIEW_ANIMATION_DURATION = 300L

        internal val CLASS_LOADER = BaseDetailsView::class.java.classLoader

    }


    private var mItemTitleColor: Int = 0
    private var mItemValueColor: Int = 0
    private var mItemSeparatorColor: Int = 0

    private var mProgressBarColor: Int = 0
    private var mInfoViewCaptionColor: Int = 0

    private var mInfoViewEmptyCaption: String = ""
    private var mInfoViewErrorCaption: String = ""

    protected var mAttributes: Attributes = Attributes()

    protected var mData: Data? = null

    private lateinit var mDottedMapViews: Array<DottedMapView>




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


    private fun init() {
        if(hasProgressBar()) {
            initProgressBar()
        }

        if(hasInfoView()) {
            initInfoView()
        }

        initDottedMapViews()
    }


    private fun initProgressBar() {
        hideProgressBar()
    }


    private fun initInfoView() {
        hideInfoView()
    }


    private fun initDottedMapViews() {
        mDottedMapViews = getDottedMapViewsArray()
    }


    /**
     * Should restore the previously saved attributes.
     */
    protected open fun restoreAttributes() {
        with(mAttributes) {
            mItemTitleColor = get(ATTRIBUTE_KEY_ITEM_TITLE_COLOR, DEFAULT_ITEM_TITLE_COLOR)
            mItemValueColor = get(ATTRIBUTE_KEY_ITEM_VALUE_COLOR, DEFAULT_ITEM_VALUE_COLOR)
            mItemSeparatorColor = get(ATTRIBUTE_KEY_ITEM_SEPARATOR_COLOR, DEFAULT_ITEM_SEPARATOR_COLOR)
            mProgressBarColor = get(ATTRIBUTE_KEY_PROGRESS_BAR_COLOR, DEFAULT_PROGRESS_BAR_COLOR)
            mInfoViewCaptionColor = get(ATTRIBUTE_KEY_INFO_VIEW_CAPTION_COLOR, DEFAULT_INFO_VIEW_CAPTION_COLOR)
            mInfoViewEmptyCaption = get(ATTRIBUTE_KEY_INFO_VIEW_EMPTY_CAPTION, DEFAULT_INFO_VIEW_CAPTION)
            mInfoViewErrorCaption = get(ATTRIBUTE_KEY_INFO_VIEW_ERROR_CAPTION, DEFAULT_INFO_VIEW_CAPTION)
        }
    }


    /**
     * Should apply the previously fetched attributes (if any).
     */
    protected open fun applyAttributes() {
        setItemTitleColor(mItemTitleColor)
        setItemValueColor(mItemValueColor)
        setItemSeparatorColor(mItemSeparatorColor)
        setProgressBarColor(mProgressBarColor)
        setInfoViewCaptionColor(mInfoViewCaptionColor)
        setInfoViewEmptyCaption(mInfoViewEmptyCaption)
        setInfoViewErrorCaption(mInfoViewErrorCaption)
    }


    /**
     * Shows the main view.
     */
    fun showMainView() {
        getMainView().alpha = 1f
    }


    /**
     * Shows the main view by animating it.
     */
    fun showMainViewAnimated() {
        getMainView().alpha = 0f
        getMainView()
            .animate()
            .alpha(1f)
            .setInterpolator(LinearInterpolator())
            .setDuration(MAIN_VIEW_ANIMATION_DURATION)
            .start()
    }


    /**
     * Hides the main view.
     */
    fun hideMainView() {
        getMainView().animate().cancel()

        if(getMainView().alpha != 0f) {
            getMainView().alpha = 0f
        }
    }


    /**
     * Shows the progress bar.
     */
    fun showProgressBar() {
        if(!hasProgressBar()) {
            return
        }

        getProgressBar()?.makeVisible()
    }


    /**
     * Hides the progress bar.
     */
    fun hideProgressBar() {
        if(!hasProgressBar()) {
            return
        }

        getProgressBar()?.makeGone()
    }


    /**
     * Shows the empty view.
     */
    fun showEmptyView() {
        if(!hasInfoView()) {
            return
        }

        val infoView = getInfoView() ?: return

        infoView.text = mInfoViewEmptyCaption
        infoView.makeVisible()
    }


    /**
     * Shows the error view.
     */
    fun showErrorView() {
        if(!hasInfoView()) {
            return
        }

        val infoView = getInfoView() ?: return

        infoView.text = mInfoViewErrorCaption
        infoView.makeVisible()
    }


    /**
     * Hides the info view.
     */
    fun hideInfoView() {
        if(!hasInfoView()) {
            return
        }

        getInfoView()?.makeGone()
    }


    /**
     * Sets a title color of the item.
     *
     * @param color The color to set
     */
    fun setItemTitleColor(@ColorInt color: Int) {
        mItemTitleColor = color

        mDottedMapViews.forEach {
            it.setTitleColor(color)
        }
    }


    /**
     * Sets a value color of the item.
     *
     * @param color The color to set
     */
    fun setItemValueColor(@ColorInt color: Int) {
        mItemValueColor = color

        mDottedMapViews.forEach {
            it.setValueColor(color)
        }
    }


    /**
     * Sets a separator color of the item.
     *
     * @param color The color to set
     */
    fun setItemSeparatorColor(@ColorInt color: Int) {
        mItemSeparatorColor = color

        mDottedMapViews.forEach {
            it.setSeparatorColor(color)
        }
    }


    /**
     * Sets a progress bar color.
     *
     * @param color The color to set
     */
    fun setProgressBarColor(@ColorInt color: Int) {
        if(!hasProgressBar()) {
            return
        }

        mProgressBarColor = color

        getProgressBar()?.setColor(color)
    }


    /**
     * Sets a caption color of the info view.
     *
     * @param color The color to set
     */
    fun setInfoViewCaptionColor(@ColorInt color: Int) {
        mInfoViewCaptionColor = color

        getInfoView()?.setTextColor(color)
    }


    /**
     * Sets a text of the empty caption of the info view.
     *
     * @param caption The caption to set
     */
    fun setInfoViewEmptyCaption(caption: String) {
        mInfoViewEmptyCaption = caption
    }


    /**
     * Sets a text of the error caption of the info view.
     *
     * @param caption The caption to set
     */
    fun setInfoViewErrorCaption(caption: String) {
        mInfoViewErrorCaption = caption
    }


    /**
     * Sets the data.
     *
     * @param data The data to set
     * @param shouldBindData Whether the data should be bound or not
     */
    fun setData(data: Data?, shouldBindData: Boolean) {
        mData = data

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
     * @param data The new data
     */
    open fun updateData(data: Data) {
        setData(data, true)
    }


    /**
     * Binds the previously set data to the main view.
     */
    abstract fun bindData()


    /**
     * Returns a boolean indicating whether the data is empty or not.
     *
     * @return true if empty; false otherwise
     */
    fun isDataEmpty(): Boolean {
        return (mData == null)
    }


    /**
     * Should return whether this info view has a progress bar or not.
     * By default returns false.
     *
     * @return true if has; false otherwise
     */
    protected open fun hasProgressBar(): Boolean = false


    /**
     * Should return whether this info view has an info view or not.
     * By default returns false.
     *
     * @return true if has; false otherwise
     */
    protected open fun hasInfoView(): Boolean = false


    /**
     * Should return a layout resource ID for this view.
     *
     * @return The layout resource ID
     */
    protected abstract fun getLayoutResourceId(): Int


    /**
     * Should return a reference to an instance of [ProgressBar]
     * if this view has one. Returns null by default.
     *
     * @return The reference to the instance of [ProgressBar] or null
     * if the view does not have one
     */
    protected open fun getProgressBar(): ProgressBar? = null


    /**
     * Should return a reference to an informational view
     * if this view has one. Returns null by default.
     *
     * @return The reference to the informational view or null
     * if the view does not have one
     */
    protected open fun getInfoView(): TextView? = null


    /**
     * Should return a main view.
     *
     * @return The main view
     */
    protected abstract fun getMainView(): View


    /**
     * Should return an array of all instances of [DottedMapView] class
     * the view has.
     *
     * @return The array of instances of [DottedMapView] class
     */
    protected abstract fun getDottedMapViewsArray(): Array<DottedMapView>


    @Suppress("UNCHECKED_CAST")
    override fun onRestoreInstanceState(state: Parcelable?) {
        super.onRestoreInstanceState(state.fetchProperState())

        if(state is BaseDetailsSavedState<*>) {
            setItemTitleColor(state.itemTitleColor)
            setItemValueColor(state.itemValueColor)
            setItemSeparatorColor(state.itemSeparatorColor)
            setProgressBarColor(state.progressBarColor)
            setInfoViewCaptionColor(state.infoViewCaptionColor)
            setInfoViewEmptyCaption(state.infoViewEmptyCaption)
            setInfoViewErrorCaption(state.infoViewErrorCaption)
            setData((state.data as Data), true)
        }
    }


    /**
     * Method used for saving instance state.
     *
     * @param savedState The instance of [BaseDetailsSavedState]
     */
    @CallSuper
    protected open fun onSaveInstanceState(savedState: BaseDetailsSavedState<Data>) {
        with(savedState) {
            itemTitleColor = mItemTitleColor
            itemValueColor = mItemValueColor
            itemSeparatorColor = mItemSeparatorColor
            progressBarColor = mProgressBarColor
            infoViewCaptionColor = mInfoViewCaptionColor
            infoViewEmptyCaption = mInfoViewEmptyCaption
            infoViewErrorCaption = mInfoViewErrorCaption
            data = mData
        }
    }


    abstract class BaseDetailsSavedState<Data : Parcelable> : BaseSavedState {

        companion object {

            private const val KEY_ITEM_TITLE_COLOR = "item_title_color"
            private const val KEY_ITEM_VALUE_COLOR = "item_value_color"
            private const val KEY_ITEM_SEPARATOR_COLOR = "item_separator_color"
            private const val KEY_PROGRESS_BAR_COLOR = "progress_bar_color"
            private const val KEY_INFO_VIEW_CAPTION_COLOR = "info_view_caption_color"
            private const val KEY_INFO_VIEW_EMPTY_CAPTION = "info_view_empty_caption"
            private const val KEY_INFO_VIEW_ERROR_CAPTION = "info_view_error_caption"
            private const val KEY_DATA = "data"

        }


        internal var itemTitleColor: Int = 0
        internal var itemValueColor: Int = 0
        internal var itemSeparatorColor: Int = 0
        internal var progressBarColor: Int = 0
        internal var infoViewCaptionColor: Int = 0

        internal var infoViewEmptyCaption: String = ""
        internal var infoViewErrorCaption: String = ""

        internal var data: Data? = null


        constructor(parcel: Parcel): super(parcel) {
            parcel.readBundle(CLASS_LOADER)?.run {
                itemTitleColor = getInt(KEY_ITEM_TITLE_COLOR, DEFAULT_ITEM_TITLE_COLOR)
                itemValueColor = getInt(KEY_ITEM_VALUE_COLOR, DEFAULT_ITEM_VALUE_COLOR)
                itemSeparatorColor = getInt(KEY_ITEM_SEPARATOR_COLOR, DEFAULT_ITEM_SEPARATOR_COLOR)
                progressBarColor = getInt(KEY_PROGRESS_BAR_COLOR, DEFAULT_PROGRESS_BAR_COLOR)
                infoViewCaptionColor = getInt(KEY_INFO_VIEW_CAPTION_COLOR, DEFAULT_INFO_VIEW_CAPTION_COLOR)
                infoViewEmptyCaption = getString(KEY_INFO_VIEW_EMPTY_CAPTION, DEFAULT_INFO_VIEW_CAPTION)
                infoViewErrorCaption = getString(KEY_INFO_VIEW_ERROR_CAPTION, DEFAULT_INFO_VIEW_CAPTION)
                data = getParcelable(KEY_DATA)
            }
        }


        constructor(superState: Parcelable?): super(superState)


        override fun writeToParcel(parcel: Parcel, flags: Int) {
            super.writeToParcel(parcel, flags)

            val bundle = Bundle(CLASS_LOADER).apply {
                putInt(KEY_ITEM_TITLE_COLOR, itemTitleColor)
                putInt(KEY_ITEM_VALUE_COLOR, itemValueColor)
                putInt(KEY_ITEM_SEPARATOR_COLOR, itemSeparatorColor)
                putInt(KEY_PROGRESS_BAR_COLOR, progressBarColor)
                putInt(KEY_INFO_VIEW_CAPTION_COLOR, infoViewCaptionColor)
                putString(KEY_INFO_VIEW_EMPTY_CAPTION, infoViewEmptyCaption)
                putString(KEY_INFO_VIEW_ERROR_CAPTION, infoViewErrorCaption)
                putParcelable(KEY_DATA, data)
            }

            parcel.writeBundle(bundle)
        }

    }


}