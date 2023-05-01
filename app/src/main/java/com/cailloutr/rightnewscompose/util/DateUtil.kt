package com.cailloutr.rightnewscompose.util

import android.os.Build
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
        return DateTimeFormatter
            .ofPattern(
                "EEE, d MMM, yyyy - HH:mm - z",
                Locale.getDefault()
            )
            .format(Instant.ofEpochMilli(instantDate.time))
    }
}