package com.sample.myapplication

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.Presenter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.sample.myapplication.hero.ListItem
import kotlin.properties.Delegates

/**
 * A CardPresenter is used to generate Views and bind Objects to them on demand.
 * It contains an ImageCardView.
 */
class CardPresenter : Presenter() {
    private var mDefaultCardImage: Drawable? = null
    private var sSelectedBackgroundColor: Int by Delegates.notNull()
    private var sDefaultBackgroundColor: Int by Delegates.notNull()

    override fun onCreateViewHolder(parent: ViewGroup): Presenter.ViewHolder {
        Log.d(TAG, "onCreateViewHolder")

        sDefaultBackgroundColor = ContextCompat.getColor(parent.context, R.color.default_background)
        sSelectedBackgroundColor =
            ContextCompat.getColor(parent.context, R.color.selected_background)
        mDefaultCardImage = ContextCompat.getDrawable(parent.context, R.drawable.movie)

        val cardView = object : ImageCardView(parent.context) {
            override fun setSelected(selected: Boolean) {
//                updateCardBackgroundColor(this, selected)
                super.setSelected(selected)
            }
        }

        cardView.isFocusable = true
        cardView.isFocusableInTouchMode = true
//        updateCardBackgroundColor(cardView, false)
        return Presenter.ViewHolder(cardView)
    }

    override fun onBindViewHolder(viewHolder: Presenter.ViewHolder, item: Any) {
        val browseData = item as ListItem
        val cardView = viewHolder.view as ImageCardView

        Log.d(TAG, "onBindViewHolder")
        if (browseData.title != null) {
            cardView.titleText = browseData.title
            cardView.contentText = "Sample Data"
            cardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT)
            Log.i(TAG, "onBindViewHolder: " + browseData.image)
/*
            Glide.with(viewHolder.view.context)
                    .load(browseData.image)
                    .centerCrop()
                    .error(R.drawable.app_icon_your_company)
                    .into(cardView.mainImageView)
*/
/*
            Glide.with(viewHolder.view.context)
                .load(browseData.image)
                .error(R.mipmap.ic_launcher)
                .transition(DrawableTransitionOptions.withCrossFade())
                .timeout(10000)
                .diskCacheStrategy(
                DiskCacheStrategy.AUTOMATIC)
                .into(cardView.mainImageView)
*/
            Glide.with(viewHolder.view.context).load(browseData.image)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.app_icon_your_company)
                .override(100, 100)
                .dontAnimate()
                .listener(object : RequestListener<Drawable?> {
                    override fun onLoadFailed(
                        @Nullable glideException: GlideException?, model: Any,
                        target: Target<Drawable?>,
                        isFirstResource: Boolean
                    ): Boolean {
                        if (glideException != null) {
                            Log.i(TAG, "onLoadFailed: "+glideException.message)
                        }
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any,
                        target: Target<Drawable?>,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        Log.i(TAG, "onResourceReady: ")
                        cardView.mainImageView.scaleType = ImageView.ScaleType.FIT_CENTER
                        return false
                    }
                })
                .into(cardView.mainImageView)

        }
    }

    override fun onUnbindViewHolder(viewHolder: Presenter.ViewHolder) {
        Log.d(TAG, "onUnbindViewHolder")
        val cardView = viewHolder.view as ImageCardView
        // Remove references to images so that the garbage collector can free up memory
        cardView.badgeImage = null
        cardView.mainImage = null
    }

    private fun updateCardBackgroundColor(view: ImageCardView, selected: Boolean) {
        val color = if (selected) sSelectedBackgroundColor else sDefaultBackgroundColor
        // Both background colors should be set because the view's background is temporarily visible
        // during animations.
        view.setBackgroundColor(color)
        view.setInfoAreaBackgroundColor(color)
    }

    companion object {
        private val TAG = "CardPresenter"

        private val CARD_WIDTH = 313
        private val CARD_HEIGHT = 176
    }
}
