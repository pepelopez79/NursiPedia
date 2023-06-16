package com.torredelrey.nursipedia

import android.os.Looper
import android.os.Bundle
import android.os.Handler
import android.content.Intent
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity

class PantallaCarga : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_carga)

        // SharedPreferences
        val sharedPreferences: SharedPreferences = getSharedPreferences(Constantes.CLAVE_PREFERENCIAS, Context.MODE_PRIVATE)
        val sesionIniciada: Boolean = sharedPreferences.getBoolean(Constantes.CLAVE_SESION_INICIADA, false)

        // Retraso de 2 segundos
        Handler(Looper.getMainLooper()).postDelayed( {
            if (sesionIniciada) {
                // Muestra la pantalla Principal
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                // Muestra la pantalla de Login
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            // Animación de desvanecimiento creada y guardada en la carpeta anim
            overridePendingTransition(R.anim.desvanecimiento, R.anim.desvanecimiento)
            finish()
        }, 2000)
    }

}