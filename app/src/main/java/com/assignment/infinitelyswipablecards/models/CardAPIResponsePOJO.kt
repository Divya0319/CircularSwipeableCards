package com.assignment.infinitelyswipablecards.models

/**
 * Created by Divya Gupta.
 */

/**
 * A POJO class created to hold the response data coming from server.
 */
class CardAPIResponsePOJO {
    var data: MutableList<DataBean> = mutableListOf()

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