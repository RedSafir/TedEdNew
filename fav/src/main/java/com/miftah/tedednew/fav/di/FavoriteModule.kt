package com.miftah.tedednew.fav.di

import com.miftah.tedednew.fav.FavoriteStoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favModule = module {
    viewModel {
        FavoriteStoryViewModel(get())
    }
}