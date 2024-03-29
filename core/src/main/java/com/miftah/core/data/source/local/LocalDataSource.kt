package com.miftah.core.data.source.local

import androidx.paging.PagingSource
import com.miftah.core.data.source.local.entity.RemoteKeysEntity
import com.miftah.core.data.source.local.entity.SavedStoriesEntity
import com.miftah.core.data.source.local.entity.StoriesEntity
import com.miftah.core.data.source.local.room.RemoteKeysDao
import com.miftah.core.data.source.local.room.SaveStoriesDao
import com.miftah.core.data.source.local.room.StoriesDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(
    private val storiesDao: StoriesDao,
    private val remoteKeysDao: RemoteKeysDao,
    private val saveStoriesDao: SaveStoriesDao
) {

    suspend fun insertRemoteKeys(remoteKey: List<RemoteKeysEntity>) =
        remoteKeysDao.insertAll(remoteKey)

    suspend fun getRemoteKeyById(id: String): RemoteKeysEntity? =
        remoteKeysDao.getRemoteKeysId(id)

    suspend fun deleteRemoteKeys() =
        remoteKeysDao.deleteRemoteKeys()

    suspend fun insertStory(stories: List<StoriesEntity>) =
        storiesDao.insertStory(stories)

    suspend fun deleteAllStories() =
        storiesDao.deleteAllStories()

    fun getAllStories(): PagingSource<Int, StoriesEntity> =
        storiesDao.getAllStories()

    suspend fun deleteSaveStory(id : String) =
        saveStoriesDao.deleteSaveStory(id)

    suspend fun saveStory(story: SavedStoriesEntity) =
        saveStoriesDao.saveStory(story)

    fun isStoryFav(id : String) : Flow<Boolean> =
        saveStoriesDao.isStoryFav(id)

    fun getAllSavedStories() : Flow<List<SavedStoriesEntity>> =
        saveStoriesDao.getAllSavedStories()
}
