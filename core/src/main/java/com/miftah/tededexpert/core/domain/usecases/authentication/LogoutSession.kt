package com.miftah.tededexpert.core.domain.usecases.authentication

import com.miftah.tededexpert.core.domain.preference.PreferenceDataSource

class LogoutSession (
    private val preferenceDataSource: PreferenceDataSource
) {
    suspend operator fun invoke() =
        preferenceDataSource.logout()
}