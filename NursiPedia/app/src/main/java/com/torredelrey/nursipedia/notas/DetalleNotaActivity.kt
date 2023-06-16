package com.torredelrey.nursipedia.notas

import java.util.*
import android.Manifest
import android.os.Bundle
import android.os.Handler
import com.google.gson.Gson
import android.widget.TimePicker
import android.app.TimePickerDialog
import com.torredelrey.nursipedia.R
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import androidx.core.app.NotificationCompat
import com.torredelrey.nursipedia.Constantes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import com.torredelrey.nursipedia.databinding.ActivityDetalleNotaBinding

class DetalleNotaActivity : AppCompatActivity() {

    private val gson = Gson()

    private val enlace: ActivityDetalleNotaBinding by lazy {
        ActivityDetalleNotaBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(enlace.root)

        // No permitir la rotación de pantalla
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // Título de la pantalla
        supportActionBar!!.title = Constantes.TITULO_DETALLE_NOTA

        val extras = intent.extras
        extras?.let{
            // Obtenemos la nota del Json recibido y mostramos sus datos
            val notaNueva = gson.fromJson(it.getString(Constantes.CLAVE_NOTA), Nota::class.java)
            enlace.lblTituloNota.text = notaNueva.titulo
            enlace.lblContenidoNota.text = notaNueva.contenido
            enlace.lblFechaNota.text = notaNueva.fecha

            enlace.btnNotificacion.setOnClickListener {
                // Notificación Recordatorio
                mostrarRecordatorio(notaNueva)
            }
        }
    }

    private fun mostrarRecordatorio(notaNueva: Nota) {
        val notificationId = 1
        val channelId = Constantes.CANAL_RECORDATORIO

        // Hora Actual
        val calendar = Calendar.getInstance()
        val horaActual = calendar.get(Calendar.HOUR_OF_DAY)
        val minutoActual = calendar.get(Calendar.MINUTE)

        // TimePickerDialog
        val timePickerDialog = TimePickerDialog(
            this,
            { _: TimePicker, hora: Int, minuto: Int ->
                // Calcula el tiempo hasta que se tenga que mostrar el recordatorio
                val tiempoActual = System.currentTimeMillis()
                val calendarRecordatorio = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, hora)
                    set(Calendar.MINUTE, minuto)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }
                val tiempoRecordatorio = calendarRecordatorio.timeInMillis

                // Mostar la notificación después del tiempo especificado
                Handler().postDelayed({
                    // Crear notificación
                    val builder = NotificationCompat.Builder(applicationContext, channelId)
                        .setSmallIcon(R.drawable.notificacion)
                        .setContentTitle(notaNueva.titulo)
                        .setContentText(notaNueva.contenido)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setAutoCancel(true)

                    // Mostrar Notificación
                    with(NotificationManagerCompat.from(applicationContext)) {
                        if (ActivityCompat.checkSelfPermission(
                                applicationContext,
                                Manifest.permission.POST_NOTIFICATIONS
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            return@postDelayed
                        }
                        notify(notificationId, builder.build())
                    }
                }, tiempoRecordatorio - tiempoActual)
            },
            horaActual,
            minutoActual,
            true
        )
        timePickerDialog.show()
    }

}

