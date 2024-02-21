package com.miftah.core.domain.usecases.story

import com.miftah.core.domain.repository.AppRepository

class IsStorySave(
    private val appRepository: AppRepository
) {
    operator fun invoke(id: String) =
        appRepository.isStorySaved(id)
}