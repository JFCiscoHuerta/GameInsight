package com.gklyphon.gameinsight.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gklyphon.gameinsight.BuildConfig
import com.gklyphon.gameinsight.R
import com.gklyphon.gameinsight.adapters.DlcItem
import com.gklyphon.gameinsight.adapters.AchievementsAdapter
import com.gklyphon.gameinsight.adapters.AchievementsResponse
import com.gklyphon.gameinsight.adapters.DlcsAdapter
import com.gklyphon.gameinsight.models.Achievement
import com.gklyphon.gameinsight.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AchievementActivity : AppCompatActivity() {

    private val apiKey = BuildConfig.RAWG_API_KEY
    private lateinit var recyclerView: RecyclerView
    private lateinit var achievementsAdapter: AchievementsAdapter
    private var achievementList = mutableListOf<Achievement>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.achievements_view)

        recyclerView = findViewById(R.id.recyclerViewAchievements)
        recyclerView.layoutManager = LinearLayoutManager(this)
        achievementsAdapter = AchievementsAdapter(achievementList)
        recyclerView.adapter = achievementsAdapter

        val gameId = intent.getIntExtra("GAME_ID", -1)
        if (gameId != -1) {
            fetchGameAchievement(gameId)
        } else {
            Toast.makeText(this, "Error: No se recibió un ID de juego", Toast.LENGTH_SHORT).show()
        }


        }
    private fun fetchGameAchievement(gameId: Int) {
        RetrofitClient.instance.getGameAchievements(gameId, apiKey).enqueue(object : Callback<AchievementsResponse> {
            override fun onResponse(call: Call<AchievementsResponse>, response: Response<AchievementsResponse>) {
                if (response.isSuccessful) {
                    val achievement = response.body()
                    achievement?.let {dlcResponse ->
                        achievementList.clear()
                        achievementList.addAll(dlcResponse.results)
                        achievementsAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<AchievementsResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }
    }