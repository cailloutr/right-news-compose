package com.cailloutr.rightnewscompose.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cailloutr.rightnewscompose.data.local.roommodel.RoomNewsContainer
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsContainerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSection(vararg newsContainer: RoomNewsContainer)

    @Delete
    suspend fun deleteNewsContainer(vararg newsContainer: RoomNewsContainer)

    @Query("SELECT * FROM news_container WHERE id == :section")
    fun getNewsContainer(section: String): Flow<RoomNewsContainer>

    @Query("SELECT * FROM news_container")
    fun getAllNewsContainer(): Flow<List<RoomNewsContainer>>
}