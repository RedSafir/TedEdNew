package com.miftah.core.domain.usecases.authentication

import com.miftah.core.data.source.preference.model.UserModel
import com.miftah.core.domain.preference.PreferenceDataSource

class SaveSession(
    private val preferenceDataSource: PreferenceDataSource
) {
    suspend operator fun invoke(userModel: UserModel) =
        preferenceDataSource.saveSession(userModel)
}