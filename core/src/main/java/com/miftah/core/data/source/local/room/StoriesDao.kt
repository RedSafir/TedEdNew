package com.miftah.core.data.source.local.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miftah.core.data.source.local.entity.StoriesEntity


@Dao
interface StoriesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(stories: List<StoriesEntity>)

    @Query("SELECT * FROM Stories")
    fun getAllStories(): PagingSource<Int, StoriesEntity>

    @Query("DELETE FROM Stories")
    suspend fun deleteAllStories()
}