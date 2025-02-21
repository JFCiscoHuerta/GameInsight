package com.gklyphon.gameinsight.activities

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.gklyphon.gameinsight.R

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details)

        val gameId = intent.getIntExtra("GAME_ID", -1)
        val gameName = intent.getStringExtra("GAME_NAME")

        // Mostrar el nombre del juego en un TextView
        val titleTextView: TextView = findViewById(R.id.game_title)
        titleTextView.text = gameName
    }
}