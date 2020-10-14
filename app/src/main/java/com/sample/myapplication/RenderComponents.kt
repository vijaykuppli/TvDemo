package com.sample.myapplication

import android.content.res.Resources
import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.leanback.widget.*
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import com.sample.myapplication.hero.ComponentsItem
import com.sample.myapplication.hero.HeroResponse
import java.io.IOException

class RenderComponents {

    companion object {
        private val TAG = "MainFragment"
        val ASSET_BASE_PATH = "../app/src/assets/"

        private val BACKGROUND_UPDATE_DELAY = 300
        private val GRID_ITEM_WIDTH = 200
        private val GRID_ITEM_HEIGHT = 200
        private val NUM_ROWS = 6
        private val NUM_COLS = 15
    }

    fun setRectangleComponent(
        resources: Resources,
        componentsItem: ComponentsItem?
    ): ArrayObjectAdapter {
        val mGridPresenter = GridItemPresenter()
        val gridRowAdapter = ArrayObjectAdapter(mGridPresenter)
        gridRowAdapter.add(componentsItem?.data?.name)
        return gridRowAdapter
    }

    fun setCarouselComponent(resources: Resources): ArrayObjectAdapter {
        val list = getStringFromFile(resources)
        val cardPresenter = CardPresenter()
        val rowsAdapter = ArrayObjectAdapter(ListRowPresenter())

//        val header = HeaderItem(0, "Home")

        for (i in 0 until (list?.components?.size!!)) {
            val listRowAdapter = ArrayObjectAdapter(cardPresenter)
            if (list.components.get(i)?.componentType.equals("simple_card_component")) {
                rowsAdapter.add(ListRow(setRectangleComponent(resources, list.components.get(i))))
            } else if (list.components.get(i)?.componentType.equals("carousel_component")) {
                for (listitem in 0 until (list.components.get(i)?.data?.list?.size ?: 0)) {
                    listRowAdapter.add(list?.components.get(i)?.data?.list?.get(listitem))
                }
                rowsAdapter.add(ListRow(listRowAdapter))
            }
        }
        return rowsAdapter
    }

    private inner class GridItemPresenter() : Presenter() {
        override fun onCreateViewHolder(parent: ViewGroup): Presenter.ViewHolder {
            val view = TextView(parent.context)
            view.layoutParams = ViewGroup.LayoutParams(
                GRID_ITEM_WIDTH,
                GRID_ITEM_HEIGHT
            )
            view.isFocusable = true
            view.isFocusableInTouchMode = true
            view.setBackgroundColor(
                ContextCompat.getColor(
                    parent.context,
                    R.color.default_background
                )
            )
            view.setTextColor(Color.WHITE)
            view.gravity = Gravity.CENTER
            return Presenter.ViewHolder(view)
        }

        override fun onBindViewHolder(viewHolder: Presenter.ViewHolder, item: Any) {
            (viewHolder.view as TextView).text = item as String
        }

        override fun onUnbindViewHolder(viewHolder: Presenter.ViewHolder) {}
    }

    @Throws(Exception::class)
    fun getStringFromFile(resources: Resources): HeroResponse? {

        var browseDataList: HeroResponse? = null
        try {
            val inputStream = resources.assets.open("hero.json")
            val streamArray = ByteArray(inputStream.available())
            inputStream.read(streamArray)
            inputStream.close()
            val jsonElement = JsonParser().parse(String(streamArray))
            val listType = object : TypeToken<HeroResponse>() {}.type
            browseDataList = Gson().fromJson<HeroResponse>(jsonElement, listType)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return browseDataList
    }

}