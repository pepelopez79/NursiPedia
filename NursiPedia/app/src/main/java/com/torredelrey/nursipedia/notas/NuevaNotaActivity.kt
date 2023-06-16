package com.torredelrey.nursipedia.notas

import java.util.Date
import android.os.Bundle
import android.widget.Toast
import android.app.Activity
import com.google.gson.Gson
import android.text.Editable
import android.content.Intent
import android.content.Context
import java.text.SimpleDateFormat
import android.content.pm.ActivityInfo
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.torredelrey.nursipedia.Constantes
import com.torredelrey.nursipedia.databinding.ActivityNuevaNotaBinding

class NuevaNotaActivity : AppCompatActivity() {

    private val gson = Gson()
    private val intento = Intent()
    private lateinit var texto: String

    private val enlace: ActivityNuevaNotaBinding by lazy {
        ActivityNuevaNotaBinding.inflate(layoutInflater)
    }

    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(enlace.root)

        // No permitir la rotación de pantalla
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // Título de la pantalla
        supportActionBar!!.title = Constantes.TITULO_NUEVA_NOTA

        // Fecha actual
        val fechaActual = Date()
        val fechaActualFormateada = SimpleDateFormat(Constantes.FORMATO_FECHA).format(fechaActual)
        val editable = Editable.Factory.getInstance().newEditable(fechaActualFormateada)
        texto = editable.toString()

        enlace.btnGuardar.setColorFilter(1)
        enlace.btnVolver.setColorFilter(1)

        enlace.btnGuardar.setOnClickListener {
            val notaNueva = Nota()
            if(!enlace.txtTitulo.text.isNullOrEmpty() && !enlace.txtContenido.text.isNullOrEmpty()) {
                // SharedPreferences
                val sharedPreferences: SharedPreferences = getSharedPreferences(Constantes.CLAVE_PREFERENCIAS, Context.MODE_PRIVATE)
                val email = sharedPreferences.getString(Constantes.CLAVE_EMAIL, "")

                // Mostar Datos
                notaNueva.titulo = enlace.txtTitulo.text.toString()
                notaNueva.contenido = enlace.txtContenido.text.toString()
                notaNueva.fecha = texto
                notaNueva.emailUsuario = email.toString()

                // Creamos una nueva nota en FirebaseFirestore con el id de la nota en la tabla notas
                db.collection(Constantes.CLAVE_NOTAS).document(notaNueva.id).set(hashMapOf(Constantes.CLAVE_NOTA to notaNueva))

                // Devolver Nota en como Json
                val cadenaJson = gson.toJson(notaNueva)
                intento.putExtra(Constantes.CLAVE_NOTA, cadenaJson)
                setResult(Activity.RESULT_OK, intento)
                finish()
            } else {
                Toast.makeText(applicationContext, Constantes.MENSAJE_RELLENAR_CAMPOS, Toast.LENGTH_SHORT).show()
            }
        }

        enlace.btnVolver.setOnClickListener {
            // No devolver nada al pulsar Botón Volver
            setResult(Activity.RESULT_CANCELED, intento)
            finish()
        }
    }

}