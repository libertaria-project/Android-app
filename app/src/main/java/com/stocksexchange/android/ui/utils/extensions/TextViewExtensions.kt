package com.stocksexchange.android.ui.utils.extensions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.drawable.Drawable
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import androidx.annotation.StyleRes
import android.widget.TextView
import androidx.annotation.ColorInt
import com.stocksexchange.android.Constants

/**
 * Sets a left drawable for the TextView.
 *
 * @param drawable The drawable to set
 */
fun TextView.setLeftDrawable(drawable: Drawable?) {
    this.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null)
}


/**
 * Gets a left drawable of the TextView.
 *
 * @return The TextView's left drawable
 */
fun TextView.getLeftDrawable(): Drawable? {
    return compoundDrawablesRelative[0]
}


/**
 * Sets a top drawable for the TextView.
 *
 * @param drawable The drawable to set
 */
fun TextView.setTopDrawable(drawable: Drawable?) {
    this.setCompoundDrawablesRelativeWithIntrinsicBounds(null, drawable, null, null)
}


/**
 * Gets a top drawable of the TextView.
 *
 * @return The TextView's top drawable
 */
fun TextView.getTopDrawable(): Drawable? {
    return compoundDrawablesRelative[1]
}


/**
 * Sets a right drawable for the TextView.
 *
 * @param drawable The drawable to set
 */
fun TextView.setRightDrawable(drawable: Drawable?) {
    this.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null)
}


/**
 * Gets a right drawable of the TextView.
 *
 * @return The TextView's right drawable
 */
fun TextView.getRightDrawable(): Drawable? {
    return compoundDrawablesRelative[2]
}


/**
 * Sets a bottom drawable for the TextView.
 *
 * @param drawable The drawable to set
 */
fun TextView.setBottomDrawable(drawable: Drawable?) {
    this.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, drawable)
}


/**
 * Gets a bottom drawable of the TextView.
 *
 * @return The TextView's bottom drawable
 */
fun TextView.getBottomDrawable(): Drawable? {
    return compoundDrawablesRelative[3]
}


/**
 * Clears the TextView from any compound drawables.
 */
fun TextView.clearDrawable() {
    this.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null)
    this.compoundDrawablePadding = 0
}


/**
 * Sets a style for the typeface.
 *
 * @param style The style to set
 */
fun TextView.setTypefaceStyle(style: Int) {
    setTypeface(null, style)
}


/**
 * Sets a text compatibility appearance.
 *
 * @param resId The resource id of the text appearance
 */
@SuppressWarnings("NewApi")
fun TextView.setTextAppearanceCompat(@StyleRes resId: Int) {
    if(Constants.AT_LEAST_MARSHMALLOW) {
        setTextAppearance(resId)
    } else {
        setTextAppearance(context, resId)
    }
}


/**
 * Colors compound drawables of the TextView.
 *
 * @param color The color to set
 */
fun TextView.setCompoundDrawablesColor(@ColorInt color: Int) {
    setCompoundDrawables(
        getLeftDrawable()?.apply { setColor(color) },
        getTopDrawable()?.apply { setColor(color) },
        getRightDrawable()?.apply { setColor(color) },
        getBottomDrawable()?.apply { setColor(color) }
    )
}


/**
 * Converts the view to be the primary button.
 *
 * @param contentColor The content color
 * @param pressedStateBackgroundColor The background color for the pressed state
 * @param releasedStateBackgroundColor The background color for the released state
 */
fun TextView.toPrimaryButton(
    @ColorInt contentColor: Int,
    @ColorInt pressedStateBackgroundColor: Int,
    @ColorInt releasedStateBackgroundColor: Int
) {
    setTextColor(contentColor)
    setCompoundDrawablesColor(contentColor)
    background = context.getPrimaryButtonBackground(
        pressedStateBackgroundColor,
        releasedStateBackgroundColor
    )
}


/**
 * Converts the view to be the secondary button.
 *
 * @param backgroundColor The color for the background
 * @param foregroundColor The color for the foreground
 */
fun TextView.toSecondaryButton(
    @ColorInt backgroundColor: Int,
    @ColorInt foregroundColor: Int
) {
    setTextColor(backgroundColor)
    background = context.getSecondaryButtonBackground(
        backgroundColor,
        foregroundColor
    )
}


/**
 * Changes the text of this text view by cross fading previous text with new one.
 *
 * @param text The new text to set
 * @param duration The duration for the cross fade animation. Default is 300 milliseconds.
 * @param interpolator The interpolator for the animation. Default is [LinearInterpolator].
 * @param onFinish The callback to invoke when the animation has been finished
 */
fun TextView.crossFadeText(text: CharSequence, duration: Long = 300L,
                           interpolator: Interpolator = LinearInterpolator(),
                           onFinish: (() -> Unit)? = null) {
    crossFade(duration, interpolator, onFinish) {
        setText(text)
    }
}