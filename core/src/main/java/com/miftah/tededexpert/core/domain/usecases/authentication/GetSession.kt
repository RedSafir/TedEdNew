package com.miftah.tededexpert.core.domain.usecases.authentication

import com.miftah.tededexpert.core.data.source.preference.model.UserModel
import com.miftah.tededexpert.core.domain.preference.PreferenceDataSource
import kotlinx.coroutines.flow.Flow

class GetSession(
    private val preferenceDataSource: PreferenceDataSource
) {
    operator fun invoke(): Flow<UserModel> =
        preferenceDataSource.getSession()
}