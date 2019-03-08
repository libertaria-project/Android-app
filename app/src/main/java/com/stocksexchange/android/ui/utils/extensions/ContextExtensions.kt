package com.stocksexchange.android.ui.utils.extensions

import android.app.AlarmManager
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutManager
import android.content.res.Configuration
import android.graphics.drawable.*
import android.net.ConnectivityManager
import android.os.PowerManager
import android.provider.Settings
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.*
import androidx.annotation.IntRange
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.hardware.fingerprint.FingerprintManagerCompat
import com.stocksexchange.android.Constants
import com.stocksexchange.android.R
import com.stocksexchange.android.model.ScreenSizes
import java.util.*
import android.graphics.drawable.Drawable
import android.content.res.TypedArray




/**
 * Property to access the context itself.
 */
inline val Context.ctx: Context
    get() = this


/**
 * Converts DP dimension in pixels.
 *
 * @param value The value to convert to pixels
 *
 * @return The converted dimension in pixels
 */
fun Context.dpToPx(value: Int): Int = (value * resources.displayMetrics.density).toInt()


/**
 * Converts DP dimension in pixels.
 *
 * @param value The value to convert to pixels
 *
 * @return The converted dimension in pixels
 */
fun Context.dpToPx(value: Float): Float = (value * resources.displayMetrics.density)


/**
 * Converts SP dimension in pixels.
 *
 * @param value The value to convert to pixels
 *
 * @return The converted dimension in pixels
 */
fun Context.spToPx(value: Int): Int = (value * resources.displayMetrics.scaledDensity).toInt()


/**
 * Converts SP dimension in pixels.
 *
 * @param value The value to convert to pixels
 *
 * @return The converted dimension in pixels
 */
fun Context.spToPx(value: Float): Float = (value * resources.displayMetrics.scaledDensity)


/**
 * Fetches the resource as raw pixels and returns it.
 *
 * @param resourceId The resource to fetch
 *
 * @return The fetched resource in raw pixels
 */
fun Context.dimenInPx(@DimenRes resourceId: Int): Int = resources.getDimensionPixelSize(resourceId)


/**
 * Retrieves a layout inflater.
 *
 * @return The instance of the [LayoutInflater] class
 */
fun Context.getLayoutInflater(): LayoutInflater {
    return LayoutInflater.from(this)
}


/**
 * Gets a drawable by using compat library.
 *
 * @param id The id of the drawable
 *
 * @return The instance of [Drawable]
 */
fun Context.getCompatDrawable(@DrawableRes id: Int): Drawable? {
    return AppCompatResources.getDrawable(this, id)
}


/**
 * Gets a colored drawable by using compat library.
 *
 * @param drawableId The ID for the drawable
 * @param colorId The ID for the color
 *
 * @return The colored drawable
 */
fun Context.getColoredCompatDrawable(@DrawableRes drawableId: Int, @ColorInt colorId: Int): Drawable? {
    return getCompatDrawable(drawableId)?.apply {
        setColor(colorId)
    }
}


/**
 * Gets a color by using compat library.
 *
 * @param id The id for the color
 *
 * @return The compatibility color
 */
fun Context.getCompatColor(@ColorRes id: Int): Int {
    return ContextCompat.getColor(this, id)
}


/**
 * Retrieves a dimension.
 *
 * @param id The dimension ID
 *
 * @return The dimension from the specified ID
 */
fun Context.getDimension(@DimenRes id: Int): Float {
    return resources.getDimension(id)
}


/**
 * Fetches the height of the status bar.
 *
 * @return The status bar's height
 */
fun Context.getStatusBarHeight(): Int {
    with(resources) {
        return getDimensionPixelSize(getIdentifier("status_bar_height", "dimen", "android"))
    }
}


/**
 * Gets an [Animation] instance from a context.
 *
 * @param resId The id for the animation
 *
 * @return The instance of [Animation] class
 */
fun Context.loadAnimation(@AnimRes resId: Int): Animation {
    return AnimationUtils.loadAnimation(this, resId)
}


/**
 * Retrieves a state list drawable.
 *
 * @param pressedStateDrawableId The ID for the pressed state drawable
 * @param pressedStateBackgroundColor The background color for the pressed state
 * @param releasedStateDrawableId The ID for the released state drawable
 * @param releasedStateBackgroundColor The background color for the released state
 *
 * @return The instance of [StateListDrawable]
 */
