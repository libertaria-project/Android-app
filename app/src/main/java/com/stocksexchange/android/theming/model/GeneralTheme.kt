package com.stocksexchange.android.theming.model

import java.io.Serializable

/**
 * A model class containing general colors of a particular theme.
 */
data class GeneralTheme(
    val primaryColor: Int,
    val primaryLightColor: Int,
    val primaryDarkColor: Int,
    val primaryTextColor: Int,
    val primaryDarkTextColor: Int,
    val secondaryTextColor: Int,
    val secondaryDarkTextColor: Int,
    val accentColor: Int,
    val darkAccentColor: Int,
    val contentContainerColor: Int,
    val contentContainerLightColor: Int,
    val contentContainerTextColor: Int,
    val tabIndicatorColor: Int,
    val infoViewColor: Int,
    val progressBarColor: Int,
    val gradientStartColor: Int,
    val gradientEndColor: Int,
    val linkReleasedStateBackgroundColor: Int,
    val linkPressedStateBackgroundColor: Int,
    val feedbackCardViewColor: Int,
    val feedbackFooterButtonColor: Int,
    val settingsBackgroundColor: Int
) : Serializable