package com.assignment.infinitelyswipablecards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.assignment.infinitelyswipablecards.commons.Constants

/**
 * Created by Divya Gupta.
 */

/**
 * Fragment class for Card which holds the data to be displayed in view of a card
 */
class CardFragment : Fragment() {
    private lateinit var bundle: Bundle
    private var cardPosition: Int = 0
    private lateinit var cardText: String
    private var cardCount: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bundle = arguments!!
        cardPosition = bundle.getInt(Constants.ARG_CARD_POSITION)
        cardText = bundle.getString(Constants.ARG_CARD_TEXT)!!
        cardCount = bundle.getInt(Constants.ARG_CARD_COUNT)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.item_cards, container, false)
        val cardItemPos: TextView = view.findViewById(R.id.tvNumOfCard)
        val cardItemText: TextView = view.findViewById(R.id.card_item_text)
        cardItemPos.text = "#$cardPosition/$cardCount"
        cardItemText.text = cardText
        return view
    }
}