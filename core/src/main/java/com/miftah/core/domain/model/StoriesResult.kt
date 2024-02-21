package com.miftah.core.domain.model

data class StoriesResult(
    val error : Boolean,
    val message : String,
    val story : List<StoryResult> = emptyList()
)

data class StoryResult(
    val photoUrl: String,
    val name: String,
    val description: String,
    val id: String,
)
