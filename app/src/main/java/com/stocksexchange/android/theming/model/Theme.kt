package com.stocksexchange.android.theming.model

import java.io.Serializable

/**
 * A main model class used for theming views across the application.
 */
data class Theme(
    val id: Int,
    val name: String,
    val generalTheme: GeneralTheme,
    val buttonTheme: ButtonTheme,
    val cardViewTheme: CardViewTheme,
    val switchOptionsViewTheme: SwitchOptionsViewTheme,
    val priceChartViewTheme: PriceChartViewTheme,
    val depthChartViewTheme: DepthChartViewTheme,
    val marketDetailsViewTheme: MarketDetailsViewTheme,
    val orderbookDetailsViewTheme: OrderbookDetailsViewTheme,
    val tradesViewTheme: TradesViewTheme,
    val orderbookViewTheme: OrderbookViewTheme,
    val dialogTheme: DialogTheme,
    val dottedMapViewTheme: DottedMapViewTheme,
    val navigationDrawerTheme: NavigationDrawerTheme,
    val labeledEditTextTheme: LabeledEditTextTheme,
    val qrCodeScannerTheme: QrCodeScannerTheme,
    val sortPanelTheme: SortPanelTheme,
    val switchTheme: SwitchTheme,
    val popupMenuTheme: PopupMenuTheme,
    val pinEntryKeypadTheme: PinEntryKeypadTheme
) : Serializable