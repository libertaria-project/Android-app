package com.stocksexchange.android.theming.factories

import com.stocksexchange.android.theming.factories.base.BaseThemeFactory
import com.stocksexchange.android.theming.model.Theme
import com.stocksexchange.android.theming.model.Themes

/**
 * A main factory producing instances of [Theme] class.
 */
object ThemeFactory : BaseThemeFactory<Theme>() {


    /**
     * Returns a default theme.
     *
     * @return The default theme
     */
    fun getDefaultTheme(): Theme {
        return getDeepTealTheme()
    }


    /**
     * Returns a theme for a specific ID.
     *
     * @param id The id of the theme
     *
     * @return The instance of [Theme] class
     */
    fun getThemeForId(id: Int): Theme {
        return when(id) {

            Themes.DEEP_TEAL.id -> getDeepTealTheme()
            Themes.NIGHT_BLUE.id -> getNightBlueTheme()
            Themes.PITCH_BLACK.id -> getPitchBlackTheme()
            Themes.DARK_GREEN.id -> getDarkGreenTheme()
            Themes.GRAYISH_BLUE.id -> getGrayishBlueTheme()
            Themes.BRIGHT_GRAY.id -> getBrightGrayTheme()
            Themes.DARK_SILVER.id -> getDarkSilverTheme()
            Themes.BROWNEE.id -> getBrowneeTheme()
            Themes.VIOLET.id -> getVioletTheme()
            Themes.PURPLE.id -> getPurpleTheme()
            Themes.BLUE_ZODIAC.id -> getBlueZodiacTheme()
            Themes.BUBBLE_BLUE.id -> getBubbleBlueTheme()

            else -> getDeepTealTheme()

        }
    }


    override fun getStubTheme(): Theme {
        return Theme(
            id = -1,
            name = "Stub",
            generalTheme = GeneralThemeFactory.getStubTheme(),
            buttonTheme = ButtonThemeFactory.getStubTheme(),
            cardViewTheme = CardViewThemeFactory.getStubTheme(),
            switchOptionsViewTheme = SwitchOptionsViewThemeFactory.getStubTheme(),
            priceChartViewTheme = PriceChartViewThemeFactory.getStubTheme(),
            depthChartViewTheme = DepthChartViewThemeFactory.getStubTheme(),
            marketDetailsViewTheme = MarketDetailsViewThemeFactory.getStubTheme(),
            orderbookViewTheme = OrderbookViewThemeFactory.getStubTheme(),
            tradesViewTheme = TradesViewThemeFactory.getStubTheme(),
            orderbookDetailsViewTheme = OrderbookDetailsViewThemeFactory.getStubTheme(),
            dialogTheme = DialogThemeFactory.getStubTheme(),
            dottedMapViewTheme = DottedMapViewThemeFactory.getStubTheme(),
            navigationDrawerTheme = NavigationDrawerFactory.getStubTheme(),
            labeledEditTextTheme = LabeledEditTextFactory.getStubTheme(),
            qrCodeScannerTheme = QrCodeScannerThemeFactory.getStubTheme(),
            sortPanelTheme = SortPanelThemeFactory.getStubTheme(),
            switchTheme = SwitchThemeFactory.getStubTheme(),
            popupMenuTheme = PopupMenuThemeFactory.getStubTheme(),
            pinEntryKeypadTheme = PinEntryKeypadThemeFactory.getStubTheme()
        )
    }


    override fun getDeepTealTheme(): Theme {
        return Theme(
            id = Themes.DEEP_TEAL.id,
            name = Themes.DEEP_TEAL.title,
            generalTheme = GeneralThemeFactory.getDeepTealTheme(),
            buttonTheme = ButtonThemeFactory.getDeepTealTheme(),
            cardViewTheme = CardViewThemeFactory.getDeepTealTheme(),
            switchOptionsViewTheme = SwitchOptionsViewThemeFactory.getDeepTealTheme(),
            priceChartViewTheme = PriceChartViewThemeFactory.getDeepTealTheme(),
            depthChartViewTheme = DepthChartViewThemeFactory.getDeepTealTheme(),
            marketDetailsViewTheme = MarketDetailsViewThemeFactory.getDeepTealTheme(),
            orderbookViewTheme = OrderbookViewThemeFactory.getDeepTealTheme(),
            tradesViewTheme = TradesViewThemeFactory.getDeepTealTheme(),
            orderbookDetailsViewTheme = OrderbookDetailsViewThemeFactory.getDeepTealTheme(),
            dialogTheme = DialogThemeFactory.getDeepTealTheme(),
            dottedMapViewTheme = DottedMapViewThemeFactory.getDeepTealTheme(),
            navigationDrawerTheme = NavigationDrawerFactory.getDeepTealTheme(),
            labeledEditTextTheme = LabeledEditTextFactory.getDeepTealTheme(),
            qrCodeScannerTheme = QrCodeScannerThemeFactory.getDeepTealTheme(),
            sortPanelTheme = SortPanelThemeFactory.getDeepTealTheme(),
            switchTheme = SwitchThemeFactory.getDeepTealTheme(),
            popupMenuTheme = PopupMenuThemeFactory.getDeepTealTheme(),
            pinEntryKeypadTheme = PinEntryKeypadThemeFactory.getDeepTealTheme()
        )
    }


