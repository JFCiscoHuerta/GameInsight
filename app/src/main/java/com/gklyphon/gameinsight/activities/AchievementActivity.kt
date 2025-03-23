package com.gklyphon.gameinsight.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gklyphon.gameinsight.BuildConfig
import com.gklyphon.gameinsight.R
import com.gklyphon.gameinsight.models.Achievement
import com.gklyphon.gameinsight.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AchievementActivity : AppCompatActivity() {

    private val apiKey = BuildConfig.RAWG_API_KEY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dlcs)
        val gameId = intent.getIntExtra("GAME_ID", -1)

        RetrofitClient.instance.getGameAchievements(gameId, apiKey).enqueue(object : Callback<Achievement> {
            override fun onResponse(call: Call<Achievement>, response: Response<Achievement>) {
                if (response.isSuccessful) {
                    val achievement = response.body()
                    achievement?.let {}
                }
            }

            override fun onFailure(call: Call<Achievement>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

    }

}