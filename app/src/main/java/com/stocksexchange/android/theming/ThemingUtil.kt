package com.stocksexchange.android.theming

import android.content.res.ColorStateList
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SwitchCompat
import androidx.browser.customtabs.CustomTabsIntent
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.drawerlayout.widget.DrawerLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.stocksexchange.android.R
import com.stocksexchange.android.theming.model.*
import com.stocksexchange.android.ui.drawer.NavigationDrawer
import com.stocksexchange.android.ui.utils.extensions.*
import com.stocksexchange.android.ui.views.*
import com.stocksexchange.android.ui.views.Toolbar
import com.stocksexchange.android.ui.views.dialogs.CredentialsInputDialog
import com.stocksexchange.android.ui.views.dialogs.DeviceMetricsDialog
import com.stocksexchange.android.ui.views.dialogs.FingerprintDialog
import com.stocksexchange.android.ui.views.dialogs.QrDialog
import com.stocksexchange.android.ui.views.detailsviews.MarketDetailsView
import com.stocksexchange.android.ui.views.marketpreview.DepthChartView
import com.stocksexchange.android.ui.views.marketpreview.PriceChartView
import com.stocksexchange.android.ui.views.marketpreview.orderbook.OrderbookView
import com.stocksexchange.android.ui.views.popupmenu.PopupMenu
import com.stocksexchange.android.ui.views.marketpreview.trades.TradesView
import com.stocksexchange.android.ui.views.detailsviews.OrderbookDetailsView
import com.stocksexchange.android.ui.views.login.LoginCredentialsView
import com.stocksexchange.android.ui.views.login.confirmationviews.base.BaseLoginConfirmationView

/**
 * A utility class used for theming different views and widgets.
 */
object ThemingUtil {


    object Main {


        fun toolbar(toolbar: Toolbar, theme: Theme) {
            toolbar(toolbar, theme.generalTheme)
        }


        fun toolbar(toolbar: Toolbar, theme: GeneralTheme) {
            toolbar.setBackgroundColor(theme.primaryColor)
            toolbar.setButtonDrawableColor(theme.primaryTextColor)
            toolbar.setTitleColor(theme.primaryTextColor)
            toolbar.setProgressBarColor(theme.progressBarColor)
        }


        fun toolbarBackground(view: View, theme: Theme) {
            toolbarBackground(view, theme.generalTheme)
        }


        fun toolbarBackground(view: View, theme: GeneralTheme) {
            view.setBackgroundColor(theme.primaryColor)
        }


        fun toolbarTitle(textView: TextView, theme: Theme) {
            toolbarTitle(textView, theme.generalTheme)
        }


        fun toolbarTitle(textView: TextView, theme: GeneralTheme) {
            textView.setTextColor(theme.primaryTextColor)
        }


        fun toolbarIcon(imageView: ImageView, theme: Theme) {
            toolbarIcon(imageView, theme.generalTheme)
        }


        fun toolbarIcon(imageView: ImageView, theme: GeneralTheme) {
            imageView.setColor(theme.primaryTextColor)
        }


        fun searchToolbar(searchToolbar: SearchToolbar, theme: Theme) {
            searchToolbar(searchToolbar, theme.generalTheme)
        }


        fun searchToolbar(searchToolbar: SearchToolbar, theme: GeneralTheme) {
            searchToolbar.setBackgroundColor(theme.primaryColor)
            searchToolbar.setButtonDrawableColor(theme.primaryTextColor)
            searchToolbar.setHintColor(theme.primaryTextColor)
            searchToolbar.setTextColor(theme.primaryTextColor)
            searchToolbar.setProgressBarColor(theme.progressBarColor)
            searchToolbar.setCursorDrawable(searchToolbar.context.getCursorDrawable(theme.primaryTextColor))
        }


        fun contentContainer(view: View, theme: Theme) {
            contentContainer(view, theme.generalTheme)
        }


        fun contentContainer(view: View, theme: GeneralTheme) {
            view.setBackgroundColor(theme.contentContainerColor)
        }


        fun lightContentContainer(view: View, theme: Theme) {
            lightContentContainer(view, theme.generalTheme)
        }


        fun lightContentContainer(view: View, theme: GeneralTheme) {
            view.setBackgroundColor(theme.contentContainerLightColor)
        }


    }


    object Text {


        fun primary(textView: TextView, theme: Theme) {
            primary(textView, theme.generalTheme)
        }


        fun primary(textView: TextView, theme: GeneralTheme) {
            textView.setTextColor(theme.primaryTextColor)
        }


        fun primaryDark(textView: TextView, theme: Theme) {
            primaryDark(textView, theme.generalTheme)
        }


        fun primaryDark(textView: TextView, theme: GeneralTheme) {
            textView.setTextColor(theme.primaryDarkTextColor)
        }


        fun secondary(textView: TextView, theme: Theme) {
            secondary(textView, theme.generalTheme)
        }


        fun secondary(textView: TextView, theme: GeneralTheme) {
            textView.setTextColor(theme.secondaryTextColor)
        }


        fun secondaryDark(textView: TextView, theme: Theme) {
            secondaryDark(textView, theme.generalTheme)
        }


        fun secondaryDark(textView: TextView, theme: GeneralTheme) {
            textView.setTextColor(theme.secondaryDarkTextColor)
        }


    }


    object Buttons {


        fun primaryButton(button: Button, theme: Theme) {
            primaryButton(button, theme.buttonTheme)
        }


        fun primaryButton(button: Button, theme: ButtonTheme) {
            button.toPrimaryButton(
                theme.contentColor,
                theme.pressedStateBackgroundColor,
                theme.releasedStateBackgroundColor
            )
        }


        fun actionButton(floatingActionButton: FloatingActionButton, theme: Theme) {
            actionButton(floatingActionButton, theme.generalTheme)
        }


        fun actionButton(floatingActionButton: FloatingActionButton, theme: GeneralTheme) {
            with(floatingActionButton) {
                backgroundTintList = ColorStateList.valueOf(theme.accentColor)
                setColor(theme.primaryTextColor)
            }
        }


    }


    object CardItem {


        fun cardView(cardView: CardView, theme: Theme) {
            cardView(cardView, theme.cardViewTheme)
        }


        fun cardView(cardView: CardView, theme: CardViewTheme) {
            cardView.setCardBackgroundColor(theme.backgroundColor)
        }


        fun primaryText(textView: TextView, theme: Theme) {
            primaryText(textView, theme.cardViewTheme)
        }


        fun primaryText(textView: TextView, theme: CardViewTheme) {
            textView.setTextColor(theme.primaryTextColor)
        }


