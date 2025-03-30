package com.gklyphon.gameinsight.models

import com.google.gson.annotations.SerializedName

/**
 * Data class representing detailed information about a game.
 *
 * This class maps the JSON response from the RAWG API containing comprehensive
 * details about a specific game.
 *
 * @property id Unique identifier of the game.
 * @property slug URL-friendly name of the game.
 * @property name Display name of the game.
 * @property nameOriginal Original name of the game.
 * @property description Detailed description of the game.
 * @property metacritic Metacritic score of the game, nullable if not available.
 * @property released Release date of the game, nullable if not released.
 * @property tba Indicates if the release date is "To Be Announced".
 * @property updated The last updated date for the game's information.
 * @property backgroundImage URL of the game's background image.
 * @property website Official website URL of the game.
 * @property rating Average rating of the game.
 * @property ratingTop Maximum rating value.
 * @property ratingsCount Total number of ratings received.
 * @property suggestionsCount Number of suggestions based on user interactions.
 * @property metacriticUrl URL to the game's page on Metacritic.
 * @property esrbRating ESRB rating for age classification, nullable.
 * @property platforms List of platforms where the game is available.
 *
 * @author JFCiscoHuerta
 */
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

/**
 * Data class representing information about a platform where the game is available.
 *
 * @property platform The [Platform] object containing platform details.
 * @property releasedAt The release date of the game on this specific platform.
 * @property requirements Optional requirements for the platform.
 *
 * @author JFCiscoHuerta
 */
data class PlatformInfo(
    val platform: Platform,
    @SerializedName("released_at") val releasedAt: String?,
    val requirements: Requirements?
)
