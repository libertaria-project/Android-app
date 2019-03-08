@file:Suppress("NOTHING_TO_INLINE")

package com.stocksexchange.android.ui.utils.extensions

import android.content.Context
import androidx.annotation.DimenRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

/**
 * Property to access the activity instance from the fragment.
 */
inline val Fragment.act: FragmentActivity
    get() = activity!!


/**
 * Property to access the content instance from the fragment.
 */
inline val Fragment.ctx: Context
    get() = activity!!


/**
 * Converts DP dimension in pixels.
 *
 * @param value The value to convert to pixels
 *
 * @return The converted dimension in pixels
 */
inline fun Fragment.dpToPx(value: Int): Int = ctx.dpToPx(value)


/**
 * Converts DP dimension in pixels.
 *
 * @param value The value to convert to pixels
 *
 * @return The converted dimension in pixels
 */
inline fun Fragment.dpToPx(value: Float): Float = ctx.dpToPx(value)


/**
 * Converts SP dimension in pixels.
 *
 * @param value The value to convert to pixels
 *
 * @return The converted dimension in pixels
 */
inline fun Fragment.spToPx(value: Int): Int = ctx.spToPx(value)


/**
 * Converts SP dimension in pixels.
 *
 * @param value The value to convert to pixels
 *
 * @return The converted dimension in pixels
 */
inline fun Fragment.spToPx(value: Float): Float = ctx.spToPx(value)


/**
 * Fetches the resource as raw pixels and returns it.
 *
 * @param resourceId The resource to fetch
 *
 * @return The fetched resource in raw pixels
 */
inline fun Fragment.dimenInPx(@DimenRes resourceId: Int): Int = ctx.dimenInPx(resourceId)