    override fun getNightBlueTheme(): Theme {
        return Theme(
            id = Themes.NIGHT_BLUE.id,
            name = Themes.NIGHT_BLUE.title,
            generalTheme = GeneralThemeFactory.getNightBlueTheme(),
            buttonTheme = ButtonThemeFactory.getNightBlueTheme(),
            cardViewTheme = CardViewThemeFactory.getNightBlueTheme(),
            switchOptionsViewTheme = SwitchOptionsViewThemeFactory.getNightBlueTheme(),
            priceChartViewTheme = PriceChartViewThemeFactory.getNightBlueTheme(),
            depthChartViewTheme = DepthChartViewThemeFactory.getNightBlueTheme(),
            marketDetailsViewTheme = MarketDetailsViewThemeFactory.getNightBlueTheme(),
            orderbookViewTheme = OrderbookViewThemeFactory.getNightBlueTheme(),
            tradesViewTheme = TradesViewThemeFactory.getNightBlueTheme(),
            orderbookDetailsViewTheme = OrderbookDetailsViewThemeFactory.getNightBlueTheme(),
            dialogTheme = DialogThemeFactory.getNightBlueTheme(),
            dottedMapViewTheme = DottedMapViewThemeFactory.getNightBlueTheme(),
            navigationDrawerTheme = NavigationDrawerFactory.getNightBlueTheme(),
            labeledEditTextTheme = LabeledEditTextFactory.getNightBlueTheme(),
            qrCodeScannerTheme = QrCodeScannerThemeFactory.getNightBlueTheme(),
            sortPanelTheme = SortPanelThemeFactory.getNightBlueTheme(),
            switchTheme = SwitchThemeFactory.getNightBlueTheme(),
            popupMenuTheme = PopupMenuThemeFactory.getNightBlueTheme(),
            pinEntryKeypadTheme = PinEntryKeypadThemeFactory.getNightBlueTheme()
        )
    }


    override fun getDarkGreenTheme(): Theme {
        return Theme(
            id = Themes.DARK_GREEN.id,
            name = Themes.DARK_GREEN.title,
            generalTheme = GeneralThemeFactory.getDarkGreenTheme(),
            buttonTheme = ButtonThemeFactory.getDarkGreenTheme(),
            cardViewTheme = CardViewThemeFactory.getDarkGreenTheme(),
            switchOptionsViewTheme = SwitchOptionsViewThemeFactory.getDarkGreenTheme(),
            priceChartViewTheme = PriceChartViewThemeFactory.getDarkGreenTheme(),
            depthChartViewTheme = DepthChartViewThemeFactory.getDarkGreenTheme(),
            marketDetailsViewTheme = MarketDetailsViewThemeFactory.getDarkGreenTheme(),
            orderbookViewTheme = OrderbookViewThemeFactory.getDarkGreenTheme(),
            tradesViewTheme = TradesViewThemeFactory.getDarkGreenTheme(),
            orderbookDetailsViewTheme = OrderbookDetailsViewThemeFactory.getDarkGreenTheme(),
            dialogTheme = DialogThemeFactory.getDarkGreenTheme(),
            dottedMapViewTheme = DottedMapViewThemeFactory.getDarkGreenTheme(),
            navigationDrawerTheme = NavigationDrawerFactory.getDarkGreenTheme(),
            labeledEditTextTheme = LabeledEditTextFactory.getDarkGreenTheme(),
            qrCodeScannerTheme = QrCodeScannerThemeFactory.getDarkGreenTheme(),
            sortPanelTheme = SortPanelThemeFactory.getDarkGreenTheme(),
            switchTheme = SwitchThemeFactory.getDarkGreenTheme(),
            popupMenuTheme = PopupMenuThemeFactory.getDarkGreenTheme(),
            pinEntryKeypadTheme = PinEntryKeypadThemeFactory.getDarkGreenTheme()
        )
    }