fun Context.getStateListDrawable(
    @DrawableRes pressedStateDrawableId: Int,
    @ColorInt pressedStateBackgroundColor: Int,
    @DrawableRes releasedStateDrawableId: Int,
    @ColorInt releasedStateBackgroundColor: Int
): StateListDrawable {
    val stateListDrawable = StateListDrawable()

    // Pressed state
    val pressedStateDrawable = (getCompatDrawable(pressedStateDrawableId) as GradientDrawable)
    pressedStateDrawable.setColor(pressedStateBackgroundColor)

    // Released state
    val releasedStateDrawable = (getCompatDrawable(releasedStateDrawableId) as GradientDrawable)
    releasedStateDrawable.setColor(releasedStateBackgroundColor)

    // Adding the actual states
    stateListDrawable.addState(intArrayOf(android.R.attr.state_pressed), pressedStateDrawable)
    stateListDrawable.addState(intArrayOf(android.R.attr.state_selected), pressedStateDrawable)
    stateListDrawable.addState(intArrayOf(), releasedStateDrawable)

    return stateListDrawable
}


/**
 * Retrieves a layer drawable.
 *
 * @param drawableId The ID for the drawable to fetch
 * @param backgroundColor The color for the background
 * @param foregroundColor The color for the foreground
 *
 * @return The layer drawable
 */
fun Context.getLayerDrawable(
    @DrawableRes drawableId: Int,
    @ColorInt backgroundColor: Int,
    @ColorInt foregroundColor: Int
): Drawable {
    val layerDrawable = (getCompatDrawable(drawableId) as LayerDrawable)

    // Background
    val backgroundDrawable = (layerDrawable.findDrawableByLayerId(R.id.background) as GradientDrawable)
    backgroundDrawable.setColor(backgroundColor)

    // Foreground
    val foregroundDrawable = (layerDrawable.findDrawableByLayerId(R.id.foreground) as GradientDrawable)
    foregroundDrawable.setColor(foregroundColor)

    return layerDrawable
}


/**
 * Retrieves the primary button background drawable with specified colors.
 *
 * @param pressedStateBackgroundColor The background color for the pressed state
 * @param releasedStateBackgroundColor The background color for the released state
 *
 * @return The primary button background drawable
 */
fun Context.getPrimaryButtonBackground(
    @ColorInt pressedStateBackgroundColor: Int,
    @ColorInt releasedStateBackgroundColor: Int
) : Drawable? {
    return getStateListDrawable(
        R.drawable.button_bg_state_pressed,
        pressedStateBackgroundColor,
        R.drawable.button_bg_state_released,
        releasedStateBackgroundColor
    )
}


/**
 * Retrieves the secondary button background drawable with specified colors.
 *
 * @param backgroundColor The color for the background layer
 * @param foregroundColor The color for the foreground layer
 *
 * @return The secondary button background drawable
 */
fun Context.getSecondaryButtonBackground(
    @ColorInt backgroundColor: Int,
    @ColorInt foregroundColor: Int
): Drawable? {
    return getLayerDrawable(
        R.drawable.secondary_button_background,
        backgroundColor,
        foregroundColor
    )
}


/**
 * Retrieves a colored particles drawable.
 *
 * @param drawableId The drawable ID
 * @param startColor The start color for the gradient
 * @param endColor The end color for the gradient
 *
 * @return The colored gradient drawable
 */
fun Context.getParticlesDrawable(
    @DrawableRes drawableId: Int,
    @ColorInt startColor: Int,
    @ColorInt endColor: Int
): Drawable {
    val layerDrawable = (getCompatDrawable(drawableId) as LayerDrawable)

    // Background
    val backgroundDrawable = (layerDrawable.findDrawableByLayerId(R.id.background) as GradientDrawable)
    backgroundDrawable.setGradientColors(startColor, endColor)

    return layerDrawable
}


/**
 * Retrieves a particles drawable in a landscape mode.
 *
 * @param startColor The start color of the gradient
 * @param endColor The end color of the gradient
 *
 * @return The colored landscape particles
 */
fun Context.getLandscapeParticlesDrawable(
    @ColorInt startColor: Int,
    @ColorInt endColor: Int
): Drawable {
    return getParticlesDrawable(
        R.drawable.particles_gradient_land_bg,
        startColor,
        endColor
    )
}


/**
 * Retrieves a particles drawable in a portrait mode.
 *
 * @param startColor The start color of the gradient
 * @param endColor The end color of the gradient
 *
 * @return The colored portrait particles
 */
fun Context.getPortraitParticlesDrawable(
    @ColorInt startColor: Int,
    @ColorInt endColor: Int
): Drawable {
    return getParticlesDrawable(
        R.drawable.particles_gradient_port_bg,
        startColor,
        endColor
    )
}


/**
 * Fetches the cursor drawable for the EditText.
 *
 * @param color The color of the cursor
 */
fun Context.getCursorDrawable(@ColorInt color: Int): Drawable? {
    return getColoredCompatDrawable(R.drawable.edit_text_cursor_drawable, color)
}


/**
 * Gets a dotted-line drawable.
 *
 * @param color The color for the dots
 *
 * @return The dotted-line drawable
 */
