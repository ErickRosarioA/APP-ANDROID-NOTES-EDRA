package com.devedra.androidnotesedra

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, true)

        window.statusBarColor = ContextCompat.getColor(
            this,
            R.color.colorPrimary
        )

        WindowInsetsControllerCompat(
            window,
            window.decorView
        ).isAppearanceLightStatusBars = false

        setContentView(R.layout.activity_main)
    }
}