    override fun getPitchBlackTheme(): Theme {
        return Theme(
            id = Themes.PITCH_BLACK.id,
            name = Themes.PITCH_BLACK.title,
            generalTheme = GeneralThemeFactory.getPitchBlackTheme(),
            buttonTheme = ButtonThemeFactory.getPitchBlackTheme(),
            cardViewTheme = CardViewThemeFactory.getPitchBlackTheme(),
            switchOptionsViewTheme = SwitchOptionsViewThemeFactory.getPitchBlackTheme(),
            priceChartViewTheme = PriceChartViewThemeFactory.getPitchBlackTheme(),
            depthChartViewTheme = DepthChartViewThemeFactory.getPitchBlackTheme(),
            marketDetailsViewTheme = MarketDetailsViewThemeFactory.getPitchBlackTheme(),
            orderbookViewTheme = OrderbookViewThemeFactory.getPitchBlackTheme(),
            tradesViewTheme = TradesViewThemeFactory.getPitchBlackTheme(),
            orderbookDetailsViewTheme = OrderbookDetailsViewThemeFactory.getPitchBlackTheme(),
            dialogTheme = DialogThemeFactory.getPitchBlackTheme(),
            dottedMapViewTheme = DottedMapViewThemeFactory.getPitchBlackTheme(),
            navigationDrawerTheme = NavigationDrawerFactory.getPitchBlackTheme(),
            labeledEditTextTheme = LabeledEditTextFactory.getPitchBlackTheme(),
            qrCodeScannerTheme = QrCodeScannerThemeFactory.getPitchBlackTheme(),
            sortPanelTheme = SortPanelThemeFactory.getPitchBlackTheme(),
            switchTheme = SwitchThemeFactory.getPitchBlackTheme(),
            popupMenuTheme = PopupMenuThemeFactory.getPitchBlackTheme(),
            pinEntryKeypadTheme = PinEntryKeypadThemeFactory.getPitchBlackTheme()
        )
    }


    override fun getGrayishBlueTheme(): Theme {
        return Theme(
            id = Themes.GRAYISH_BLUE.id,
            name = Themes.GRAYISH_BLUE.title,
            generalTheme = GeneralThemeFactory.getGrayishBlueTheme(),
            buttonTheme = ButtonThemeFactory.getGrayishBlueTheme(),
            cardViewTheme = CardViewThemeFactory.getGrayishBlueTheme(),
            switchOptionsViewTheme = SwitchOptionsViewThemeFactory.getGrayishBlueTheme(),
            priceChartViewTheme = PriceChartViewThemeFactory.getGrayishBlueTheme(),
            depthChartViewTheme = DepthChartViewThemeFactory.getGrayishBlueTheme(),
            marketDetailsViewTheme = MarketDetailsViewThemeFactory.getGrayishBlueTheme(),
            orderbookViewTheme = OrderbookViewThemeFactory.getGrayishBlueTheme(),
            tradesViewTheme = TradesViewThemeFactory.getGrayishBlueTheme(),
            orderbookDetailsViewTheme = OrderbookDetailsViewThemeFactory.getGrayishBlueTheme(),
            dialogTheme = DialogThemeFactory.getGrayishBlueTheme(),
            dottedMapViewTheme = DottedMapViewThemeFactory.getGrayishBlueTheme(),
            navigationDrawerTheme = NavigationDrawerFactory.getGrayishBlueTheme(),
            labeledEditTextTheme = LabeledEditTextFactory.getGrayishBlueTheme(),
            qrCodeScannerTheme = QrCodeScannerThemeFactory.getGrayishBlueTheme(),
            sortPanelTheme = SortPanelThemeFactory.getGrayishBlueTheme(),
            switchTheme = SwitchThemeFactory.getGrayishBlueTheme(),
            popupMenuTheme = PopupMenuThemeFactory.getGrayishBlueTheme(),
            pinEntryKeypadTheme = PinEntryKeypadThemeFactory.getGrayishBlueTheme()
        )
    }