        fun primaryDarkText(textView: TextView, theme: Theme) {
            primaryDarkText(textView, theme.cardViewTheme)
        }


        fun primaryDarkText(textView: TextView, theme: CardViewTheme) {
            textView.setTextColor(theme.primaryDarkTextColor)
        }


        fun icon(imageView: ImageView, theme: Theme) {
            icon(imageView, theme.cardViewTheme)
        }


        fun icon(imageView: ImageView, theme: CardViewTheme) {
            imageView.setColor(theme.primaryTextColor)
        }


        fun positiveButton(textView: TextView, theme: Theme) {
            positiveButton(textView, theme.cardViewTheme)
        }


        fun positiveButton(textView: TextView, theme: CardViewTheme) {
            textView.toSecondaryButton(
                textView.context.getCompatColor(R.color.colorGreenAccent),
                theme.backgroundColor
            )
        }


        fun negativeButton(textView: TextView, theme: Theme) {
            negativeButton(textView, theme.cardViewTheme)
        }


        fun negativeButton(textView: TextView, theme: CardViewTheme) {
            textView.toSecondaryButton(
                textView.context.getCompatColor(R.color.colorRedAccent),
                theme.backgroundColor
            )
        }


        fun neutralButton(textView: TextView, theme: Theme) {
            neutralButton(textView, theme.cardViewTheme)
        }


        fun neutralButton(textView: TextView, theme: CardViewTheme) {
            textView.toSecondaryButton(
                textView.context.getCompatColor(R.color.colorBlueAccent),
                theme.backgroundColor
            )
        }



        fun editText(editText: EditText, theme: Theme) {
            editText(editText, theme.cardViewTheme)
        }


        fun editText(editText: EditText, theme: CardViewTheme) {
            with(editText) {
                setHintTextColor(theme.primaryTextColor)
                setTextColor(theme.primaryTextColor)
                setCursorDrawable(context.getCursorDrawable(theme.primaryTextColor))
            }
        }


    }


    object Dialogs {


        fun dialogBuilder(materialDialogBuilder: MaterialDialog.Builder, theme: Theme) {
            dialogBuilder(materialDialogBuilder, theme.dialogTheme)
        }


        fun dialogBuilder(materialDialogBuilder: MaterialDialog.Builder, theme: DialogTheme) {
            with(materialDialogBuilder) {
                titleColor(theme.titleColor)
                contentColor(theme.textColor)
                itemsColor(theme.textColor)
                positiveColor(theme.buttonColor)
                negativeColor(theme.buttonColor)
                backgroundColor(theme.backgroundColor)
            }
        }


        fun deviceMetricsDialog(dialog: DeviceMetricsDialog, theme: Theme) {
            dialog.setBackgroundColor(theme.dialogTheme.backgroundColor)
            dialog.setTitlesColor(theme.dialogTheme.titleColor)
            dialog.setValuesColor(theme.generalTheme.accentColor)
        }


        fun qrDialog(qrDialog: QrDialog, theme: Theme) {
            qrDialog.setBackgroundColor(theme.dialogTheme.backgroundColor)
            qrDialog.setTitleColor(theme.dialogTheme.titleColor)
            qrDialog.setHashColor(theme.dialogTheme.textColor)
            qrDialog.setButtonsBackgroundColor(
                theme.generalTheme.darkAccentColor,
                theme.generalTheme.accentColor
            )
        }


        fun fingerprintDialog(dialog: FingerprintDialog, theme: Theme) {
            with(dialog) {
                setBackgroundColor(theme.dialogTheme.backgroundColor)
                setTextColor(theme.dialogTheme.textColor)
                setStatusTextColor(theme.generalTheme.secondaryTextColor)
                setButtonTextColor(theme.generalTheme.accentColor)
            }
        }


        fun credentialsInputDialog(dialog: CredentialsInputDialog, theme: Theme) {
            with(dialog) {
                setBackgroundColor(theme.dialogTheme.backgroundColor)
                setTextColor(theme.dialogTheme.textColor)
                setEditTextBackgroundColor(theme.generalTheme.primaryTextColor)
                setEditTextHintColor(theme.generalTheme.primaryDarkTextColor)
                setPublicKeyEtCursorDrawable(dialog.context.getCursorDrawable(theme.generalTheme.primaryTextColor))
                setSecretKeyEtCursorDrawable(dialog.context.getCursorDrawable(theme.generalTheme.primaryTextColor))
                setButtonTextColor(theme.generalTheme.accentColor)
            }
        }


    }


    object DottedMap {


        fun title(textView: TextView, theme: Theme) {
            title(textView, theme.dottedMapViewTheme)
        }


        fun title(textView: TextView, theme: DottedMapViewTheme) {
            textView.setTextColor(theme.titleColor)
        }


        fun view(view: DottedMapView, theme: Theme) {
            view(view, theme.dottedMapViewTheme)
        }


        fun view(view: DottedMapView, theme: DottedMapViewTheme) {
            view.setTitleColor(theme.textColor)
            view.setSeparatorColor(theme.textColor)
            view.setValueColor(theme.textColor)
        }


    }


    object TabBar {


        fun tabLayout(tabLayout: TabLayout, theme: Theme) {
            tabLayout(tabLayout, theme.generalTheme)
        }


        fun tabLayout(tabLayout: TabLayout, theme: GeneralTheme) {
            with(tabLayout) {
                setBackgroundColor(theme.primaryColor)
                setTabTextColors(theme.primaryTextColor, theme.primaryTextColor)
                setSelectedTabIndicatorColor(theme.tabIndicatorColor)
            }
        }


        fun tab(customTab: CustomTab, theme: Theme) {
            tab(customTab, theme.generalTheme)
        }


        fun tab(customTab: CustomTab, theme: GeneralTheme) {
            customTab.setTitleColor(theme.primaryTextColor)
        }


        fun tabsBackground(view: View, theme: Theme) {
            tabsBackground(view, theme.generalTheme)
        }


        fun tabsBackground(view: View, theme: GeneralTheme) {
            view.setBackgroundColor(theme.primaryColor)
        }


        fun tabText(view: View, theme: Theme) {
            tabText(view, theme.generalTheme)
        }


        fun tabText(view: View, theme: GeneralTheme) {
            view.setBackgroundColor(theme.primaryTextColor)
        }


        fun tabIndicator(view: View, theme: Theme) {
            tabIndicator(view, theme.generalTheme)
        }


        fun tabIndicator(view: View, theme: GeneralTheme) {
            view.setBackgroundColor(theme.tabIndicatorColor)
        }


    }


