package com.gklyphon.gameinsight.services

import com.gklyphon.gameinsight.adapters.AchievementsResponse
import com.gklyphon.gameinsight.adapters.DlcItem
import com.gklyphon.gameinsight.adapters.DlcResponse
import com.gklyphon.gameinsight.models.Achievement
import com.gklyphon.gameinsight.models.Game
import com.gklyphon.gameinsight.models.GameDetail
import com.gklyphon.gameinsight.models.GameResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interface defining the Retrofit service for API requests.
 *
 * @author JFCiscoHuerta
 */
interface IRetrofitService {

    /**
     * Retrieves a list of games from the API.
     *
     * @param apiKey The API key required for authentication.
     * @param page The page number to retrieve (defaults to 1).
     * @return A [Call] object containing a [GameResponse] with the list of games.
     */
    @GET("games")
    fun getGames(
        @Query("key") apiKey: String,
        @Query("page") page: Int = 1
    ): Call<GameResponse>

    /**
     * Retrieves detailed information about a specific game.
     *
     * @param id The unique identifier of the game.
     * @param apiKey The API key required for authentication.
     * @return A [Call] object containing a [GameDetail] with detailed information about the game.
     *
     */
    @GET("games/{id}")
    fun getGameDetails(
        @Path("id") id: Int,
        @Query("key") apiKey: String
    ): Call<GameDetail>

    /**
     * Retrieves a list of achievements for a specific game.
     *
     * This endpoint fetches the achievements associated with the game, which can include
     * details such as the name of the achievement, description, and unlock conditions.
     *
     * @param id The unique identifier of the game.
     * @param apiKey The API key required for authentication.
     * @return A [Call] object containing an [Achievement] with the game's achievements data.
     */
    @GET("games/{id}/achievements")
    fun getGameAchievements(
        @Path("id") id: Int,
        @Query("key") apiKey: String
    ): Call<AchievementsResponse>

    /**
     * Retrieves additional content (DLCs) for a specific game.
     *
     * @param id The unique identifier of the game.
     * @param apiKey The API key required for authentication.
     * @return A [Call] object containing a [GameDetail] with detailed information about the game's additions.
     */
    @GET("games/{id}/additions")
    fun getGameAdditions(
        @Path("id") id: Int,
        @Query("key") apiKey: String
    ): Call<DlcResponse>
}