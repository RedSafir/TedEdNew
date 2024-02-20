package com.miftah.core.domain.preference

import com.miftah.core.data.source.preference.model.UserModel
import kotlinx.coroutines.flow.Flow

interface PreferenceDataSource {
    suspend fun saveSession(user: UserModel)

    fun getSession(): Flow<UserModel>

    suspend fun logout()
}