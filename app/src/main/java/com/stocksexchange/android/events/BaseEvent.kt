package com.stocksexchange.android.events

import com.stocksexchange.android.utils.helpers.tag
import org.greenrobot.eventbus.EventBus

/**
 * A base class for events.
 */
abstract class BaseEvent<out Attachment>(
    val type: Int,
    val attachment: Attachment,
    val sourceTag: String,
    var consumerCount: Int = 0,
    var isConsumed: Boolean = false,
    var onConsumeListener: OnConsumeListener? = null
) {


    companion object {

        const val TYPE_INVALID = -1
        const val TYPE_SINGLE_ITEM = 0
        const val TYPE_MULTIPLE_ITEMS = 1

    }


    private val consumers: HashSet<String> = hashSetOf()




    /**
     * Consumes a one-time event.
     */
    fun consume() {
        if(!isConsumed) {
            consumeInternal()
        }
    }


    /**
     * Adds a specified consumer to the set of consumers and
     * removes the event if the it becomes exhausted afterwards.
     *
     * @param consumer The consumer to add to the set
     */
    fun consume(consumer: Any) {
        if(consumerCount == 0) {
            consume()
            return
        }

        consumers.add(tag(consumer))

        if(isExhausted()) {
            consumeInternal()
        }
    }


    private fun consumeInternal() {
        EventBus.getDefault().removeStickyEvent(this)
        isConsumed = true

        onConsumeListener?.onConsumed()
    }


    /**
     * Checks whether the specified consumer has already
     * consumed this event.
     *
     * @return true if consumed; false otherwise
     */
    fun isConsumed(consumer: Any): Boolean {
        return consumers.contains(tag(consumer))
    }


    /**
     * Checks whether all consumers have consumed this event.
     *
     * @return true if this event has been consumed by all consumers;
     * false otherwise
     */
    fun isExhausted(): Boolean {
        return (consumerCount == consumers.size)
    }


    /**
     * Checks whether the event is originated from
     * the passed in source.
     *
     * @param source The source to check against
     *
     * @return true if the event was originated from source; false otherwise
     */
    fun isOriginatedFrom(source: Any): Boolean {
        return (sourceTag.isNotBlank() && (sourceTag == tag(source)))
    }


    /**
     * Returns a current consumer count meaning how many
     * consumers have already consumed this event.
     *
     * @return How many consumers have already consumed this event
     */
    fun getCurrentConsumerCount(): Int {
        return consumers.size
    }


    /**
     * An interface listener to implement if you want to get notified
     * whenever a particular event has been consumed.
     */
    interface OnConsumeListener {

        fun onConsumed()

    }


}