@file:Suppress("UsePropertyAccessSyntax", "NOTHING_TO_INLINE")

package com.stocksexchange.android.ui.utils.extensions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import androidx.annotation.DimenRes
import org.jetbrains.anko.childrenSequence


/**
 * Converts DP dimension in pixels.
 *
 * @param value The value to convert to pixels
 *
 * @return The converted dimension in pixels
 */
inline fun View.dpToPx(value: Int): Int = context.dpToPx(value)


/**
 * Converts DP dimension in pixels.
 *
 * @param value The value to convert to pixels
 *
 * @return The converted dimension in pixels
 */
inline fun View.dpToPx(value: Float): Float = context.dpToPx(value)


/**
 * Converts SP dimension in pixels.
 *
 * @param value The value to convert to pixels
 *
 * @return The converted dimension in pixels
 */
inline fun View.spToPx(value: Int): Int = context.spToPx(value)


/**
 * Converts SP dimension in pixels.
 *
 * @param value The value to convert to pixels
 *
 * @return The converted dimension in pixels
 */
inline fun View.spToPx(value: Float): Float = context.spToPx(value)


/**
 * Fetches the resource as raw pixels and returns it.
 *
 * @param resourceId The resource to fetch
 *
 * @return The fetched resource in raw pixels
 */
inline fun View.dimenInPx(@DimenRes resourceId: Int): Int = context.dimenInPx(resourceId)


/**
 * Checks whether the view is visible, that is, the visibility flag
 * is set to [View.VISIBLE].
 *
 * @return true if the view is visible; false otherwise
 */
fun View.isVisible(): Boolean = visibility == View.VISIBLE


/**
 * Sets the view's visibility flag to [View.VISIBLE].
 */
fun View.makeVisible() = setVisibility(View.VISIBLE)


/**
 * Checks whether the view is invisible, that is, the visibility flag
 * is set to [View.INVISIBLE].
 *
 * @return true if the view is invisible; false otherwise
 */
fun View.isInvisible(): Boolean = visibility == View.INVISIBLE


/**
 * Sets the view's visibility flag to [View.INVISIBLE].
 */
fun View.makeInvisible() = setVisibility(View.INVISIBLE)


/**
 * Checks whether the view in gone, that is, the visibility flag
 * is set to [View.GONE].
 *
 * @return true if the view is gone; false otherwise
 */
fun View.isGone(): Boolean = visibility == View.GONE


/**
 * Sets the view's visibility flag to [View.GONE].
 */
fun View.makeGone() = setVisibility(View.GONE)


/**
 * Enables the view by setting its [View.isEnabled] property
 * to true and, optionally, changing its alpha.
 *
 * @param changeAlpha Whether to change the alpha of the view.
 * Default is false.
 * @param alpha The new alpha value for the view if [changeAlpha]
 * parameter is true. Default is 0.5f.
 * @param childrenToo Whether to enable children as well
 * Default is false.
 */
fun View.enable(changeAlpha: Boolean = false, alpha: Float = 1f,
                childrenToo: Boolean = false) {
    if(!isEnabled) {
        isEnabled = true

        if(changeAlpha) {
            setAlpha(alpha)
        }

        if(childrenToo) {
            for(child in childrenSequence()) {
                child.enable()
            }
        }
    }
}


/**
 * Disables the view by setting its [View.isEnabled] property
 * to false and, optionally, changing its alpha.
 *
 * @param changeAlpha Whether to change the alpha of the view.
 * Default is false.
 * @param alpha The new alpha value for the view if [changeAlpha]
 * parameter is true. Default is 0.5f.
 * @param childrenToo Whether to disable children as well.
 * Default is false.
 */
fun View.disable(changeAlpha: Boolean = false, alpha: Float = 0.5f,
                 childrenToo: Boolean = false) {
    if(isEnabled) {
        isEnabled = false

        if(changeAlpha) {
            setAlpha(alpha)
        }

        if(childrenToo) {
            for(child in childrenSequence()) {
                child.disable()
            }
        }
    }
}


/**
 * Sets the view's horizontal and vertical scale.
 *
 * @param scale The new scale to assign
 */
fun View.setScale(scale: Float) {
    scaleX = scale
    scaleY = scale
}


/**
 * Sets the view's margins.
 *
 * @param leftMargin The left margin to set
 * @param topMargin The top margin to set
 * @param rightMargin The right margin to set
 * @param bottomMargin The bottom margin to set
 */
