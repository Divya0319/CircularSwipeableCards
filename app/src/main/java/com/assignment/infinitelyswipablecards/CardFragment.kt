package com.assignment.infinitelyswipablecards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

/**
 * Created by Divya Gupta.
 */
class CardFragment : Fragment() {
    private lateinit var bundle: Bundle
    private lateinit var pos: String
    private lateinit var text: String
    private var size: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bundle = arguments!!
        pos = bundle.getString("pos")!!
        text = bundle.getString("text")!!
        size = bundle.getInt("size")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.item_cards, container, false)
        val cardItemPos: TextView = view.findViewById(R.id.tvNumOfCard)
        val cardItemText: TextView = view.findViewById(R.id.card_item_text)
        cardItemPos.text = "#$pos/$size"
        cardItemText.text = text
        return view
    }
}