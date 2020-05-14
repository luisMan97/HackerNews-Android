package com.example.hackernewscolegium.modules.news.entity

import android.annotation.SuppressLint
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.io.Serializable
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

infix fun <T> Boolean.then(param: T): T? = if (this) param else null

class New(newJson: JsonObject?): Serializable {
    lateinit var title: String
    lateinit var storyTitle: String
    lateinit var createdAt: String
    lateinit var author: String
    lateinit var storyUrl: String

    init {
        try {
            title      = getNullAsEmptyString(newJson?.get(TITLE))
            storyTitle = getNullAsEmptyString(newJson?.get(STORYTITLE))
            createdAt  = getFormatDate(getNullAsEmptyString(newJson?.get(CREATEDAT)))
            author     = getNullAsEmptyString(newJson?.get(AUTHOR))
            storyUrl   = getNullAsEmptyString(newJson?.get(STORYURL))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val TITLE      = "title"
        private const val STORYTITLE = "story_title"
        private const val CREATEDAT  = "created_at"
        private const val AUTHOR     = "author"
        private const val STORYURL   = "story_url"
    }

    private fun getNullAsEmptyString(jsonElement: JsonElement?): String {
        jsonElement?.let {
            return it.isJsonNull then "" ?: it.asString
        }
        return ""
    }

    @SuppressLint("SimpleDateFormat")
    private fun getFormatDate(dateCoupon: String): String {
        val format = SimpleDateFormat("yyyy-MM-dd")
        val dateFormat = SimpleDateFormat("dd MMMM yyyy")
        return try {
            val parsedDateFormat = format.parse(dateCoupon)
            val cal = Calendar.getInstance()
            parsedDateFormat?.let {
                cal.time = parsedDateFormat
            }
            dateFormat.format(cal.time)
        } catch (e: ParseException) {
            e.printStackTrace()
            ""
        }
    }

}