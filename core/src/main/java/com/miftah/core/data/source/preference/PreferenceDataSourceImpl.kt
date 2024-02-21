package com.miftah.core.data.source.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.miftah.core.data.source.preference.model.UserModel
import com.miftah.core.domain.preference.PreferenceDataSource
import com.miftah.core.utils.Constants.DATA_STORAGE_PREFERENCE_IS_LOGIN
import com.miftah.core.utils.Constants.DATA_STORAGE_PREFERENCE_TOKEN
import com.miftah.core.utils.Constants.DATA_STORAGE_PREFERENCE_USERNAME
import com.miftah.core.utils.Constants.DATA_STORAGE_PREFERENCE_USER_ID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val readOnlyProperty = preferencesDataStore(name = "access")

val Context.dataStore: DataStore<Preferences> by readOnlyProperty

class PreferenceDataSourceImpl(
    private val dataStore: DataStore<Preferences>
) : PreferenceDataSource {

    override suspend fun saveSession(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[USER_ID] = user.userId
            preferences[TOKEN_KEY] = user.token
            preferences[USERNAME] = user.username
            preferences[IS_LOGIN_KEY] = true
        }
    }

    override fun getSession(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[USERNAME] ?: "",
                preferences[USER_ID] ?: "",
                preferences[TOKEN_KEY] ?: "",
                preferences[IS_LOGIN_KEY] ?: false
            )
        }
    }

    override suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    private companion object {
        private val USERNAME = stringPreferencesKey(DATA_STORAGE_PREFERENCE_USERNAME)
        private val USER_ID = stringPreferencesKey(DATA_STORAGE_PREFERENCE_USER_ID)
        private val TOKEN_KEY = stringPreferencesKey(DATA_STORAGE_PREFERENCE_TOKEN)
        private val IS_LOGIN_KEY = booleanPreferencesKey(DATA_STORAGE_PREFERENCE_IS_LOGIN)
    }
}