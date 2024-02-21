package com.miftah.core.utils

import com.miftah.core.data.DataResult
import com.miftah.core.data.source.local.entity.StoriesEntity
import com.miftah.core.data.source.preference.model.UserModel
import com.miftah.core.data.source.remote.dto.ListStoryItemResponse
import com.miftah.core.data.source.remote.dto.LoginResponse
import com.miftah.core.data.source.remote.dto.LoginResultResponse
import com.miftah.core.data.source.remote.dto.RegisterResponse
import com.miftah.core.data.source.remote.dto.StoriesResponse
import com.miftah.core.data.source.remote.network.ApiResponse
import com.miftah.core.domain.model.LoginResult
import com.miftah.core.domain.model.LoginValueResult
import com.miftah.core.domain.model.RegisterResult
import com.miftah.core.domain.model.StoriesResult
import com.miftah.core.domain.model.StoryResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object DataMapper {
    fun StoryResult.toStoriesEntity(): StoriesEntity {
        return StoriesEntity(
            id = this.id,
            name = this.name,
            description = this.description,
            photoUrl = this.photoUrl,
        )
    }

    fun StoriesEntity.toStoriesResult() : StoryResult {
        return StoryResult(
            id = this.id,
            name = this.name,
            description = this.description,
            photoUrl = this.photoUrl
        )
    }

    fun LoginValueResult.toUserModel() : UserModel {
        return UserModel(
            username = this.name,
            userId = this.userId,
            token = this.token
        )
    }

    fun <T> Flow<ApiResponse<T>>.mapToDataResult(): Flow<DataResult<T>> {
        return this.map { apiResponse ->
            when (apiResponse) {
                is ApiResponse.Success -> DataResult.Success(apiResponse.data)
                is ApiResponse.Error -> DataResult.Error(apiResponse.errorMessage)
                is ApiResponse.Loading -> DataResult.Loading
            }
        }
    }

    fun RegisterResponse.toRegisterResult(): RegisterResult {
        return RegisterResult(error, message)
    }

    fun LoginResponse.toLoginResult(): LoginResult {
        return LoginResult(
            error = this.error,
            message = this.message,
            result = this.loginResult.toLoginValueResult()
        )
    }

    fun LoginResultResponse.toLoginValueResult(): LoginValueResult {
        return LoginValueResult(
            name,
            userId,
            token
        )
    }

    fun StoriesResponse.toStoriesResult(): StoriesResult {
        return StoriesResult(
            error = this.error,
            message = this.message,
            story = this.listStory.map { it.toListStory() }
        )
    }

    fun ListStoryItemResponse.toListStory(): StoryResult {
        return StoryResult(
            photoUrl = this.photoUrl,
            name = this.name,
            description = this.description,
            id = this.id
        )
    }
}