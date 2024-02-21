package com.miftah.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.miftah.core.data.source.local.entity.RemoteKeysEntity
import com.miftah.core.data.source.local.entity.SavedStoriesEntity
import com.miftah.core.data.source.local.entity.StoriesEntity


@Database(
    entities = [StoriesEntity::class, RemoteKeysEntity::class, SavedStoriesEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun remoteKeysDao(): RemoteKeysDao

    abstract fun storiesDao(): StoriesDao

    abstract fun saveStoriesDao(): SaveStoriesDao
}
