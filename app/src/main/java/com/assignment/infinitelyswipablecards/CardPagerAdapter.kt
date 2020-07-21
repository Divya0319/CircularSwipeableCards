package com.assignment.infinitelyswipablecards

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.assignment.infinitelyswipablecards.models.CardAPIResponsePOJO

/**
 * Created by Divya Gupta.
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
        bundle.putString("pos", cardsList[position].id)
        bundle.putString("text", cardsList[position].text)
        bundle.putInt("size", cardsList.size)
        cardFragment.arguments = bundle
        return cardFragment
    }

}