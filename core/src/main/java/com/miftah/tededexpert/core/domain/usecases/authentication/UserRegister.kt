package com.miftah.tededexpert.core.domain.usecases.authentication

import com.miftah.tededexpert.core.data.source.remote.dto.ResultResponse
import com.miftah.tededexpert.core.data.source.remote.network.ApiResponse
import com.miftah.tededexpert.core.domain.repository.AppRepository
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