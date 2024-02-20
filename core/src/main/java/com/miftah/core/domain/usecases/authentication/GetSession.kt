package com.miftah.core.domain.usecases.authentication

import com.miftah.core.data.source.preference.model.UserModel
import com.miftah.core.domain.preference.PreferenceDataSource
import kotlinx.coroutines.flow.Flow

class GetSession(
    private val preferenceDataSource: PreferenceDataSource
) {
    operator fun invoke(): Flow<UserModel> =
        preferenceDataSource.getSession()
}