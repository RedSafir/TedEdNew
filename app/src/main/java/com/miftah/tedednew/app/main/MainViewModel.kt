package com.miftah.tedednew.app.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.miftah.core.data.source.preference.model.UserModel
import com.miftah.core.domain.model.StoryResult
import com.miftah.core.domain.usecases.authentication.GetSession
import com.miftah.core.domain.usecases.authentication.LogoutSession
import com.miftah.core.domain.usecases.story.DeleteStory
import com.miftah.core.domain.usecases.story.GetAllSavedStories
import com.miftah.core.domain.usecases.story.GetAllStories
import com.miftah.core.domain.usecases.story.IsStorySave
import com.miftah.core.domain.usecases.story.SaveStory
import kotlinx.coroutines.launch

class MainViewModel(
    private val getSessionCase: GetSession,
    private val logoutSessionCase: LogoutSession,
    private val getAllStoriesCase: GetAllStories,
    private val saveStoryCase: SaveStory,
    private val getAllSavedStoriesCase : GetAllSavedStories,
    private val isStorySaveCase: IsStorySave,
    private val deleteStoryCase: DeleteStory
) : ViewModel() {

    private var _storyResult = MutableLiveData<StoryResult>()
    val storyResult: LiveData<StoryResult> = _storyResult

    fun getSession(): LiveData<UserModel> = getSessionCase().asLiveData()

    fun removeSession() {
        viewModelScope.launch {
            logoutSessionCase()
        }
    }

    fun getAllStories() = getAllStoriesCase().asLiveData().cachedIn(viewModelScope)

    fun setStory(photoUrl : String, description: String, name: String, id : String) {
        val storyResult = StoryResult(
            photoUrl = photoUrl,
            description = description,
            name = name,
            id = id
        )
        _storyResult.postValue(storyResult)
    }

    fun saveStory() {
        viewModelScope.launch {
            storyResult.value?.let {
                saveStoryCase(it)
            }
        }
    }

    fun deleteStory() {
        viewModelScope.launch {
            storyResult.value?.let {
                deleteStoryCase(it.id)
            }
        }
    }

    fun getAllSavedStories() = getAllSavedStoriesCase().asLiveData()

    fun isStorySave(id : String) = isStorySaveCase(id).asLiveData()
}