    object SortPanel {


        fun background(view: View, theme: Theme) {
            background(view, theme.sortPanelTheme)
        }


        fun background(view: View, theme: SortPanelTheme) {
            view.setBackgroundColor(theme.backgroundColor)
        }


        fun selectedTitle(textView: TextView, theme: Theme) {
            selectedTitle(textView, theme.sortPanelTheme)
        }


        fun selectedTitle(textView: TextView, theme: SortPanelTheme) {
            textView.setTextColor(theme.selectedTitleColor)
        }


        fun unselectedTitle(textView: TextView, theme: Theme) {
            unselectedTitle(textView, theme.sortPanelTheme)
        }


        fun unselectedTitle(textView: TextView, theme: SortPanelTheme) {
            textView.setTextColor(theme.unselectedTitleColor)
        }


        fun separator(textView: TextView, theme: Theme) {
            separator(textView, theme.generalTheme)
        }


        fun separator(textView: TextView, theme: GeneralTheme) {
            Text.primary(textView, theme)
        }


    }


    object InfoView {


        fun icon(imageView: ImageView, theme: Theme) {
            icon(imageView, theme.generalTheme)
        }


        fun icon(imageView: ImageView, theme: GeneralTheme) {
            imageView.setColor(theme.infoViewColor)
        }


        fun caption(textView: TextView, theme: Theme) {
            caption(textView, theme.generalTheme)
        }


        fun caption(textView: TextView, theme: GeneralTheme) {
            textView.setTextColor(theme.infoViewColor)
        }


    }


    object Popup {


        fun menu(popupMenu: PopupMenu, theme: Theme) {
            menu(popupMenu, theme.popupMenuTheme)
        }


        fun menu(popupMenu: PopupMenu, theme: PopupMenuTheme) {
            popupMenu.setBackgroundColor(theme.backgroundColor)
        }


        object Item {


            fun title(textView: TextView, theme: Theme) {
                title(textView, theme.popupMenuTheme)
            }


            fun title(textView: TextView, theme: PopupMenuTheme) {
                textView.setTextColor(theme.titleColor)
            }


            fun separator(imageView: ImageView, theme: Theme) {
                separator(imageView, theme.popupMenuTheme)
            }


            fun separator(imageView: ImageView, theme: PopupMenuTheme) {
                imageView.setImageDrawable(imageView.context.getDottedLineDrawable(theme.titleColor))
            }


        }


    }


    object Authentication {


        fun contentContainer(contentContainerRl: RelativeLayout, theme: Theme) {
            contentContainer(contentContainerRl, theme.generalTheme)
        }


        fun contentContainer(contentContainerRl: RelativeLayout, theme: GeneralTheme) {
            contentContainerRl.background = contentContainerRl.context.getPortraitParticlesDrawable(
                theme.gradientStartColor,
                theme.gradientEndColor
            )
        }


        fun message(textView: TextView, theme: Theme) {
            Text.secondary(textView, theme)
        }


        fun emptyPinBox(imageView: ImageView, theme: Theme) {
            emptyPinBox(imageView, theme.generalTheme)
        }


        fun emptyPinBox(imageView: ImageView, theme: GeneralTheme) {
            imageView.setImageDrawable(imageView.context.getPinBoxBorderDrawable(theme.accentColor))
        }


        fun filledPinBox(imageView: ImageView, theme: Theme) {
            filledPinBox(imageView, theme.generalTheme)

        }


        fun filledPinBox(imageView: ImageView, theme: GeneralTheme) {
            imageView.setImageDrawable(imageView.context.getPinBoxSolidDrawable(theme.accentColor))
        }


        fun pinEntryKeypad(pinEntryKeypad: PinEntryKeypad, theme: Theme) {
            apply(pinEntryKeypad, theme)
        }


        fun dialogBuilder(materialDialogBuilder: MaterialDialog.Builder, theme: Theme) {
            Dialogs.dialogBuilder(materialDialogBuilder, theme)
        }


    }


    object Dashboard {


        fun drawerLayout(drawerLayout: DrawerLayout, theme: Theme) {
            drawerLayout(drawerLayout, theme.generalTheme)
        }


        fun drawerLayout(drawerLayout: DrawerLayout, theme: GeneralTheme) {
            drawerLayout.setStatusBarBackgroundColor(theme.primaryDarkColor)
        }


        fun appBarLayout(appBarLayout: AppBarLayout, theme: Theme) {
            appBarLayout(appBarLayout, theme.generalTheme)
        }


        fun appBarLayout(appBarLayout: AppBarLayout, theme: GeneralTheme) {
            appBarLayout.setBackgroundColor(theme.primaryColor)
        }


        fun toolbarBackground(view: View, theme: Theme) {
            Main.toolbarBackground(view, theme)
        }


        fun toolbarTitle(textView: TextView, theme: Theme) {
            Main.toolbarTitle(textView, theme)
        }


        fun toolbarIcon(imageView: ImageView, theme: Theme) {
            Main.toolbarIcon(imageView, theme)
        }


        fun sortPanel(sortPanel: CurrencyMarketsSortPanel, theme: Theme) {
            SortPanel.background(sortPanel, theme)
        }


        fun drawerToggle(drawerToggle: ActionBarDrawerToggle, theme: Theme) {
            drawerToggle(drawerToggle, theme.generalTheme)
        }


        fun drawerToggle(drawerToggle: ActionBarDrawerToggle, theme: GeneralTheme) {
            drawerToggle.drawerArrowDrawable.color = theme.primaryTextColor
        }


        fun navigationDrawer(navigationDrawer: NavigationDrawer, theme: Theme) {
            Drawer.navigationDrawer(navigationDrawer, theme)
        }


    }


    object Drawer {


        fun navigationDrawer(drawer: NavigationDrawer, theme: Theme) {
            navigationDrawer(drawer, theme.navigationDrawerTheme)
        }


        fun navigationDrawer(drawer: NavigationDrawer, theme: NavigationDrawerTheme) {
            drawer.setBackgroundColor(theme.backgroundColor)
        }


        object Header {


            fun background(view: View, theme: Theme) {
                background(view, theme.generalTheme)
            }


            fun background(view: View, theme: GeneralTheme) {
                view.background = view.context.getLandscapeParticlesDrawable(
                    theme.gradientStartColor,
                    theme.gradientEndColor
                )
            }


            fun profileImage(markableImageView: MarkableImageView, theme: Theme) {
                profileImage(markableImageView, theme.generalTheme)
            }


