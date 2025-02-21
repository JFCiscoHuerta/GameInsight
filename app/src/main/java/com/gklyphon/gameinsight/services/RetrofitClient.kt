package com.gklyphon.gameinsight.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Singleton object that provides a Retrofit client instance for making API requests.
 *
 * @author JFCiscoHuerta
 */
object RetrofitClient {

    /**
     * Base URL of the API.
     */
    private const val  BASE_URL = "https://api.rawg.io/api/"

    /**
     * Lazy-initialized instance of [IRetrofitService] for making requests.
     *
     * @return An implementation of [IRetrofitService] to interact with the API.
     */
    val instance: IRetrofitService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IRetrofitService::class.java)
    }

}