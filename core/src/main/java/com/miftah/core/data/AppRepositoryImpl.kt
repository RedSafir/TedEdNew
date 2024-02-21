package com.miftah.core.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.miftah.core.data.source.local.LocalDataSource
import com.miftah.core.data.source.remote.RemoteDataSource
import com.miftah.core.domain.model.LoginResult
import com.miftah.core.domain.model.RegisterResult
import com.miftah.core.domain.model.StoryResult
import com.miftah.core.domain.repository.AppRepository
import com.miftah.core.utils.DataMapper.mapToDataResult
import com.miftah.core.utils.DataMapper.toSavedStoriesEntity
import com.miftah.core.utils.DataMapper.toStoryResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : AppRepository {

    override fun userLogin(email: String, password: String): Flow<DataResult<LoginResult>> =
        remoteDataSource.login(email, password).mapToDataResult()

    override fun userRegister(
        name: String,
        email: String,
        password: String
    ): Flow<DataResult<RegisterResult>> =
        remoteDataSource.register(name, email, password).mapToDataResult()

    override fun getAllStories(): Flow<PagingData<StoryResult>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(localDataSource, remoteDataSource),
            pagingSourceFactory = {
                localDataSource.getAllStories()
            }
        ).flow.map { pagingData ->
            pagingData.map { entity ->
                entity.toStoryResult()
            }
        }
    }

    override suspend fun saveStory(storyResult: StoryResult) =
        localDataSource.saveStory(storyResult.toSavedStoriesEntity())

    override fun isStorySaved(id: String): Flow<Boolean> =
        localDataSource.isStoryFav(id)

    override fun getAllSavedStories(): Flow<List<StoryResult>> =
        localDataSource.getAllSavedStories().map { listStories ->
            listStories.map { savedStoriesEntity ->
                savedStoriesEntity.toStoryResult()
            }
        }

    override suspend fun deleteSavedStories(id : String) =
        localDataSource.deleteSaveStory(id)
}