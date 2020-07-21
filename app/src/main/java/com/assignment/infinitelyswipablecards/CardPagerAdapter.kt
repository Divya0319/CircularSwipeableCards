package com.assignment.infinitelyswipablecards

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.PagerAdapter
import com.assignment.infinitelyswipablecards.commons.Constants
import com.assignment.infinitelyswipablecards.models.CardItem

/**
 * Created by Divya Gupta.
 */
class CardPagerAdapter : PagerAdapter() {

    private val mViews: MutableList<CardView?> = mutableListOf()
    private val mData: MutableList<CardItem> = mutableListOf()
    private var mBaseElevation = 0f

    fun addCardItem(item: CardItem) {
        mViews.add(null)
        mData.add(item)
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun getCount(): Int {
        return mData.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view =
            LayoutInflater.from(container.context).inflate(R.layout.item_cards, container, false)
        container.addView(view)
        bind(mData[position], view)
        val cardView: CardView = view.findViewById(R.id.cv_item_swipeable_card)
        if (mBaseElevation == 0f) {
            mBaseElevation = cardView.cardElevation
        }

        cardView.maxCardElevation = mBaseElevation * Constants.MAX_ELEVATION_FACTOR
        mViews[position] = cardView
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
        mViews[position] = null
    }

    private fun bind(item: CardItem, view: View) {
        val numberIndicator: TextView = view.findViewById(R.id.tvNumOfCard)
        val contentTextView: TextView = view.findViewById(R.id.card_item_text)
        numberIndicator.text = item.id
        contentTextView.text = item.text

    }
}