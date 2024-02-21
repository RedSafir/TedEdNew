package com.miftah.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miftah.core.data.source.local.entity.SavedStoriesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SaveStoriesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveStory(story : SavedStoriesEntity)

    @Query("SELECT EXISTS(SELECT * FROM save_stories WHERE id = :id)")
    fun isStoryFav(id: String): Flow<Boolean>

    @Query("SELECT * FROM save_stories")
    fun getAllSavedStories() : Flow<List<SavedStoriesEntity>>

    @Query("SELECT * FROM save_stories WHERE id = :id")
    fun getStory(id : String) : Flow<SavedStoriesEntity>

    @Query("DELETE from save_stories where id = :id")
    suspend fun deleteSaveStory(id : String)
}