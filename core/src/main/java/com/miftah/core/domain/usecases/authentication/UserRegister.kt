package com.miftah.core.domain.usecases.authentication

import com.miftah.core.domain.repository.AppRepository

class UserRegister(
    private val appRepository: AppRepository
) {
    operator fun invoke(name: String, email: String, password: String) =
        appRepository.userRegister(name, email, password)
}