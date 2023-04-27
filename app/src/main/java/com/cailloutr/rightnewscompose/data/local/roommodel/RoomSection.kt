package com.cailloutr.rightnewscompose.data.local.roommodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cailloutr.rightnewscompose.model.ChipItem
import com.cailloutr.rightnewscompose.model.Section

@Entity(tableName = "section")
data class RoomSection(
    @PrimaryKey
    val id: String,
    val title: String,
    @ColumnInfo(name = "web_url")
    val webUrl: String,
    @ColumnInfo(name = "api_url")
    val apiUrl: String,
    val code: String
)

fun RoomSection.toChipItem() = ChipItem(
    id = id,
    text = title
)

fun RoomSection.toSection() = Section(
    id, title, webUrl, apiUrl, code
)
