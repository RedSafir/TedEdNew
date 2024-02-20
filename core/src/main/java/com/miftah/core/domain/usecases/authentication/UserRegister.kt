package com.miftah.core.domain.usecases.authentication

import com.miftah.core.data.source.remote.dto.ResultResponse
import com.miftah.core.data.source.remote.network.ApiResponse
import com.miftah.core.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow

class UserRegister(
    private val appRepository: AppRepository
) {
    operator fun invoke(
        name: String,
        email: String,
        password: String
    ): Flow<ApiResponse<ResultResponse>> =
        appRepository.userRegister(name, email, password)
}