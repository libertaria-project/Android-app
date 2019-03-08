package com.stocksexchange.android.ui.views.marketpreview

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar
import androidx.annotation.ColorInt
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarLineChartBase
import com.github.mikephil.charting.charts.CombinedChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.highlight.Highlight
import com.google.android.material.tabs.TabLayout
import com.stocksexchange.android.R
import com.stocksexchange.android.api.model.CandleStick
import com.stocksexchange.android.api.model.PriceChartData
import com.stocksexchange.android.api.model.PriceChartDataIntervals
import com.stocksexchange.android.api.model.PriceChartParameters
import com.stocksexchange.android.model.CandleStickStyles
import com.stocksexchange.android.model.CandleStickTypes
import com.stocksexchange.android.model.ChartHighlight
import com.stocksexchange.android.ui.utils.extensions.getCompatDrawable
import com.stocksexchange.android.ui.utils.extensions.spToPx
import com.stocksexchange.android.ui.views.InfoView
import com.stocksexchange.android.ui.views.SimpleMapView
import com.stocksexchange.android.ui.views.marketpreview.base.BaseMarketPreviewChartView
import com.stocksexchange.android.utils.formatters.TimeFormatter
import kotlinx.android.synthetic.main.price_chart_view_layout.view.*

/**
 * A view representing a candle stick price chart as well as data interval
 * controls support.
 */
class PriceChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseMarketPreviewChartView<PriceChartData, CandleStick, PriceChartParameters>(context, attrs, defStyleAttr) {


    companion object {

        internal const val ATTRIBUTE_KEY_IS_CHART_ZOOM_IN_ENABLED = "is_chart_zoom_in_enabled"
        internal const val ATTRIBUTE_KEY_CHART_VOLUME_BAR_COLOR = "chart_volume_bar_color"
        internal const val ATTRIBUTE_KEY_CHART_CANDLE_STICK_SHADOW_COLOR = "chart_candle_stick_shadow_color"
        internal const val ATTRIBUTE_KEY_MIN_CANDLE_STICK_COUNT_FOR_ZOOMING_IN = "min_candle_stick_count_for_zooming_in"

        private const val CHART_HIGHLIGHT_DATA_KEY_CS = "candle_stick"

        private const val CHART_HIGHLIGHT_DATA_KEY_CS_PAYLOAD_KEY_CS = "candle_stick"

        private const val DEFAULT_IS_CHART_ZOOM_IN_ENABLED = true

        private const val DEFAULT_CHART_VOLUME_BAR_COLOR = Color.CYAN
        private const val DEFAULT_CHART_CANDLE_STICK_SHADOW_COLOR = Color.WHITE

        private const val DEFAULT_MIN_CANDLE_STICK_COUNT_FOR_ZOOMING_IN = 15

        private const val CHART_TRANSLATION_COMPENSATION = 2f
        private const val CHART_ZOOM_IN_Y_SCALE = 1f

        private const val CHART_SHADOW_WIDTH = 0.5f
        private const val CHART_HORIZONTAL_AXIS_COMPENSATION = 2f
        private const val CHART_VOLUME_BARS_COMPENSATION = 8f

        private const val CHART_CANDLE_DATA_SET_LABEL = "CandleDataSet"
        private const val CHART_VOLUME_DATA_SET_LABEL = "VolumeDataSet"

    }


    private var mIsChartZoomInEnabled: Boolean = true
    private var mWasChartZoomInPerformed: Boolean = false

    private var mChartVolumeBarColor: Int = 0
    private var mChartCandleStickShadowColor: Int = 0

    private var mMinCandleStickCountForZoomingIn: Int = 0

    private var mBullishCandleStickStyle: CandleStickStyles = CandleStickStyles.SOLID
    private var mBearishCandleStickStyle: CandleStickStyles = CandleStickStyles.SOLID

    private val mTimeFormatter: TimeFormatter = TimeFormatter.getInstance(context)

