package com.cailloutr.rightnewscompose.data.local.roommodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cailloutr.rightnewscompose.model.Article

@Entity(tableName = "articles")
data class RoomArticle(
    @PrimaryKey
    val id: String,
    val type: String,
    @ColumnInfo(name = "section_id")
    val sectionId: String,
    @ColumnInfo(name = "section_name")
    val sectionName: String,
    @ColumnInfo(name = "web_publication_date")
    val webPublicationDate: String,
    @ColumnInfo(name = "web_title")
    val webTitle: String,
    @ColumnInfo(name = "web_url")
    val webUrl: String,
    @ColumnInfo(name = "api_url")
    val apiUrl: String,
    @ColumnInfo(name = "is_hosted")
    val isHosted: Boolean?,
    @ColumnInfo(name = "pillar_id")
    val pillarId: String?,
    @ColumnInfo(name = "pillar_name")
    val pillarName: String?,
    @ColumnInfo(name = "trail_text")
    val trailText: String?,
    val thumbnail: String?,
    val headline: String?,
    val body: String?,
    @ColumnInfo(name = "container_id")
    val containerId: String,
)

fun RoomArticle.toArticle() = Article(
    id = id,
    type = type,
    sectionId = sectionId,
    sectionName = sectionName,
    webPublicationDate = webPublicationDate,
    webTitle = webTitle,
    webUrl = webUrl,
    apiUrl = apiUrl,
    isHosted = isHosted,
    pillarId = pillarId,
    pillarName = pillarName,
    trailText = trailText,
    thumbnail = thumbnail,
    headline = headline,
    body = body
)