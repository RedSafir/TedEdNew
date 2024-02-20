package com.miftah.core.data.source.remote

import com.google.gson.Gson
import com.miftah.core.data.source.remote.dto.LoginResponse
import com.miftah.core.data.source.remote.dto.ResultResponse
import com.miftah.core.data.source.remote.dto.StoriesResponse
import com.miftah.core.data.source.remote.network.ApiResponse
import com.miftah.core.data.source.remote.network.StoryService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import timber.log.Timber

class RemoteDataSource(private val storyService: StoryService) {

    fun login(email: String, password: String): Flow<ApiResponse<LoginResponse>> =
        flow {
            emit(ApiResponse.Loading)
            try {
                val client = storyService.login(email, password)
                emit(ApiResponse.Success(client))
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ResultResponse::class.java)
                val errorMessage = errorBody.message
                Timber.e(e)
                emit(ApiResponse.Error(errorMessage))
            }
        }.flowOn(Dispatchers.IO)

    fun register(name: String, email: String, password: String): Flow<ApiResponse<ResultResponse>> =
        flow {
            emit(ApiResponse.Loading)
            try {
                val client = storyService.register(name = name, email = email, password = password)
                emit(ApiResponse.Success(client))
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ResultResponse::class.java)
                val errorMessage = errorBody.message
                Timber.e(e)
                emit(ApiResponse.Error(errorMessage))
            }
        }.flowOn(Dispatchers.IO)

    fun getStories(page: Int, size: Int): Flow<ApiResponse<StoriesResponse>> =
        flow {
            emit(ApiResponse.Loading)
            try {
                val client = storyService.getStories(page, size)
                emit(ApiResponse.Success(client))
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ResultResponse::class.java)
                val errorMessage = errorBody.message
                Timber.e(e)
                emit(ApiResponse.Error(errorMessage))
            }
        }.flowOn(Dispatchers.IO)
}