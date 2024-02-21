package com.miftah.core.domain.repository

import androidx.paging.PagingData
import com.miftah.core.data.DataResult
import com.miftah.core.domain.model.LoginResult
import com.miftah.core.domain.model.RegisterResult
import com.miftah.core.domain.model.StoryResult
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    fun userLogin(email: String, password: String): Flow<DataResult<LoginResult>>

    fun userRegister(name: String, email: String, password: String): Flow<DataResult<RegisterResult>>

    fun getAllStories(): Flow<PagingData<StoryResult>>

    suspend fun saveStory(storyResult: StoryResult)

    fun isStorySaved(id : String) : Flow<Boolean>

    fun getAllSavedStories() : Flow<List<StoryResult>>

    suspend fun deleteSavedStories(id : String)
}