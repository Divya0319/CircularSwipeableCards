package com.assignment.infinitelyswipablecards.models

/**
 * Created by Divya Gupta.
 */
class CardAPIResponsePOJO {
    var data: List<DataBean> = listOf()

    data class DataBean(
        var id: String = "",
        var text: String = ""
    ) {
        /**
         * id : 1
         * text : Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
         */

    }
}