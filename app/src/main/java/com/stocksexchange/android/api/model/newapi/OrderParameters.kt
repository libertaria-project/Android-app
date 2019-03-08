package com.stocksexchange.android.api.model.newapi

import android.os.Parcelable
import com.stocksexchange.android.api.model.newapi.utils.HasUniqueKey
import kotlinx.android.parcel.Parcelize

/**
 * A model class representing parameters for loading orders.
 */
@Parcelize
data class OrderParameters(
    val status: OrderStatuses,
    val sortOrder: SortOrders,
    val limit: Int,
    val offset: Int
) : Parcelable, HasUniqueKey {


    override val uniqueKey: String
        get() = status.name


}