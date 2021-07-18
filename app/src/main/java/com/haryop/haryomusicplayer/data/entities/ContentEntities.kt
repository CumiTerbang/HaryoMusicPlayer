package com.haryop.haryomusicplayer.data.entities

import com.google.gson.annotations.SerializedName

data class ContentEntities(
    @SerializedName("resultCount") val resultCount: String,
    @SerializedName("results") val results: List<ContentEntity>
)
