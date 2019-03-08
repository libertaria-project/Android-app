package com.stocksexchange.android.ui.currencymarkets.fragment

import com.stocksexchange.android.api.model.CurrencyMarket
import com.stocksexchange.android.model.*
import com.stocksexchange.android.model.utils.log
import com.stocksexchange.android.repositories.currencymarkets.CurrencyMarketsRepository
import com.stocksexchange.android.ui.base.mvp.model.BaseDataLoadingSimpleModel
import com.stocksexchange.android.ui.currencymarkets.fragment.CurrencyMarketsModel.ActionListener
import org.koin.standalone.inject

class CurrencyMarketsModel : BaseDataLoadingSimpleModel<
    List<CurrencyMarket>,
    CurrencyMarketParameters,
    ActionListener
>() {


    private val mCurrencyMarketsRepository: CurrencyMarketsRepository by inject()




    override fun canLoadData(params: CurrencyMarketParameters, dataType: DataTypes,
                             dataLoadingSource: DataLoadingSources): Boolean {
        val marketType = params.currencyMarketType
        val searchQuery = params.searchQuery

        val isMarketSearch = (marketType == CurrencyMarketTypes.SEARCH)
        val isNewData = (dataType == DataTypes.NEW_DATA)

        val isMarketSearchWithNoQuery = (isMarketSearch && searchQuery.isBlank())
        val isMarketSearchNewData = (isMarketSearch && isNewData)
        val isFavoriteMarketsNewData = ((marketType == CurrencyMarketTypes.FAVORITES) && isNewData)

        val isNetworkConnectivitySource = (dataLoadingSource == DataLoadingSources.NETWORK_CONNECTIVITY)
        val isRealTimeDataLossSource = (dataLoadingSource == DataLoadingSources.REAL_TIME_DATA_LOSS)
        val dataLoadingIntervalCancellingFlags = (isNetworkConnectivitySource || isRealTimeDataLossSource)

        val isNewDataWithIntervalNotApplied = (isNewData && !isDataLoadingIntervalApplied() && !dataLoadingIntervalCancellingFlags)

        return (!isMarketSearchWithNoQuery
                && !isMarketSearchNewData
                && !isFavoriteMarketsNewData
                && !isNewDataWithIntervalNotApplied)
    }


    override fun refreshData(params: CurrencyMarketParameters) {
        mCurrencyMarketsRepository.refresh()
    }


    override suspend fun getRepositoryResult(params: CurrencyMarketParameters): RepositoryResult<List<CurrencyMarket>> {
        return when(params.currencyMarketType) {
            CurrencyMarketTypes.BTC -> mCurrencyMarketsRepository.getBitcoinMarkets()
            CurrencyMarketTypes.USDT -> mCurrencyMarketsRepository.getTetherMarkets()
            CurrencyMarketTypes.NXT -> mCurrencyMarketsRepository.getNxtMarkets()
            CurrencyMarketTypes.LTC -> mCurrencyMarketsRepository.getLitecoinMarkets()
            CurrencyMarketTypes.ETH -> mCurrencyMarketsRepository.getEthereumMarkets()
            CurrencyMarketTypes.USD -> mCurrencyMarketsRepository.getUsdMarkets()
            CurrencyMarketTypes.EUR -> mCurrencyMarketsRepository.getEurMarkets()
            CurrencyMarketTypes.JPY -> mCurrencyMarketsRepository.getJpyMarkets()
            CurrencyMarketTypes.RUB -> mCurrencyMarketsRepository.getRubMarkets()
            CurrencyMarketTypes.FAVORITES -> mCurrencyMarketsRepository.getFavoriteMarkets()
            CurrencyMarketTypes.SEARCH -> mCurrencyMarketsRepository.search(params.lowercasedSearchQuery)
        }.log("currencyMarketsRepository.get(params: $params)")
    }


    interface ActionListener : BaseDataLoadingActionListener<List<CurrencyMarket>>


}