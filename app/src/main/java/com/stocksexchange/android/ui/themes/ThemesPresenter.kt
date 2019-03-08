package com.stocksexchange.android.ui.themes

import com.stocksexchange.android.model.ThemeData
import com.stocksexchange.android.ui.base.mvp.presenters.BasePresenter

class ThemesPresenter(
    model: ThemesModel,
    view: ThemesContract.View
) : BasePresenter<ThemesModel, ThemesContract.View>(model, view), ThemesContract.ActionListener {


    constructor(view: ThemesContract.View): this(ThemesModel(), view)


    override fun start() {
        super.start()

        if(mView.isDataSetEmpty()) {
            mView.setItems(mModel.getThemesData(mView.getAppSettings()))
        }
    }


    override fun onThemeClicked(themeData: ThemeData) {
        val dataSetSize = mView.getDataSetSize()
        var oldThemeData: ThemeData?

        for(i in 0..dataSetSize) {
            oldThemeData = mView.getThemeDataAt(i)

            if(oldThemeData?.isSelected == true) {
                oldThemeData.isSelected = false
                themeData.isSelected = true

                mView.updateThemeData(oldThemeData)
                mView.updateThemeData(themeData)

                break
            }
        }
    }


}