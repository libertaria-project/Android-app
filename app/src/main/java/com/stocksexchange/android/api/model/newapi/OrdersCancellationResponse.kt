package com.stocksexchange.android.api.model.newapi

import com.google.gson.annotations.SerializedName

/**
 * A wrapper model class used for encapsulating data
 * returned from the server when trying to cancel active
 * orders.
 */
data class OrdersCancellationResponse(
    @SerializedName("put_into_processing_queue") val ordersPutIntoProcessingQueue: List<Order>,
    @SerializedName("not_put_into_processing_queue") val ordersNotPutIntoProcessingQueue: List<Order>,
    @SerializedName("message") val message: String
)