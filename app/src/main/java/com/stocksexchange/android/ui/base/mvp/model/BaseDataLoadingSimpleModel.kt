package com.stocksexchange.android.ui.base.mvp.model

import com.stocksexchange.android.model.RepositoryResult
import com.stocksexchange.android.model.utils.onFailure
import com.stocksexchange.android.model.utils.onSuccess
import com.stocksexchange.android.ui.base.mvp.model.BaseDataLoadingModel.BaseDataLoadingActionListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * A simple base data loading model to build model classes on.
 * Simple meaning that the data directly is fetched from the repository
 * without any intermediate transformations.
 */
abstract class BaseDataLoadingSimpleModel<
    Data: Any,
    in Parameters : Any,
    ActionListener: BaseDataLoadingActionListener<Data>
> : BaseDataLoadingModel<Data, Parameters, ActionListener>() {


    override suspend fun performDataLoading(params: Parameters) {
        val repositoryResult = getRepositoryResult(params)

        withContext(Dispatchers.Main) {
            repositoryResult
                .onSuccess { handleSuccessfulResponse(it) }
                .onFailure { handleUnsuccessfulResponse(it) }
        }
    }


    /**
     * Should return a repository result by fetching from
     * a specific repository. Gets called inside [performDataLoading].
     */
    protected abstract suspend fun getRepositoryResult(params: Parameters): RepositoryResult<Data>


}