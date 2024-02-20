package com.miftah.tededexpert.core.domain.usecases.story

import androidx.paging.PagingData
import com.miftah.tededexpert.core.data.source.local.entity.Stories
import com.miftah.tededexpert.core.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow

class GetAllStories(
    private val appRepository: AppRepository
) {
    operator fun invoke(): Flow<PagingData<Stories>> =
        appRepository.getAllStories()
}