package com.gklyphon.gameinsight

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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

    private lateinit var paginationLayout: LinearLayout
    private lateinit var previousButton: TextView
    private lateinit var nextButton: TextView
    private var currentPage = 1
    private var totalPages = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        gameAdapter = GameAdapter(gameList)
        recyclerView.adapter = gameAdapter

        paginationLayout = findViewById(R.id.pagination_layout)
        previousButton = findViewById(R.id.btn_previous)
        nextButton = findViewById(R.id.btn_next)

        fabMain = findViewById(R.id.fab_main)
        fabAchives = findViewById(R.id.fab_avhives)
        fabDlcs = findViewById(R.id.fab_dlcs)
        configureView()

        configureView()
        setupPagination()
        fetchGames(currentPage)

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
     *
     * @author JFCiscoHuerta
     */
    private fun fetchGames(page: Int) {

        /**
         * Makes a request to the RAWG API to fetch a list of games.
         *
         * @param apiKey - The API key required for authentication.
         */
        RetrofitClient.instance.getGames(apiKey, page = page).enqueue(object : Callback<GameResponse> {

            /**
             * Called when the HTTP request receives a response.
             *
             * @param call - The original HTTP request.
             * @param response - The received HTTP response.
             */
            override fun onResponse(call: Call<GameResponse>, response: Response<GameResponse>) {
                if (response.isSuccessful) {
                    response.body()?.results?.let { games ->
                        gameList.clear()
                        gameList.addAll(games)
                        gameAdapter.updateGames(gameList)
                        recyclerView.scrollToPosition(0)
                        setupPagination()
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
    /**
     * Configures and displays the pagination controls for navigating through pages of game data.
     *
     * This method dynamically generates pagination buttons based on the current page and total pages.
     * It ensures that only a range of pages (previous, current, next) are displayed for navigation.
     *
     * Behavior:
     * - When a page number is clicked, it updates the `currentPage` and reloads the data.
     * - The "Previous" button navigates to the previous page if available.
     * - The "Next" button navigates to the next page if available.
     *
     * @author JFCiscoHuerta
     */
    private fun setupPagination() {
        paginationLayout.removeAllViews()
        paginationLayout.addView(previousButton)

        val startPage = maxOf(1, currentPage - 1)
        val endPage = minOf(totalPages, currentPage + 1)

        for (i in startPage..endPage) {
            val pageTextView = TextView(this).apply {
                text = i.toString()
                textSize = 16f
                gravity = Gravity.CENTER
                setBackgroundResource(R.drawable.circle_background)
                setTextColor(ContextCompat.getColor(context, android.R.color.black))
                layoutParams = LinearLayout.LayoutParams(100, 100).apply {
                    marginEnd = 16
                }

                setOnClickListener {
                    currentPage = i
                    fetchGames(currentPage)
                }
            }

            paginationLayout.addView(pageTextView)
        }

        paginationLayout.addView(nextButton)

        previousButton.setOnClickListener {
            if (currentPage > 1) {
                currentPage--
                fetchGames(currentPage)
            }
        }

        nextButton.setOnClickListener {
            if (currentPage < totalPages) {
                currentPage++
                fetchGames(currentPage)
            }
        }
    }
}