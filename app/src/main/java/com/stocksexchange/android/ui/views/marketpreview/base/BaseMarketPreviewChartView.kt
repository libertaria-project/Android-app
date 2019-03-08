package com.stocksexchange.android.ui.views.marketpreview.base

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.util.TypedValue
import androidx.annotation.CallSuper
import androidx.annotation.ColorInt
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarLineChartBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.google.android.material.tabs.TabLayout
import com.stocksexchange.android.R
import com.stocksexchange.android.model.ChartHighlight
import com.stocksexchange.android.ui.utils.CustomTabAnimator
import com.stocksexchange.android.ui.utils.extensions.getLocale
import com.stocksexchange.android.ui.utils.extensions.makeInvisible
import com.stocksexchange.android.ui.utils.extensions.makeVisible
import com.stocksexchange.android.ui.utils.extensions.spToPx
import com.stocksexchange.android.ui.utils.listeners.adapters.OnTabSelectedListenerAdapter
import com.stocksexchange.android.ui.views.SimpleMapView
import com.stocksexchange.android.utils.formatters.DoubleFormatter

/**
 * A base market preview chart view to extend from to create custom views
 * with a chart in them.
 */
abstract class BaseMarketPreviewChartView<
    Data : Any,
    DataItem : Any,
    Params: Parcelable
> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseMarketPreviewView<Data, DataItem, Params>(context, attrs, defStyleAttr) {


    companion object {

        internal const val ATTRIBUTE_KEY_IS_CHART_ANIMATION_ENABLED = "is_chart_animation_enabled"
        internal const val ATTRIBUTE_KEY_CHART_INFO_FIELDS_DEFAULT_TEXT_COLOR = "chart_info_fields_default_text_color"
        internal const val ATTRIBUTE_KEY_CHART_AXIS_GRID_COLOR = "chart_axis_grid_color"
        internal const val ATTRIBUTE_KEY_CHART_HIGHLIGHTER_COLOR = "chart_highlighter_color"
        internal const val ATTRIBUTE_KEY_CHART_POSITIVE_COLOR = "chart_positive_color"
        internal const val ATTRIBUTE_KEY_CHART_NEGATIVE_COLOR = "chart_negative_color"
        internal const val ATTRIBUTE_KEY_CHART_NEUTRAL_COLOR = "chart_neutral_color"
        internal const val ATTRIBUTE_KEY_TAB_TEXT_COLOR = "tab_text_color"
        internal const val ATTRIBUTE_KEY_TAB_INDICATOR_COLOR = "tab_indicator_color"
        internal const val ATTRIBUTE_KEY_TAB_TEXT_SIZE = "tab_text_size"

        internal const val DEFAULT_IS_CHART_ANIMATION_ENABLED = true
        internal const val DEFAULT_CHART_INFO_FIELDS_TEXT_COLOR = Color.LTGRAY
        internal const val DEFAULT_CHART_AXIS_GRID_COLOR = Color.DKGRAY
        internal const val DEFAULT_CHART_HIGHLIGHTER_COLOR = Color.YELLOW
        internal const val DEFAULT_CHART_POSITIVE_COLOR = Color.GREEN
        internal const val DEFAULT_CHART_NEGATIVE_COLOR = Color.RED
        internal const val DEFAULT_CHART_NEUTRAL_COLOR = Color.BLUE
        internal const val DEFAULT_TAB_TEXT_COLOR = Color.WHITE
        internal const val DEFAULT_TAB_INDICATOR_COLOR = Color.GRAY

        internal const val DEFAULT_TAB_TEXT_SIZE_IN_SP = 16f

        internal const val DEFAULT_CHART_INFO_FIELD_VALUE = "-"

        internal const val CHART_HIGHLIGHTER_WIDTH = 0.5f

        private const val CHART_ANIMATION_DURATION = 300

    }


    protected var mIsTabSelectedProgrammatically: Boolean = false
    protected var mIsChartAnimationEnabled: Boolean = true

    protected var mChartInfoFieldsDefaultTextColor: Int = 0
    protected var mChartAxisGridColor: Int = 0
    protected var mChartHighlighterColor: Int = 0
    protected var mChartPositiveColor: Int = 0
    protected var mChartNegativeColor: Int = 0
    protected var mChartNeutralColor: Int = 0
    protected var mTabTextColor: Int = 0
    protected var mTabIndicatorColor: Int = 0

    protected var mSelectedTabPosition: Int = 0

    protected var mTabTextSize: Float = 0f

    protected var mChartHighlight: ChartHighlight? = null

    protected val mDoubleFormatter: DoubleFormatter = DoubleFormatter.getInstance(context.getLocale())

    protected var mTabAnimator: CustomTabAnimator? = null

    /**
     * An instance of [OnChartTouchListener].
     */
    var onChartTouchListener: OnChartTouchListener? = null




    override fun init() {
        super.init()

        initChartInfoFields()
        initChart()

        if(shouldInitTabs()) {
            initTabs()
        }
    }


    /**
     * Should initialize the informational fields of the chart.
     */
    protected open fun initChartInfoFields() {
        getChartInfoFieldsArray().forEach {
            it.setValueTypefaceStyle(Typeface.NORMAL)
        }

        if(isDataEmpty()) {
            resetChartInfoFieldsValues()
        }
    }


    /**
     * Initializes the chart.
     */
    @CallSuper
    protected open fun initChart() {
        with(getChart()) {
            description.isEnabled = false
            setDrawGridBackground(false)
            isDoubleTapToZoomEnabled = false
            setPinchZoom(true)
            isScaleXEnabled = true
            isScaleYEnabled = false
            setNoDataText("")
            setOnChartValueSelectedListener(mOnChartValueSelectedListener)
            setOnTouchListener(mOnChartTouchListener)

            with(xAxis) {
                setDrawGridLines(true)
                setDrawAxisLine(false)
                setDrawLabels(false)
                position = XAxis.XAxisPosition.BOTTOM
            }

            with(axisLeft) {
                setDrawGridLines(true)
                setDrawAxisLine(false)
                setDrawLabels(false)
                setDrawZeroLine(false)
            }

            with(axisRight) {
                setDrawGridLines(false)
                setDrawAxisLine(false)
                setDrawLabels(false)
                setDrawZeroLine(false)
            }

            legend.isEnabled = false

            setViewPortOffsets(0f, 0f, 0f, 0f)
        }
    }


    /**
     * Initializes the tabs.
     */
    @CallSuper
    protected open fun initTabs() {
        initTabLayout()
        initTabAnimator()
    }


    /**
     * Initializes the tab layout.
     */
    @CallSuper
    protected open fun initTabLayout() {
        with(getTabLayout()) {
            addTabLayoutTabs()
            setSelectedTabPosition(mSelectedTabPosition, true)
            addOnTabSelectedListener(mOnTabSelectionListener)
        }
    }


    /**
     * Should add tab layout tabs.
     */
    protected abstract fun addTabLayoutTabs()


    /**
     * Initializes the tab animator.
     */
    @CallSuper
    protected open fun initTabAnimator() {
        mTabAnimator = CustomTabAnimator.newInstance(getTabLayout())

        initTabAnimatorTabs()
    }


    /**
     * Should initialize tabs of the tab animator.
     */
    protected abstract fun initTabAnimatorTabs()


    override fun restoreAttributes() {
        super.restoreAttributes()

        with(mAttributes) {
            mIsChartAnimationEnabled = get(ATTRIBUTE_KEY_IS_CHART_ANIMATION_ENABLED, DEFAULT_IS_CHART_ANIMATION_ENABLED)
            mChartInfoFieldsDefaultTextColor = get(ATTRIBUTE_KEY_CHART_INFO_FIELDS_DEFAULT_TEXT_COLOR, DEFAULT_CHART_INFO_FIELDS_TEXT_COLOR)
            mChartAxisGridColor = get(ATTRIBUTE_KEY_CHART_AXIS_GRID_COLOR, DEFAULT_CHART_AXIS_GRID_COLOR)
            mChartHighlighterColor = get(ATTRIBUTE_KEY_CHART_HIGHLIGHTER_COLOR, DEFAULT_CHART_HIGHLIGHTER_COLOR)
            mChartPositiveColor = get(ATTRIBUTE_KEY_CHART_POSITIVE_COLOR, DEFAULT_CHART_POSITIVE_COLOR)
            mChartNegativeColor = get(ATTRIBUTE_KEY_CHART_NEGATIVE_COLOR, DEFAULT_CHART_NEGATIVE_COLOR)
            mChartNeutralColor = get(ATTRIBUTE_KEY_CHART_NEUTRAL_COLOR, DEFAULT_CHART_NEUTRAL_COLOR)
            mTabTextColor = get(ATTRIBUTE_KEY_TAB_TEXT_COLOR, DEFAULT_TAB_TEXT_COLOR)
            mTabIndicatorColor = get(ATTRIBUTE_KEY_TAB_INDICATOR_COLOR, DEFAULT_TAB_INDICATOR_COLOR)
            mTabTextSize = get(ATTRIBUTE_KEY_TAB_TEXT_SIZE, spToPx(DEFAULT_TAB_TEXT_SIZE_IN_SP))
        }
    }


    override fun applyAttributes() {
        super.applyAttributes()

        setChartAnimationEnabled(mIsChartAnimationEnabled)
        setChartInfoFieldsDefaultTextColor(mChartInfoFieldsDefaultTextColor)
        setChartAxisGridColor(mChartAxisGridColor)
        setChartHighlighterColor(mChartHighlighterColor)
        setChartPositiveColor(mChartPositiveColor)
        setChartNegativeColor(mChartNegativeColor)
        setChartNeutralColor(mChartNeutralColor)
        setTabTextColor(mTabTextColor)
        setTabIndicatorColor(mTabIndicatorColor)
        setTabTextSize(mTabTextSize)
    }


    /**
     * Updates the informational fields' colors of the chart.
     *
     * @param colors The new colors
     */
    protected fun updateChartInfoFieldsColors(vararg colors: Int) {
        if(colors.size != getChartInfoFieldsArray().size) {
            return
        }

        getChartInfoFieldsArray().forEachIndexed { index, simpleMapView ->
            simpleMapView.setTextColor(colors[index])
        }
    }


    /**
     * Updates the informational fields' titles of the chart.
     *
     * @param titles The new titles
     */
    protected fun updateChartInfoFieldsTitles(vararg titles: String) {
        if(titles.size != getChartInfoFieldsArray().size) {
            return
        }

        getChartInfoFieldsArray().forEachIndexed { index, simpleMapView ->
            simpleMapView.setTitleText(titles[index])
        }
    }


    /**
     * Updates the informational fields' values of the chart.
     *
     * @param values The new values
     */
    protected fun updateChartInfoFieldsValues(vararg values: String) {
        if(values.size != getChartInfoFieldsArray().size) {
            return
        }

        getChartInfoFieldsArray().forEachIndexed { index, simpleMapView ->
            simpleMapView.setValueText(values[index])
        }
    }


    /**
     * Updates the informational fields of the chart.
     *
     * @param titles The new titles
     * @param values The new values
     */
    protected fun updateChartInfoFields(titles: Array<String>, values: Array<String>) {
        if((titles.size != getChartInfoFieldsArray().size) ||
            (values.size != getChartInfoFieldsArray().size)) {
            return
        }

        getChartInfoFieldsArray().forEachIndexed { index, simpleMapView ->
            with(simpleMapView) {
                setTitleText(titles[index])
                setValueText(values[index])
            }
        }
    }


    /**
     * Constructs an instance of [Highlight] model class to be
     * used inside charts.
     *
     * @param x The x value of the highlight
     * @param dataSetIndex The index of the data set this highlight
     * relates to
     *
     * @return The instance of [Highlight] class
     */
    protected open fun createHighlight(x: Float, dataSetIndex: Int): Highlight {
        return Highlight(x, dataSetIndex, -1)
    }


    /**
     * Clears (resets) a chart highlight.
     */
    protected fun clearChartHighlight() {
        mChartHighlight = null

        resetChartInfoFieldsValues()
        resetChartInfoFieldsTextColor()
    }


    private fun setSelectedTabPosition(selectedTabPosition: Int, isTabSelectedProgrammatically: Boolean) {
        mIsTabSelectedProgrammatically = isTabSelectedProgrammatically

        getTabLayout().getTabAt(selectedTabPosition)?.select()

        mIsTabSelectedProgrammatically = false
    }


    private fun resetChartInfoFieldsValues() {
        getChartInfoFieldsArray().forEach {
            it.setValueText(DEFAULT_CHART_INFO_FIELD_VALUE)
        }
    }


    private fun resetChartInfoFieldsTextColor() {
        setChartInfoFieldsTextColor(mChartInfoFieldsDefaultTextColor)
    }


    override fun showMainView() {
        getMainView().makeVisible()

        if(mIsChartAnimationEnabled) {
            animateChart(CHART_ANIMATION_DURATION, Easing.Linear)

            postDelayed(
                { onMainViewShowingListener?.onMainViewShowingEnded() },
                CHART_ANIMATION_DURATION.toLong()
            )

            onMainViewShowingListener?.onMainViewShowingStarted()
        } else {
            onMainViewShowingListener?.onMainViewShowingEnded()
        }
    }


    /**
     * Should animate the chart.
     *
     * @param duration The duration of the animation
     * @param easingFunction The easing function for the animation, i.e. interpolator
     */
    protected abstract fun animateChart(duration: Int, easingFunction: Easing.EasingFunction)


    override fun hideMainView() {
        getMainView().makeInvisible()
    }


    /**
     * Sets whether the chart animation is enabled or disabled.
     *
     * @param isChartAnimationEnabled Whether the chart animation is enabled
     * or disabled
     */
    fun setChartAnimationEnabled(isChartAnimationEnabled: Boolean) {
        mIsChartAnimationEnabled = isChartAnimationEnabled
    }


    /**
     * Sets a default text color of the informational fields of the chart.
     *
     * @param color The color to set
     */
    fun setChartInfoFieldsDefaultTextColor(@ColorInt color: Int) {
        mChartInfoFieldsDefaultTextColor = color

        setChartInfoFieldsTextColor(color)
    }


    /**
     * Sets a text color of the informational fields of the chart.
     *
     * @param color The color to set
     */
    fun setChartInfoFieldsTextColor(@ColorInt color: Int) {
        getChartInfoFieldsArray().forEach {
            it.setTextColor(color)
        }
    }


    /**
     * Sets a grid color of the axis of the chart.
     */
    fun setChartAxisGridColor(@ColorInt color: Int) {
        mChartAxisGridColor = color

        with(getChart()) {
            xAxis.gridColor = color
            axisLeft.gridColor = color
            axisRight.gridColor = color
        }
    }


    /**
     * Sets a highlighter color of the chart.
     *
     * @param color The color to set
     */
    @CallSuper
    open fun setChartHighlighterColor(@ColorInt color: Int) {
        mChartHighlighterColor = color
    }


    /**
     * Sets a positive color of the chart.
     *
     * @param color The color to set
     */
    @CallSuper
    open fun setChartPositiveColor(@ColorInt color: Int) {
        mChartPositiveColor = color
    }


    /**
     * Sets a negative color of the chart.
     *
     * @param color The color to set
     */
    @CallSuper
    open fun setChartNegativeColor(@ColorInt color: Int) {
        mChartNegativeColor = color
    }


    /**
     * Sets a neutral color of the chart.
     *
     * @param color The color to set
     */
    @CallSuper
    open fun setChartNeutralColor(@ColorInt color: Int) {
        mChartNeutralColor = color
    }


    /**
     * Sets a text color of the tabs.
     *
     * @param color The color to set
     */
    fun setTabTextColor(@ColorInt color: Int) {
        if(mTabAnimator == null) {
            return
        }

        mTabTextColor = color

        with(mTabAnimator!!) {
            for(tabIndex in getTabLayoutTabIndices()) {
                getTabAt(tabIndex)?.setTitleColor(color)
            }
        }
    }


    /**
     * Sets a color of the tab indicator.
     *
     * @param color The color to set
     */
    fun setTabIndicatorColor(@ColorInt color: Int) {
        mTabIndicatorColor = color

        getTabLayout().setSelectedTabIndicatorColor(color)
    }


    /**
     * Sets a text size of the tabs.
     *
     * @param textSize The text size to set
     * @param unit The text size's unit. Default is SP.
     */
    fun setTabTextSize(textSize: Float, unit: Int = TypedValue.COMPLEX_UNIT_PX) {
        if(mTabAnimator == null) {
            return
        }

        mTabTextSize = textSize

        with(mTabAnimator!!) {
            for(tabIndex in getTabLayoutTabIndices()) {
                getTabAt(tabIndex)?.setTitleTextSize(textSize, unit)
            }
        }
    }


    private fun setChartHighlight(chartHighlight: ChartHighlight?) {
        mChartHighlight = chartHighlight
    }


    override fun clearData() {
        super.clearData()

        getChart().clear()
        clearChartHighlight()
    }


    /**
     * Should return whether to initialize tabs or not.
     *
     * @return true if needed; false otherwise
     */
    protected open fun shouldInitTabs(): Boolean {
        return true
    }


    override fun isDataAlreadyBound(): Boolean {
        return getChart().run { data != null && data.entryCount > 0 }
    }


    override fun getDefaultInfoViewIconResourceId(): Int {
        return R.drawable.ic_finance
    }


    /**
     * Should return a reference to the chart.
     */
    protected abstract fun getChart(): BarLineChartBase<*>


    /**
     * Should return a reference to the tab layout.
     */
    protected abstract fun getTabLayout(): TabLayout


    /**
     * Should return a list of tab layout indices.
     */
    protected abstract fun getTabLayoutTabIndices(): List<Int>


    /**
     * Should return an array of the informational fields of the chart.
     *
     * @return The array containing informational fields of the chart
     */
    protected abstract fun getChartInfoFieldsArray(): Array<SimpleMapView>


    /**
     * Callback to invoke whenever a chart value has been selected.
     *
     * @param entry The value
     * @param highlight The highlight
     */
    @CallSuper
    protected open fun onValueSelected(entry: Entry, highlight: Highlight) {
        mChartHighlight = ChartHighlight(
            highlight.x,
            highlight.dataSetIndex
        )
    }


    /**
     * Callback to invoke whenever nothing has been selected on the chart.
     */
    protected open fun onNothingSelected() {
        clearChartHighlight()
    }


    /**
     * Callback to invoke whenever the tab has been selected.
     *
     * @param tab The selected tab
     */
    @CallSuper
    protected open fun onTabSelected(tab: TabLayout.Tab) {
        mSelectedTabPosition = tab.position
    }


    private val mOnChartValueSelectedListener = object : OnChartValueSelectedListener {

        override fun onValueSelected(entry: Entry, highlight: Highlight) {
            this@BaseMarketPreviewChartView.onValueSelected(entry, highlight)
        }

        override fun onNothingSelected() {
            this@BaseMarketPreviewChartView.onNothingSelected()
        }

    }


    private val mOnChartTouchListener = OnTouchListener { _, event ->
        if(!isDataEmpty()) {
            when(event.action) {
                android.view.MotionEvent.ACTION_DOWN -> {
                    onChartTouchListener?.onChartPressed()
                }

                android.view.MotionEvent.ACTION_UP -> {
                    onChartTouchListener?.onChartReleased()
                }
            }
        }

        false
    }


    private val mOnTabSelectionListener = object : OnTabSelectedListenerAdapter {

        override fun onTabSelected(tab: TabLayout.Tab) {
            this@BaseMarketPreviewChartView.onTabSelected(tab)
        }

    }


    /**
     * A listener to invoke to get notified when has been pressed and released.
     */
    interface OnChartTouchListener {

        fun onChartPressed() {
            // Stub
        }

        fun onChartReleased() {
            // Stub
        }

    }


    override fun onRestoreInstanceState(state: Parcelable?) {
        super.onRestoreInstanceState(state)

        if(state is BaseMarketPreviewChartBasedState<*>) {
            setChartAnimationEnabled(state.isChartAnimationEnabled)
            setChartInfoFieldsDefaultTextColor(state.chartInfoFieldsDefaultTextColor)
            setChartAxisGridColor(state.chartAxisGridColor)
            setChartHighlighterColor(state.chartHighlighterColor)
            setChartPositiveColor(state.chartPositiveColor)
            setChartNegativeColor(state.chartNegativeColor)
            setChartNeutralColor(state.chartNeutralColor)
            setTabTextColor(state.tabTextColor)
            setTabIndicatorColor(state.tabIndicatorColor)
            setSelectedTabPosition(state.selectedTabPosition, true)
            setTabTextSize(state.tabTextSize)
            setChartHighlight(state.chartHighlight)
        }
    }


    override fun onSaveInstanceState(savedState: BaseMarketPreviewSavedState<Params>) {
        super.onSaveInstanceState(savedState)

        if(savedState is BaseMarketPreviewChartBasedState) {
            with(savedState) {
                isChartAnimationEnabled = mIsChartAnimationEnabled
                chartInfoFieldsDefaultTextColor = mChartInfoFieldsDefaultTextColor
                chartAxisGridColor = mChartAxisGridColor
                chartHighlighterColor = mChartHighlighterColor
                chartPositiveColor = mChartPositiveColor
                chartNegativeColor = mChartNegativeColor
                chartNeutralColor = mChartNeutralColor
                tabTextColor = mTabTextColor
                tabIndicatorColor = mTabIndicatorColor
                selectedTabPosition = mSelectedTabPosition
                tabTextSize = mTabTextSize
                chartHighlight = mChartHighlight
            }
        }
    }


    abstract class BaseMarketPreviewChartBasedState<Params: Parcelable> : BaseMarketPreviewSavedState<Params> {

        companion object {

            private const val KEY_IS_CHART_ANIMATION_ENABLED = "is_chart_animation_enabled"
            private const val KEY_CHART_INFO_FIELDS_DEFAULT_TEXT_COLOR = "chart_info_fields_default_text_color"
            private const val KEY_CHART_AXIS_GRID_COLOR = "chart_axis_grid_color"
            private const val KEY_CHART_HIGHLIGHTER_COLOR = "chart_highlighter_color"
            private const val KEY_CHART_POSITIVE_COLOR = "chart_positive_color"
            private const val KEY_CHART_NEGATIVE_COLOR = "chart_negative_color"
            private const val KEY_CHART_NEUTRAL_COLOR = "chart_neutral_color"
            private const val KEY_TAB_TEXT_COLOR = "tab_text_color"
            private const val KEY_TAB_INDICATOR_COLOR = "tab_indicator_color"
            private const val KEY_SELECTED_TAB_POSITION = "selected_tab_position"
            private const val KEY_TAB_TEXT_SIZE = "tab_text_size"
            private const val KEY_CHART_HIGHLIGHT = "chart_highlight"

        }


        internal var isChartAnimationEnabled: Boolean = true

        internal var chartInfoFieldsDefaultTextColor: Int = 0
        internal var chartAxisGridColor: Int = 0
        internal var chartHighlighterColor: Int = 0
        internal var chartPositiveColor: Int = 0
        internal var chartNegativeColor: Int = 0
        internal var chartNeutralColor: Int = 0
        internal var tabTextColor: Int = 0
        internal var tabIndicatorColor: Int = 0

        internal var selectedTabPosition: Int = 0

        internal var tabTextSize: Float = 0f

        internal var chartHighlight: ChartHighlight? = null


        constructor(parcel: Parcel): super(parcel) {
            parcel.readBundle(CLASS_LOADER)?.run {
                isChartAnimationEnabled = getBoolean(KEY_IS_CHART_ANIMATION_ENABLED, DEFAULT_IS_CHART_ANIMATION_ENABLED)
                chartInfoFieldsDefaultTextColor = getInt(KEY_CHART_INFO_FIELDS_DEFAULT_TEXT_COLOR, DEFAULT_CHART_INFO_FIELDS_TEXT_COLOR)
                chartAxisGridColor = getInt(KEY_CHART_AXIS_GRID_COLOR, DEFAULT_CHART_AXIS_GRID_COLOR)
                chartHighlighterColor = getInt(KEY_CHART_HIGHLIGHTER_COLOR, DEFAULT_CHART_HIGHLIGHTER_COLOR)
                chartPositiveColor = getInt(KEY_CHART_POSITIVE_COLOR, DEFAULT_CHART_POSITIVE_COLOR)
                chartNegativeColor = getInt(KEY_CHART_NEGATIVE_COLOR, DEFAULT_CHART_NEGATIVE_COLOR)
                chartNeutralColor = getInt(KEY_CHART_NEUTRAL_COLOR, DEFAULT_CHART_NEUTRAL_COLOR)
                tabTextColor = getInt(KEY_TAB_TEXT_COLOR, DEFAULT_TAB_TEXT_COLOR)
                tabIndicatorColor = getInt(KEY_TAB_INDICATOR_COLOR, 0)
                selectedTabPosition = getInt(KEY_SELECTED_TAB_POSITION, 0)
                tabTextSize = getFloat(KEY_TAB_TEXT_SIZE, 0f)
                chartHighlight = getParcelable(KEY_CHART_HIGHLIGHT)
            }
        }


        constructor(superState: Parcelable?): super(superState)


        override fun writeToParcel(parcel: Parcel, flags: Int) {
            super.writeToParcel(parcel, flags)

            val bundle = Bundle(CLASS_LOADER).apply {
                putBoolean(KEY_IS_CHART_ANIMATION_ENABLED, isChartAnimationEnabled)
                putInt(KEY_CHART_INFO_FIELDS_DEFAULT_TEXT_COLOR, chartInfoFieldsDefaultTextColor)
                putInt(KEY_CHART_AXIS_GRID_COLOR, chartAxisGridColor)
                putInt(KEY_CHART_HIGHLIGHTER_COLOR, chartHighlighterColor)
                putInt(KEY_CHART_POSITIVE_COLOR, chartPositiveColor)
                putInt(KEY_CHART_NEGATIVE_COLOR, chartNegativeColor)
                putInt(KEY_CHART_NEUTRAL_COLOR, chartNeutralColor)
                putInt(KEY_TAB_TEXT_COLOR, tabTextColor)
                putInt(KEY_TAB_INDICATOR_COLOR, tabIndicatorColor)
                putInt(KEY_SELECTED_TAB_POSITION, selectedTabPosition)
                putFloat(KEY_TAB_TEXT_SIZE, tabTextSize)
                putParcelable(KEY_CHART_HIGHLIGHT, chartHighlight)
            }

            parcel.writeBundle(bundle)
        }

    }


}