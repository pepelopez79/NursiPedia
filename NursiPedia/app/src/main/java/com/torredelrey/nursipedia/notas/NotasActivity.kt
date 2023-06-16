package com.torredelrey.nursipedia.notas

import android.os.Bundle
import android.view.Gravity
import android.app.Activity
import android.widget.Toast
import com.google.gson.Gson
import android.content.Intent
import android.text.TextUtils
import android.content.Context
import android.widget.TextView
import android.graphics.Typeface
import android.widget.LinearLayout
import com.torredelrey.nursipedia.R
import android.content.pm.ActivityInfo
import android.content.SharedPreferences
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import androidx.activity.result.contract.ActivityResultContracts
import com.torredelrey.nursipedia.Constantes
import com.torredelrey.nursipedia.databinding.ActivityNotasBinding

class NotasActivity : AppCompatActivity() {

    private val gson = Gson()

    private val enlace: ActivityNotasBinding by lazy {
        ActivityNotasBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(enlace.root)

        // No permitir la rotación de pantalla
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // Título de la pantalla
        supportActionBar!!.title = Constantes.TITULO_NOTAS

        enlace.btnNuevaNota.setColorFilter(1)

        // SharedPreferences
        val sharedPreferences: SharedPreferences =
            getSharedPreferences(Constantes.CLAVE_PREFERENCIAS, Context.MODE_PRIVATE)
        val email = sharedPreferences.getString(Constantes.CLAVE_EMAIL, "")

        if (email != null) {
            cargarNotasDeFirebase(email)
        }

        val crearNuevaNota = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    // Si recibimos OK, guardamos los datos Gson en una Nota Nueva
                    val notaNueva = gson.fromJson(it.data?.getStringExtra(Constantes.CLAVE_NOTA), Nota::class.java)
                    val nuevaNota = Nota(
                        notaNueva.id,
                        notaNueva.titulo,
                        notaNueva.contenido,
                        notaNueva.fecha,
                        notaNueva.emailUsuario
                    )
                    // Creamos la celda de la tabla con los datos
                    crearCelda(nuevaNota)
                }
            }

        enlace.btnNuevaNota.setOnClickListener {
            // Navegación a pantalla Nueva Nota
            val intento = Intent(this, NuevaNotaActivity::class.java)
            crearNuevaNota.launch(intento)
        }
    }

    private fun crearCelda(nota: Nota) {
        // Estilo del contenido de la celda por código
        val txtTituloNota = TextView(this).apply {
            text = nota.titulo
            typeface = Typeface.DEFAULT_BOLD
            textSize = 18.0f
            setTextColor(ContextCompat.getColor(applicationContext, R.color.negro))
            gravity = Gravity.START or Gravity.CENTER_VERTICAL
            maxLines = 1
            ellipsize = TextUtils.TruncateAt.END
        }

        val txtContenidoNota = TextView(this).apply {
            gravity = Gravity.START or Gravity.CENTER_VERTICAL
            text = nota.contenido
            textSize = 14.0f
            setTextColor(
                ContextCompat.getColor(
                    applicationContext,
                    androidx.appcompat.R.color.material_blue_grey_800
                )
            )
            maxLines = 1
            ellipsize = TextUtils.TruncateAt.END
        }

        val btnEliminarNota = TextView(this).apply {
            setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.cancelar, 0, 0, 0)
            gravity = Gravity.CENTER_VERTICAL
            val marginLayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                0.5f
            )
            marginLayoutParams.setMargins(0, 16, 16, 0)
            layoutParams = marginLayoutParams
        }

        val linearLayoutContenido = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            val marginLayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                10.0f
            )
            marginLayoutParams.setMargins(48, 32, 32, 48)
            layoutParams = marginLayoutParams
            addView(txtTituloNota)
            addView(txtContenidoNota)
        }

        val linearLayout = LinearLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
            )
            orientation = LinearLayout.HORIZONTAL
            val marginLayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            marginLayoutParams.setMargins(0, 0, 0, 48)
            layoutParams = marginLayoutParams
            background = resources.getDrawable(R.drawable.estilo_secciones, null)
            addView(linearLayoutContenido)
            addView(btnEliminarNota)
        }

        // Al pulsar la celda
        linearLayoutContenido.setOnClickListener {
            val cadenaJson = gson.toJson(nota)

            // Navegación a la pantalla de Detalle Nota
            val intentoDetalle = Intent(this, DetalleNotaActivity::class.java)
            intentoDetalle.putExtra(Constantes.CLAVE_NOTA, cadenaJson)
            startActivity(intentoDetalle)
        }

        // Al pulsar la x
        btnEliminarNota.setOnClickListener {
            eliminarNotaDeFirebase(nota)
            enlace.notas.removeView(linearLayout)
        }
        enlace.notas.addView(linearLayout)
    }

    private fun cargarNotasDeFirebase(email: String) {
        // Obtenemos una instancia de Firebase y realizamos una consulta de la tabla nota filtrando por email
        val db = FirebaseFirestore.getInstance()
        db.collection(Constantes.CLAVE_NOTAS)
            .whereEqualTo(Constantes.CLAVE_NOTA_EMAIL_USUARIO, email)
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot.documents) {
                    val notaData = document.get(Constantes.CLAVE_NOTA) as HashMap<*, *>?
                    val nota = notaData?.let { Nota().apply { fromHashMap(it) } }
                    if (nota != null) {
                        crearCelda(nota)
                    }
                }
            }
            .addOnFailureListener {
                // En caso de no encontrar notas nos aparece un mensaje
                Toast.makeText(applicationContext, Constantes.MENSAJE_ERROR_CARGAR_NOTAS, Toast.LENGTH_SHORT).show()
            }
    }

    private fun eliminarNotaDeFirebase(nota: Nota) {
        // Filtramos por el id de la nota en la tabla notas de FirebaseFirestore y la borramos
        val db = FirebaseFirestore.getInstance()
        val notasRef = db.collection(Constantes.CLAVE_NOTAS)
        notasRef.whereEqualTo(Constantes.CLAVE_NOTA_ID, nota.id)
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot.documents) {
                    document.reference.delete()
                        .addOnSuccessListener {
                            // Si funciona correctamente se elimina la nota de la tabla notas en FirebaseFirestore
                            Toast.makeText(applicationContext, Constantes.MENSAJE_NOTA_ELIMINADA, Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            // En caso de no poder eliminar la nota nos muestra un mensaje y no ocurre nada en FirebaseFirestore
                            Toast.makeText(applicationContext, Constantes.MENSAJE_ERROR_ELIMINAR_NOTA, Toast.LENGTH_SHORT).show()
                        }
                }
            }
            .addOnFailureListener {
                // En caso de no encontrar la nota nos muestra un mensaje y no ocurre nada en FirebaseFirestore
                Toast.makeText(applicationContext, Constantes.MENSAJE_BUSCAR_NOTA, Toast.LENGTH_SHORT).show()
            }
    }

}