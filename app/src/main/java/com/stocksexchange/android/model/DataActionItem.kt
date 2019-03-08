package com.stocksexchange.android.model

/**
 * A model class holding a data item (e.g., an instance of some model class)
 * and an action that was performed or should be performed on it.
 */
data class DataActionItem<T>(
    val dataItem: T,
    val action: Actions
) {


    companion object {


        /**
         * Creates an instance of [DataActionItem] with an [Actions.INSERT] action.
         *
         * @param dataItem The data item itself
         *
         * @return The instance of [DataActionItem]
         */
        fun <T> insert(dataItem: T) = DataActionItem(dataItem, Actions.INSERT)


        /**
         * Creates an instance of [DataActionItem] with an [Actions.UPDATE] action.
         *
         * @param dataItem The data item itself
         *
         * @return The instance of [DataActionItem]
         */
        fun <T> update(dataItem: T) = DataActionItem(dataItem, Actions.UPDATE)


        /**
         * Creates an instance of [DataActionItem] with an [Actions.REMOVE] action.
         *
         * @param dataItem The data item itself
         *
         * @return The instance of [DataActionItem]
         */
        fun <T> remove(dataItem: T) = DataActionItem(dataItem, Actions.REMOVE)


    }


    enum class Actions {

        INSERT,
        UPDATE,
        REMOVE

    }


}