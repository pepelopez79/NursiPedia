package com.torredelrey.nursipedia

import android.os.Bundle
import android.widget.Toast
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import androidx.appcompat.app.AppCompatActivity
import com.torredelrey.nursipedia.databinding.ActivityRegistroBinding

class RegistroActivity : AppCompatActivity() {

    private lateinit var enlace: ActivityRegistroBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enlace = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(enlace.root)

        // Firebase Auth
        auth = FirebaseAuth.getInstance()

        var emailValido: Boolean
        var contrasenaValida: Boolean

        enlace.btnVolverRegistro.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        enlace.btnRegistrarseRegistro.setOnClickListener {
            // Validación Email
            if (enlace.txtEmailRegistro.text != null && !Constantes.FORMATO_EMAIL.toRegex().matches(enlace.txtEmailRegistro.text)) {
                enlace.txtEmailRegistro.error = Constantes.MENSAJE_ERROR_EMAIL
                emailValido = false
            } else {
                enlace.txtEmailRegistro.error = null
                emailValido = true
            }
            // Validación Repetir Email
            if (enlace.txtEmailRegistro.text.toString() != enlace.txtRepetirEmailRegistro.text.toString()) {
                enlace.txtRepetirEmailRegistro.error = Constantes.MENSAJE_ERROR_COINCIDENCIA_EMAIL
                emailValido = false
            } else {
                enlace.txtRepetirEmailRegistro.error = null
                emailValido = true
            }

            // Validación Contraseña
            if (enlace.txtContrasenaRegistro.text != null && !Constantes.FORMATO_CONTRASENA.toRegex().matches(enlace.txtContrasenaRegistro.text)) {
                enlace.txtContrasenaRegistro.error = Constantes.MENSAJE_ERROR_CONTRASENA
                contrasenaValida = false
            } else {
                enlace.txtContrasenaRegistro.error = null
                contrasenaValida = true
            }
            // Validación Repetir Contraseña
            if (enlace.txtContrasenaRegistro.text.toString() != enlace.txtRepetirContrasenaRegistro.text.toString()) {
                enlace.txtRepetirContrasenaRegistro.error = Constantes.MENSAJE_ERROR_COINCIDENCIA_CONTRASENA
                contrasenaValida = false
            } else {
                enlace.txtRepetirContrasenaRegistro.error = null
                contrasenaValida = true
            }

            // Registro
            if (!(enlace.txtEmailRegistro.text.toString().isEmpty() || enlace.txtRepetirEmailRegistro.text.toString().isEmpty() || enlace.txtContrasenaRegistro.text.toString().isEmpty() || enlace.txtRepetirContrasenaRegistro.text.toString().isEmpty())) {
                if (emailValido && contrasenaValida) {
                    val email = enlace.txtEmailRegistro.text.toString()
                    val password = enlace.txtContrasenaRegistro.text.toString()
                    registrarUsuario(email, password)
                }
            }
        }
    }

    private fun registrarUsuario(email: String, password: String) {
        // Función createUser de la clase FirebaseAuth
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, Constantes.MENSAJE_REGISTRO_EXITOSO, Toast.LENGTH_SHORT).show()

                // Navegación a la pantalla de Login
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, Constantes.MENSAJE_ERROR_REGISTRO, Toast.LENGTH_SHORT).show()
            }
        }
    }

}