    /**
     * A listener to invoke whenever a price chart data interval has been changed.
     */
    var onChartIntervalChangeListener: ((PriceChartDataIntervals) -> Unit)? = null




    override fun saveAttributes(attrs: AttributeSet?, defStyleAttr: Int) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.PriceChartView, defStyleAttr, 0).run {
            try {
                with(mAttributes) {
                    save(ATTRIBUTE_KEY_IS_CHART_ANIMATION_ENABLED, getBoolean(R.styleable.PriceChartView_isChartAnimationEnabled, DEFAULT_IS_CHART_ANIMATION_ENABLED))
                    save(ATTRIBUTE_KEY_IS_CHART_ZOOM_IN_ENABLED, getBoolean(R.styleable.PriceChartView_isChartZoomInEnabled, DEFAULT_IS_CHART_ZOOM_IN_ENABLED))
                    save(ATTRIBUTE_KEY_PROGRESS_BAR_COLOR, getColor(R.styleable.PriceChartView_progressBarColor, DEFAULT_PROGRESS_BAR_COLOR))
                    save(ATTRIBUTE_KEY_INFO_VIEW_COLOR, getColor(R.styleable.PriceChartView_infoViewColor, DEFAULT_INFO_VIEW_COLOR))
                    save(ATTRIBUTE_KEY_CHART_INFO_FIELDS_DEFAULT_TEXT_COLOR, getColor(R.styleable.PriceChartView_chartInfoFieldsDefaultTextColor, DEFAULT_CHART_INFO_FIELDS_TEXT_COLOR))
                    save(ATTRIBUTE_KEY_CHART_AXIS_GRID_COLOR, getColor(R.styleable.PriceChartView_chartAxisGridColor, DEFAULT_CHART_AXIS_GRID_COLOR))
                    save(ATTRIBUTE_KEY_CHART_HIGHLIGHTER_COLOR, getColor(R.styleable.PriceChartView_chartHighlighterColor, DEFAULT_CHART_HIGHLIGHTER_COLOR))
                    save(ATTRIBUTE_KEY_CHART_POSITIVE_COLOR, getColor(R.styleable.PriceChartView_chartPositiveColor, DEFAULT_CHART_POSITIVE_COLOR))
                    save(ATTRIBUTE_KEY_CHART_NEGATIVE_COLOR, getColor(R.styleable.PriceChartView_chartNegativeColor, DEFAULT_CHART_NEGATIVE_COLOR))
                    save(ATTRIBUTE_KEY_CHART_NEUTRAL_COLOR, getColor(R.styleable.PriceChartView_chartNeutralColor, DEFAULT_CHART_NEUTRAL_COLOR))
                    save(ATTRIBUTE_KEY_CHART_VOLUME_BAR_COLOR, getColor(R.styleable.PriceChartView_chartVolumeBarColor, DEFAULT_CHART_VOLUME_BAR_COLOR))
                    save(ATTRIBUTE_KEY_CHART_CANDLE_STICK_SHADOW_COLOR, getColor(R.styleable.PriceChartView_chartCandleStickShadowColor, DEFAULT_CHART_CANDLE_STICK_SHADOW_COLOR))
                    save(ATTRIBUTE_KEY_TAB_TEXT_COLOR, getColor(R.styleable.PriceChartView_tabTextColor, DEFAULT_TAB_TEXT_COLOR))
                    save(ATTRIBUTE_KEY_TAB_INDICATOR_COLOR, getColor(R.styleable.PriceChartView_tabIndicatorColor, DEFAULT_TAB_INDICATOR_COLOR))
                    save(ATTRIBUTE_KEY_MIN_CANDLE_STICK_COUNT_FOR_ZOOMING_IN, getInteger(R.styleable.PriceChartView_minCandleStickCountForZoomingIn, DEFAULT_MIN_CANDLE_STICK_COUNT_FOR_ZOOMING_IN))
                    save(ATTRIBUTE_KEY_TAB_TEXT_SIZE, getDimension(R.styleable.PriceChartView_tabTextSize, spToPx(DEFAULT_TAB_TEXT_SIZE_IN_SP)))
                    save(ATTRIBUTE_KEY_INFO_VIEW_EMPTY_CAPTION, (getString(R.styleable.PriceChartView_infoViewEmptyCaption) ?: DEFAULT_INFO_VIEW_CAPTION))
                    save(ATTRIBUTE_KEY_INFO_VIEW_ERROR_CAPTION, (getString(R.styleable.PriceChartView_infoViewErrorCaption) ?: DEFAULT_INFO_VIEW_CAPTION))
                    save(ATTRIBUTE_KEY_INFO_VIEW_ICON, (getDrawable(R.styleable.PriceChartView_infoViewIcon) ?: context.getCompatDrawable(getDefaultInfoViewIconResourceId())))
                }
            } finally {
                recycle()
            }
        }
    }


    override fun initChart() {
        super.initChart()

        chart.drawOrder = arrayOf(CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.CANDLE)
    }


    override fun addTabLayoutTabs() {
        with(tabLayout) {
            addTab(newTab(), PriceChartDataIntervals.ONE_DAY.ordinal)
            addTab(newTab(), PriceChartDataIntervals.ONE_WEEK.ordinal)
            addTab(newTab(), PriceChartDataIntervals.ONE_MONTH.ordinal)
            addTab(newTab(), PriceChartDataIntervals.THREE_MONTHS.ordinal)
        }
    }


    override fun initTabAnimatorTabs() {
        with(mTabAnimator!!) {
            getTabAt(PriceChartDataIntervals.ONE_DAY.ordinal)?.setTitleText(resources.getString(R.string.chart_interval_one_day))
            getTabAt(PriceChartDataIntervals.ONE_WEEK.ordinal)?.setTitleText(resources.getString(R.string.chart_interval_one_week))
            getTabAt(PriceChartDataIntervals.ONE_MONTH.ordinal)?.setTitleText(resources.getString(R.string.chart_interval_one_month))
            getTabAt(PriceChartDataIntervals.THREE_MONTHS.ordinal)?.setTitleText(resources.getString(R.string.chart_interval_three_months))
        }
    }


    override fun restoreAttributes() {
        super.restoreAttributes()

        with(mAttributes) {
            mIsChartZoomInEnabled = get(ATTRIBUTE_KEY_IS_CHART_ZOOM_IN_ENABLED, DEFAULT_IS_CHART_ZOOM_IN_ENABLED)
            mChartVolumeBarColor = get(ATTRIBUTE_KEY_CHART_VOLUME_BAR_COLOR, DEFAULT_CHART_VOLUME_BAR_COLOR)
            mChartCandleStickShadowColor = get(ATTRIBUTE_KEY_CHART_CANDLE_STICK_SHADOW_COLOR, DEFAULT_CHART_CANDLE_STICK_SHADOW_COLOR)
            mMinCandleStickCountForZoomingIn = get(ATTRIBUTE_KEY_MIN_CANDLE_STICK_COUNT_FOR_ZOOMING_IN, DEFAULT_MIN_CANDLE_STICK_COUNT_FOR_ZOOMING_IN)
        }
    }


    override fun applyAttributes() {
        super.applyAttributes()

        setChartZoomInEnabled(mIsChartZoomInEnabled)
        setChartVolumeBarColor(mChartVolumeBarColor)
        setChartCandleStickShadowColor(mChartCandleStickShadowColor)
        setMinCandleStickCountForZoomingIn(mMinCandleStickCountForZoomingIn)
    }


    private fun calculateXValueToMoveChartTo(highlightXValue: Float, candleStickCount: Int): Float {
        // Since simply calling moveViewTo(...) with a highlightXValue shifts the chart
        // to the specified x value and cuts half of the candle stick, we need to manually
        // adjust the x value to move the chart to. We do this by checking if the x value
        // of the highlight is not equal to an index of some of the latest candle sticks (because
        // moving the chart to of the latest candle sticks does not have a problem of cutting half of
        // the candle stick) and then subtracting some constant to shift the chart more to the left.
        return if((highlightXValue.toInt() <= (candleStickCount - mMinCandleStickCountForZoomingIn))) {
            (highlightXValue - CHART_TRANSLATION_COMPENSATION)
        } else {
            highlightXValue
        }
    }


    private fun calculateHorizontalZoomInChartScale(candleStickCount: Int): Float {
        // Since the candle stick count can differ greatly based on the interval,
        // we calculate the scale by dividing the total count of candle sticks
        // by the minimal candle stick count necessary for zooming in. By doing this,
        // we can guarantee that the chart will display roughly the same number of
        // candle sticks when zoomed in regardless of the interval.
        return (candleStickCount.toFloat() / mMinCandleStickCountForZoomingIn)
    }


    private fun shouldZoomInTheChart(candleStickCount: Int): Boolean {
        return ((mIsChartZoomInEnabled) &&
                !mWasChartZoomInPerformed &&
                (candleStickCount > mMinCandleStickCountForZoomingIn))
    }


    private fun getHighlight(candleSticks: List<CandleStick>, candleData: CandleData,
                             candleDataSet: CandleDataSet): Highlight {
        val createDefaultHighlight: () -> Highlight = {
            createHighlight(
                candleSticks.lastIndex.toFloat(),
                candleData.getIndexOfDataSet(candleDataSet)
            )
        }

        if(mChartHighlight == null) {
            return createDefaultHighlight()
        }

        val chartHighlight = mChartHighlight!!

        if((chartHighlight.data == null) ||
            chartHighlight.data?.key != CHART_HIGHLIGHT_DATA_KEY_CS) {
            return createDefaultHighlight()
        }

        val chartHighlightData = chartHighlight.data!!
        val candleStick = chartHighlightData.bundle?.run {
            // Adding a class loader to prevent ClassNotFoundException
            // when unmarshalling data from the parcel
            if(classLoader == null) {
                classLoader = CLASS_LOADER
            }

            getParcelable(CHART_HIGHLIGHT_DATA_KEY_CS_PAYLOAD_KEY_CS) as? CandleStick
        } ?: return createDefaultHighlight()
        val candleStickIndex = chartHighlight.x.toInt()

        if(candleSticks.getOrNull(candleStickIndex)?.date != candleStick.date) {
            for(index in candleSticks.indices) {
                if(candleSticks[index].date != candleStick.date) {
                    continue
                }

                return createHighlight(
                    index.toFloat(),
                    candleData.getIndexOfDataSet(candleDataSet)
                )
            }

            return createDefaultHighlight()
        }

        return mChartHighlight!!.toHighlight()
    }


    private fun getPaintStyleForType(type: CandleStickTypes): Paint.Style {
        val candleStickStyle = when(type) {
            CandleStickTypes.BULLISH -> mBullishCandleStickStyle
            CandleStickTypes.BEARISH -> mBearishCandleStickStyle
        }

        return when(candleStickStyle) {
            CandleStickStyles.SOLID -> Paint.Style.FILL
            CandleStickStyles.HOLLOW -> Paint.Style.STROKE
        }
    }


    private fun getPriceChartIntervalForTabPosition(tabPosition: Int): PriceChartDataIntervals {
        return when {
            (tabPosition == PriceChartDataIntervals.ONE_DAY.ordinal) -> PriceChartDataIntervals.ONE_DAY
            (tabPosition == PriceChartDataIntervals.ONE_WEEK.ordinal) -> PriceChartDataIntervals.ONE_WEEK
            (tabPosition == PriceChartDataIntervals.ONE_MONTH.ordinal) -> PriceChartDataIntervals.ONE_MONTH
            (tabPosition == PriceChartDataIntervals.THREE_MONTHS.ordinal) -> PriceChartDataIntervals.THREE_MONTHS

            else -> throw IllegalStateException("Please provide a price chart data interval for a tabPosition: $tabPosition")
        }
    }
    

    private fun enumerateCandleDataSets(callback: (CandleDataSet) -> Unit) {
        if(isDataEmpty()) {
            return
        }

        chart.data?.candleData?.dataSets?.forEach {
            if(it is CandleDataSet) {
                callback(it)
            }
        }
    }


    private fun enumerateBarDataSets(callback: (BarDataSet) -> Unit) {
        if(isDataEmpty()) {
            return
        }

        chart.data?.barData?.dataSets?.forEach {
            if(it is BarDataSet) {
                callback(it)
            }
        }
    }


    private fun createCandleDataSet(candleEntries: MutableList<CandleEntry>): CandleDataSet {
        return CandleDataSet(candleEntries, CHART_CANDLE_DATA_SET_LABEL).apply {
            setDrawIcons(false)
            setDrawValues(false)

            axisDependency = YAxis.AxisDependency.LEFT

            shadowWidth = CHART_SHADOW_WIDTH

            decreasingPaintStyle = getPaintStyleForType(CandleStickTypes.BEARISH)
            decreasingColor = mChartNegativeColor

            increasingPaintStyle = getPaintStyleForType(CandleStickTypes.BULLISH)
            increasingColor = mChartPositiveColor

            isHighlightEnabled = true
            highlightLineWidth = CHART_HIGHLIGHTER_WIDTH

            neutralColor = mChartNeutralColor
            shadowColor = mChartCandleStickShadowColor
            highLightColor = mChartHighlighterColor
        }
    }


    private fun createVolumeDataSet(volumeEntries: MutableList<BarEntry>): BarDataSet {
        return BarDataSet(volumeEntries, CHART_VOLUME_DATA_SET_LABEL).apply {
            setDrawIcons(false)
            setDrawValues(false)

            axisDependency = YAxis.AxisDependency.RIGHT
            isHighlightEnabled = false

            color = mChartVolumeBarColor
        }
    }


    override fun bindData() {
        if(isDataEmpty()) {
            return
        }

        val data = mData!!
        val candleStickCount = data.candleSticks.size

        val candleEntries: MutableList<CandleEntry> = mutableListOf()
        val volumeEntries: MutableList<BarEntry> = mutableListOf()
        var adjustedIndex: Float
        var candleStick: CandleStick

        for(i in 0 until candleStickCount) {
            adjustedIndex = i.toFloat()
            candleStick = data.candleSticks[i]

            candleEntries.add(CandleEntry(
                adjustedIndex,
                candleStick.highPrice.toFloat(),
                candleStick.lowPrice.toFloat(),
                candleStick.openPrice.toFloat(),
                candleStick.closePrice.toFloat()
            ))

            volumeEntries.add(BarEntry(adjustedIndex, candleStick.volume.toFloat()))
        }

        with(chart) {
            val candleDataSet = createCandleDataSet(candleEntries)
            val volumeDataSet = createVolumeDataSet(volumeEntries)

            val candleData = CandleData(candleDataSet)
            val volumeData = BarData(volumeDataSet)

            val combinedData = CombinedData().apply {
                setData(candleData)
                setData(volumeData)
            }

            // Setting the data of the chart
            this.data = combinedData

            with(axisRight) {
                // Setting the minimum and maximum value of the axis
                // to "separate" bars from candle sticks
                axisMinimum = 0f
                axisMaximum = (volumeData.yMax * CHART_VOLUME_BARS_COMPENSATION)
            }

            with(xAxis) {
                axisMinimum = (candleData.xMin - CHART_HORIZONTAL_AXIS_COMPENSATION)
                axisMaximum = (candleData.xMax + CHART_HORIZONTAL_AXIS_COMPENSATION)
            }

            // Highlighting the last candle stick
            val highlight = getHighlight(data.candleSticks, candleData, candleDataSet).apply {
                dataIndex = combinedData.getDataIndex(candleData)
            }

            highlightValue(highlight, true)

            // Locking the view to the x value of the highlight
            moveViewToX(calculateXValueToMoveChartTo(highlight.x, candleStickCount))

            // Zooming if necessary
            if(shouldZoomInTheChart(candleStickCount)) {
                mWasChartZoomInPerformed = true

                val candleEntry = candleEntries[highlight.x.toInt()]

                // Resetting the zoom
                fitScreen()

                // Actual zooming is performed here
                zoom(
                    calculateHorizontalZoomInChartScale(candleStickCount),
                    CHART_ZOOM_IN_Y_SCALE,
                    candleEntry.x,
                    candleEntry.y
                )
            }
        }
    }


    override fun clearData() {
        super.clearData()

        mWasChartZoomInPerformed = false
    }


    override fun animateChart(duration: Int, easingFunction: Easing.EasingFunction) {
        chart.animateX(duration, easingFunction)
    }


    override fun setChartHighlighterColor(@ColorInt color: Int) {
        super.setChartHighlighterColor(color)

        enumerateCandleDataSets {
            it.highLightColor = color
        }
    }


    override fun setChartPositiveColor(color: Int) {
        super.setChartPositiveColor(color)

        enumerateCandleDataSets {
            it.increasingColor = color
        }
    }


    override fun setChartNegativeColor(color: Int) {
        super.setChartNegativeColor(color)

        enumerateCandleDataSets {
            it.decreasingColor = color
        }
    }


    override fun setChartNeutralColor(@ColorInt color: Int) {
        super.setChartNeutralColor(color)

        enumerateCandleDataSets {
            it.neutralColor = color
        }
    }


    /**
     * Sets whether to zoom in the candle sticks or show them all.
     *
     * @param isChartZoomInEnabled Whether to zoom in the candle sticks or
     * show them all on the chart
     */
    fun setChartZoomInEnabled(isChartZoomInEnabled: Boolean) {
        mIsChartZoomInEnabled = isChartZoomInEnabled
    }


    /**
     * Sets a volume bar color of the chart.
     *
     * @param color The color to set
     */
    fun setChartVolumeBarColor(@ColorInt color: Int) {
        mChartVolumeBarColor = color

        enumerateBarDataSets {
            it.color = color
        }
    }


    /**
     * Sets a candle stick shadow color of the chart.
     *
     * @param color The color to set
     */
    fun setChartCandleStickShadowColor(@ColorInt color: Int) {
        mChartCandleStickShadowColor = color

        enumerateCandleDataSets {
            it.shadowColor = color
        }
    }


    /**
     * Sets a minimum candle stick count in order for a chart to be zoomed in.
     *
     * @param count The minimum candle stick count
     */
    fun setMinCandleStickCountForZoomingIn(count: Int) {
        mMinCandleStickCountForZoomingIn = count
    }


    /**
     * Sets a style for a bullish candle stick.
     *
     * @param style The style to set
     */
    fun setBullishCandleStickStyle(style: CandleStickStyles) {
        mBullishCandleStickStyle = style

        enumerateCandleDataSets {
            it.increasingPaintStyle = getPaintStyleForType(CandleStickTypes.BULLISH)
        }
    }


    /**
     * Sets a style for bearish candle stick.
     *
     * @param style The style to set
     */
    fun setBearishCandleStickStyle(style: CandleStickStyles) {
        mBearishCandleStickStyle = style

        enumerateCandleDataSets {
            it.decreasingPaintStyle = getPaintStyleForType(CandleStickTypes.BEARISH)
        }
    }


    /**
     * Sets a market name of the price data to load.
     *
     * @param marketName The market name to set
     */
    fun setMarketName(marketName: String) {
        mDataParameters = mDataParameters.copy(marketName = marketName)
    }


    override fun isDataEmpty(): Boolean {
        return (mData?.candleSticks?.isEmpty() ?: true)
    }


    override fun getLayoutResourceId(): Int = R.layout.price_chart_view_layout


    override fun getDefaultParameters(): PriceChartParameters {
        return PriceChartParameters.getDefaultParameters()
    }


    override fun getProgressBar(): ProgressBar = progressBar


    override fun getInfoView(): InfoView = infoView


    override fun getMainView(): View = chartContainerLl


    override fun getChart(): BarLineChartBase<*> = chart


    override fun getTabLayout(): TabLayout = tabLayout


    override fun getTabLayoutTabIndices(): List<Int> {
        return listOf(
            PriceChartDataIntervals.ONE_DAY.ordinal,
            PriceChartDataIntervals.ONE_WEEK.ordinal,
            PriceChartDataIntervals.ONE_MONTH.ordinal,
            PriceChartDataIntervals.THREE_MONTHS.ordinal
        )
    }


    override fun getChartInfoFieldsArray(): Array<SimpleMapView> {
        return arrayOf(
            openSmv, highSmv,
            lowSmv, closeSmv,
            volumeSmv, timeSmv
        )
    }


    override fun onValueSelected(entry: Entry, highlight: Highlight) {
        super.onValueSelected(entry, highlight)

        if(entry !is CandleEntry) {
            return
        }

        val candleStick = mData!!.candleSticks[entry.x.toInt()]

        mChartHighlight?.data = ChartHighlight.ChartHighlightData(
            key = CHART_HIGHLIGHT_DATA_KEY_CS,
            bundle = Bundle(CLASS_LOADER).apply {
                putParcelable(CHART_HIGHLIGHT_DATA_KEY_CS_PAYLOAD_KEY_CS, candleStick)
            }
        )

        setChartInfoFieldsTextColor(when {
            (candleStick.openPrice < candleStick.closePrice) -> mChartPositiveColor
            (candleStick.openPrice > candleStick.closePrice) -> mChartNegativeColor

            else -> mChartNeutralColor
        })

        updateChartInfoFieldsValues(
            mDoubleFormatter.formatFixedPrice(candleStick.openPrice),
            mDoubleFormatter.formatFixedPrice(candleStick.highPrice),
            mDoubleFormatter.formatFixedPrice(candleStick.lowPrice),
            mDoubleFormatter.formatFixedPrice(candleStick.closePrice),
            mDoubleFormatter.formatAmount(candleStick.volume),
            mTimeFormatter.formatCandleStickDate(candleStick.date)
        )
    }


    override fun onTabSelected(tab: TabLayout.Tab) {
        super.onTabSelected(tab)

        mDataParameters = mDataParameters.copy(
            interval = getPriceChartIntervalForTabPosition(tab.position)
        )

        if(!mIsTabSelectedProgrammatically) {
            onChartIntervalChangeListener?.invoke(mDataParameters.interval)
        }
    }


    override fun onRestoreInstanceState(state: Parcelable?) {
        super.onRestoreInstanceState(state)

        if(state is SavedState) {
            setChartZoomInEnabled(state.isChartZoomInEnabled)
            setChartVolumeBarColor(state.chartVolumeBarColor)
            setChartCandleStickShadowColor(state.chartCandleStickShadowColor)
            setMinCandleStickCountForZoomingIn(state.minCandleStickCountForZoomingIn)
            setBullishCandleStickStyle(state.bullishCandleStickStyle)
            setBearishCandleStickStyle(state.bearishCandleStickStyle)
            setData(state.data, true)
        }
    }


    override fun onSaveInstanceState(): Parcelable? {
        return SavedState(super.onSaveInstanceState()).apply {
            onSaveInstanceState(this)
        }
    }


    override fun onSaveInstanceState(savedState: BaseMarketPreviewSavedState<PriceChartParameters>) {
        super.onSaveInstanceState(savedState)

        if(savedState is SavedState) {
            with(savedState) {
                isChartZoomInEnabled = mIsChartZoomInEnabled
                chartVolumeBarColor = mChartVolumeBarColor
                chartCandleStickShadowColor = mChartCandleStickShadowColor
                minCandleStickCountForZoomingIn = mMinCandleStickCountForZoomingIn
                bullishCandleStickStyle = mBullishCandleStickStyle
                bearishCandleStickStyle = mBearishCandleStickStyle
                data = mData
            }
        }
    }


    private class SavedState : BaseMarketPreviewChartBasedState<PriceChartParameters> {

        companion object {

            private const val KEY_IS_CHART_ZOOM_IN_ENABLED = "is_chart_zoom_in_enabled"
            private const val KEY_CHART_VOLUME_BAR_COLOR = "chart_volume_bar_color"
            private const val KEY_CHART_CANDLE_STICK_SHADOW_COLOR = "chart_candle_stick_shadow_color"
            private const val KEY_MIN_CANDLE_STICK_COUNT_FOR_ZOOMING_IN = "min_candle_stick_count_for_zooming_in"
            private const val KEY_BULLISH_CANDLE_STICK_STYLE = "bullish_candle_stick_style"
            private const val KEY_BEARISH_CANDLE_STICK_STYLE = "bearish_candle_stick_style"
            private const val KEY_DATA = "data"


            @JvmField
            val CREATOR = object : Parcelable.Creator<SavedState> {

                override fun createFromParcel(parcel: Parcel): SavedState = SavedState(parcel)

                override fun newArray(size: Int): Array<SavedState?> = arrayOfNulls(size)

            }

        }


        internal var isChartZoomInEnabled: Boolean = true

        internal var chartVolumeBarColor: Int = 0
        internal var chartCandleStickShadowColor: Int = 0

        internal var minCandleStickCountForZoomingIn: Int = 0

        internal var bullishCandleStickStyle: CandleStickStyles = CandleStickStyles.SOLID
        internal var bearishCandleStickStyle: CandleStickStyles = CandleStickStyles.SOLID

        internal var data: PriceChartData? = null


        constructor(parcel: Parcel): super(parcel) {
            parcel.readBundle(CLASS_LOADER)?.run {
                isChartZoomInEnabled = getBoolean(KEY_IS_CHART_ZOOM_IN_ENABLED, DEFAULT_IS_CHART_ZOOM_IN_ENABLED)
                chartVolumeBarColor = getInt(KEY_CHART_VOLUME_BAR_COLOR, DEFAULT_CHART_VOLUME_BAR_COLOR)
                chartCandleStickShadowColor = getInt(KEY_CHART_CANDLE_STICK_SHADOW_COLOR, DEFAULT_CHART_CANDLE_STICK_SHADOW_COLOR)
                minCandleStickCountForZoomingIn = getInt(KEY_MIN_CANDLE_STICK_COUNT_FOR_ZOOMING_IN, DEFAULT_MIN_CANDLE_STICK_COUNT_FOR_ZOOMING_IN)
                bullishCandleStickStyle = (getSerializable(KEY_BULLISH_CANDLE_STICK_STYLE) as CandleStickStyles)
                bearishCandleStickStyle = (getSerializable(KEY_BEARISH_CANDLE_STICK_STYLE) as CandleStickStyles)
                data = getParcelable(KEY_DATA)
            }
        }


        constructor(superState: Parcelable?): super(superState)


        override fun writeToParcel(parcel: Parcel, flags: Int) {
            super.writeToParcel(parcel, flags)

            val bundle = Bundle(CLASS_LOADER).apply {
                putBoolean(KEY_IS_CHART_ZOOM_IN_ENABLED, isChartZoomInEnabled)
                putInt(KEY_CHART_VOLUME_BAR_COLOR, chartVolumeBarColor)
                putInt(KEY_CHART_CANDLE_STICK_SHADOW_COLOR, chartCandleStickShadowColor)
                putInt(KEY_MIN_CANDLE_STICK_COUNT_FOR_ZOOMING_IN, minCandleStickCountForZoomingIn)
                putSerializable(KEY_BULLISH_CANDLE_STICK_STYLE, bullishCandleStickStyle)
                putSerializable(KEY_BEARISH_CANDLE_STICK_STYLE, bearishCandleStickStyle)
                putParcelable(KEY_DATA, data)
            }

            parcel.writeBundle(bundle)
        }

    }


}