package com.miftah.tedednew.app.di


import com.miftah.core.domain.usecases.authentication.GetSession
import com.miftah.core.domain.usecases.authentication.LogoutSession
import com.miftah.core.domain.usecases.authentication.SaveSession
import com.miftah.core.domain.usecases.authentication.UserLogin
import com.miftah.core.domain.usecases.authentication.UserRegister
import com.miftah.core.domain.usecases.story.GetAllStories
import com.miftah.tedednew.app.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<GetSession> {
        GetSession(get())
    }
    factory<LogoutSession> {
        LogoutSession(get())
    }
    factory<SaveSession> {
        SaveSession(get())
    }
    factory<UserRegister> {
        UserRegister(get())
    }
    factory<UserLogin> {
        UserLogin(get())
    }
    factory<GetAllStories> {
        GetAllStories(get())
    }
}

val viewModelModule = module {
    viewModel {
        MainViewModel(get(), get(), get())
    }
}