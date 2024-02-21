package com.miftah.core.domain.model

data class LoginResult(
    val error : Boolean,
    val message: String,
    val result : LoginValueResult?
)

data class LoginValueResult(
    val name: String,
    val userId: String,
    val token: String,
)