fun Context.getDottedLineDrawable(@ColorInt color: Int): Drawable {
    val drawable = (getCompatDrawable(R.drawable.dotted_line_drawable) as GradientDrawable)
    drawable.setStroke(
        dimenInPx(R.dimen.dotted_line_separator_stroke_width),
        color,
        getDimension(R.dimen.dotted_line_separator_dash_width),
        getDimension(R.dimen.dotted_line_separator_dash_gap)
    )

    return drawable
}


/**
 * Fetches an uptrend drawable.
 *
 * @return The uptrend drawable
 */
fun Context.getUpTrendDrawable(): Drawable? {
    return getCompatDrawable(R.drawable.up_trend_drawable)
}


/**
 * Fetches a downtrend drawable.
 *
 * @return The downtrend drawable
 */
fun Context.getDownTrendDrawable(): Drawable? {
    return getCompatDrawable(R.drawable.down_trend_drawable)
}


/**
 * Returns a rounded drawable for the daily change.
 *
 * @param colorId The color ID to paint the drawable to
 *
 * @return The round drawable
 */
fun Context.getDailyChangeRoundedDrawable(@ColorRes colorId: Int): Drawable? {
    return getColoredCompatDrawable(
        R.drawable.rounded_bg_drawable,
        getCompatColor(colorId)
    )
}


/**
 * Returns a pin box border drawable with the specified border color.
 *
 * @param borderColor The color for the border
 *
 * @return The pin box border drawable
 */
fun Context.getPinBoxBorderDrawable(@ColorInt borderColor: Int): Drawable? {
    val drawable = (getCompatDrawable(R.drawable.pin_box_border_drawable) as GradientDrawable)
    drawable.setStroke(dimenInPx(R.dimen.pin_entry_box_border_width), borderColor)

    return drawable
}


/**
 * Returns a pin box solid drawable with the specified color.
 *
 * @param color The color for the box
 *
 * @return The pin box solid drawable
 */
fun Context.getPinBoxSolidDrawable(@ColorInt color: Int): Drawable? {
    return getColoredCompatDrawable(R.drawable.pin_box_solid_drawable, color)
}


/**
 * Returns a pin entry drawable for the digit button.
 *
 * @param color The color to set
 *
 * @return The digit button drawable
 */
fun Context.getPinEntryDigitButtonDrawable(@ColorInt color: Int): Drawable {
    val layerDrawable = (getCompatDrawable(R.drawable.pin_entry_digit_button_drawable) as LayerDrawable)

    // Background
    val backgroundDrawable = (layerDrawable.findDrawableByLayerId(R.id.background) as GradientDrawable)
    backgroundDrawable.setColor(color)

    return layerDrawable
}


/**
 * Retrieves a pin entry drawable for the action button.
 *
 * @param drawableId The ID for the drawable to fetch
 * @param backgroundColor The color for the background
 * @param foregroundColor The color for the foreground
 *
 * @return The action button drawable
 */
fun Context.getPinEntryActionButtonDrawable(
    @DrawableRes drawableId: Int,
    @ColorInt backgroundColor: Int,
    @ColorInt foregroundColor: Int
): Drawable {
    val layerDrawable = (getCompatDrawable(drawableId) as LayerDrawable)

    // Background
    val backgroundDrawable = (layerDrawable.findDrawableByLayerId(R.id.background) as GradientDrawable)
    backgroundDrawable.setColor(backgroundColor)

    // Foreground
    val foregroundDrawable = (layerDrawable.findDrawableByLayerId(R.id.foreground) as VectorDrawable)
    foregroundDrawable.setColor(foregroundColor)

    return layerDrawable
}


/**
 * Retrieves a pin entry drawable for the fingerprint button.
 *
 * @param backgroundColor The color for the background
 * @param foregroundColor The color for the foreground
 *
 * @return The fingerprint button drawable
 */
fun Context.getPinEntryFingerprintButtonDrawable(@ColorInt backgroundColor: Int,
                                                 @ColorInt foregroundColor: Int): Drawable {
    return getPinEntryActionButtonDrawable(
        R.drawable.pin_entry_fingerprint_button_drawable,
        backgroundColor,
        foregroundColor
    )
}


/**
 * Retrieves a pin entry drawable for the delete button.
 *
 * @param backgroundColor The color for the background
 * @param foregroundColor The color for the foreground
 *
 * @return The delete button drawable
 */
fun Context.getPinEntryDeleteButtonDrawable(@ColorInt backgroundColor: Int,
                                            @ColorInt foregroundColor: Int): Drawable {
    return getPinEntryActionButtonDrawable(
        R.drawable.pin_entry_delete_button_drawable,
        backgroundColor,
        foregroundColor
    )
}


/**
 * Retrieves a login button drawable that has a particular color.
 *
 * @param color The color to set
 *
 * @return The login button drawable
 */
