package com.gklyphon.gameinsight.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gklyphon.gameinsight.BuildConfig
import com.gklyphon.gameinsight.R
import com.gklyphon.gameinsight.models.GameDetail
import com.gklyphon.gameinsight.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdditionsActivity : AppCompatActivity() {

    private val apiKey = BuildConfig.RAWG_API_KEY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dlcs)
        val gameId = intent.getIntExtra("GAME_ID", -1)

        RetrofitClient.instance.getGameAdditions(gameId, apiKey).enqueue(object : Callback<GameDetail> {
            override fun onResponse(call: Call<GameDetail>, response: Response<GameDetail>) {
                if (response.isSuccessful) {
                    val additions = response.body()
                    additions?.let {}
                }
            }

            override fun onFailure(call: Call<GameDetail>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

    }

}