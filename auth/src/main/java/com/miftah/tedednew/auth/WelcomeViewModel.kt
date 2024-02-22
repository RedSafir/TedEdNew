package com.miftah.tedednew.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.miftah.core.data.source.preference.model.UserModel
import com.miftah.core.domain.usecases.authentication.SaveSession
import com.miftah.core.domain.usecases.authentication.UserLogin
import com.miftah.core.domain.usecases.authentication.UserRegister
import kotlinx.coroutines.launch

class WelcomeViewModel(
    private val userRegister: UserRegister,
    private val userLogin: UserLogin,
    private val saveSession: SaveSession
) : ViewModel() {


    fun userRegis(email: String, username: String, password: String) =
        userRegister(name = username, email = email, password = password).asLiveData()

    fun userLogIn(email: String, password: String) =
        userLogin(email, password).asLiveData()

    fun userSaveSession(user: UserModel) {
        viewModelScope.launch {
            saveSession(user)
        }
    }

}