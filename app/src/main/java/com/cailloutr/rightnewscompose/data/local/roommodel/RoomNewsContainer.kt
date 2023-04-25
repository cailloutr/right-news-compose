package com.cailloutr.rightnewscompose.data.local.roommodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_container")
data class RoomNewsContainer(
    @PrimaryKey
    val id: String,
    val total: Long,
    @ColumnInfo(name = "start_index")
    val startIndex: Long,
    @ColumnInfo(name = "page_size")
    val pageSize: Long,
    @ColumnInfo(name = "current_page")
    val currentPage: Long,
    val pages: Long,
    @ColumnInfo(name = "order_by")
    val orderBy: String,
)