    override fun getBrightGrayTheme(): Theme {
        return Theme(
            id = Themes.BRIGHT_GRAY.id,
            name = Themes.BRIGHT_GRAY.title,
            generalTheme = GeneralThemeFactory.getBrightGrayTheme(),
            buttonTheme = ButtonThemeFactory.getBrightGrayTheme(),
            cardViewTheme = CardViewThemeFactory.getBrightGrayTheme(),
            switchOptionsViewTheme = SwitchOptionsViewThemeFactory.getBrightGrayTheme(),
            priceChartViewTheme = PriceChartViewThemeFactory.getBrightGrayTheme(),
            depthChartViewTheme = DepthChartViewThemeFactory.getBrightGrayTheme(),
            marketDetailsViewTheme = MarketDetailsViewThemeFactory.getBrightGrayTheme(),
            orderbookViewTheme = OrderbookViewThemeFactory.getBrightGrayTheme(),
            tradesViewTheme = TradesViewThemeFactory.getBrightGrayTheme(),
            orderbookDetailsViewTheme = OrderbookDetailsViewThemeFactory.getBrightGrayTheme(),
            dialogTheme = DialogThemeFactory.getBrightGrayTheme(),
            dottedMapViewTheme = DottedMapViewThemeFactory.getBrightGrayTheme(),
            navigationDrawerTheme = NavigationDrawerFactory.getBrightGrayTheme(),
            labeledEditTextTheme = LabeledEditTextFactory.getBrightGrayTheme(),
            qrCodeScannerTheme = QrCodeScannerThemeFactory.getBrightGrayTheme(),
            sortPanelTheme = SortPanelThemeFactory.getBrightGrayTheme(),
            switchTheme = SwitchThemeFactory.getBrightGrayTheme(),
            popupMenuTheme = PopupMenuThemeFactory.getBrightGrayTheme(),
            pinEntryKeypadTheme = PinEntryKeypadThemeFactory.getBrightGrayTheme()
        )
    }


    override fun getDarkSilverTheme(): Theme {
        return Theme(
            id = Themes.DARK_SILVER.id,
            name = Themes.DARK_SILVER.title,
            generalTheme = GeneralThemeFactory.getDarkSilverTheme(),
            buttonTheme = ButtonThemeFactory.getDarkSilverTheme(),
            cardViewTheme = CardViewThemeFactory.getDarkSilverTheme(),
            switchOptionsViewTheme = SwitchOptionsViewThemeFactory.getDarkSilverTheme(),
            priceChartViewTheme = PriceChartViewThemeFactory.getDarkSilverTheme(),
            depthChartViewTheme = DepthChartViewThemeFactory.getDarkSilverTheme(),
            marketDetailsViewTheme = MarketDetailsViewThemeFactory.getDarkSilverTheme(),
            orderbookViewTheme = OrderbookViewThemeFactory.getDarkSilverTheme(),
            tradesViewTheme = TradesViewThemeFactory.getDarkSilverTheme(),
            orderbookDetailsViewTheme = OrderbookDetailsViewThemeFactory.getDarkSilverTheme(),
            dialogTheme = DialogThemeFactory.getDarkSilverTheme(),
            dottedMapViewTheme = DottedMapViewThemeFactory.getDarkSilverTheme(),
            navigationDrawerTheme = NavigationDrawerFactory.getDarkSilverTheme(),
            labeledEditTextTheme = LabeledEditTextFactory.getDarkSilverTheme(),
            qrCodeScannerTheme = QrCodeScannerThemeFactory.getDarkSilverTheme(),
            sortPanelTheme = SortPanelThemeFactory.getDarkSilverTheme(),
            switchTheme = SwitchThemeFactory.getDarkSilverTheme(),
            popupMenuTheme = PopupMenuThemeFactory.getDarkSilverTheme(),
            pinEntryKeypadTheme = PinEntryKeypadThemeFactory.getDarkSilverTheme()
        )
    }