fun Context.getLoginButtonDrawable(@ColorInt color: Int): Drawable {
    val layerDrawable = (getCompatDrawable(R.drawable.login_button_background_drawable) as LayerDrawable)

    // Main
    val mainDrawable = (layerDrawable.findDrawableByLayerId(R.id.main) as GradientDrawable)
    mainDrawable.setColor(color)

    return layerDrawable
}


/**
 * Retrieves a background drawable for the orderbook order.
 *
 * @param color The color of the drawable
 * @param gravity The gravity of the drawable
 * @param level The level of the clip drawable
 *
 * @return The background drawable
 */
fun Context.getOrderbookOrderBackgroundDrawable(
    @ColorInt color: Int,
    gravity: Int,
    @IntRange(from = 0L, to = 10000L) level: Int
): ClipDrawable {
    return ClipDrawable(ColorDrawable(color), gravity, ClipDrawable.HORIZONTAL).apply {
        setLevel(level)
    }
}


/**
 * Retrieves a locale.
 */
@SuppressWarnings("NewApi")
fun Context.getLocale(): Locale {
    return if(Constants.AT_LEAST_NOUGAT_V1) {
        resources.configuration.locales[0]
    } else {
        resources.configuration.locale
    }
}


/**
 * Returns a context with a specified locale.
 *
 * @param locale The locale of the context
 *
 * @return The context with a specified locale
 */
fun Context.createContextWithLocale(locale: Locale): Context {
    val configuration = Configuration(resources.configuration).apply {
        setLocale(locale)
    }

    return createConfigurationContext(configuration)
}


/**
 * Checks whether the network is available or not.
 *
 * @return true if available; false otherwise
 */
fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager = (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
    return connectivityManager.activeNetworkInfo?.isAvailable ?: false
}


/**
 * Checks whether the given intent can be handled by some activity or not.
 *
 * @param intent The intent to check
 *
 * @return true if can be handled; false otherwise
 */
fun Context.canIntentBeHandled(intent: Intent): Boolean {
    return packageManager.queryIntentActivities(intent, 0).isNotEmpty()
}


/**
 * Returns the size of the screen.
 *
 * @return One of [ScreenSizes] constants
 */
fun Context.getScreenSize(): ScreenSizes {
    val screenLayout = (resources.configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK)

    return when(screenLayout) {
        Configuration.SCREENLAYOUT_SIZE_SMALL -> ScreenSizes.SMALL
        Configuration.SCREENLAYOUT_SIZE_NORMAL -> ScreenSizes.NORMAL
        Configuration.SCREENLAYOUT_SIZE_LARGE -> ScreenSizes.LARGE
        Configuration.SCREENLAYOUT_SIZE_XLARGE -> ScreenSizes.XLARGE

        else -> ScreenSizes.UNDEFINED
    }
}


/**
 * Returns the smallest screen width in DPI.
 *
 * @return The smallest screen width in DPI
 */
fun Context.getSmallestScreenWidthInDp(): Int {
    return Math.round(resources.displayMetrics.widthPixels / resources.displayMetrics.density)
}


/**
 * Retrieves a notification manager.
 *
 * @return The notification manager
 */
fun Context.getNotificationManager(): NotificationManager {
    return (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
}


/**
 * Retrieves a notification manager from a compat library.
 *
 * @return The backwards compatible notification manager
 */
fun Context.getCompatNotificationManager(): NotificationManagerCompat {
    return NotificationManagerCompat.from(this)
}


/**
 * Retrieves an alarm manager.
 *
 * @return The alarm manager
 */
fun Context.getAlarmManager(): AlarmManager {
    return (getSystemService(Context.ALARM_SERVICE) as AlarmManager)
}


/**
 * Retrieves a power manager.
 *
 * @return The power manager
 */
fun Context.getPowerManager(): PowerManager {
    return (getSystemService(Context.POWER_SERVICE) as PowerManager)
}


/**
 * Retrieves a shortcut manager.
 *
 * @return The shortcut manager
 */
@SuppressWarnings("NewApi")
fun Context.getShortcutManager(): ShortcutManager {
    return (getSystemService(Context.SHORTCUT_SERVICE) as ShortcutManager)
}


/**
 * Retrieves a fingerprint manager.
 *
 * @return The fingerprint manager
 */
fun Context.getFingerprintManager(): FingerprintManagerCompat {
    return FingerprintManagerCompat.from(this)
}


/**
 * Checks whether the keyboard currently used on the device is by Google.
 *
 * @return true if belongs to Google; false otherwise
 */
fun Context.isGoogleKeyboard(): Boolean {
    return Settings.Secure.getString(contentResolver, Settings.Secure.DEFAULT_INPUT_METHOD)
            .toLowerCase()
            .contains("google")
}