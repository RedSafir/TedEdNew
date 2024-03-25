package com.miftah.tedednew.app.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.miftah.core.data.source.preference.model.UserModel
import com.miftah.core.domain.usecases.authentication.GetSession
import com.miftah.core.domain.usecases.authentication.LogoutSession
import com.miftah.core.domain.usecases.story.GetAllStories
import kotlinx.coroutines.launch

class MainViewModel(
    private val getSessionCase: GetSession,
    private val logoutSessionCase: LogoutSession,
    private val getAllStoriesCase: GetAllStories,
) : ViewModel() {

    fun getSession(): LiveData<UserModel> = getSessionCase().asLiveData()

    fun removeSession() {
        viewModelScope.launch {
            logoutSessionCase()
        }
    }

    fun getAllStories() = getAllStoriesCase().asLiveData().cachedIn(viewModelScope)
}