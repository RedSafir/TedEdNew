package com.miftah.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.miftah.core.data.source.local.entity.SavedStoriesEntity

@Dao
interface SaveStoriesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveStory(story : SavedStoriesEntity)

    @Delete
    suspend fun deleteSaveStory(story : SavedStoriesEntity)
}