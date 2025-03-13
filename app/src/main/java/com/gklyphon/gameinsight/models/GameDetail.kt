package com.gklyphon.gameinsight.models

import com.google.gson.annotations.SerializedName

data class GameDetail (
    val id: Int,
    val slug: String,
    val name: String,
    @SerializedName("name_original") val nameOriginal: String,
    val description: String,
    val metacritic: Int?,
    val released: String?,
    val tba: Boolean,
    val updated: String?,
    @SerializedName("background_image") val backgroundImage: String?,
    @SerializedName("website") val website: String?,
    val rating: Float,
    @SerializedName("rating_top") val ratingTop: Int,
    @SerializedName("ratings_count") val ratingsCount: Int,
    @SerializedName("suggestions_count") val suggestionsCount: Int,
    @SerializedName("metacritic_url") val metacriticUrl: String?,
    @SerializedName("esrb_rating") val esrbRating: EsrbRating?,
    val platforms: List<PlatformInfo>?
)

data class PlatformInfo(
    val platform: Platform,
    @SerializedName("released_at") val releasedAt: String?,
    val requirements: Requirements?
)
