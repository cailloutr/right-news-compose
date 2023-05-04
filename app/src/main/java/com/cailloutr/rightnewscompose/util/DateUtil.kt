package com.cailloutr.rightnewscompose.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
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
        val instant = Instant.parse(date)
        val zoneId = ZoneId.systemDefault()
        val localDateTime = LocalDateTime.ofInstant(instant, zoneId)
        val formatter =
            DateTimeFormatter.ofPattern("EEE, d MMM, yyyy - HH:mm - z", Locale.getDefault())
        return try {
            localDateTime.atZone(zoneId).format(formatter)
        } catch (e: Exception) {
            date
        }
    }
}