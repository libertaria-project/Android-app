package com.stocksexchange.android

import android.os.Build
import com.stocksexchange.android.model.DashboardTabs
import com.stocksexchange.android.model.OrderTabs
import java.util.Locale

/**
 * A singleton holding application specific constants.
 */
object Constants {


    // Request codes related
    const val REQUEST_CODE_CAMERA_PERMISSION = 1000
    const val REQUEST_CODE_STORAGE_PERMISSION = 1001
    const val REQUEST_CODE_NEW_EMAIL = 1002
    const val REQUEST_CODE_SECURITY_PROVIDER = 1003
    const val REQUEST_CODE_RINGTONE_PERMISSION = 1004
    const val REQUEST_CODE_RINGTONE_ACTIVITY = 1005
    const val REQUEST_CODE_SHARE_VIA = 1006
    const val REQUEST_CODE_COPY_LINK = 1007
    const val REQUEST_CODE_THEMES_ACTIVITY = 1008
    const val REQUEST_CODE_AUTHENTICATION_ACTIVITY = 1009
    const val REQUEST_CODE_APP_LOCK_RECEIVER = 1010

    // Links
    const val STEX_API_BASE_URL = "https://app.stex.com/api2/"
    const val STEX_NEW_API_BASE_URL = "https://api3.stex.com/"

    const val STEX_HOSTNAME = "stex.com"
    const val STEX_WEBSITE_URL = "https://stex.com"
    const val STEX_TERMS_OF_USE_URL = "https://app.stex.com/en/terms-of-use"
    const val STEX_TWITTER_URL = "https://twitter.com/stexExchangeR"
    const val STEX_FACEBOOK_URL = "https://www.facebook.com/stex.exchanger"
    const val STEX_TELEGRAM_URL = "https://t.co/mZKBF2bNPN"
    const val STEX_SOCKET_URL = "https://socket.stex.com"

    const val FEEDBACK_EMAIL_ADDRESS = "feedback@stex.com"
    const val SUPPORT_EMAIL_ADDRESS = "support@stex.com"

    // Certificates
    const val CERTIFICATE_PUBLIC_KEY_HASH_FIRST = "sha256/eq7Gpo5uoPjGSsMq17dN+Tk6Y92cuM1QZUB/mwcPGEY="
    const val CERTIFICATE_PUBLIC_KEY_HASH_SECOND = "sha256/8Rw90Ej3Ttt8RRkrg+WYDS9n7IS03bk5bjP/UXPtaY8="
    const val CERTIFICATE_PUBLIC_KEY_HASH_THIRD = "sha256/Ko8tivDrEjiY90yGasP6ZpBU4jwXvHqVvQI0GS3GNdA="

    const val MAIN_VIEW_ANIMATION_DURATION = 300L

    const val MIN_DATA_REFRESHING_INTERVAL = 30_000L

    const val STOCKS_EXCHANGE_OK_HTTP_CLIENT_TIMEOUT = 30_000L

    const val CURRENCY_MARKET_SEPARATOR = "_"

    val DASHBOARD_ACTIVITY_TAB_COUNT = DashboardTabs.values().size
    val ORDERS_ACTIVITY_TAB_COUNT = OrderTabs.values().size

    const val PLAY_STORE_APP_REFERENCE = "https://play.google.com/store/apps/details?id=com.stocksexchange.android"

    // Versions
    val API_VERSION = Build.VERSION.SDK_INT
    val AT_LEAST_KITKAT = (API_VERSION >= Build.VERSION_CODES.KITKAT)
    val AT_LEAST_LOLLIPOP = (API_VERSION >= Build.VERSION_CODES.LOLLIPOP)
    val AT_LEAST_MARSHMALLOW = (API_VERSION >= Build.VERSION_CODES.M)
    val AT_LEAST_NOUGAT_V1 = (API_VERSION >= Build.VERSION_CODES.N)
    val AT_LEAST_NOUGAT_V2 = (API_VERSION >= Build.VERSION_CODES.N_MR1)
    val AT_LEAST_OREO = (API_VERSION >= Build.VERSION_CODES.O)
    val AT_LEAST_PIE = (API_VERSION >= Build.VERSION_CODES.P)

    val DEFAULT_LOCALE = Locale.US


}