            fun profileImage(markableImageView: MarkableImageView, theme: GeneralTheme) {
                with(markableImageView) {
                    setOuterBackgroundColor(theme.accentColor)
                    setInnerBackgroundColor(theme.accentColor)
                }
            }


            fun userName(textView: TextView, theme: Theme) {
                Text.primary(textView, theme)
            }


            fun email(textView: TextView, theme: Theme) {
                Text.primary(textView, theme)
            }


            fun signInButton(button: Button, theme: Theme) {
                signInButton(button, theme.navigationDrawerTheme)
            }


            fun signInButton(button: Button, theme: NavigationDrawerTheme) {
                button.toPrimaryButton(
                    theme.signInButtonContentColor,
                    theme.signInButtonPressedStateBackgroundColor,
                    theme.signInButtonReleasedStateBackgroundColor
                )
            }


        }


        object Item {


            fun icon(imageView: ImageView, theme: Theme) {
                icon(imageView, theme.navigationDrawerTheme)
            }


            fun icon(imageView: ImageView, theme: NavigationDrawerTheme) {
                imageView.setColor(theme.itemColor)
            }


            fun text(textView: TextView, theme: Theme) {
                text(textView, theme.navigationDrawerTheme)
            }


            fun text(textView: TextView, theme: NavigationDrawerTheme) {
                textView.setTextColor(theme.itemColor)
            }


            fun separator(imageView: ImageView, theme: Theme) {
                separator(imageView, theme.navigationDrawerTheme)
            }


            fun separator(imageView: ImageView, theme: NavigationDrawerTheme) {
                imageView.setImageDrawable(imageView.context.getDottedLineDrawable(theme.itemColor))
            }


        }


    }


    object Login {


        fun contentContainer(contentContainerLl: ConstraintLayout, theme: Theme) {
            contentContainer(contentContainerLl, theme.generalTheme)
        }


        fun contentContainer(contentContainerLl: ConstraintLayout, theme: GeneralTheme) {
            contentContainerLl.background = contentContainerLl.context.getPortraitParticlesDrawable(
                theme.gradientStartColor,
                theme.gradientEndColor
            )
        }


        fun title(textView: TextView, theme: Theme) {
            Text.primary(textView, theme)
        }


        fun motto(textView: TextView, theme: Theme) {
            Text.primary(textView, theme)
        }


        fun credentialsView(credentialsView: LoginCredentialsView, theme: Theme) {
            credentialsView(credentialsView, theme.generalTheme)
        }


        fun credentialsView(credentialsView: LoginCredentialsView, theme: GeneralTheme) {
            with(credentialsView) {
                val editTextHintTextColor = context.getCompatColor(R.color.colorLoginEditTextHintColor)

                setInputViewTextColor(theme.primaryTextColor)
                setInputViewHintTextColor(editTextHintTextColor)
                setInputViewCursorColor(theme.primaryTextColor)
                setHelpButtonTextColor(theme.primaryTextColor)
            }
        }


        fun confirmationView(confirmationView: BaseLoginConfirmationView, theme: Theme) {
            confirmationView(confirmationView, theme.generalTheme)
        }


        fun confirmationView(confirmationView: BaseLoginConfirmationView, theme: GeneralTheme) {
            with(confirmationView) {
                val editTextHintTextColor = context.getCompatColor(R.color.colorLoginEditTextHintColor)

                setHintTextColor(theme.primaryTextColor)
                setHelpButtonTextColor(theme.primaryTextColor)
                setInputViewTextColor(theme.primaryTextColor)
                setInputViewHintTextColor(editTextHintTextColor)
                setInputViewCursorColor(theme.primaryTextColor)
            }
        }


        fun button(button: Button, theme: Theme) {
            button(button, theme.generalTheme)
        }


        fun button(button: Button, theme: GeneralTheme) {
            with(button) {
                setTextColor(theme.primaryTextColor)
                background = context.getLoginButtonDrawable(theme.accentColor)
            }
        }


        fun hint(textView: TextView, theme: Theme) {
            Text.primary(textView, theme)
        }


        fun signInProblems(textView: TextView, theme: Theme) {
            Text.primary(textView, theme)
        }


        fun qrScannerBorder(view: View, theme: Theme) {
            qrScannerBorder(view, theme.qrCodeScannerTheme)
        }


        fun qrScannerBorder(view: View, theme: QrCodeScannerTheme) {
            view.setBackgroundColor(theme.borderColor)
        }


        fun qrScannerTint(view: View, theme: Theme) {
            qrScannerTint(view, theme.qrCodeScannerTheme)
        }


        fun qrScannerTint(view: View, theme: QrCodeScannerTheme) {
            view.setBackgroundColor(theme.tintColor)
        }


        fun dialogBuilder(materialDialogBuilder: MaterialDialog.Builder, theme: Theme) {
            Dialogs.dialogBuilder(materialDialogBuilder, theme)
        }


        fun credentialsInputDialog(dialog: CredentialsInputDialog, theme: Theme) {
            Dialogs.credentialsInputDialog(dialog, theme)
        }


    }


    object Wallets {


        fun toolbar(toolbar: Toolbar, theme: Theme) {
            Main.toolbar(toolbar, theme)
        }


        fun contentContainer(view: View, theme: Theme) {
            Main.contentContainer(view, theme)
        }


        fun dialogBuilder(materialDialogBuilder: MaterialDialog.Builder, theme: Theme) {
            Dialogs.dialogBuilder(materialDialogBuilder, theme)
        }


        object WalletItem {


            fun cardView(cardView: CardView, theme: Theme) {
                CardItem.cardView(cardView, theme)
            }


            fun currencyName(textView: TextView, theme: Theme) {
                currencyName(textView, theme.cardViewTheme)
            }


            fun currencyName(textView: TextView, theme: CardViewTheme) {
                textView.toSecondaryButton(
                    textView.context.getCompatColor(R.color.colorOrangeAccent),
                    theme.backgroundColor
                )
            }


            fun currencyLongName(textView: TextView, theme: Theme) {
                DottedMap.title(textView, theme)
            }


            fun dottedMap(dottedMapView: DottedMapView, theme: Theme) {
                DottedMap.view(dottedMapView, theme)
            }


            fun actionButton(textView: TextView, theme: Theme) {
                actionButton(textView, theme.cardViewTheme)
            }


            fun actionButton(textView: TextView, theme: CardViewTheme) {
                CardItem.neutralButton(textView, theme)
            }


        }


    }


    object Deposit {


        fun toolbar(toolbar: Toolbar, theme: Theme) {
            Main.toolbar(toolbar, theme)
        }


        fun contentContainer(view: View, theme: Theme) {
            Main.toolbarBackground(view, theme)
        }


