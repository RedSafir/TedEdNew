package com.miftah.core.domain.usecases.authentication

import com.miftah.core.domain.preference.PreferenceDataSource

class GetSession(
    private val preferenceDataSource: PreferenceDataSource
) {
    operator fun invoke() =
        preferenceDataSource.getSession()
}