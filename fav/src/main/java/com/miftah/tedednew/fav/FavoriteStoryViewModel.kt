package com.miftah.tedednew.fav

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.miftah.core.domain.usecases.story.GetAllSavedStories

class FavoriteStoryViewModel(
    private val getAllSavedStoriesCase : GetAllSavedStories,
) : ViewModel() {

    fun getAllSavedStories() = getAllSavedStoriesCase().asLiveData()

}