        fun currency(textView: TextView, theme: Theme) {
            Text.secondary(textView, theme)
        }


        fun borderedMap(borderedMapView: BorderedMapView, theme: Theme) {
            borderedMap(borderedMapView, theme.generalTheme)
        }


        fun borderedMap(borderedMapView: BorderedMapView, theme: GeneralTheme) {
            borderedMapView.setTitleColor(theme.primaryTextColor)
            borderedMapView.setSubtitleColor(theme.accentColor)
            borderedMapView.setBordersColor(theme.secondaryTextColor)
        }


        fun hint(textView: TextView, theme: Theme) {
            Text.secondary(textView, theme)
        }


    }


    object Orders {


        fun toolbar(toolbar: Toolbar, theme: Theme) {
            Main.toolbar(toolbar, theme)
        }


        fun contentContainer(view: View, theme: Theme) {
            Main.contentContainer(view, theme)
        }


        fun dialogBuilder(materialDialogBuilder: MaterialDialog.Builder, theme: Theme) {
            Dialogs.dialogBuilder(materialDialogBuilder, theme)
        }


        object OrderItem {


            fun cardView(cardView: CardView, theme: Theme) {
                CardItem.cardView(cardView, theme)
            }


            fun buyButton(textView: TextView, theme: Theme) {
                CardItem.positiveButton(textView, theme)
            }


            fun sellButton(textView: TextView, theme: Theme) {
                CardItem.negativeButton(textView, theme)
            }


            fun title(textView: TextView, theme: Theme) {
                DottedMap.title(textView, theme)
            }


            fun dottedMap(dottedMapView: DottedMapView, theme: Theme) {
                DottedMap.view(dottedMapView, theme)
            }


            fun cancelButton(textView: TextView, theme: Theme) {
                CardItem.neutralButton(textView, theme)
            }


        }


    }


    object Transactions {


        fun toolbar(toolbar: Toolbar, theme: Theme) {
            Main.toolbar(toolbar, theme)
        }


        fun contentContainer(view: View, theme: Theme) {
            Main.contentContainer(view, theme)
        }


        object TransactionItem {


            fun cardView(cardView: CardView, theme: Theme) {
                CardItem.cardView(cardView, theme)
            }


            fun title(textView: TextView, theme: Theme) {
                DottedMap.title(textView, theme)
            }


            fun dottedMap(dottedMapView: DottedMapView, theme: Theme) {
                DottedMap.view(dottedMapView, theme)
            }


            fun positiveStatusButton(textView: TextView, theme: Theme) {
                CardItem.positiveButton(textView, theme)
            }


            fun negativeStatusButton(textView: TextView, theme: Theme) {
                CardItem.negativeButton(textView, theme)
            }


            fun neutralStatusButton(textView: TextView, theme: Theme) {
                CardItem.neutralButton(textView, theme)
            }


        }


    }


    object Settings {


        fun toolbar(toolbar: Toolbar, theme: Theme) {
            Main.toolbar(toolbar, theme)
        }


        fun contentContainer(view: View, theme: Theme) {
            contentContainer(view, theme.generalTheme)
        }


        fun contentContainer(view: View, theme: GeneralTheme) {
            view.setBackgroundColor(theme.settingsBackgroundColor)
        }


        fun dialogBuilder(materialDialogBuilder: MaterialDialog.Builder, theme: Theme) {
            Dialogs.dialogBuilder(materialDialogBuilder, theme)
        }


        fun deviceMetricsDialog(deviceMetricsDialog: DeviceMetricsDialog, theme: Theme) {
            Dialogs.deviceMetricsDialog(deviceMetricsDialog, theme)
        }


        object SettingSection {


            fun title(textView: TextView, theme: Theme) {
                title(textView, theme.generalTheme)
            }


            fun title(textView: TextView, theme: GeneralTheme) {
                textView.setTextColor(theme.accentColor)
            }


        }


        object SettingItem {


            fun title(textView: TextView, theme: Theme) {
                title(textView, theme.generalTheme)
            }


            fun title(textView: TextView, theme: GeneralTheme) {
                textView.setTextColor(theme.contentContainerTextColor)
            }


            fun description(textView: TextView, theme: Theme) {
                Text.secondary(textView, theme)
            }


            fun switch(switchView: SwitchCompat, theme: Theme) {
                apply(switchView, theme)
            }


        }


    }


    object Themes {


        fun toolbar(toolbar: Toolbar, theme: Theme) {
            Main.toolbar(toolbar, theme)
        }


        fun contentContainer(view: View, theme: Theme) {
            Main.toolbarBackground(view, theme)
        }


        object Item {


            fun progressBar(progressBarIv: ImageView, theme: Theme) {
                progressBar(progressBarIv, theme.generalTheme)
            }


            fun progressBar(progressBarIv: ImageView, theme: GeneralTheme) {
                progressBarIv.setImageDrawable(progressBarIv.context.getColoredCompatDrawable(
                    R.drawable.ic_progress_bar,
                    theme.accentColor
                ))
            }


        }


    }


    object About {


        fun toolbar(toolbar: Toolbar, theme: Theme) {
            Main.toolbar(toolbar, theme)
        }


        fun contentContainer(view: View, theme: Theme) {
            Main.toolbarBackground(view, theme)
        }


        fun appName(textView: TextView, theme: Theme) {
            Text.primary(textView, theme)
        }


        fun appVersion(textView: TextView, theme: Theme) {
            Text.primary(textView, theme)
        }


        fun description(textView: TextView, theme: Theme) {
            Text.primary(textView, theme)
        }


        fun separator(view: View, theme: Theme) {
            separator(view, theme.generalTheme)
        }


        fun separator(view: View, theme: GeneralTheme) {
            view.setBackgroundColor(theme.primaryTextColor)
        }


        fun featureView(featureView: FeatureView, theme: Theme) {
            featureView(featureView, theme.generalTheme)
        }


        fun featureView(featureView: FeatureView, theme: GeneralTheme) {
            featureView.setIconColor(theme.primaryTextColor)
            featureView.setCaptionColor(theme.primaryTextColor)
        }


        fun button(button: Button, theme: Theme) {
            Buttons.primaryButton(button, theme)
        }


    }


    object Feedback {


        fun toolbar(toolbar: Toolbar, theme: Theme) {
            Main.toolbar(toolbar, theme)
        }


        fun contentContainer(view: View, theme: Theme) {
            Main.toolbarBackground(view, theme)
        }


        fun greeting(textView: TextView, theme: Theme) {
            Text.primary(textView, theme)
        }


