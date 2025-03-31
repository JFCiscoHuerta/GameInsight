package com.gklyphon.gameinsight

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gklyphon.gameinsight.activities.DetailsActivity
import com.gklyphon.gameinsight.adapters.GameAdapter
import com.gklyphon.gameinsight.models.Game
import com.gklyphon.gameinsight.models.GameResponse
import com.gklyphon.gameinsight.services.RetrofitClient
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val apiKey = BuildConfig.RAWG_API_KEY

    private lateinit var recyclerView: RecyclerView
    private lateinit var gameAdapter: GameAdapter
    private val gameList = mutableListOf<Game>()

    private lateinit var fabMain: FloatingActionButton
    private lateinit var fabAchives: FloatingActionButton
    private lateinit var fabDlcs: FloatingActionButton
    private var isFabMenuOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        gameAdapter = GameAdapter(gameList)
        recyclerView.adapter = gameAdapter
        fetchGames()

        fabMain = findViewById(R.id.fab_main)
        fabAchives = findViewById(R.id.fab_avhives)
        fabDlcs = findViewById(R.id.fab_dlcs)
        configureView()

    }

    private fun configureView() {
        // Cargar animaciones
        val fabOpen: Animation = AnimationUtils.loadAnimation(this, R.anim.fab_open)
        val fabClose: Animation = AnimationUtils.loadAnimation(this, R.anim.fab_close)

        // Configurar el clic del botón principal
        fabMain.setOnClickListener {
            if (isFabMenuOpen) {
                // Cerrar el menú
                fabAchives.startAnimation(fabClose)
                fabDlcs.startAnimation(fabClose)
                fabAchives.visibility = View.GONE
                fabDlcs.visibility = View.GONE
            } else {
                // Abrir el menú
                fabAchives.startAnimation(fabOpen)
                fabDlcs.startAnimation(fabOpen)
                fabAchives.visibility = View.VISIBLE
                fabDlcs.visibility = View.VISIBLE
            }
            isFabMenuOpen = !isFabMenuOpen
        }

        // Configurar el clic del botón "Logos"
        fabAchives.setOnClickListener {
            // Acción para el botón "Logos"
        }

        // Configurar el clic del botón "DLCs"
        fabDlcs.setOnClickListener {
            // Acción para el botón "DLCs"
        }
    }

    /**
     * Fetches a list of games from the RAWG API and updates the RecyclerView.
     *
     * This method makes an asynchronous HTTP request to retrieve a list of games.
     * If the request is successful, the retrieved games are added to the game list
     * and displayed in the adapter. If the request fails, an error is logged.
     */
    private fun fetchGames() {

        /**
         * Makes a request to the RAWG API to fetch a list of games.
         *
         * @param apiKey - The API key required for authentication.
         */
        RetrofitClient.instance.getGames(apiKey).enqueue(object : Callback<GameResponse> {

            /**
             * Called when the HTTP request receives a response.
             *
             * @param call - The original HTTP request.
             * @param response - The received HTTP response.
             */
            override fun onResponse(call: Call<GameResponse>, response: Response<GameResponse>) {
                if (response.isSuccessful) {
                    response.body()?.results?.let { games ->
                        gameList.addAll(games)
                        gameAdapter.updateGames(gameList)
                    }
                }
            }

            /**
             * Called when the HTTP request fails due to a network error or other exception.
             *
             * @param call - The original HTTP request.
             * @param t - The error that caused the request to fail.
             */
            override fun onFailure(call: Call<GameResponse>, t: Throwable) {
                Log.e("API_ERROR", "Error: ${t.message}")
            }

        })
    }

}