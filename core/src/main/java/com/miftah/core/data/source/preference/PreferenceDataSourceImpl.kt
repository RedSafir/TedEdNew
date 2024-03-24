package com.miftah.core.data.source.preference

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.miftah.core.data.source.preference.model.UserModel
import com.miftah.core.domain.preference.PreferenceDataSource
import com.miftah.core.utils.Constants.DATA_STORAGE_PREFERENCE_IS_LOGIN
import com.miftah.core.utils.Constants.DATA_STORAGE_PREFERENCE_TOKEN
import com.miftah.core.utils.Constants.DATA_STORAGE_PREFERENCE_USERNAME
import com.miftah.core.utils.Constants.DATA_STORAGE_PREFERENCE_USER_ID
import com.securepreferences.SecurePreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PreferenceDataSourceImpl(
    context: Context
) : PreferenceDataSource {

    private var pref: SharedPreferences = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val spec = KeyGenParameterSpec.Builder(
            MasterKey.DEFAULT_MASTER_KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setKeySize(MasterKey.DEFAULT_AES_GCM_MASTER_KEY_SIZE)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .build()
        val masterKey = MasterKey.Builder(context)
            .setKeyGenParameterSpec(spec)
            .build()
        EncryptedSharedPreferences
            .create(
                context,
                "Session",
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
    } else {
        SecurePreferences(context)
    }

    private var editor: SharedPreferences.Editor = pref.edit()

    override suspend fun saveSession(user: UserModel) {
        editor.apply {
            this.putString(DATA_STORAGE_PREFERENCE_USER_ID, user.userId)
            this.putString(DATA_STORAGE_PREFERENCE_TOKEN, user.token)
            this.putString(DATA_STORAGE_PREFERENCE_USERNAME, user.username)
            this.putBoolean(DATA_STORAGE_PREFERENCE_IS_LOGIN, true)
            this.commit()
        }
    }

    override fun getSession(): Flow<UserModel> = flow {
        emit(
            UserModel(
                username = pref.getString(DATA_STORAGE_PREFERENCE_USERNAME, "") ?: "",
                userId = pref.getString(DATA_STORAGE_PREFERENCE_USER_ID, "") ?: "",
                token = pref.getString(DATA_STORAGE_PREFERENCE_TOKEN, "") ?: "",
                isLogin = pref.getBoolean(DATA_STORAGE_PREFERENCE_IS_LOGIN, false)
            )
        )
    }

    override suspend fun logout() {
        editor.clear()
        editor.commit()
    }
}