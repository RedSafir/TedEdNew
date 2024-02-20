package com.miftah.tededexpert.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.miftah.tededexpert.core.data.source.local.entity.SaveStories

@Dao
interface SaveStoriesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveStory(story : SaveStories)

    @Delete
    suspend fun deleteSaveStory(story : SaveStories)
}