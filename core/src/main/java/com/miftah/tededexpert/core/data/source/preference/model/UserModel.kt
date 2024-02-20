package com.miftah.tededexpert.core.data.source.preference.model

data class UserModel(
    val username: String,
    val userId : String,
    val token: String,
    val isLogin: Boolean = false
)