        fun detailedMessage(textView: TextView, theme: Theme) {
            Text.primary(textView, theme)
        }


        fun cardView(cardView: CardView, theme: Theme) {
            cardView(cardView, theme.generalTheme)
        }


        fun cardView(cardView: CardView, theme: GeneralTheme) {
            cardView.setCardBackgroundColor(theme.feedbackCardViewColor)
        }


        fun feedbackTitle(textView: TextView, theme: Theme) {
            CardItem.primaryText(textView, theme)
        }


        fun button(textView: TextView, theme: Theme) {
            button(textView, theme.generalTheme)
        }


        fun button(textView: TextView, theme: GeneralTheme) {
            textView.toSecondaryButton(
                theme.feedbackFooterButtonColor,
                theme.feedbackCardViewColor
            )
        }


        fun dottedSeparator(imageView: ImageView, theme: Theme) {
            dottedSeparator(imageView, theme.generalTheme)
        }


        fun dottedSeparator(imageView: ImageView, theme: GeneralTheme) {
            imageView.setImageDrawable(imageView.context.getDottedLineDrawable(theme.secondaryTextColor))
        }


        fun editText(editText: EditText, theme: Theme) {
            CardItem.editText(editText, theme)
        }


    }


    object Help {


        fun toolbar(toolbar: Toolbar, theme: Theme) {
            Main.toolbar(toolbar, theme)
        }


        fun contentContainer(view: View, theme: Theme) {
            Main.contentContainer(view, theme)
        }


        fun dialogBuilder(materialDialogBuilder: MaterialDialog.Builder, theme: Theme) {
            Dialogs.dialogBuilder(materialDialogBuilder, theme)
        }


        object HelpItem {


            fun cardView(cardView: CardView, theme: Theme) {
                CardItem.cardView(cardView, theme)
            }


            fun question(textView: TextView, theme: Theme) {
                CardItem.primaryText(textView, theme)
            }


            fun answer(textView: TextView, theme: Theme) {
                CardItem.primaryText(textView, theme)
            }


            fun arrowIcon(imageView: ImageView, theme: Theme) {
                CardItem.icon(imageView, theme)
            }


            fun actionButton(textView: TextView, theme: Theme) {
                textView.toSecondaryButton(
                    theme.generalTheme.accentColor,
                    theme.cardViewTheme.backgroundColor
                )
            }


        }


    }


    object CurrencyMarkets {


        fun contentContainer(view: View, theme: Theme) {
            Main.contentContainer(view, theme)
        }


        object Item {


            fun cardView(cardView: CardView, theme: Theme) {
                CardItem.cardView(cardView, theme)
            }


            fun positiveStatusBar(view: View, theme: Theme) {
                view.setBackgroundColor(view.context.getCompatColor(R.color.colorGreenAccent))
            }


            fun negativeStatusBar(view: View, theme: Theme) {
                view.setBackgroundColor(view.context.getCompatColor(R.color.colorRedAccent))

            }


            fun neutralStatusBar(view: View, theme: Theme) {
                view.setBackgroundColor(view.context.getCompatColor(R.color.colorBlueAccent))

            }


            fun baseCurrencySymbol(textView: TextView, theme: Theme) {
                CardItem.primaryText(textView, theme)
            }


            fun separator(textView: TextView, theme: Theme) {
                CardItem.primaryDarkText(textView, theme)
            }


            fun quoteCurrencySymbol(textView: TextView, theme: Theme) {
                CardItem.primaryDarkText(textView, theme)
            }


            fun volume(textView: TextView, theme: Theme) {
                CardItem.primaryText(textView, theme)
            }


            fun lastPrice(textView: TextView, theme: Theme) {
                CardItem.primaryText(textView, theme)
            }


            fun positiveDailyPriceChange(textView: TextView, theme: Theme) {
                textView.background = textView.context.getDailyChangeRoundedDrawable(R.color.colorGreenAccent)
            }


            fun negativeDailyPriceChange(textView: TextView, theme: Theme) {
                textView.background = textView.context.getDailyChangeRoundedDrawable(R.color.colorRedAccent)
            }


            fun neutralDailyPriceChange(textView: TextView, theme: Theme) {
                textView.background = textView.context.getDailyChangeRoundedDrawable(R.color.colorBlueAccent)
            }


        }


    }


    object Favorites {


        fun contentContainer(view: View, theme: Theme) {
            Main.contentContainer(view, theme)
        }


        fun toolbar(toolbar: Toolbar, theme: Theme) {
            Main.toolbar(toolbar, theme)
        }


        fun sortPanel(sortPanel: CurrencyMarketsSortPanel, theme: Theme) {
            SortPanel.background(sortPanel, theme)
        }


    }


    object CurrencyMarketsSearch {


        fun sortPanel(sortPanel: CurrencyMarketsSortPanel, theme: Theme) {
            SortPanel.background(sortPanel, theme)
        }


    }


    object CurrencyMarketPreview {


        fun toolbar(toolbar: Toolbar, theme: Theme) {
            Main.toolbar(toolbar, theme)
        }


        fun favoriteButton(imageView: ImageView, theme: Theme) {
            imageView.setColor(imageView.context.getCompatColor(R.color.colorYellowAccent))
        }


        fun unfavoriteButton(imageView: ImageView, theme: Theme) {
            Main.toolbarIcon(imageView, theme)
        }


        fun scrollView(scrollView: LockableScrollView, theme: Theme) {
            Main.lightContentContainer(scrollView, theme)
        }


        fun topPanel(view: View, theme: Theme) {
            topPanel(view, theme.generalTheme)
        }


        fun topPanel(view: View, theme: GeneralTheme) {
            view.setBackgroundColor(theme.primaryColor)
        }


        fun positivePriceField(textView: TextView, theme: Theme) {
            textView.setTextColor(textView.context.getCompatColor(R.color.colorGreenAccent))
        }


        fun negativePriceField(textView: TextView, theme: Theme) {
            textView.setTextColor(textView.context.getCompatColor(R.color.colorRedAccent))
        }


        fun neutralPriceField(textView: TextView, theme: Theme) {
            textView.setTextColor(textView.context.getCompatColor(R.color.colorBlueAccent))
        }


        fun chartTypes(chartTypesSov: SwitchOptionsView, theme: Theme) {
            apply(chartTypesSov, theme)
        }


        fun priceChartView(priceChartView: PriceChartView, theme: Theme) {
            apply(priceChartView, theme)
        }


        fun depthChartView(depthChartView: DepthChartView, theme: Theme) {
            apply(depthChartView, theme)
        }


