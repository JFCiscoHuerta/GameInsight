package com.gklyphon.gameinsight.models

data class Game (
    val id: Int,
    val slug: String,
    val name: String,
    val released: String?,
    val tba: Boolean,
    val background_image: String?,
    val rating: Float,
    val rating_top: Int,
    val ratings_count: Int,
    val reviews_text_count: String,
    val added: Int,
    val metacritic: Int?,
    val playtime: Int,
    val suggestions_count: Int,
    val updated: String,
    val esrb_rating: EsrbRating?,
    val platforms: List<PlatformWrapper>,
    val genres: List<Genre>
)

data class EsrbRating (
    val id: Long,
    val slug: String,
    val name: String
)

data class PlatformWrapper (
    val platform: Platform,
    val released_at: String?,
    val requirements: Requirements?
)

data class Platform (
    val id: Long,
    val slug: String,
    val name: String
)

data class Requirements (
    val minimum: String?,
    val recommended: String?
)