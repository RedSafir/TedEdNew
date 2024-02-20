package com.miftah.tededexpert.core.domain.usecases.authentication

import com.miftah.tededexpert.core.data.source.preference.model.UserModel
import com.miftah.tededexpert.core.domain.preference.PreferenceDataSource

class SaveSession(
    private val preferenceDataSource: PreferenceDataSource
) {
    suspend operator fun invoke(userModel: UserModel) =
        preferenceDataSource.saveSession(userModel)
}