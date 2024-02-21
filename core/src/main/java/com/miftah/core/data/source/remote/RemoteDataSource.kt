package com.miftah.core.data.source.remote

import com.google.gson.Gson
import com.miftah.core.data.source.remote.dto.RegisterResponse
import com.miftah.core.data.source.remote.network.ApiResponse
import com.miftah.core.data.source.remote.network.StoryService
import com.miftah.core.domain.model.LoginResult
import com.miftah.core.domain.model.RegisterResult
import com.miftah.core.domain.model.StoriesResult
import com.miftah.core.utils.DataMapper.toLoginResult
import com.miftah.core.utils.DataMapper.toRegisterResult
import com.miftah.core.utils.DataMapper.toStoryResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import timber.log.Timber

class RemoteDataSource(private val storyService: StoryService) {

    fun login(email: String, password: String): Flow<ApiResponse<LoginResult>> =
        flow {
            emit(ApiResponse.Loading)
            try {
                val client = storyService.login(email, password).toLoginResult()
                emit(ApiResponse.Success(client))
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, RegisterResponse::class.java)
                val errorMessage = errorBody.message
                Timber.e(e)
                emit(ApiResponse.Error(errorMessage))
            }
        }.flowOn(Dispatchers.IO)

    fun register(name: String, email: String, password: String): Flow<ApiResponse<RegisterResult>> =
        flow {
            emit(ApiResponse.Loading)
            try {
                val client = storyService.register(name = name, email = email, password = password).toRegisterResult()
                emit(ApiResponse.Success(client))
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, RegisterResponse::class.java)
                val errorMessage = errorBody.message
                Timber.e(e)
                emit(ApiResponse.Error(errorMessage))
            }
        }.flowOn(Dispatchers.IO)

    fun getStories(page: Int, size: Int): Flow<ApiResponse<StoriesResult>> =
        flow {
            emit(ApiResponse.Loading)
            try {
                val client = storyService.getStories(page, size).toStoryResult()
                emit(ApiResponse.Success(client))
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, RegisterResponse::class.java)
                val errorMessage = errorBody.message
                Timber.e(e)
                emit(ApiResponse.Error(errorMessage))
            }
        }.flowOn(Dispatchers.IO)
}