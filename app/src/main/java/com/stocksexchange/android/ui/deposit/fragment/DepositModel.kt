package com.stocksexchange.android.ui.deposit.fragment

import com.stocksexchange.android.api.exceptions.NoWalletAddressException
import com.stocksexchange.android.api.model.Deposit
import com.stocksexchange.android.api.model.DepositParameters
import com.stocksexchange.android.model.utils.*
import com.stocksexchange.android.repositories.deposits.DepositsRepository
import com.stocksexchange.android.ui.base.mvp.model.BaseDataLoadingModel
import com.stocksexchange.android.ui.deposit.fragment.DepositModel.ActionListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.standalone.inject

class DepositModel : BaseDataLoadingModel<
    Deposit,
    DepositParameters,
    ActionListener
>() {


    private val mDepositsRepository: DepositsRepository by inject()




    override fun refreshData(params: DepositParameters) {
        mDepositsRepository.refresh(params)
    }


    override suspend fun performDataLoading(params: DepositParameters) {
        mDepositsRepository.get(params)
            .log("depositsRepository.get(params: $params)")
            .onSuccess { withContext(Dispatchers.Main) { handleSuccessfulResponse(it) } }
            .onFailure { exception ->
                when(exception) {
                    is NoWalletAddressException -> {
                        val repositoryResult = mDepositsRepository.generateWalletAddress(params).apply {
                            log("depositsRepository.generateWalletAddress(params: $params)")
                        }

                        withContext(Dispatchers.Main) {
                            repositoryResult
                                .onSuccess { handleSuccessfulResponse(it) }
                                .onFailure { handleUnsuccessfulResponse(it) }
                        }
                    }

                    else -> withContext(Dispatchers.Main) { handleUnsuccessfulResponse(exception) }
                }
            }
    }


    interface ActionListener : BaseDataLoadingActionListener<Deposit>


}