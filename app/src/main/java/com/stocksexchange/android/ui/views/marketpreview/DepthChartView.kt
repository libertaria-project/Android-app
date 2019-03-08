package com.stocksexchange.android.ui.views.marketpreview

import android.content.Context
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarLineChartBase
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.ChartHighlighter
import com.github.mikephil.charting.highlight.Highlight
import com.google.android.material.tabs.TabLayout
import com.stocksexchange.android.R
import com.stocksexchange.android.api.model.Orderbook
import com.stocksexchange.android.api.model.OrderbookOrder
import com.stocksexchange.android.api.model.OrderbookParameters
import com.stocksexchange.android.api.utils.truncate
import com.stocksexchange.android.model.ChartHighlight
import com.stocksexchange.android.model.DepthChartLineStyles
import com.stocksexchange.android.model.DepthChartTab
import com.stocksexchange.android.model.OrderbookOrderTypes
import com.stocksexchange.android.ui.utils.extensions.getCompatDrawable
import com.stocksexchange.android.ui.utils.extensions.spToPx
import com.stocksexchange.android.ui.views.InfoView
import com.stocksexchange.android.ui.views.SimpleMapView
import com.stocksexchange.android.ui.views.marketpreview.base.BaseMarketPreviewChartView
import kotlinx.android.synthetic.main.depth_chart_view_layout.view.*

/**
 * A view representing depth of the specific market.
 */
class DepthChartView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseMarketPreviewChartView<Orderbook, OrderbookOrder, OrderbookParameters>(context, attrs, defStyleAttr) {


    companion object {

        internal const val ATTRIBUTE_KEY_DEPTH_LEVEL = "depth_level"
        internal const val ATTRIBUTE_KEY_PRICE_CHART_INFO_STRING = "price_chart_info_string"
        internal const val ATTRIBUTE_KEY_AMOUNT_CHART_INFO_STRING = "amount_chart_info_string"
        internal const val ATTRIBUTE_KEY_VOLUME_CHART_INFO_STRING = "volume_chart_info_string"
        internal const val ATTRIBUTE_KEY_HIGHEST_BID_CHART_INFO_STRING = "highest_bid_chart_info_string"
        internal const val ATTRIBUTE_KEY_LOWEST_ASK_CHART_INFO_STRING = "lowest_ask_chart_info_string"
        internal const val ATTRIBUTE_KEY_SPREAD_CHART_INFO_STRING = "spread_chart_info_string"

        private const val CHART_HIGHLIGHT_DATA_KEY_SE = "spread_entry"
        private const val CHART_HIGHLIGHT_DATA_KEY_OO = "orderbook_order"

        private const val CHART_HIGHLIGHT_DATA_KEY_OO_PAYLOAD_KEY_OO = "orderbook_order"
        private const val CHART_HIGHLIGHT_DATA_KEY_OO_PAYLOAD_KEY_OOT = "orderbook_order_type"

        private const val DEFAULT_DEPTH_LEVEL = 15

        private const val DEFAULT_CHART_INFO_STRING = ""

        private const val SPREAD_ENTRY_VOLUME = 0f

        private const val CHART_DATA_SET_LINE_WIDTH = 1.5f

        private const val CHART_BUY_DATA_SET_LABEL = "BuyDataSet"
        private const val CHART_SELL_DATA_SET_LABEL = "SellDataSet"

    }


    private var mIsBidSpreadEntryAdded: Boolean = false
    private var mIsAskSpreadEntryAdded: Boolean = false

    private var mDepthLevel: Int = 0

    private var mPriceChartInfoString: String = ""
    private var mAmountChartInfoString: String = ""
    private var mVolumeChartInfoString: String = ""
    private var mHighestBidChartInfoString: String = ""
    private var mLowestAskChartInfoString: String = ""
    private var mSpreadChartInfoString: String = ""

    private var mLineStyle: DepthChartLineStyles = DepthChartLineStyles.LINEAR

    private var mTabs: List<DepthChartTab> = emptyList()

    private val mBuyOrderVolumes: MutableList<Double> = mutableListOf()
    private val mSellOrderVolumes: MutableList<Double> = mutableListOf()

    private var mBuyOrderbookOrders: List<OrderbookOrder> = emptyList()
    private var mSellOrderbookOrders: List<OrderbookOrder> = emptyList()

    /**
     * A listener to invoke whenever a depth level of the depth chart has been changed.
     */
    var onDepthLevelChangeListener: ((Int) -> Unit)? = null




