package com.miftah.tededexpert.core.domain.usecases.authentication

import com.miftah.tededexpert.core.data.source.remote.dto.LoginResponse
import com.miftah.tededexpert.core.data.source.remote.network.ApiResponse
import com.miftah.tededexpert.core.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow

class UserLogin(
    private val appRepository: AppRepository
){
    operator fun invoke(email: String, password : String) : Flow<ApiResponse<LoginResponse>> =
        appRepository.userLogin(email, password)
}