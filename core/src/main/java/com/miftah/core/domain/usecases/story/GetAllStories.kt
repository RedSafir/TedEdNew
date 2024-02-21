package com.miftah.core.domain.usecases.story

import com.miftah.core.domain.repository.AppRepository

class GetAllStories(
    private val appRepository: AppRepository
) {
    operator fun invoke() =
        appRepository.getAllStories()
}