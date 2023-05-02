package com.cailloutr.rightnewscompose.util

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.*

object DateUtil {

    /*@RequiresApi(Build.VERSION_CODES.O)
    fun getFormattedDate(date: String): String {
        val instantDate = Date.from(Instant.parse(date))
        val simpleDateFormat = SimpleDateFormat(
            "EEE, d MMM, yyyy - HH:mm - z",
            Locale.getDefault()
        )
        return simpleDateFormat.format(instantDate)
    }*/

    @RequiresApi(Build.VERSION_CODES.O)
    fun getFormattedDate(date: String): String {
        val instantDate = Date.from(Instant.parse(date))
        return try {
            DateTimeFormatter
                .ofPattern(
                    "EEE, dd MMM, yyyy - HH:mm - z",
                    Locale.getDefault()
                )
                .format(Instant.ofEpochMilli(instantDate.time))
        } catch (e: Exception) {
            Log.e("getFormattedDate", "Error: ${e.message.toString()}", )
            date
        }
    }
}