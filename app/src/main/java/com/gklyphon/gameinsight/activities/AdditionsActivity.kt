package com.gklyphon.gameinsight.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gklyphon.gameinsight.R
import com.gklyphon.gameinsight.adapters.DlcItem
import com.gklyphon.gameinsight.adapters.DlcsAdapter
import com.gklyphon.gameinsight.services.RetrofitClient
import com.gklyphon.gameinsight.BuildConfig
import com.gklyphon.gameinsight.adapters.DlcResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdditionsActivity : AppCompatActivity() {

    private val apiKey = BuildConfig.RAWG_API_KEY
    private lateinit var recyclerView: RecyclerView
    private lateinit var dlcAdapter: DlcsAdapter
    private var dlcList = mutableListOf<DlcItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dlcs)

        recyclerView = findViewById(R.id.recyclerViewDlc)
        recyclerView.layoutManager = LinearLayoutManager(this)
        dlcAdapter = DlcsAdapter(dlcList)
        recyclerView.adapter = dlcAdapter

        val gameId = intent.getIntExtra("GAME_ID", -1)
        if (gameId != -1) {
            fetchGameAdditions(gameId)
        } else {
            Toast.makeText(this, "Error: No se recibió un ID de juego", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchGameAdditions(gameId: Int) {
        RetrofitClient.instance.getGameAdditions(gameId, apiKey).enqueue(object : Callback<DlcResponse> {
            override fun onResponse(call: retrofit2.Call<DlcResponse>, response: Response<DlcResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { dlcResponse ->
                        dlcList.clear()
                        dlcList.addAll(dlcResponse.results)
                        dlcAdapter.notifyDataSetChanged()
                    }
                } else {
                    Toast.makeText(this@AdditionsActivity, "Error al obtener los DLCs", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: retrofit2.Call<DlcResponse>, t: Throwable) {
                Toast.makeText(this@AdditionsActivity, "Fallo en la conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
