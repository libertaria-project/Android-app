package com.stocksexchange.android.model

import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import com.stocksexchange.android.ui.utils.interfaces.Recyclable

/**
 * A custom class used for storing attributes fetched from
 * instances of the [AttributeSet] class.
 */
class Attributes(): Parcelable, Recyclable {


    private var mIsRecycled: Boolean = false

    private var mAttributesMap: MutableMap<String, Any?> = mutableMapOf()




    private constructor(parcel: Parcel): this() {
        with(parcel) {
            mIsRecycled = (readInt() == 1)

            val mapSize = readInt()
            var key: String
            var value: Any?

            for(i in 0 until mapSize) {
                key = readString()!!
                value = readValue(CLASS_LOADER)

                save(key, value)
            }
        }
    }


    /**
     * Saves an attribute.
     *
     * @param key The key to associate the value with
     * @param value The value to save
     */
    fun save(key: String, value: Any?) {
        mAttributesMap[key] = value
    }


    /**
     * Retrieves a previously saved attribute.
     *
     * @param key The key associated with the value to fetch
     * @param default The default value in case the attribute with
     * specified key is not present
     *
     * @return The saved attribute
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: String, default: T): T {
        return (mAttributesMap[key] as? T) ?: default
    }


    override fun recycle() {
        if(mIsRecycled) {
            return
        }

        if(mAttributesMap.isNotEmpty()) {
            mAttributesMap.clear()
        }

        mIsRecycled = true
    }


    override fun describeContents(): Int = 0


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        with(parcel) {
            writeInt(if(mIsRecycled) 1 else 0)
            writeInt(mAttributesMap.size)

            for(attribute in mAttributesMap) {
                writeString(attribute.key)
                writeValue(attribute.value)
            }
        }
    }


    companion object {

        private val CLASS_LOADER = Attributes::class.java.classLoader


        @JvmField
        val CREATOR = object : Parcelable.Creator<Attributes> {

            override fun createFromParcel(parcel: Parcel): Attributes = Attributes(parcel)

            override fun newArray(size: Int): Array<Attributes?> = arrayOfNulls(size)

        }

    }


}