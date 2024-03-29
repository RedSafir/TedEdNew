package com.miftah.core.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.miftah.core.data.source.local.LocalDataSource
import com.miftah.core.data.source.local.entity.RemoteKeysEntity
import com.miftah.core.data.source.local.entity.StoriesEntity
import com.miftah.core.data.source.remote.RemoteDataSource
import com.miftah.core.data.source.remote.network.ApiResponse
import com.miftah.core.utils.DataMapper.toStoriesEntity
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class StoryRemoteMediator(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : RemoteMediator<Int, StoriesEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, StoriesEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            var endOfPaginationReached = false
            remoteDataSource.getStories(page, state.config.pageSize)
                .collect { value ->
                    when (value) {
                        is ApiResponse.Success -> {
                            val listStory = value.data.story
                            endOfPaginationReached = listStory.isEmpty()

                            if (loadType == LoadType.REFRESH) {
                                localDataSource.deleteRemoteKeys()
                                localDataSource.deleteAllStories()
                            }
                            val prevKey = if (page == 1) null else page - 1
                            val nextKey = if (endOfPaginationReached) null else page + 1
                            val keys = listStory.map {
                                RemoteKeysEntity(id = it.id, prevKey = prevKey, nextKey = nextKey)
                            }
                            localDataSource.insertRemoteKeys(keys)
                            localDataSource.insertStory(listStory.map { it.toStoriesEntity() })
                        }

                        is ApiResponse.Error -> {

                        }

                        is ApiResponse.Loading -> {

                        }
                    }
                }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, StoriesEntity>): RemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            localDataSource.getRemoteKeyById(data.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, StoriesEntity>): RemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            localDataSource.getRemoteKeyById(data.id)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, StoriesEntity>): RemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                localDataSource.getRemoteKeyById(id)
            }
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}