package com.miftah.tededexpert.core.domain.repository

import androidx.paging.PagingData
import com.miftah.tededexpert.core.data.source.local.entity.Stories
import com.miftah.tededexpert.core.data.source.remote.dto.LoginResponse
import com.miftah.tededexpert.core.data.source.remote.dto.ResultResponse
import com.miftah.tededexpert.core.data.source.remote.network.ApiResponse
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    fun userLogin(email: String, password: String): Flow<ApiResponse<LoginResponse>>

    fun userRegister(name: String, email: String, password: String): Flow<ApiResponse<ResultResponse>>

    fun getAllStories(): Flow<PagingData<Stories>>
}