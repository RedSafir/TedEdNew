package com.miftah.core.utils

import com.miftah.core.data.source.local.entity.Stories
import com.miftah.core.data.source.preference.model.UserModel
import com.miftah.core.data.source.remote.dto.ListStoryItem
import com.miftah.core.data.source.remote.dto.LoginResult

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