    override fun getBrowneeTheme(): Theme {
        return Theme(
            id = Themes.BROWNEE.id,
            name = Themes.BROWNEE.title,
            generalTheme = GeneralThemeFactory.getBrowneeTheme(),
            buttonTheme = ButtonThemeFactory.getBrowneeTheme(),
            cardViewTheme = CardViewThemeFactory.getBrowneeTheme(),
            switchOptionsViewTheme = SwitchOptionsViewThemeFactory.getBrowneeTheme(),
            priceChartViewTheme = PriceChartViewThemeFactory.getBrowneeTheme(),
            depthChartViewTheme = DepthChartViewThemeFactory.getBrowneeTheme(),
            marketDetailsViewTheme = MarketDetailsViewThemeFactory.getBrowneeTheme(),
            orderbookViewTheme = OrderbookViewThemeFactory.getBrowneeTheme(),
            tradesViewTheme = TradesViewThemeFactory.getBrowneeTheme(),
            orderbookDetailsViewTheme = OrderbookDetailsViewThemeFactory.getBrowneeTheme(),
            dialogTheme = DialogThemeFactory.getBrowneeTheme(),
            dottedMapViewTheme = DottedMapViewThemeFactory.getBrowneeTheme(),
            navigationDrawerTheme = NavigationDrawerFactory.getBrowneeTheme(),
            labeledEditTextTheme = LabeledEditTextFactory.getBrowneeTheme(),
            qrCodeScannerTheme = QrCodeScannerThemeFactory.getBrowneeTheme(),
            sortPanelTheme = SortPanelThemeFactory.getBrowneeTheme(),
            switchTheme = SwitchThemeFactory.getBrowneeTheme(),
            popupMenuTheme = PopupMenuThemeFactory.getBrowneeTheme(),
            pinEntryKeypadTheme = PinEntryKeypadThemeFactory.getBrowneeTheme()
        )
    }


    override fun getVioletTheme(): Theme {
        return Theme(
            id = Themes.VIOLET.id,
            name = Themes.VIOLET.title,
            generalTheme = GeneralThemeFactory.getVioletTheme(),
            buttonTheme = ButtonThemeFactory.getVioletTheme(),
            cardViewTheme = CardViewThemeFactory.getVioletTheme(),
            switchOptionsViewTheme = SwitchOptionsViewThemeFactory.getVioletTheme(),
            priceChartViewTheme = PriceChartViewThemeFactory.getVioletTheme(),
            depthChartViewTheme = DepthChartViewThemeFactory.getVioletTheme(),
            marketDetailsViewTheme = MarketDetailsViewThemeFactory.getVioletTheme(),
            orderbookViewTheme = OrderbookViewThemeFactory.getVioletTheme(),
            tradesViewTheme = TradesViewThemeFactory.getVioletTheme(),
            orderbookDetailsViewTheme = OrderbookDetailsViewThemeFactory.getVioletTheme(),
            dialogTheme = DialogThemeFactory.getVioletTheme(),
            dottedMapViewTheme = DottedMapViewThemeFactory.getVioletTheme(),
            navigationDrawerTheme = NavigationDrawerFactory.getVioletTheme(),
            labeledEditTextTheme = LabeledEditTextFactory.getVioletTheme(),
            qrCodeScannerTheme = QrCodeScannerThemeFactory.getVioletTheme(),
            sortPanelTheme = SortPanelThemeFactory.getVioletTheme(),
            switchTheme = SwitchThemeFactory.getVioletTheme(),
            popupMenuTheme = PopupMenuThemeFactory.getVioletTheme(),
            pinEntryKeypadTheme = PinEntryKeypadThemeFactory.getVioletTheme()
        )
    }


    override fun getPurpleTheme(): Theme {
        return Theme(
            id = Themes.PURPLE.id,
            name = Themes.PURPLE.title,
            generalTheme = GeneralThemeFactory.getPurpleTheme(),
            buttonTheme = ButtonThemeFactory.getPurpleTheme(),
            cardViewTheme = CardViewThemeFactory.getPurpleTheme(),
            switchOptionsViewTheme = SwitchOptionsViewThemeFactory.getPurpleTheme(),
            priceChartViewTheme = PriceChartViewThemeFactory.getPurpleTheme(),
            depthChartViewTheme = DepthChartViewThemeFactory.getPurpleTheme(),
            marketDetailsViewTheme = MarketDetailsViewThemeFactory.getPurpleTheme(),
            orderbookViewTheme = OrderbookViewThemeFactory.getPurpleTheme(),
            tradesViewTheme = TradesViewThemeFactory.getPurpleTheme(),
            orderbookDetailsViewTheme = OrderbookDetailsViewThemeFactory.getPurpleTheme(),
            dialogTheme = DialogThemeFactory.getPurpleTheme(),
            dottedMapViewTheme = DottedMapViewThemeFactory.getPurpleTheme(),
            navigationDrawerTheme = NavigationDrawerFactory.getPurpleTheme(),
            labeledEditTextTheme = LabeledEditTextFactory.getPurpleTheme(),
            qrCodeScannerTheme = QrCodeScannerThemeFactory.getPurpleTheme(),
            sortPanelTheme = SortPanelThemeFactory.getPurpleTheme(),
            switchTheme = SwitchThemeFactory.getPurpleTheme(),
            popupMenuTheme = PopupMenuThemeFactory.getPurpleTheme(),
            pinEntryKeypadTheme = PinEntryKeypadThemeFactory.getPurpleTheme()
        )
    }


