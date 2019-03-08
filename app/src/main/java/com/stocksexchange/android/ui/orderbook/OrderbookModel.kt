package com.stocksexchange.android.ui.orderbook

import com.stocksexchange.android.api.model.Orderbook
import com.stocksexchange.android.api.model.OrderbookParameters
import com.stocksexchange.android.model.DataLoadingSources
import com.stocksexchange.android.model.DataTypes
import com.stocksexchange.android.model.RepositoryResult
import com.stocksexchange.android.model.utils.log
import com.stocksexchange.android.repositories.orderbook.OrderbookRepository
import com.stocksexchange.android.ui.base.mvp.model.BaseDataLoadingSimpleModel
import com.stocksexchange.android.ui.orderbook.OrderbookModel.ActionListener
import org.koin.standalone.inject

class OrderbookModel : BaseDataLoadingSimpleModel<
    Orderbook,
    OrderbookParameters,
    ActionListener
>() {


    private val mOrderbookRepository: OrderbookRepository by inject()




    override fun canLoadData(params: OrderbookParameters, dataType: DataTypes,
                             dataLoadingSource: DataLoadingSources): Boolean {
        return true
    }


    override fun refreshData(params: OrderbookParameters) {
        mOrderbookRepository.refresh(params)
    }


    override suspend fun getRepositoryResult(params: OrderbookParameters): RepositoryResult<Orderbook> {
        return mOrderbookRepository.get(params).apply {
            log("mOrderbookRepository.get()")
        }
    }


    interface ActionListener : BaseDataLoadingActionListener<Orderbook>


}