package com.miftah.core.data.source.local

import androidx.paging.PagingSource
import com.miftah.core.data.source.local.entity.RemoteKeys
import com.miftah.core.data.source.local.entity.SaveStories
import com.miftah.core.data.source.local.entity.Stories
import com.miftah.core.data.source.local.room.RemoteKeysDao
import com.miftah.core.data.source.local.room.SaveStoriesDao
import com.miftah.core.data.source.local.room.StoriesDao

class LocalDataSource(
    private val storiesDao: StoriesDao,
    private val remoteKeysDao: RemoteKeysDao,
    private val saveStoriesDao: SaveStoriesDao
) {

    suspend fun insertRemoteKeys(remoteKey: List<RemoteKeys>) =
        remoteKeysDao.insertAll(remoteKey)

    suspend fun getRemoteKeyById(id: String): RemoteKeys? =
        remoteKeysDao.getRemoteKeysId(id)

    suspend fun deleteRemoteKeys() =
        remoteKeysDao.deleteRemoteKeys()

    suspend fun insertStory(stories: List<Stories>) =
        storiesDao.insertStory(stories)

    suspend fun deleteAllStories() =
        storiesDao.deleteAllStories()

    fun getAllStories(): PagingSource<Int, Stories> =
        storiesDao.getAllStories()

    suspend fun deleteSaveStory(story: SaveStories) =
        saveStoriesDao.deleteSaveStory(story)

    suspend fun saveStory(story: SaveStories) =
        saveStoriesDao.saveStory(story)
}