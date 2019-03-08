package com.stocksexchange.android.ui.utils.extensions

import android.app.ActivityManager
import android.graphics.BitmapFactory
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import com.stocksexchange.android.R
import com.stocksexchange.android.model.TransitionAnimations

fun AppCompatActivity?.overrideEnterTransition(transitionAnimations: TransitionAnimations) {
    if((this == null) || (transitionAnimations.id == TransitionAnimations.DEFAULT_ANIMATIONS.id)) {
        return
    }

    overridePendingTransition(
        transitionAnimations.windowBEnterAnimation,
        transitionAnimations.windowAExitAnimation
    )
}


fun AppCompatActivity?.overrideExitTransition(transitionAnimations: TransitionAnimations) {
    if((this == null) || (transitionAnimations.id == TransitionAnimations.DEFAULT_ANIMATIONS.id)) {
        return
    }

    overridePendingTransition(
        transitionAnimations.windowAEnterAnimation,
        transitionAnimations.windowBExitAnimation
    )
}


fun AppCompatActivity?.setStatusBarColor(@ColorInt color: Int) {
    if(this == null) {
        return
    }

    window.statusBarColor = color
}


fun AppCompatActivity?.setRecentAppsToolbarColor(@ColorInt color: Int) {
    if(this == null) {
        return
    }

    setTaskDescription(ActivityManager.TaskDescription(
        getString(R.string.app_name),
        BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher),
        color
    ))
}