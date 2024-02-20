package com.miftah.core.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.miftah.core.data.source.local.LocalDataSource
import com.miftah.core.data.source.local.entity.Stories
import com.miftah.core.data.source.remote.RemoteDataSource
import com.miftah.core.data.source.remote.dto.LoginResponse
import com.miftah.core.data.source.remote.dto.ResultResponse
import com.miftah.core.data.source.remote.network.ApiResponse
import com.miftah.core.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow

class AppRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : AppRepository {

    override fun userLogin(email: String, password: String): Flow<ApiResponse<LoginResponse>> =
        remoteDataSource.login(email, password)

    override fun userRegister(
        name: String,
        email: String,
        password: String
    ): Flow<ApiResponse<ResultResponse>> =
        remoteDataSource.register(name, email, password)

    override fun getAllStories(): Flow<PagingData<Stories>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(localDataSource, remoteDataSource),
            pagingSourceFactory = {
                localDataSource.getAllStories()
            }
        ).flow
    }
}