package com.cailloutr.rightnewscompose.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cailloutr.rightnewscompose.data.local.roommodel.RoomSection
import kotlinx.coroutines.flow.Flow

@Dao
interface SectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSection(vararg section: RoomSection)

    @Delete
    suspend fun deleteSection(vararg section: RoomSection)

    @Query("SELECT * FROM section")
    fun getAllSection(): Flow<List<RoomSection>>

    @Query("SELECT * FROM section WHERE id == :id")
    fun getSection(id: String): Flow<RoomSection?>
}