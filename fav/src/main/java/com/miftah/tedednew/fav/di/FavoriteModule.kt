package com.miftah.tedednew.fav.di

import com.miftah.core.domain.usecases.story.DeleteStory
import com.miftah.core.domain.usecases.story.GetAllSavedStories
import com.miftah.core.domain.usecases.story.IsStorySave
import com.miftah.core.domain.usecases.story.SaveStory
import com.miftah.tedednew.fav.FavoriteStoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favModule = module {
    viewModel {
        FavoriteStoryViewModel(get())
    }
}