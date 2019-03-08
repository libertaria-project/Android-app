package com.stocksexchange.android.ui.drawer.items

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arthurivanets.adapster.Adapter
import com.arthurivanets.adapster.listeners.OnItemClickListener
import com.arthurivanets.adapster.markers.ItemResources
import com.arthurivanets.adapster.model.BaseItem
import com.arthurivanets.adapster.model.Item
import com.arthurivanets.adapster.model.markers.Header
import com.stocksexchange.android.Constants
import com.stocksexchange.android.R
import com.stocksexchange.android.api.model.User
import com.stocksexchange.android.theming.model.Theme
import com.stocksexchange.android.ui.base.adapters.recyclerview.BaseViewHolder
import com.stocksexchange.android.ui.drawer.NavigationDrawerResources
import com.stocksexchange.android.theming.ThemingUtil
import com.stocksexchange.android.ui.utils.extensions.makeGone
import com.stocksexchange.android.ui.utils.extensions.makeVisible
import com.stocksexchange.android.ui.utils.extensions.setTopPadding
import com.stocksexchange.android.ui.views.MarkableImageView

class NavigationDrawerHeaderItem(itemModel: User?) : BaseItem<
    User?,
    NavigationDrawerHeaderItem.ViewHolder,
    NavigationDrawerResources
>(itemModel), Header<NavigationDrawerHeaderItem.ViewHolder> {


    companion object {

        const val MAIN_LAYOUT_ID = R.layout.navigation_drawer_header_layout

    }




    override fun init(adapter: Adapter<out Item<RecyclerView.ViewHolder, ItemResources>>?,
                      parent: ViewGroup, inflater: LayoutInflater, resources: NavigationDrawerResources?): ViewHolder {
        return ViewHolder(inflater.inflate(layout, parent, false)).apply {
            mContentContainerLl.setTopPadding(resources!!.dimensions[NavigationDrawerResources.DIMENSION_STATUS_BAR_HEIGHT])

            applyTheme(resources.settings.theme)
        }
    }


    override fun bind(adapter: Adapter<out Item<RecyclerView.ViewHolder, ItemResources>>?,
                      viewHolder: ViewHolder, resources: NavigationDrawerResources?) {
        super.bind(adapter, viewHolder, resources)

        with(viewHolder) {
            val user = itemModel

            if(user == null) {
                mSignInBtn.makeVisible()
                mUserNameTv.makeGone()
                mEmailTv.makeGone()

                mProfilePictureIv.setImageResource(resources!!.absentUserIconId)
            } else {
                val profilePictureMarkSize = resources!!.dimensions[NavigationDrawerResources.DIMENSION_PROFILE_PICTURE_MARK_SIZE]
                val profilePictureElevation = resources.dimensions[NavigationDrawerResources.DIMENSION_PROFILE_PICTURE_ELEVATION]

                mUserNameTv.makeVisible()
                mEmailTv.makeVisible()
                mSignInBtn.makeGone()

                mProfilePictureIv.shouldDrawBackground = true
                mProfilePictureIv.backgroundShape = MarkableImageView.Shape.OVALISH
                mProfilePictureIv.mark = user.userName.first().toUpperCase().toString()
                mProfilePictureIv.setTextSize(profilePictureMarkSize)

                if(Constants.AT_LEAST_LOLLIPOP) {
                    mProfilePictureIv.outlineProvider = resources.profilePictureOutlineProvider
                    mProfilePictureIv.elevation = profilePictureElevation.toFloat()
                }

                mUserNameTv.text = user.userName
                mEmailTv.text = user.email
            }
        }
    }


    override fun setOnItemClickListener(viewHolder: ViewHolder,
                                        onItemClickListener: OnItemClickListener<Header<ViewHolder>>?) {
        viewHolder.itemView.setOnClickListener {
            onItemClickListener?.onItemClicked(it, this@NavigationDrawerHeaderItem, 0)
        }
    }


    fun setOnSignInButtonClickListener(viewHolder: ViewHolder, position: Int,
                                       listener: ((View, NavigationDrawerHeaderItem, Int) -> Unit)?) {
        viewHolder.mSignInBtn.setOnClickListener {
            listener?.invoke(it, this@NavigationDrawerHeaderItem, position)
        }
    }


    override fun getLayout(): Int = MAIN_LAYOUT_ID


    class ViewHolder(itemView: View) : BaseViewHolder<User?>(itemView) {

        val mProfilePictureIv: MarkableImageView = itemView.findViewById(R.id.profilePictureIv)

        val mSignInBtn: Button = itemView.findViewById(R.id.signInBtn)

        val mUserNameTv: TextView = itemView.findViewById(R.id.userNameTv)
        val mEmailTv: TextView = itemView.findViewById(R.id.emailTv)

        val mContentContainerLl: LinearLayout = itemView.findViewById(R.id.contentContainerLl)


        override fun applyTheme(theme: Theme) {
            with(ThemingUtil.Drawer.Header) {
                background(itemView, theme)
                userName(mUserNameTv, theme)
                profileImage(mProfilePictureIv, theme)
                email(mEmailTv, theme)
                signInButton(mSignInBtn, theme)
            }
        }

    }


}