package com.miftah.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.miftah.core.domain.model.StoryResult
import com.miftah.core.domain.usecases.story.DeleteStory
import com.miftah.core.domain.usecases.story.IsStorySave
import com.miftah.core.domain.usecases.story.SaveStory
import kotlinx.coroutines.launch

class DetailStoryViewModel(
    private val saveStoryCase: SaveStory,
    private val isStorySaveCase: IsStorySave,
    private val deleteStoryCase: DeleteStory
) : ViewModel() {

    private var _storyResult = MutableLiveData<StoryResult>()
    val storyResult: LiveData<StoryResult> = _storyResult

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


    fun isStorySave(id : String) = isStorySaveCase(id).asLiveData()

}