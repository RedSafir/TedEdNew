package com.miftah.core.domain.usecases.authentication

import com.miftah.core.domain.repository.AppRepository

class UserLogin(
    private val appRepository: AppRepository
){
    operator fun invoke(email: String, password : String) =
        appRepository.userLogin(email, password)
}