package com.torredelrey.nursipedia

import android.os.Bundle
import android.widget.Toast
import android.content.Intent
import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import androidx.appcompat.app.AppCompatActivity
import com.torredelrey.nursipedia.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var enlace: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enlace = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(enlace.root)

        var emailValido: Boolean
        var contrasenaValida: Boolean

        // Firebase Auth
        auth = FirebaseAuth.getInstance()

        enlace.btnRegistrarseLogin.setOnClickListener {
            // Navegación a la pantalla de Registro
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
            finish()
        }

        enlace.btnIniciarSesionLogin.setOnClickListener {
            // Validación Email
            if (enlace.txtEmailLogin.text != null && !Constantes.FORMATO_EMAIL.toRegex().matches(enlace.txtEmailLogin.text)) {
                enlace.txtEmailLogin.error = Constantes.MENSAJE_ERROR_EMAIL
                emailValido = false
            } else {
                enlace.txtEmailLogin.error = null
                emailValido = true
            }

            // Validación Contraseña
            if (enlace.txtContrasenaLogin.text != null && !Constantes.FORMATO_CONTRASENA.toRegex().matches(enlace.txtContrasenaLogin.text)) {
                enlace.txtContrasenaLogin.error = Constantes.MENSAJE_ERROR_CONTRASENA
                contrasenaValida = false
            } else {
                enlace.txtContrasenaLogin.error = null
                contrasenaValida = true
            }

            // Login
            if (emailValido && contrasenaValida) {
                val email = enlace.txtEmailLogin.text.toString()
                val contrasena = enlace.txtContrasenaLogin.text.toString()
                login(email, contrasena)
            }
        }
    }

    private fun login(email: String, contrasena: String) {
        // Función signIn de la clase FirebaseAuth
        auth.signInWithEmailAndPassword(email, contrasena).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, Constantes.MENSAJE_INICIO_SESION_EXITOSO, Toast.LENGTH_SHORT).show()

                // SharedPreferences
                val sharedPreferences: SharedPreferences = getSharedPreferences(Constantes.CLAVE_PREFERENCIAS, Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString(Constantes.CLAVE_EMAIL, email)
                editor.putBoolean(Constantes.CLAVE_SESION_INICIADA, true)
                editor.apply()

                // Navegación a la pantalla de Inicio
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, Constantes.MENSAJE_ERROR_INICIO_SESION, Toast.LENGTH_SHORT).show()
            }
        }
    }

}