package com.gklyphon.gameinsight.activities

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.gklyphon.gameinsight.BuildConfig
import com.gklyphon.gameinsight.R
import com.gklyphon.gameinsight.models.Game
import com.gklyphon.gameinsight.models.GameDetail
import com.gklyphon.gameinsight.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsActivity : AppCompatActivity() {

    private val apiKey = BuildConfig.RAWG_API_KEY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details)

        val gameId = intent.getIntExtra("GAME_ID", -1)

        val gameImage = findViewById<ImageView>(R.id.game_image)
        val gameDate = findViewById<TextView>(R.id.game_date)
        val gameConsoles = findViewById<LinearLayout>(R.id.game_consoles)
        val gameTitle = findViewById<TextView>(R.id.game_title)
        val gameRatingTitle = findViewById<TextView>(R.id.game_rating_title)
        val gameTotalRatings = findViewById<TextView>(R.id.game_total_ratings)
        val gamePopularityRank = findViewById<TextView>(R.id.game_popularity_rank)
        val gameGenre = findViewById<TextView>(R.id.game_genre)
        val gameTopRank = findViewById<TextView>(R.id.game_top_rank)
        val gameTopYear = findViewById<TextView>(R.id.game_top_year)
        val gameDescription = findViewById<TextView>(R.id.game_description)
        val gameReleaseDate = findViewById<TextView>(R.id.game_release_date)
        val gamePlatform = findViewById<TextView>(R.id.game_platforms)
        val gameMinRequirements = findViewById<TextView>(R.id.min_requirements)
        val gameRequirements = findViewById<TextView>(R.id.requirements)

        RetrofitClient.instance.getGameDetails(gameId, apiKey).enqueue(object: Callback<GameDetail> {
            override fun onResponse(call: Call<GameDetail>, response: Response<GameDetail>) {
                if (response.isSuccessful) {
                    val game = response.body()
                    game?.let {
                        findViewById<TextView>(R.id.game_title).text = it.name
                        Glide.with(this@DetailsActivity).load(it.backgroundImage).into(gameImage)
                        gameDate.text = it.released
                        gameTitle.text = it.name
                        gameRatingTitle.text = it.rating.toString()
                        gameTotalRatings.text = it.ratingsCount.toString()
                        gamePopularityRank.text = it.ratingTop.toString()
                        gameTopRank.text = "#" + it.ratingTop.toString()
                        gameDescription.text = it.description
                        gameReleaseDate.text = it.released
                        gamePlatform.text = it.platforms?.map { platform -> platform.platform.name }?.joinToString(", ")
                        gameMinRequirements.text = it.platforms?.map { platform -> platform.requirements?.minimum }.toString()
                        gameRequirements.text = it.platforms?.map { platform -> platform.requirements?.recommended }.toString()

                    }
                }
            }

            override fun onFailure(call: Call<GameDetail>, t: Throwable) {

            }
        })

    }
}