package com.haryop.haryomusicplayer.data.entities

import com.google.gson.annotations.SerializedName

data class ContentEntity(
    @SerializedName("wrapperType") val wrapperType: String = "",
    @SerializedName("kind") val kind: String = "",
    @SerializedName("artistId") val artistId: String = "",
    @SerializedName("collectionId") val collectionId: String = "",
    @SerializedName("trackId") val trackId: String = "",
    @SerializedName("artistName") val artistName: String = "",
    @SerializedName("collectionName") val collectionName: String = "",
    @SerializedName("trackName") val trackName: String = "",
    @SerializedName("collectionCensoredName") val collectionCensoredName: String = "",
    @SerializedName("trackCensoredName") val trackCensoredName: String = "",
    @SerializedName("artistViewUrl") val artistViewUrl: String = "",
    @SerializedName("collectionViewUrl") val collectionViewUrl: String = "",
    @SerializedName("trackViewUrl") val trackViewUrl: String = "",
    @SerializedName("previewUrl") val previewUrl: String = "",
    @SerializedName("artworkUrl30") val artworkUrl30: String = "",
    @SerializedName("artworkUrl60") val artworkUrl60: String = "",
    @SerializedName("artworkUrl100") val artworkUrl100: String = "",
    @SerializedName("collectionPrice") val collectionPrice: String = "",
    @SerializedName("trackPrice") val trackPrice: String = "",
    @SerializedName("releaseDate") val releaseDate: String = "",
    @SerializedName("collectionExplicitness") val collectionExplicitness: String = "",
    @SerializedName("trackExplicitness") val trackExplicitness: String = "",
    @SerializedName("discCount") val discCount: String = "",
    @SerializedName("discNumber") val discNumber: String = "",
    @SerializedName("trackCount") val trackCount: String = "",
    @SerializedName("trackNumber") val trackNumber: String = "",
    @SerializedName("trackTimeMillis") val trackTimeMillis: String = "",
    @SerializedName("country") val country: String = "",
    @SerializedName("currency") val currency: String = "",
    @SerializedName("primaryGenreName") val primaryGenreName: String = "",
    @SerializedName("isStreamable") val isStreamable: Boolean = false,
    var isPlaying: Boolean = false
)

