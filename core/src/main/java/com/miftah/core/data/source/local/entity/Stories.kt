package com.miftah.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Stories")
data class Stories(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo("name")
    val name: String,

    @ColumnInfo("description")
    val description: String,

    @ColumnInfo("photoUrl")
    val photoUrl: String,

    @ColumnInfo("lat")
    val lat: Double? = null,

    @ColumnInfo("lon")
    val lon: Double? = null,
)