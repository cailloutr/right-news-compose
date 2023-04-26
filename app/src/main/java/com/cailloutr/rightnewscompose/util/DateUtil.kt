package com.cailloutr.rightnewscompose.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

object DateUtil {

    @RequiresApi(Build.VERSION_CODES.O)
    fun getFormattedDate(date: String): String {
        val instantDate = Date.from(Instant.parse(date))
        val simpleDateFormat = SimpleDateFormat(
            "EEE, d MMM, yyyy - HH:mm - z",
            Locale.getDefault()
        )
        return simpleDateFormat.format(instantDate)
    }
}