    override fun getBlueZodiacTheme(): Theme {
        return Theme(
            id = Themes.BLUE_ZODIAC.id,
            name = Themes.BLUE_ZODIAC.title,
            generalTheme = GeneralThemeFactory.getBlueZodiacTheme(),
            buttonTheme = ButtonThemeFactory.getBlueZodiacTheme(),
            cardViewTheme = CardViewThemeFactory.getBlueZodiacTheme(),
            switchOptionsViewTheme = SwitchOptionsViewThemeFactory.getBlueZodiacTheme(),
            priceChartViewTheme = PriceChartViewThemeFactory.getBlueZodiacTheme(),
            depthChartViewTheme = DepthChartViewThemeFactory.getBlueZodiacTheme(),
            marketDetailsViewTheme = MarketDetailsViewThemeFactory.getBlueZodiacTheme(),
            orderbookViewTheme = OrderbookViewThemeFactory.getBlueZodiacTheme(),
            tradesViewTheme = TradesViewThemeFactory.getBlueZodiacTheme(),
            orderbookDetailsViewTheme = OrderbookDetailsViewThemeFactory.getBlueZodiacTheme(),
            dialogTheme = DialogThemeFactory.getBlueZodiacTheme(),
            dottedMapViewTheme = DottedMapViewThemeFactory.getBlueZodiacTheme(),
            navigationDrawerTheme = NavigationDrawerFactory.getBlueZodiacTheme(),
            labeledEditTextTheme = LabeledEditTextFactory.getBlueZodiacTheme(),
            qrCodeScannerTheme = QrCodeScannerThemeFactory.getBlueZodiacTheme(),
            sortPanelTheme = SortPanelThemeFactory.getBlueZodiacTheme(),
            switchTheme = SwitchThemeFactory.getBlueZodiacTheme(),
            popupMenuTheme = PopupMenuThemeFactory.getBlueZodiacTheme(),
            pinEntryKeypadTheme = PinEntryKeypadThemeFactory.getBlueZodiacTheme()
        )
    }


    override fun getBubbleBlueTheme(): Theme {
        return Theme(
            id = Themes.BUBBLE_BLUE.id,
            name = Themes.BUBBLE_BLUE.title,
            generalTheme = GeneralThemeFactory.getBubbleBlueTheme(),
            buttonTheme = ButtonThemeFactory.getBubbleBlueTheme(),
            cardViewTheme = CardViewThemeFactory.getBubbleBlueTheme(),
            switchOptionsViewTheme = SwitchOptionsViewThemeFactory.getBubbleBlueTheme(),
            priceChartViewTheme = PriceChartViewThemeFactory.getBubbleBlueTheme(),
            depthChartViewTheme = DepthChartViewThemeFactory.getBubbleBlueTheme(),
            marketDetailsViewTheme = MarketDetailsViewThemeFactory.getBubbleBlueTheme(),
            orderbookViewTheme = OrderbookViewThemeFactory.getBubbleBlueTheme(),
            tradesViewTheme = TradesViewThemeFactory.getBubbleBlueTheme(),
            orderbookDetailsViewTheme = OrderbookDetailsViewThemeFactory.getBubbleBlueTheme(),
            dialogTheme = DialogThemeFactory.getBubbleBlueTheme(),
            dottedMapViewTheme = DottedMapViewThemeFactory.getBubbleBlueTheme(),
            navigationDrawerTheme = NavigationDrawerFactory.getBubbleBlueTheme(),
            labeledEditTextTheme = LabeledEditTextFactory.getBubbleBlueTheme(),
            qrCodeScannerTheme = QrCodeScannerThemeFactory.getBubbleBlueTheme(),
            sortPanelTheme = SortPanelThemeFactory.getBubbleBlueTheme(),
            switchTheme = SwitchThemeFactory.getBubbleBlueTheme(),
            popupMenuTheme = PopupMenuThemeFactory.getBubbleBlueTheme(),
            pinEntryKeypadTheme = PinEntryKeypadThemeFactory.getBubbleBlueTheme()
        )
    }


}