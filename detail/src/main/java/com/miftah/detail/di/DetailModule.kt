package com.miftah.detail.di

import com.miftah.detail.DetailStoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailModule = module {
    viewModel {
        DetailStoryViewModel(get(), get(), get())
    }
}