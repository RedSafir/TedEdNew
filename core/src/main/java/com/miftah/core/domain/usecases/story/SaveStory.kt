package com.miftah.core.domain.usecases.story

import com.miftah.core.domain.model.StoryResult
import com.miftah.core.domain.repository.AppRepository

class SaveStory(
    private val appRepository: AppRepository
) {
    suspend operator fun invoke(storyResult: StoryResult) =
        appRepository.saveStory(storyResult)
}