fun View.setMargins(
    leftMargin: Int? = null,
    topMargin: Int? = null,
    rightMargin: Int? = null,
    bottomMargin: Int? = null
) {
    if(layoutParams !is ViewGroup.MarginLayoutParams) {
        return
    }

    val marginLayoutParams = (layoutParams as ViewGroup.MarginLayoutParams)

    if(leftMargin != null) {
        marginLayoutParams.leftMargin = leftMargin
    }

    if(topMargin != null) {
        marginLayoutParams.topMargin = topMargin
    }

    if(rightMargin != null) {
        marginLayoutParams.rightMargin = rightMargin
    }

    if(bottomMargin != null) {
        marginLayoutParams.bottomMargin = bottomMargin
    }

    layoutParams = marginLayoutParams
}


/**
 * Sets the view's left margin.
 *
 * @param leftMargin The new left margin
 */
fun View.setLeftMargin(leftMargin: Int) {
    setMargins(leftMargin = leftMargin)
}


/**
 * Sets the view's top margin.
 *
 * @param topMargin The new top margin
 */
fun View.setTopMargin(topMargin: Int) {
    setMargins(topMargin = topMargin)
}


/**
 * Sets the view's right margin.
 *
 * @param rightMargin The new right margin
 */
fun View.setRightMargin(rightMargin: Int) {
    setMargins(rightMargin = rightMargin)
}


/**
 * Sets the view's bottom margin.
 *
 * @param bottomMargin The new bottom margin
 */
fun View.setBottomMargin(bottomMargin: Int) {
    setMargins(bottomMargin = bottomMargin)
}


/**
 * Sets the view's horizontal margin.
 *
 * @param margin The new horizontal margin
 */
fun View.setHorizontalMargin(margin: Int) {
    setMargins(
        leftMargin = margin,
        rightMargin = margin
    )
}


/**
 * Sets the view's vertical margin.
 *
 * @param margin The new vertical margin
 */
fun View.setVerticalMargin(margin: Int) {
    setMargins(
        topMargin = margin,
        bottomMargin = margin
    )
}


/**
 * Sets the view's padding.
 *
 * @param padding The padding to set
 */
fun View.setPadding(padding: Int) {
    setPadding(padding, padding, padding, padding)
}


/**
 * Sets the view's left padding.
 *
 * @param padding The new left padding
 */
fun View.setLeftPadding(padding: Int) {
    setPadding(padding, paddingTop, paddingRight, paddingBottom)
}


/**
 * Sets the view's top padding.
 *
 * @param padding The new top padding
 */
fun View.setTopPadding(padding: Int) {
    setPadding(paddingLeft, padding, paddingRight, paddingBottom)
}


/**
 * Sets the view's right padding.
 *
 * @param padding The new right padding
 */
fun View.setRightPadding(padding: Int) {
    setPadding(paddingLeft, paddingTop, padding, paddingBottom)
}


/**
 * Sets the view's bottom padding.
 *
 * @param padding The new bottom padding
 */
fun View.setBottomPadding(padding: Int) {
    setPadding(paddingLeft, paddingTop, paddingRight, padding)
}


/**
 * Sets the view's horizontal padding (left and right).
 *
 * @param padding The new horizontal padding
 */
fun View.setHorizontalPadding(padding: Int) {
    setPadding(padding, paddingTop, padding, paddingBottom)
}


/**
 * Sets the view's vertical padding (top and bottom).
 *
 * @param padding The new vertical padding
 */
fun View.setVerticalPadding(padding: Int) {
    setPadding(paddingLeft, padding, paddingRight, padding)
}


/**
 * Cross fades itself.
 *
 * @param duration The duration for the cross fade animation. Default is 300 milliseconds.
 * @param interpolator The interpolator for the animation. Default is [LinearInterpolator].
 * @param onFinish The callback to invoke when the animation has been finished
 * @param onApplyChange The callback to invoke that actually applies the desired change
 */
fun View.crossFade(duration: Long = 300L,
                   interpolator: Interpolator = LinearInterpolator(),
                   onFinish: (() -> Unit)? = null,
                   onApplyChange: () -> Unit) {
    // Cancelling the ongoing animation (if any)
    animate().cancel()

    // Calculating the duration of the fade animation
    val animationDuration = (duration / 2L)

    // Creating a listener for a fade in animation (if needed)
    val fadeInAnimationListener = if(onFinish == null) {
        null
    } else {
        object : AnimatorListenerAdapter() {

            override fun onAnimationEnd(animation: Animator?) {
                onFinish()
            }

        }
    }

    // Preparing and starting the fade out animation
    animate()
        .alpha(0f)
        .setListener(object : AnimatorListenerAdapter() {

            override fun onAnimationEnd(animation: Animator?) {
                // Calling the listener to apply a specific change
                onApplyChange()

                // Preparing and starting the fade in animation
                animate()
                    .alpha(1f)
                    .setListener(fadeInAnimationListener)
                    .setInterpolator(interpolator)
                    .setDuration(animationDuration)
                    .start()
            }

        })
        .setInterpolator(interpolator)
        .setDuration(animationDuration)
        .start()
}