        fun marketInfoTitle(textView: TextView, theme: Theme) {
            Text.secondary(textView, theme)
        }


        fun marketDetailsView(marketDetailsView: MarketDetailsView, theme: Theme) {
            apply(marketDetailsView, theme)
        }


        fun tradeInfo(tradeInfoSov: SwitchOptionsView, theme: Theme) {
            apply(tradeInfoSov, theme)
        }


        fun orderbookView(orderbookView: OrderbookView, theme: Theme) {
            apply(orderbookView, theme)
        }


        fun tradesView(tradesView: TradesView, theme: Theme) {
            apply(tradesView, theme)
        }


        fun contentContainer(view: View, theme: Theme) {
            Main.contentContainer(view, theme)
        }


        fun bottomBar(view: View, theme: Theme) {
            bottomBar(view, theme.generalTheme)
        }


        fun bottomBar(view: View, theme: GeneralTheme) {
            view.setBackgroundColor(theme.primaryLightColor)
        }


        fun buyButton(button: Button, theme: Theme) {
            val context = button.context

            button.toPrimaryButton(
                theme.generalTheme.primaryTextColor,
                context.getCompatColor(R.color.colorGreenAccentDark),
                context.getCompatColor(R.color.colorGreenAccent)
            )
        }


        fun sellButton(button: Button, theme: Theme) {
            val context = button.context

            button.toPrimaryButton(
                theme.generalTheme.primaryTextColor,
                context.getCompatColor(R.color.colorRedAccentDark),
                context.getCompatColor(R.color.colorRedAccent)
            )
        }


    }


    object Orderbook {


        fun contentContainer(view: View, theme: Theme) {
            Main.lightContentContainer(view, theme)
        }


        fun appBarLayout(appBarLayout: AppBarLayout, theme: Theme) {
            Main.lightContentContainer(appBarLayout, theme)
        }


        fun toolbar(toolbar: Toolbar, theme: Theme) {
            Main.toolbar(toolbar, theme)
        }


        fun orderbookDetailsView(orderbookDetailsView: OrderbookDetailsView, theme: Theme) {
            apply(orderbookDetailsView, theme)
        }


        fun orderbookViewTitle(textView: TextView, theme: Theme) {
            Text.secondary(textView, theme)
        }


        fun orderbookView(orderbookView: OrderbookView, theme: Theme) {
            apply(orderbookView, theme)
        }


    }


    object Trade {


        fun contentContainer(view: View, theme: Theme) {
            Main.toolbarBackground(view, theme)
        }


        fun toolbar(toolbar: Toolbar, theme: Theme) {
            Main.toolbar(toolbar, theme)
        }


        fun userBalance(textView: TextView, theme: Theme) {
            userBalance(textView, theme.generalTheme)
        }


        fun userBalance(textView: TextView, theme: GeneralTheme) {
            textView.setTextColor(theme.secondaryTextColor)
        }


        fun borderedMap(borderedMapView: BorderedMapView, theme: Theme) {
            borderedMap(borderedMapView, theme.generalTheme)
        }


        fun borderedMap(borderedMapView: BorderedMapView, theme: GeneralTheme) {
            borderedMapView.setTitleColor(theme.primaryTextColor)
            borderedMapView.setSubtitleColor(theme.primaryTextColor)
            borderedMapView.setBordersColor(theme.secondaryTextColor)
        }


        fun labeledEditText(labeledEditText: LabeledEditText, theme: Theme) {
            labeledEditText(labeledEditText, theme.labeledEditTextTheme)
        }


        fun labeledEditText(labeledEditText: LabeledEditText, theme: LabeledEditTextTheme) {
            with(labeledEditText) {
                setTitleColor(theme.titleColor)
                setInputContainerColor(theme.containerColor)
                setInputHintColor(theme.hintColor)
                setInputTextColor(theme.textColor)
                setInputCursorDrawable(context.getCursorDrawable(theme.cursorColor))
                setLabelColor(theme.labelColor)
            }
        }


        fun button(button: Button, theme: Theme) {
            button(button, theme.generalTheme)
        }


        fun button(button: Button, theme: GeneralTheme) {
            button.setTextColor(theme.primaryTextColor)
        }


    }


    fun apply(progressBar: ProgressBar, theme: Theme) {
        apply(progressBar, theme.generalTheme)
    }


    fun apply(progressBar: ProgressBar, theme: GeneralTheme) {
        progressBar.setColor(theme.progressBarColor)
    }


    fun apply(swipeRefreshLayout: SwipeRefreshLayout, theme: Theme) {
        apply(swipeRefreshLayout, theme.generalTheme)
    }


    fun apply(swipeRefreshLayout: SwipeRefreshLayout, theme: GeneralTheme) {
        swipeRefreshLayout.setColorSchemeColors(theme.accentColor)
    }


    fun apply(customTabsBuilder: CustomTabsIntent.Builder, theme: Theme) {
        apply(customTabsBuilder, theme.generalTheme)
    }


    fun apply(customTabsBuilder: CustomTabsIntent.Builder, theme: GeneralTheme) {
        customTabsBuilder.setToolbarColor(theme.primaryColor)
        customTabsBuilder.setSecondaryToolbarColor(theme.primaryDarkColor)
    }


    fun apply(featureView: FeatureView, theme: Theme) {
        apply(featureView, theme.generalTheme)
    }


    fun apply(featureView: FeatureView, theme: GeneralTheme) {
        with(featureView) {
            setIconColor(theme.primaryTextColor)
            setCaptionColor(theme.primaryTextColor)
        }
    }


    fun apply(switchView: SwitchCompat, theme: Theme) {
        apply(switchView, theme.switchTheme)
    }


    fun apply(switchView: SwitchCompat, theme: SwitchTheme) {
        switchView.setColors(
            theme.pointerDeactivatedColor,
            theme.pointerActivatedColor,
            theme.backgroundDeactivatedColor,
            theme.backgroundActivatedColor
        )
    }


    fun apply(switchOptionsView: SwitchOptionsView, theme: Theme) {
        apply(switchOptionsView, theme.switchOptionsViewTheme)
    }


    fun apply(switchOptionsView: SwitchOptionsView, theme: SwitchOptionsViewTheme) {
        with(switchOptionsView) {
            setOptionsTitleTextColor(theme.titleTextColor)
            setSwitchColor(theme.switchColor)
        }
    }


    fun apply(priceChartView: PriceChartView, theme: Theme) {
        apply(priceChartView, theme.priceChartViewTheme)
    }


