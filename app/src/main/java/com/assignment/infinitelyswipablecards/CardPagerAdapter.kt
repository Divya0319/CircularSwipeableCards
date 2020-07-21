package com.assignment.infinitelyswipablecards

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.assignment.infinitelyswipablecards.commons.Constants
import com.assignment.infinitelyswipablecards.models.CardAPIResponsePOJO

/**
 * Created by Divya Gupta.
 */

/**
 * This class holds the state of every fragment created depending on count of cards, and it supplies data to each fragment
 */
class CardPagerAdapter(
    fm: FragmentManager,
    private val cardsList: List<CardAPIResponsePOJO.DataBean>
) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {


    override fun getCount(): Int {
        return cardsList.size
    }


    override fun getItem(position: Int): Fragment {
        val cardFragment = CardFragment()
        val bundle = Bundle()
        bundle.putInt(Constants.ARG_CARD_POSITION, position + 1)
        bundle.putString(Constants.ARG_CARD_TEXT, cardsList[position].text)
        bundle.putInt(Constants.ARG_CARD_COUNT, cardsList.size)
        cardFragment.arguments = bundle
        return cardFragment
    }

}