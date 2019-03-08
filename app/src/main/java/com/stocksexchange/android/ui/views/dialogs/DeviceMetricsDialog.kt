package com.stocksexchange.android.ui.views.dialogs

import android.content.Context
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import com.afollestad.materialdialogs.MaterialDialog
import com.stocksexchange.android.R
import com.stocksexchange.android.ui.views.SimpleMapView

/**
 * A dialog used for showing device specific metrics.
 */
class DeviceMetricsDialog(context: Context) {


    // Views related
    private val mMaterialDialog: MaterialDialog = MaterialDialog.Builder(context)
        .customView(R.layout.device_metrics_dialog_layout, false)
        .build()


    private val mContentContainerLl: LinearLayout = mMaterialDialog.customView!!.findViewById(R.id.contentContainerLl)

    private val mDeviceNameSmv: SimpleMapView = mMaterialDialog.customView!!.findViewById(R.id.deviceNameSmv)
    private val mDensitySmv: SimpleMapView = mMaterialDialog.customView!!.findViewById(R.id.densitySmv)
    private val mDensityInDpSmv: SimpleMapView = mMaterialDialog.customView!!.findViewById(R.id.densityInDpSmv)
    private val mScreenSizeSmv: SimpleMapView = mMaterialDialog.customView!!.findViewById(R.id.screenSizeSmv)
    private val mScreenWidthInPxSmv: SimpleMapView = mMaterialDialog.customView!!.findViewById(R.id.screenWidthInPxSmv)
    private val mScreenWidthInDpSmv: SimpleMapView = mMaterialDialog.customView!!.findViewById(R.id.screenWidthInDpSmv)
    private val mScreenHeightInPxSmv: SimpleMapView = mMaterialDialog.customView!!.findViewById(R.id.screenHeightInPxSmv)
    private val mScreenHeightInDpSmv: SimpleMapView = mMaterialDialog.customView!!.findViewById(R.id.screenHeightInDpSmv)
    private val mSmallestWidthInDpSmv: SimpleMapView = mMaterialDialog.customView!!.findViewById(R.id.smallestWidthInDpSmv)




    /**
     * Shows the dialog.
     */
    fun show() {
        mMaterialDialog.show()
    }


    /**
     * Dismisses the dialog.
     */
    fun dismiss() {
        mMaterialDialog.dismiss()
    }


    /**
     * Sets a device name.
     *
     * @param deviceName The device name to set
     */
    fun setDeviceName(deviceName: String) {
        mDeviceNameSmv.setValueText(deviceName)
    }


    /**
     * Sets a density.
     *
     * @param density The density to set
     */
    fun setDensity(density: Float) {
        mDensitySmv.setValueText(density.toString())
    }


    /**
     * Sets a density in DPI.
     *
     * @param densityInDp The density in DPI to set
     */
    fun setDensityInDp(densityInDp: Int) {
        mDensityInDpSmv.setValueText(densityInDp.toString())
    }


    /**
     * Sets a screen size.
     *
     * @param screenSize The size of the screen
     */
    fun setScreenSize(screenSize: String) {
        mScreenSizeSmv.setValueText(screenSize)
    }


    /**
     * Sets a width in pixels.
     *
     * @param widthInPx The width in pixels to set
     */
    fun setScreenWidthInPx(widthInPx: Int) {
        mScreenWidthInPxSmv.setValueText(widthInPx.toString())
    }


    /**
     * Sets a width in DPI.
     *
     * @param widthInDp The width in DPI to set
     */
    fun setScreenWidthInDp(widthInDp: Float) {
        mScreenWidthInDpSmv.setValueText(widthInDp.toString())
    }


    /**
     * Sets a height in pixels.
     *
     * @param heightInPx The height in pixels to set
     */
    fun setScreenHeightInPx(heightInPx: Int) {
        mScreenHeightInPxSmv.setValueText(heightInPx.toString())
    }


    /**
     * Sets a height in DPI.
     *
     * @param heightInDp The height in DPI to set
     */
    fun setScreenHeightInDp(heightInDp: Float) {
        mScreenHeightInDpSmv.setValueText(heightInDp.toString())
    }


    /**
     * Sets a smallest width in DPI.
     *
     * @param smallestWidthInDp The smallest width in DPI to set
     */
    fun setSmallestWidthInDp(smallestWidthInDp: Int) {
        mSmallestWidthInDpSmv.setValueText(smallestWidthInDp.toString())
    }


    /**
     * Sets a dialog's background color.
     *
     * @param color The color to set
     */
    fun setBackgroundColor(@ColorInt color: Int) {
        mContentContainerLl.setBackgroundColor(color)
    }


    /**
     * Sets a color of the titles.
     *
     * @param color The color to set
     */
    fun setTitlesColor(@ColorInt color: Int) {
        mDeviceNameSmv.setTitleColor(color)
        mDensitySmv.setTitleColor(color)
        mDensityInDpSmv.setTitleColor(color)
        mScreenSizeSmv.setTitleColor(color)
        mScreenWidthInPxSmv.setTitleColor(color)
        mScreenWidthInDpSmv.setTitleColor(color)
        mScreenHeightInPxSmv.setTitleColor(color)
        mScreenHeightInDpSmv.setTitleColor(color)
        mSmallestWidthInDpSmv.setTitleColor(color)
    }


    /**
     * Sets a color of the values.
     *
     * @param color The color to set
     */
    fun setValuesColor(@ColorInt color: Int) {
        mDeviceNameSmv.setValueColor(color)
        mDensitySmv.setValueColor(color)
        mDensityInDpSmv.setValueColor(color)
        mScreenSizeSmv.setValueColor(color)
        mScreenWidthInPxSmv.setValueColor(color)
        mScreenWidthInDpSmv.setValueColor(color)
        mScreenHeightInPxSmv.setValueColor(color)
        mScreenHeightInDpSmv.setValueColor(color)
        mSmallestWidthInDpSmv.setValueColor(color)
    }


}