package com.miftah.core.domain.usecases.authentication

import com.miftah.core.domain.preference.PreferenceDataSource

class LogoutSession (
    private val preferenceDataSource: PreferenceDataSource
) {
    suspend operator fun invoke() =
        preferenceDataSource.logout()
}