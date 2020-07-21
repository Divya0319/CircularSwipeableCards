package com.assignment.infinitelyswipablecards.commons

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser

/**
 * Created by Divya Gupta.
 */
object Utils {
    fun toPrettyFormat(jsonString: String): String {
        val json = JsonParser.parseString(jsonString)

        val gson = GsonBuilder().setPrettyPrinting().create()

        return gson.toJson(json)
    }
}