    fun apply(priceChartView: PriceChartView, theme: PriceChartViewTheme) {
        with(priceChartView) {
            setBackgroundColor(theme.backgroundColor)
            setProgressBarColor(theme.progressBarColor)
            setInfoViewColor(theme.infoViewColor)
            setChartInfoFieldsDefaultTextColor(theme.infoFieldsDefaultTextColor)
            setChartAxisGridColor(theme.axisGridColor)
            setChartHighlighterColor(theme.highlighterColor)
            setChartPositiveColor(theme.positiveColor)
            setChartNegativeColor(theme.negativeColor)
            setChartNeutralColor(theme.neutralColor)
            setChartVolumeBarColor(theme.volumeBarsColor)
            setChartCandleStickShadowColor(theme.candleStickShadowColor)
            setTabTextColor(theme.tabTextColor)
            setTabIndicatorColor(theme.tabIndicatorColor)
        }
    }


    fun apply(depthChartView: DepthChartView, theme: Theme) {
        apply(depthChartView, theme.depthChartViewTheme)
    }


    fun apply(depthChartView: DepthChartView, theme: DepthChartViewTheme) {
        with(depthChartView) {
            setBackgroundColor(theme.backgroundColor)
            setProgressBarColor(theme.progressBarColor)
            setInfoViewColor(theme.infoViewColor)
            setChartInfoFieldsDefaultTextColor(theme.infoFieldsDefaultTextColor)
            setChartAxisGridColor(theme.axisGridColor)
            setChartHighlighterColor(theme.highlighterColor)
            setChartPositiveColor(theme.positiveColor)
            setChartNegativeColor(theme.negativeColor)
            setChartNeutralColor(theme.neutralColor)
            setTabTextColor(theme.tabTextColor)
            setTabIndicatorColor(theme.tabIndicatorColor)
        }
    }


    fun apply(marketDetailsView: MarketDetailsView, theme: Theme) {
        apply(marketDetailsView, theme.marketDetailsViewTheme)
    }


    fun apply(marketDetailsView: MarketDetailsView, theme: MarketDetailsViewTheme) {
        with(marketDetailsView) {
            setBackgroundColor(theme.backgroundColor)
            setItemTitleColor(theme.itemTitleColor)
            setItemValueColor(theme.itemValueColor)
            setItemSeparatorColor(theme.itemSeparatorColor)
            setPositiveStatusColor(theme.positiveStatusColor)
            setNegativeStatusColor(theme.negativeStatusColor)
        }
    }


    fun apply(orderbookDetailsView: OrderbookDetailsView, theme: Theme) {
        apply(orderbookDetailsView, theme.orderbookDetailsViewTheme)
    }


    fun apply(orderbookDetailsView: OrderbookDetailsView, theme: OrderbookDetailsViewTheme) {
        with(orderbookDetailsView) {
            setBackgroundColor(theme.backgroundColor)
            setItemTitleColor(theme.itemTitleColor)
            setItemValueColor(theme.itemValueColor)
            setItemSeparatorColor(theme.itemSeparatorColor)
            setHighestBidValueColor(theme.highestBidValueColor)
            setLowestAskValueColor(theme.lowestAskValueColor)
            setProgressBarColor(theme.progressBarColor)
            setInfoViewCaptionColor(theme.infoViewCaptionColor)
        }
    }


    fun apply(orderbookView: OrderbookView, theme: Theme) {
        apply(orderbookView, theme.orderbookViewTheme)
    }


    fun apply(orderbookView: OrderbookView, theme: OrderbookViewTheme) {
        with(orderbookView) {
            setBackgroundColor(theme.backgroundColor)
            setHeaderTitleTextColor(theme.headerTitleTextColor)
            setHeaderMoreButtonColor(theme.headerMoreButtonColor)
            setHeaderSeparatorColor(theme.headerSeparatorColor)
            setBuyPriceColor(theme.buyPriceColor)
            setBuyPriceHighlightColor(theme.buyPriceHighlightColor)
            setBuyOrderBackgroundColor(theme.buyOrderBackgroundColor)
            setBuyHighlightBackgroundColor(theme.buyOrderHighlightBackgroundColor)
            setSellPriceColor(theme.sellPriceColor)
            setSellPriceHighlightColor(theme.sellPriceHighlightColor)
            setSellOrderBackgroundColor(theme.sellOrderBackgroundColor)
            setSellHighlightBackgroundColor(theme.sellOrderHighlightBackgroundColor)
            setAmountColor(theme.amountColor)
            setProgressBarColor(theme.progressBarColor)
            setInfoViewColor(theme.infoViewColor)
        }
    }


    fun apply(tradesView: TradesView, theme: Theme) {
        apply(tradesView, theme.tradesViewTheme)
    }


    fun apply(tradesView: TradesView, theme: TradesViewTheme) {
        with(tradesView) {
            setBackgroundColor(theme.backgroundColor)
            setHeaderTitleTextColor(theme.headerTitleTextColor)
            setHeaderSeparatorColor(theme.headerSeparatorColor)
            setBuyHighlightBackgroundColor(theme.buyHighlightBackgroundColor)
            setSellHighlightBackgroundColor(theme.sellHighlightBackgroundColor)
            setBuyPriceHighlightColor(theme.buyPriceHighlightColor)
            setSellPriceHighlightColor(theme.sellPriceHighlightColor)
            setBuyPriceColor(theme.buyPriceColor)
            setSellPriceColor(theme.sellPriceColor)
            setAmountColor(theme.amountColor)
            setTradeTimeColor(theme.tradeTimeColor)
            setProgressBarColor(theme.progressBarColor)
            setInfoViewColor(theme.infoViewColor)
        }
    }


    fun apply(pinEntryKeypad: PinEntryKeypad, theme: Theme) {
        apply(pinEntryKeypad, theme.pinEntryKeypadTheme)
    }


    fun apply(pinEntryKeypad: PinEntryKeypad, theme: PinEntryKeypadTheme) {
        with(pinEntryKeypad) {
            setDigitButtonTextColor(theme.digitButtonTextColor)
            setDigitButtonColors(
                theme.enabledButtonBackgroundColor,
                theme.disabledButtonBackgroundColor
            )
            setFingerprintButtonColors(
                theme.enabledButtonBackgroundColor,
                theme.disabledButtonBackgroundColor,
                theme.enabledFingerprintButtonForegroundColor,
                theme.disabledActionButtonForegroundColor
            )
            setDeleteButtonColors(
                theme.enabledButtonBackgroundColor,
                theme.disabledButtonBackgroundColor,
                theme.enabledDeleteButtonForegroundColor,
                theme.disabledActionButtonForegroundColor
            )
        }
    }


}