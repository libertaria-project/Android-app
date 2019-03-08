package com.stocksexchange.android.ui.drawer

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arthurivanets.adapster.model.BaseItem
import com.arthurivanets.adapster.model.markers.Footer
import com.arthurivanets.adapster.model.markers.Header
import com.google.android.material.internal.ScrimInsetsFrameLayout
import com.stocksexchange.android.R
import com.stocksexchange.android.model.Settings
import com.stocksexchange.android.ui.drawer.items.NavigationDrawerHeaderItem
import com.stocksexchange.android.ui.drawer.items.NavigationDrawerItem
import com.stocksexchange.android.ui.utils.extensions.disableAnimations

/**
 * A custom navigation drawer.
 */
@SuppressLint("RestrictedApi")
class NavigationDrawer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ScrimInsetsFrameLayout(context, attrs, defStyleAttr) {


    // Flags
    var mIsAutocloseable: Boolean = false

    // Model
    private var mItems: MutableList<BaseItem<*, *, *>> = mutableListOf()

    // UI elements
    private var mDrawerLayout: DrawerLayout? = null

    private var mRecyclerView: RecyclerView? = null

    private lateinit var mAdapter: NavigationDrawerRecyclerViewAdapter

    // Utils
    private var mLayoutInflater: LayoutInflater = LayoutInflater.from(context)

    // Listeners
    var onHeaderClickListener: ((View, Header<*>, Int) -> Unit)? = null
    var onFooterClickListener: ((View, Footer<*>, Int) -> Unit)? = null

    var onSignInButtonClickListener: ((View, NavigationDrawerHeaderItem, Int) -> Unit)? = null
    var onItemClickListener: ((View, NavigationDrawerItem, Int) -> Unit)? = null




    init {
        initDefaults()
        initRecyclerView()
        addView(mRecyclerView)
    }


    private fun initDefaults() {
        mIsAutocloseable = true
        mLayoutInflater = LayoutInflater.from(context)
    }


    private fun initRecyclerView() {
        mRecyclerView = (mLayoutInflater.inflate(R.layout.navigation_drawer_layout, this, false) as RecyclerView)
        mRecyclerView?.disableAnimations()

        mRecyclerView?.layoutManager = LinearLayoutManager(context)

        mAdapter = NavigationDrawerRecyclerViewAdapter(context, mItems).apply {
            setOnHeaderClickListener { view, item, position ->
                closeIfNecessary()

                this@NavigationDrawer.onHeaderClickListener?.invoke(view, item, position)
            }

            setOnFooterClickListener { view, item, position ->
                closeIfNecessary()

                this@NavigationDrawer.onFooterClickListener?.invoke(view, item, position)
            }

            this.onSignInButtonClickListener = { view, drawerHeaderItem, position ->
                closeIfNecessary()

                this@NavigationDrawer.onSignInButtonClickListener?.invoke(view, drawerHeaderItem, position)
            }

            this.onItemClickListener = { view, drawerItem, position ->
                closeIfNecessary()

                this@NavigationDrawer.onItemClickListener?.invoke(view, drawerItem, position)
            }
        }

        mRecyclerView?.adapter = mAdapter
    }


    /**
     * Sets the drawer layout.
     *
     * @param drawerLayout The drawer layout
     */
    fun setDrawerLayout(drawerLayout: DrawerLayout) {
        mDrawerLayout = drawerLayout
    }


    /**
     * Opens the drawer layout, i.e. makes it visible.
     */
    fun open() {
        mDrawerLayout?.openDrawer(this)
    }


    /**
     * Closes the drawer layout, i.e. makes it invisible.
     */
    fun close() {
        mDrawerLayout?.closeDrawer(this)
    }


    private fun closeIfNecessary() {
        if(mIsAutocloseable) {
            close()
        }
    }


    /**
     * Locks the drawer in its current state, i.e. it's impossible to open or
     * close it afterwards.
     */
    fun lock() {
        mDrawerLayout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }


    /**
     * Unlocks the drawer, i.e. it's possible to open or close it afterwards.
     */
    fun unlock() {
        mDrawerLayout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }


    /**
     * Sets the settings.
     *
     * @param settings The settings to set
     */
    fun setSettings(settings: Settings) {
        mAdapter.setSettings(settings)
    }


    /**
     * Sets the items for the drawer.
     *
     * @param items The items to set
     */
    fun setItems(items: MutableList<BaseItem<*, *, *>>) {
        mItems = items
        mAdapter.items = items
    }


    /**
     * Adds the item to the adapter at a specific position.
     *
     * @param item The item to add
     * @param position The position to add at
     */
    fun addItem(item: BaseItem<*, *, *>, position: Int = getItemCount()) {
        mAdapter.addItem(position, item)
    }


    /**
     * Adds or updates the item at a specific position.
     *
     * @param item The item to add or update
     * @param position The position to add at
     */
    fun addOrUpdateItem(item: BaseItem<*, *, *>, position: Int) {
        mAdapter.addOrUpdateItem(position, item)
    }


    /**
     * Updates the item in the adapter.
     *
     * @param item The item to update
     */
    fun updateItem(item: BaseItem<*, *, *>) {
        mAdapter.updateItemWith(item)
    }


    /**
     * Removes the header from the drawer.
     */
    fun removeHeader() {
        mAdapter.removeHeader()
    }


    /**
     * Removes the footer from the footer.
     */
    fun removeFooter() {
        mAdapter.removeFooter()
    }


    /**
     * Gets the item at a particular position.
     *
     * @param position The position of the item
     *
     * @return The item at the particular position
     */
    fun getItem(position: Int): BaseItem<*, *, *>? {
        return mAdapter.getItem(position)
    }


    /**
     * Gets the item for a particular ID.
     *
     * @param id The id of the item
     *
     * @return The item with the particular ID
     */
    fun getItemForId(id: Int): BaseItem<*, *, *> {
        return (mAdapter.getTrackable(id) as BaseItem<*, *, *>)
    }


    /**
     * Gets the item count of the adapter.
     *
     * @return The item count
     */
    fun getItemCount(): Int {
        return mAdapter.itemCount
    }


    /**
     * Checks whether the navigation drawer is opened or closed.
     *
     * @return true if opened; false otherwise
     */
    fun isOpen(): Boolean {
        return mDrawerLayout?.isDrawerOpen(this) ?: false
    }


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        onHeaderClickListener = null
        onFooterClickListener = null
        onSignInButtonClickListener = null
        onItemClickListener = null
    }


}