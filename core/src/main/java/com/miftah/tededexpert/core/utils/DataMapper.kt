package com.miftah.tededexpert.core.utils

import com.miftah.tededexpert.core.data.source.local.entity.Stories
import com.miftah.tededexpert.core.data.source.preference.model.UserModel
import com.miftah.tededexpert.core.data.source.remote.dto.ListStoryItem
import com.miftah.tededexpert.core.data.source.remote.dto.LoginResult

object DataMapper {
    fun ListStoryItem.listStoryToStories(): Stories {
        return Stories(
            id = id,
            name = name,
            description = description,
            photoUrl = photoUrl,
            lat = lat,
            lon = lon
        )
    }

    fun LoginResult.loginResultToUserModel(): UserModel {
        return UserModel(
            username = name,
            userId = userId,
            token = token
        )
    }

}