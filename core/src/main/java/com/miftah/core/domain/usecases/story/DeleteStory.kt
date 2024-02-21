package com.miftah.core.domain.usecases.story

import com.miftah.core.domain.repository.AppRepository

class DeleteStory(
    private val appRepository: AppRepository
) {
    suspend operator fun invoke(id: String) =
        appRepository.deleteSavedStories(id)
}