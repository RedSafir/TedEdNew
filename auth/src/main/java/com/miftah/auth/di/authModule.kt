package com.miftah.auth.di

import com.miftah.auth.WelcomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    viewModel {
        WelcomeViewModel(get(), get(), get())
    }
}