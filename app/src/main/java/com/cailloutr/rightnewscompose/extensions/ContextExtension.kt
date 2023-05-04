package com.cailloutr.rightnewscompose.extensions

import android.content.Context
import android.content.Intent
import androidx.annotation.StringRes

fun Context.shareLinkIntent(
    @StringRes title: Int,
    value: String
) {
    val shareLink = Intent(Intent.ACTION_SEND)
    shareLink.type = "text/plain"
    shareLink.putExtra(Intent.EXTRA_TEXT, value)
    val chooser = Intent.createChooser(shareLink, getString(title))

    if (shareLink.resolveActivity(this.packageManager) != null) {
        this.startActivity(chooser)
    }
}