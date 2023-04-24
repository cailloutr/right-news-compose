package com.cailloutr.rightnewscompose.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val id: String,
    val type: String,
    val sectionId: String,
    val sectionName: String,
    val webPublicationDate: String,
    val webTitle: String,
    val webUrl: String,
    val apiUrl: String,
    val isHosted: Boolean?,
    val pillarId: String?,
    val pillarName: String?,
    val trailText: String?,
    val thumbnail: String?,
    val headline: String?,
    val body: String?
): Parcelable
