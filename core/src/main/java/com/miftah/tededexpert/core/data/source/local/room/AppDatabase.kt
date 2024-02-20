package com.miftah.tededexpert.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.miftah.tededexpert.core.data.source.local.entity.RemoteKeys
import com.miftah.tededexpert.core.data.source.local.entity.SaveStories
import com.miftah.tededexpert.core.data.source.local.entity.Stories


@Database(
    entities = [Stories::class, RemoteKeys::class, SaveStories::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun remoteKeysDao(): RemoteKeysDao

    abstract fun storiesDao(): StoriesDao

    abstract fun saveStoriesDao() : SaveStoriesDao
}
