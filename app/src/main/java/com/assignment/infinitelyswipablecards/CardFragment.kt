package com.assignment.infinitelyswipablecards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.assignment.infinitelyswipablecards.adapters.CardAdapter

/**
 * Created by Divya Gupta.
 */
class CardFragment : Fragment() {
    private var mCardView: CardView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_adapter, container, false)
        mCardView = view.findViewById(R.id.cv_swipeable_fragment_card)
        mCardView?.maxCardElevation =
            mCardView?.maxCardElevation!! * CardAdapter.MAX_ELEVATION_FACTOR
        return view
    }

    fun getCardView(): CardView? {
        return mCardView
    }
}