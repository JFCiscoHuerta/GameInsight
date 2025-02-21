package com.gklyphon.gameinsight.services

import com.gklyphon.gameinsight.models.GameResponse
import retrofit2.Call
import retrofit2.http.GET
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
     * @return A response containing game data.
     */
    @GET("games")
    fun getGames(
        @Query("key") apiKey: String,
        @Query("page") page: Int = 1
    ): Call<GameResponse>

}