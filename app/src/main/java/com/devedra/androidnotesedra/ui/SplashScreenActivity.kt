package com.devedra.androidnotesedra.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.devedra.androidnotesedra.MainActivity
import com.devedra.androidnotesedra.R

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        // Agregar un temporizador para la pantalla de inicio
        Handler(Looper.getMainLooper()).postDelayed({
            // Iniciar la actividad principal
            startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            // Finalizar la actividad de la pantalla de inicio
            finish()
        }, 3000)
    }
}