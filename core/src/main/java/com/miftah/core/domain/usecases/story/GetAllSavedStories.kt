package com.miftah.core.domain.usecases.story

import com.miftah.core.domain.repository.AppRepository

class GetAllSavedStories(
    private val appRepository: AppRepository
) {
    operator fun invoke() =
        appRepository.getAllSavedStories()
}