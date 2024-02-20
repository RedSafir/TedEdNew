package com.miftah.core.domain.usecases.story

import androidx.paging.PagingData
import com.miftah.core.data.source.local.entity.Stories
import com.miftah.core.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow

class GetAllStories(
    private val appRepository: AppRepository
) {
    operator fun invoke(): Flow<PagingData<Stories>> =
        appRepository.getAllStories()
}