    override fun saveAttributes(attrs: AttributeSet?, defStyleAttr: Int) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.DepthChartView, defStyleAttr, 0).run {
            try {
                with(mAttributes) {
                    save(ATTRIBUTE_KEY_IS_CHART_ANIMATION_ENABLED, getBoolean(R.styleable.DepthChartView_isChartAnimationEnabled, DEFAULT_IS_CHART_ANIMATION_ENABLED))
                    save(ATTRIBUTE_KEY_PROGRESS_BAR_COLOR, getColor(R.styleable.DepthChartView_progressBarColor, DEFAULT_PROGRESS_BAR_COLOR))
                    save(ATTRIBUTE_KEY_INFO_VIEW_COLOR, getColor(R.styleable.DepthChartView_infoViewColor, DEFAULT_INFO_VIEW_COLOR))
                    save(ATTRIBUTE_KEY_CHART_INFO_FIELDS_DEFAULT_TEXT_COLOR, getColor(R.styleable.DepthChartView_chartInfoFieldsDefaultTextColor, DEFAULT_CHART_INFO_FIELDS_TEXT_COLOR))
                    save(ATTRIBUTE_KEY_CHART_AXIS_GRID_COLOR, getColor(R.styleable.DepthChartView_chartAxisGridColor, DEFAULT_CHART_AXIS_GRID_COLOR))
                    save(ATTRIBUTE_KEY_CHART_HIGHLIGHTER_COLOR, getColor(R.styleable.DepthChartView_chartHighlighterColor, DEFAULT_CHART_HIGHLIGHTER_COLOR))
                    save(ATTRIBUTE_KEY_CHART_POSITIVE_COLOR, getColor(R.styleable.DepthChartView_chartPositiveColor, DEFAULT_CHART_POSITIVE_COLOR))
                    save(ATTRIBUTE_KEY_CHART_NEGATIVE_COLOR, getColor(R.styleable.DepthChartView_chartNegativeColor, DEFAULT_CHART_NEGATIVE_COLOR))
                    save(ATTRIBUTE_KEY_CHART_NEUTRAL_COLOR, getColor(R.styleable.DepthChartView_chartNeutralColor, DEFAULT_CHART_NEUTRAL_COLOR))
                    save(ATTRIBUTE_KEY_TAB_TEXT_COLOR, getColor(R.styleable.DepthChartView_tabTextColor, DEFAULT_TAB_TEXT_COLOR))
                    save(ATTRIBUTE_KEY_TAB_INDICATOR_COLOR, getColor(R.styleable.DepthChartView_tabIndicatorColor, DEFAULT_TAB_INDICATOR_COLOR))
                    save(ATTRIBUTE_KEY_DEPTH_LEVEL, getInteger(R.styleable.DepthChartView_depthLevel, DEFAULT_DEPTH_LEVEL))
                    save(ATTRIBUTE_KEY_TAB_TEXT_SIZE, getDimension(R.styleable.DepthChartView_tabTextSize, spToPx(DEFAULT_TAB_TEXT_SIZE_IN_SP)))
                    save(ATTRIBUTE_KEY_INFO_VIEW_EMPTY_CAPTION, (getString(R.styleable.DepthChartView_infoViewEmptyCaption) ?: DEFAULT_INFO_VIEW_CAPTION))
                    save(ATTRIBUTE_KEY_INFO_VIEW_ERROR_CAPTION, (getString(R.styleable.DepthChartView_infoViewErrorCaption) ?: DEFAULT_INFO_VIEW_CAPTION))
                    save(ATTRIBUTE_KEY_PRICE_CHART_INFO_STRING, (getString(R.styleable.DepthChartView_priceChartInfoString) ?: DEFAULT_CHART_INFO_STRING))
                    save(ATTRIBUTE_KEY_AMOUNT_CHART_INFO_STRING, (getString(R.styleable.DepthChartView_amountChartInfoString) ?: DEFAULT_CHART_INFO_STRING))
                    save(ATTRIBUTE_KEY_VOLUME_CHART_INFO_STRING, (getString(R.styleable.DepthChartView_volumeChartInfoString) ?: DEFAULT_CHART_INFO_STRING))
                    save(ATTRIBUTE_KEY_HIGHEST_BID_CHART_INFO_STRING, (getString(R.styleable.DepthChartView_highestBidChartInfoString) ?: DEFAULT_CHART_INFO_STRING))
                    save(ATTRIBUTE_KEY_LOWEST_ASK_CHART_INFO_STRING, (getString(R.styleable.DepthChartView_lowestAskChartInfoString) ?: DEFAULT_CHART_INFO_STRING))
                    save(ATTRIBUTE_KEY_SPREAD_CHART_INFO_STRING, (getString(R.styleable.DepthChartView_spreadChartInfoString) ?: DEFAULT_CHART_INFO_STRING))
                    save(ATTRIBUTE_KEY_INFO_VIEW_ICON, (getDrawable(R.styleable.DepthChartView_infoViewIcon) ?: context.getCompatDrawable(getDefaultInfoViewIconResourceId())))
                }
            } finally {
                recycle()
            }
        }
    }


    override fun initChart() {
        super.initChart()

        chart.setHighlighter(mDepthChartHighlighter)
    }


    override fun addTabLayoutTabs() {
        mTabs.forEach {
            tabLayout.addTab(tabLayout.newTab(), it.position)
        }
    }


    override fun initTabAnimatorTabs() {
        mTabs.forEach {
            mTabAnimator!!.getTabAt(it.position)?.setTitleText(it.getTitle(resources))
        }
    }


    override fun restoreAttributes() {
        super.restoreAttributes()

        with(mAttributes) {
            mDepthLevel = get(ATTRIBUTE_KEY_DEPTH_LEVEL, DEFAULT_DEPTH_LEVEL)
            mPriceChartInfoString = get(ATTRIBUTE_KEY_PRICE_CHART_INFO_STRING, DEFAULT_CHART_INFO_STRING)
            mAmountChartInfoString = get(ATTRIBUTE_KEY_AMOUNT_CHART_INFO_STRING, DEFAULT_CHART_INFO_STRING)
            mVolumeChartInfoString = get(ATTRIBUTE_KEY_VOLUME_CHART_INFO_STRING, DEFAULT_CHART_INFO_STRING)
            mHighestBidChartInfoString = get(ATTRIBUTE_KEY_HIGHEST_BID_CHART_INFO_STRING, DEFAULT_CHART_INFO_STRING)
            mLowestAskChartInfoString = get(ATTRIBUTE_KEY_LOWEST_ASK_CHART_INFO_STRING, DEFAULT_CHART_INFO_STRING)
            mSpreadChartInfoString = get(ATTRIBUTE_KEY_SPREAD_CHART_INFO_STRING, DEFAULT_CHART_INFO_STRING)
        }
    }


    override fun applyAttributes() {
        super.applyAttributes()

        setDepthLevel(mDepthLevel)
        setPriceChartInfoString(mPriceChartInfoString)
        setAmountChartInfoString(mAmountChartInfoString)
        setVolumeChartInfoString(mVolumeChartInfoString)
        setHighestBidChartInfoString(mHighestBidChartInfoString)
        setLowestAskChartInfoString(mLowestAskChartInfoString)
        setSpreadChartInfoString(mSpreadChartInfoString)
    }


    private fun clearTabs() {
        with(tabLayout) {
            removeAllTabs()
            clearOnTabSelectedListeners()
        }
    }


    private fun createBuyDataSet(buyEntries: MutableList<Entry>): LineDataSet {
        return createLineDataSet(buyEntries, CHART_BUY_DATA_SET_LABEL).apply {
            color = mChartPositiveColor
            fillColor = mChartPositiveColor
            axisDependency = YAxis.AxisDependency.LEFT
        }
    }


    private fun createSellDataSet(sellEntries: MutableList<Entry>): LineDataSet {
        return createLineDataSet(sellEntries, CHART_SELL_DATA_SET_LABEL).apply {
            color = mChartNegativeColor
            fillColor = mChartNegativeColor
            axisDependency = YAxis.AxisDependency.RIGHT
        }
    }


    private fun createLineDataSet(entries: MutableList<Entry>, label: String): LineDataSet {
        return LineDataSet(entries, label).apply {
            setDrawValues(false)
            setDrawIcons(false)
            setDrawCircleHole(false)
            setDrawCircles(false)
            setDrawFilled(true)

            lineWidth = CHART_DATA_SET_LINE_WIDTH
            mode = getLineDataSetMode()

            isHighlightEnabled = true
            highlightLineWidth = CHART_HIGHLIGHTER_WIDTH
            highLightColor = mChartHighlighterColor
        }
    }


    private fun enumerateLineDataSets(callback: (LineDataSet) -> Unit) {
        if(isDataEmpty()) {
            return
        }

        chart.data?.dataSets?.forEach {
            if(it is LineDataSet) {
                callback(it)
            }
        }
    }


    private fun convertXValueToOrderbookOrderIndex(type: OrderbookOrderTypes,
                                                   x: Int): Int {
        return when(type) {
            OrderbookOrderTypes.BID -> (mBuyOrderbookOrders.lastIndex - x)
            OrderbookOrderTypes.ASK -> (x - mBuyOrderbookOrders.size - if(mIsAskSpreadEntryAdded) 1 else 0)
        }
    }


    private fun convertOrderbookOrderIndexToXValue(type: OrderbookOrderTypes,
                                                   index: Int): Float {
        return when(type) {
            OrderbookOrderTypes.BID -> (mBuyOrderbookOrders.lastIndex - index).toFloat()
            OrderbookOrderTypes.ASK -> (mBuyOrderbookOrders.size + index + if(mIsAskSpreadEntryAdded) 1 else 0).toFloat()
        }
    }


    private fun getSpreadEntryXValue(): Float {
        return mBuyOrderbookOrders.size.toFloat()
    }


    private fun getHorizontalAxisCompensation(itemCount: Int): Float {
        return if(mTabs.isEmpty()) {
            (itemCount / (DEFAULT_DEPTH_LEVEL.toFloat() * 2f))
        } else {
            (itemCount / (mTabs[0].level.toFloat() * 2f))
        }
    }


    private fun getLineDataSetMode(): LineDataSet.Mode {
        return when(mLineStyle) {
            DepthChartLineStyles.LINEAR -> LineDataSet.Mode.LINEAR
            DepthChartLineStyles.STEPPED -> LineDataSet.Mode.STEPPED
        }
    }


    private fun getLineDataSetByLabel(label: String): LineDataSet? {
        if(isDataEmpty()) {
            return null
        }

        return chart.data?.getDataSetByLabel(label, false)?.let {
            if(it is LineDataSet) {
                it
            } else {
                null
            }
        }
    }


    private fun getHighlight(orderbook: Orderbook, buyDataSet: LineDataSet,
                             sellDataSet: LineDataSet, chartData: LineData): Highlight? {
        val createDefaultHighlight: () -> Highlight? = {
            if(mIsBidSpreadEntryAdded || mIsAskSpreadEntryAdded) {
                createHighlight(
                    getSpreadEntryXValue(),
                    chartData.getIndexOfDataSet(if(mIsBidSpreadEntryAdded) {
                        buyDataSet
                    } else {
                        sellDataSet
                    })
                )
            } else {
                null
            }
        }

        if(mChartHighlight == null) {
            return createDefaultHighlight()
        }

        val chartHighlight = mChartHighlight!!
        val entryIndex = chartHighlight.x.toInt()
        val dataSet = chartData.getDataSetByIndex(chartHighlight.dataSetIndex)

        if((dataSet == null)) {
            return createDefaultHighlight()
        }

        if(chartHighlight.data == null) {
            return createDefaultHighlight()
        }

        val chartHighlightData = chartHighlight.data!!
        val dataKey = chartHighlightData.key

        if((dataKey != CHART_HIGHLIGHT_DATA_KEY_SE) &&
            (dataKey != CHART_HIGHLIGHT_DATA_KEY_OO)) {
            return createDefaultHighlight()
        }

        if(dataKey == CHART_HIGHLIGHT_DATA_KEY_SE) {
            if((buyDataSet.values.getOrNull(entryIndex)?.y != SPREAD_ENTRY_VOLUME) &&
                (sellDataSet.values.getOrNull(entryIndex)?.y != SPREAD_ENTRY_VOLUME)) {
                return createDefaultHighlight()
            }
        } else {
            val order = chartHighlightData.bundle?.run {
                // Adding a class loader to prevent ClassNotFoundException
                // when unmarshalling data from the parcel
                if(classLoader == null) {
                    classLoader = CLASS_LOADER
                }

                getParcelable(CHART_HIGHLIGHT_DATA_KEY_OO_PAYLOAD_KEY_OO) as? OrderbookOrder
            } ?: return createDefaultHighlight()
            val orderType = chartHighlightData.bundle.run {
                getSerializable(CHART_HIGHLIGHT_DATA_KEY_OO_PAYLOAD_KEY_OOT) as? OrderbookOrderTypes
            } ?: return createDefaultHighlight()
            val orderIndex = convertXValueToOrderbookOrderIndex(orderType, entryIndex)
            val orders = when(orderType) {
                OrderbookOrderTypes.BID -> orderbook.buyOrders
                OrderbookOrderTypes.ASK -> orderbook.sellOrders
            }

            if(orders.getOrNull(orderIndex)?.price != order.price) {
                var newOrderIndex: Int? = null
                var closestOrderPrice: Double? = null
                var tempPrice: Double
                var loopOrder: OrderbookOrder

                for(index in orders.indices) {
                    loopOrder = orders[index]

                    if(loopOrder.price != order.price) {
                        // Calculating the closest order by price to prevent
                        // resetting the highlight to the spread entry
                        tempPrice = Math.abs(order.price - loopOrder.price)

                        if(closestOrderPrice == null || (tempPrice < closestOrderPrice)) {
                            newOrderIndex = index
                            closestOrderPrice = tempPrice
                        }
                    } else {
                        newOrderIndex = index
                        break
                    }
                }

                return if(newOrderIndex != null) {
                    createHighlight(
                        convertOrderbookOrderIndexToXValue(orderType, newOrderIndex),
                        chartData.getIndexOfDataSet(when(orderType) {
                            OrderbookOrderTypes.BID -> buyDataSet
                            OrderbookOrderTypes.ASK -> sellDataSet
                        })
                    )
                } else {
                    createDefaultHighlight()
                }
            }
        }

        return chartHighlight.toHighlight()
    }


    private fun getOrderbookDataChartInfoColorsArray(type: OrderbookOrderTypes): IntArray {
        val color = when(type) {
            OrderbookOrderTypes.BID -> mChartPositiveColor
            OrderbookOrderTypes.ASK -> mChartNegativeColor
        }

        return intArrayOf(color, color, color)
    }


    private fun getOrderbookDataChartInfoTitlesArray(): Array<String> {
        return arrayOf(
            mPriceChartInfoString,
            mAmountChartInfoString,
            mVolumeChartInfoString
        )
    }


    private fun getOrderbookDataChartInfoValuesArray(price: Double, amount: Double,
                                                     volume: Double): Array<String> {
        return arrayOf(
            mDoubleFormatter.formatFixedPrice(price),
            mDoubleFormatter.formatAmount(amount),
            mDoubleFormatter.formatAmount(volume)
        )
    }


    private fun getSpreadChartInfoColorsArray(): IntArray {
        return intArrayOf(
            mChartPositiveColor,
            mChartNegativeColor,
            mChartNeutralColor
        )
    }


    private fun getSpreadChartInfoTitlesArray(): Array<String> {
        return arrayOf(
            mHighestBidChartInfoString,
            mLowestAskChartInfoString,
            mSpreadChartInfoString
        )
    }


    private fun getSpreadChartInfoValuesArray(highestBidOrderPrice: Double?,
                                              lowestAskOrderPrice: Double?): Array<String> {
        return arrayOf(
            if(highestBidOrderPrice == null) {
                DEFAULT_CHART_INFO_FIELD_VALUE
            } else {
                mDoubleFormatter.formatFixedPrice(highestBidOrderPrice)
            },
            if(lowestAskOrderPrice == null) {
                DEFAULT_CHART_INFO_FIELD_VALUE
            } else {
                mDoubleFormatter.formatFixedPrice(lowestAskOrderPrice)
            },
            if((highestBidOrderPrice == null) || (lowestAskOrderPrice == null)) {
                DEFAULT_CHART_INFO_FIELD_VALUE
            } else {
                val spread = (lowestAskOrderPrice - highestBidOrderPrice)
                val percentSpread = ((spread / lowestAskOrderPrice) * 100.0)

                "${mDoubleFormatter.formatFixedPrice(spread)} (${mDoubleFormatter.formatPercentSpread(percentSpread)})"
            }
        )
    }


    private fun getDefaultChartInfoValuesArray(): Array<String> {
        return arrayOf(
            DEFAULT_CHART_INFO_FIELD_VALUE,
            DEFAULT_CHART_INFO_FIELD_VALUE,
            DEFAULT_CHART_INFO_FIELD_VALUE
        )
    }


    override fun truncateData(data: Orderbook?): Orderbook? {
        return data.truncate(mDepthLevel)
    }


    override fun bindData() {
        if(isDataEmpty()) {
            return
        }

        val data = mData!!

        var buyEntries: MutableList<Entry> = mutableListOf()
        val sellEntries: MutableList<Entry> = mutableListOf()

        mIsBidSpreadEntryAdded = false
        mIsAskSpreadEntryAdded = false

        mBuyOrderVolumes.clear()
        mSellOrderVolumes.clear()

        mBuyOrderbookOrders = data.buyOrders
        mSellOrderbookOrders = data.sellOrders

        var buyOrderVolume = 0.0
        var sellOrderVolume = 0.0

        // Spread entry for bids
        if(mBuyOrderbookOrders.isNotEmpty()) {
            buyEntries.add(Entry(
                getSpreadEntryXValue(),
                SPREAD_ENTRY_VOLUME,
                OrderbookOrderTypes.BID
            ))

            mIsBidSpreadEntryAdded = true
        }

        for(i in 0 until mBuyOrderbookOrders.size) {
            buyOrderVolume += (mBuyOrderbookOrders.getOrNull(i)?.amount ?: 0.0)
            mBuyOrderVolumes.add(i, buyOrderVolume)

            buyEntries.add(Entry(
                convertOrderbookOrderIndexToXValue(OrderbookOrderTypes.BID, i),
                mBuyOrderVolumes[i].toFloat(),
                OrderbookOrderTypes.BID
            ))
        }

        buyEntries = buyEntries.reversed().toMutableList()

        // Spread entry for asks
        if(mSellOrderbookOrders.isNotEmpty()) {
            sellEntries.add(Entry(
                getSpreadEntryXValue(),
                SPREAD_ENTRY_VOLUME,
                OrderbookOrderTypes.ASK
            ))

            mIsAskSpreadEntryAdded = true
        }

        for(i in 0 until mSellOrderbookOrders.size) {
            sellOrderVolume += (mSellOrderbookOrders.getOrNull(i)?.amount ?: 0.0)
            mSellOrderVolumes.add(i, sellOrderVolume)

            sellEntries.add(Entry(
                convertOrderbookOrderIndexToXValue(OrderbookOrderTypes.ASK, i),
                mSellOrderVolumes[i].toFloat(),
                OrderbookOrderTypes.ASK
            ))
        }

        with(chart) {
            val buyDataSet = createBuyDataSet(buyEntries)
            val sellDataSet = createSellDataSet(sellEntries)
            val chartData = LineData(buyDataSet, sellDataSet)

            // Setting the data of the chart
            this.data = chartData

            with(xAxis) {
                val compensation = getHorizontalAxisCompensation(
                    mBuyOrderbookOrders.size + mSellOrderbookOrders.size
                )

                axisMinimum = (chartData.xMin - compensation)
                axisMaximum = (chartData.xMax + compensation)
            }

            // Highlighting the value based on the market state
            val highlight = getHighlight(data, buyDataSet, sellDataSet, chartData)

            if(highlight != null) {
                highlightValue(highlight, true)
            } else {
                updateChartInfoFields(
                    getSpreadChartInfoTitlesArray(),
                    getDefaultChartInfoValuesArray()
                )
            }
        }
    }


    override fun clearData() {
        super.clearData()

        mIsBidSpreadEntryAdded = false
        mIsAskSpreadEntryAdded = false

        mBuyOrderVolumes.clear()
        mSellOrderVolumes.clear()

        mBuyOrderbookOrders = emptyList()
        mSellOrderbookOrders = emptyList()
    }


    override fun animateChart(duration: Int, easingFunction: Easing.EasingFunction) {
        chart.animateY(duration, easingFunction)
    }


    /**
     * Sets a depth level of the chart. The level denotes
     * the number of orders taken from both the buy and sell
     * sides.
     *
     * @param depthLevel The depth level to set
     */
    fun setDepthLevel(depthLevel: Int) {
        mDepthLevel = depthLevel
        mTabs = DepthChartTab.getDepthChartTabsForDepthLevel(depthLevel)

        clearTabs()
        initTabs()
    }


    /**
     * Sets a price chart info string.
     *
     * @param string The string to set
     */
    fun setPriceChartInfoString(string: String) {
        mPriceChartInfoString = string
    }


    /**
     * Sets an amount chart info string.
     *
     * @param string The string to set
     */
    fun setAmountChartInfoString(string: String) {
        mAmountChartInfoString = string
    }


    /**
     * Sets a volume chart info string.
     *
     * @param string The string to set
     */
    fun setVolumeChartInfoString(string: String) {
        mVolumeChartInfoString = string
    }


    /**
     * Sets a highest bid chart info string.
     *
     * @param string The string to set
     */
    fun setHighestBidChartInfoString(string: String) {
        mHighestBidChartInfoString = string
    }


    /**
     * Sets a lowest ask chart info string.
     *
     * @param string The string to set
     */
    fun setLowestAskChartInfoString(string: String) {
        mLowestAskChartInfoString = string
    }


    /**
     * Sets a spread chart info string.
     *
     * @param string The string to set
     */
    fun setSpreadChartInfoString(string: String) {
        mSpreadChartInfoString = string
    }


    /**
     * Sets a line style for the chart.
     *
     * @param style The style to set
     */
    fun setLineStyle(style: DepthChartLineStyles) {
        mLineStyle = style
    }


    override fun setChartHighlighterColor(color: Int) {
        super.setChartHighlighterColor(color)

        enumerateLineDataSets {
            it.highLightColor = color
        }
    }


    override fun setChartPositiveColor(color: Int) {
        super.setChartPositiveColor(color)

        getLineDataSetByLabel(CHART_BUY_DATA_SET_LABEL)?.fillColor = color
    }


    override fun setChartNegativeColor(color: Int) {
        super.setChartNegativeColor(color)

        getLineDataSetByLabel(CHART_SELL_DATA_SET_LABEL)?.fillColor = color
    }


    /**
     * Sets a market name of the orderbook data to load.
     *
     * @param marketName The market name to set
     */
    fun setMarketName(marketName: String) {
        mDataParameters = mDataParameters.copy(marketName = marketName)
    }


    override fun isDataEmpty(): Boolean {
        return (mData?.isEmpty() ?: true)
    }


    override fun shouldInitTabs(): Boolean = mTabs.isNotEmpty()


    override fun getLayoutResourceId(): Int = R.layout.depth_chart_view_layout


    override fun getDefaultParameters(): OrderbookParameters {
        return OrderbookParameters.getDefaultParameters()
    }


    override fun getProgressBar(): ProgressBar = progressBar


    override fun getInfoView(): InfoView = infoView


    override fun getMainView(): View = chartContainerLl


    override fun getChart(): BarLineChartBase<*> = chart


    override fun getTabLayout(): TabLayout = tabLayout


    override fun getTabLayoutTabIndices(): List<Int> {
        val indices: MutableList<Int> = mutableListOf()

        mTabs.forEach {
            indices.add(it.position)
        }

        return indices
    }


    override fun getChartInfoFieldsArray(): Array<SimpleMapView> {
        return arrayOf(firstSmv, secondSmv, thirdSmv)
    }


    override fun onValueSelected(entry: Entry, highlight: Highlight) {
        super.onValueSelected(entry, highlight)

        val entryIndex = entry.x.toInt()

        val colors: IntArray
        val titles: Array<String>
        val values: Array<String>

        // Checking for spread entries
        if((entry.y == SPREAD_ENTRY_VOLUME) && (mIsBidSpreadEntryAdded || mIsAskSpreadEntryAdded)) {
            val highestBidOrder = if(mBuyOrderbookOrders.isEmpty()) {
                null
            } else {
                mBuyOrderbookOrders.first()
            }
            val lowestAskOrder = if(mSellOrderbookOrders.isEmpty()) {
                null
            } else {
                mSellOrderbookOrders.first()
            }

            mChartHighlight?.data = ChartHighlight.ChartHighlightData(
                key = CHART_HIGHLIGHT_DATA_KEY_SE
            )

            colors = getSpreadChartInfoColorsArray()
            titles = getSpreadChartInfoTitlesArray()
            values = getSpreadChartInfoValuesArray(
                highestBidOrder?.price,
                lowestAskOrder?.price
            )
        } else {
            val orderbookOrderType = (entry.data as OrderbookOrderTypes)
            val orderbookOrderIndex = convertXValueToOrderbookOrderIndex(
                orderbookOrderType,
                entryIndex
            )

            val orderbookOrderVolume: Double
            val orderbookOrder: OrderbookOrder

            when(orderbookOrderType) {

                OrderbookOrderTypes.BID -> {
                    orderbookOrder = mBuyOrderbookOrders[orderbookOrderIndex]
                    orderbookOrderVolume = mBuyOrderVolumes[orderbookOrderIndex]
                }

                OrderbookOrderTypes.ASK -> {
                    orderbookOrder = mSellOrderbookOrders[orderbookOrderIndex]
                    orderbookOrderVolume = mSellOrderVolumes[orderbookOrderIndex]
                }

            }

            mChartHighlight?.data = ChartHighlight.ChartHighlightData(
                key = CHART_HIGHLIGHT_DATA_KEY_OO,
                bundle = Bundle(CLASS_LOADER).apply {
                    putParcelable(CHART_HIGHLIGHT_DATA_KEY_OO_PAYLOAD_KEY_OO, orderbookOrder)
                    putSerializable(CHART_HIGHLIGHT_DATA_KEY_OO_PAYLOAD_KEY_OOT, orderbookOrderType)
                }
            )

            colors = getOrderbookDataChartInfoColorsArray(orderbookOrderType)
            titles = getOrderbookDataChartInfoTitlesArray()
            values = getOrderbookDataChartInfoValuesArray(
                orderbookOrder.price,
                orderbookOrder.amount,
                orderbookOrderVolume
            )
        }

        updateChartInfoFieldsColors(*colors)
        updateChartInfoFields(titles, values)
    }


    override fun onTabSelected(tab: TabLayout.Tab) {
        super.onTabSelected(tab)

        mDepthLevel = mTabs[mSelectedTabPosition].level

        if(!mIsTabSelectedProgrammatically) {
            onDepthLevelChangeListener?.invoke(mDepthLevel)
        }
    }


    private val mDepthChartHighlighter = object : ChartHighlighter<LineChart>(chart) {

        override fun getHighlightForX(xVal: Float, x: Float, y: Float): Highlight? {
            val closestValues = getHighlightsAtXValue(xVal, x, y)

            if(closestValues.isEmpty()) {
                return null
            }

            val leftAxisMinDist = getMinimumDistance(closestValues, x, YAxis.AxisDependency.LEFT)
            val rightAxisMinDist = getMinimumDistance(closestValues, x, YAxis.AxisDependency.RIGHT)

            val axis = if(leftAxisMinDist < rightAxisMinDist) {
                YAxis.AxisDependency.LEFT
            } else {
                YAxis.AxisDependency.RIGHT
            }

            return getClosestHighlightByPixel(closestValues, x, y, axis, mChart.maxHighlightDistance)
        }


        override fun getHighlightPos(highlight: Highlight): Float {
            return highlight.xPx
        }


        override fun getDistance(x1: Float, y1: Float, x2: Float, y2: Float): Float {
            return Math.abs(x1 - x2)
        }

    }


    override fun onRestoreInstanceState(state: Parcelable?) {
        super.onRestoreInstanceState(state)

        if(state is SavedState) {
            setPriceChartInfoString(state.priceChartInfoString)
            setAmountChartInfoString(state.amountChartInfoString)
            setVolumeChartInfoString(state.volumeChartInfoString)
            setHighestBidChartInfoString(state.highestBidChartInfoString)
            setLowestAskChartInfoString(state.lowestAskChartInfoString)
            setSpreadChartInfoString(state.spreadChartInfoString)
            setLineStyle(state.lineStyle)
            setData(state.data, true)
        }
    }


    override fun onSaveInstanceState(): Parcelable? {
        return SavedState(super.onSaveInstanceState()).apply {
            onSaveInstanceState(this)
        }
    }


    override fun onSaveInstanceState(savedState: BaseMarketPreviewSavedState<OrderbookParameters>) {
        super.onSaveInstanceState(savedState)

        if(savedState is SavedState) {
            with(savedState) {
                priceChartInfoString = mPriceChartInfoString
                amountChartInfoString = mAmountChartInfoString
                volumeChartInfoString = mVolumeChartInfoString
                highestBidChartInfoString = mHighestBidChartInfoString
                lowestAskChartInfoString = mLowestAskChartInfoString
                spreadChartInfoString = mSpreadChartInfoString
                lineStyle = mLineStyle
                data = mData
            }
        }
    }


    private class SavedState : BaseMarketPreviewChartBasedState<OrderbookParameters> {

        companion object {

            private const val KEY_PRICE_CHART_INFO_STRING = "price_chart_info_string"
            private const val KEY_AMOUNT_CHART_INFO_STRING = "amount_chart_info_string"
            private const val KEY_VOLUME_CHART_INFO_STRING = "volume_chart_info_string"
            private const val KEY_HIGHEST_BID_CHART_INFO_STRING = "highest_bid_chart_info_string"
            private const val KEY_LOWEST_ASK_CHART_INFO_STRING = "lowest_ask_chart_info_string"
            private const val KEY_SPREAD_CHART_INFO_STRING = "spread_chart_info_string"
            private const val KEY_LINE_STYLE = "key_line_style"
            private const val KEY_DATA = "data"


            @JvmField
            val CREATOR = object : Parcelable.Creator<SavedState> {

                override fun createFromParcel(parcel: Parcel): SavedState = SavedState(parcel)

                override fun newArray(size: Int): Array<SavedState?> = arrayOfNulls(size)

            }

        }


        internal var priceChartInfoString: String = ""
        internal var amountChartInfoString: String = ""
        internal var volumeChartInfoString: String = ""
        internal var highestBidChartInfoString: String = ""
        internal var lowestAskChartInfoString: String = ""
        internal var spreadChartInfoString: String = ""

        internal var lineStyle: DepthChartLineStyles = DepthChartLineStyles.LINEAR

        internal var data: Orderbook? = null


        constructor(parcel: Parcel): super(parcel) {
            parcel.readBundle(CLASS_LOADER)?.run {
                priceChartInfoString = getString(KEY_PRICE_CHART_INFO_STRING, DEFAULT_CHART_INFO_STRING)
                amountChartInfoString = getString(KEY_AMOUNT_CHART_INFO_STRING, DEFAULT_CHART_INFO_STRING)
                volumeChartInfoString = getString(KEY_VOLUME_CHART_INFO_STRING, DEFAULT_CHART_INFO_STRING)
                highestBidChartInfoString = getString(KEY_HIGHEST_BID_CHART_INFO_STRING, DEFAULT_CHART_INFO_STRING)
                lowestAskChartInfoString = getString(KEY_LOWEST_ASK_CHART_INFO_STRING, DEFAULT_CHART_INFO_STRING)
                spreadChartInfoString = getString(KEY_SPREAD_CHART_INFO_STRING, DEFAULT_CHART_INFO_STRING)
                lineStyle = (getSerializable(KEY_LINE_STYLE) as DepthChartLineStyles)
                data = getParcelable(KEY_DATA)
            }
        }


        constructor(superState: Parcelable?): super(superState)


        override fun writeToParcel(parcel: Parcel, flags: Int) {
            super.writeToParcel(parcel, flags)

            val bundle = Bundle(CLASS_LOADER).apply {
                putString(KEY_PRICE_CHART_INFO_STRING, priceChartInfoString)
                putString(KEY_AMOUNT_CHART_INFO_STRING, amountChartInfoString)
                putString(KEY_VOLUME_CHART_INFO_STRING, volumeChartInfoString)
                putString(KEY_HIGHEST_BID_CHART_INFO_STRING, highestBidChartInfoString)
                putString(KEY_LOWEST_ASK_CHART_INFO_STRING, lowestAskChartInfoString)
                putString(KEY_SPREAD_CHART_INFO_STRING, spreadChartInfoString)
                putSerializable(KEY_LINE_STYLE, lineStyle)
                putParcelable(KEY_DATA, data)
            }

            parcel.writeBundle(bundle)
        }

    }


}