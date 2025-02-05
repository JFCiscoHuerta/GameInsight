package com.gklyphon.gameinsight

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var fabMain: FloatingActionButton
    private lateinit var fabLogos: FloatingActionButton
    private lateinit var fabDlcs: FloatingActionButton
    private var isFabMenuOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        fabMain = findViewById(R.id.fab_main)
        fabLogos = findViewById(R.id.fab_logos)
        fabDlcs = findViewById(R.id.fab_dlcs)

        // Cargar animaciones
        val fabOpen: Animation = AnimationUtils.loadAnimation(this, R.anim.fab_open)
        val fabClose: Animation = AnimationUtils.loadAnimation(this, R.anim.fab_close)

        // Configurar el clic del botón principal
        fabMain.setOnClickListener {
            if (isFabMenuOpen) {
                // Cerrar el menú
                fabLogos.startAnimation(fabClose)
                fabDlcs.startAnimation(fabClose)
                fabLogos.visibility = View.GONE
                fabDlcs.visibility = View.GONE
            } else {
                // Abrir el menú
                fabLogos.startAnimation(fabOpen)
                fabDlcs.startAnimation(fabOpen)
                fabLogos.visibility = View.VISIBLE
                fabDlcs.visibility = View.VISIBLE
            }
            isFabMenuOpen = !isFabMenuOpen
        }

        // Configurar el clic del botón "Logos"
        fabLogos.setOnClickListener {
            // Acción para el botón "Logos"
        }

        // Configurar el clic del botón "DLCs"
        fabDlcs.setOnClickListener {
            // Acción para el botón "DLCs"
        }
    }

}