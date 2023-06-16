package com.torredelrey.nursipedia

import android.os.Looper
import android.os.Bundle
import android.view.Menu
import android.os.Handler
import android.widget.Toast
import android.view.MenuItem
import android.content.Intent
import android.content.Context
import android.animation.ObjectAnimator
import androidx.navigation.NavController
import android.content.SharedPreferences
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.torredelrey.nursipedia.notas.NotasActivity
import androidx.navigation.ui.setupActionBarWithNavController
import com.torredelrey.nursipedia.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), Utilidades {

    private lateinit var enlace: ActivityMainBinding
    private lateinit var controladorNavegacion: NavController
    private var primerInicio = true
    private var cerrarApp = false

    private lateinit var animacionSalidaMenuInferior: ObjectAnimator
    private lateinit var animacionEntradaMenuInferior: ObjectAnimator
    private lateinit var animacionSalidaBotonNotas: ObjectAnimator
    private lateinit var animacionEntradaBotonNotas: ObjectAnimator

    private lateinit var navView: BottomNavigationView
    private val navController by lazy {
        findNavController(R.id.container)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enlace = ActivityMainBinding.inflate(layoutInflater)
        setContentView(enlace.root)

        enlace.btnNotas.setColorFilter(1)

        enlace.btnNotas.setOnClickListener {
            // Navegación a pantalla Notas
            val intento = Intent(this, NotasActivity::class.java)
            startActivity(intento)
        }

        // Animaciones
        val anchoPantalla = resources.displayMetrics.widthPixels
        animacionSalidaBotonNotas = ObjectAnimator.ofFloat(enlace.btnNotas, Constantes.ANIMACION_EJE_X, anchoPantalla.toFloat())
        animacionEntradaBotonNotas = ObjectAnimator.ofFloat(enlace.btnNotas, Constantes.ANIMACION_EJE_X, 0f)
        animacionSalidaBotonNotas.duration = 1000
        animacionEntradaBotonNotas.duration = 500

        val altoPantalla = resources.displayMetrics.heightPixels
        animacionSalidaMenuInferior = ObjectAnimator.ofFloat(enlace.menuInferior, Constantes.ANIMACION_EJE_Y, altoPantalla.toFloat())
        animacionEntradaMenuInferior = ObjectAnimator.ofFloat(enlace.menuInferior, Constantes.ANIMACION_EJE_Y, 0f)
        animacionSalidaMenuInferior.duration = 1000
        animacionEntradaMenuInferior.duration = 500

        // Menú Superior
        val fragmentoNavegacion = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        controladorNavegacion = fragmentoNavegacion.navController
        setupActionBarWithNavController(controladorNavegacion)

        // Menú Inferior
        navView = enlace.menuInferior
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_medicamentos, R.id.navigation_formulas))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        // Para que se inicie en Fragmento Fórmulas
        navView.selectedItemId = R.id.navigation_formulas
    }

    // Menú Superior
    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        if (!(navController.currentDestination?.id == R.id.navigation_medicamentos || navController.currentDestination?.id == R.id.navigation_formulas)) {
            menu.removeItem(R.id.cuentaFragment)
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_superior, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.cuentaFragment -> {
                navController.navigate(R.id.cuentaFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        if (!(navView.selectedItemId == R.id.navigation_formulas && enlace.btnNotas.translationX != 0f)) hacerVisibleBotonFlotante()
        if (navView.selectedItemId == R.id.navigation_formulas || navView.selectedItemId == R.id.navigation_medicamentos) hacerVisibleMenuInferior()
        return controladorNavegacion.navigateUp()
    }

    // Control Animaciones
    override fun hacerInvisibleBotonFlotante() {
        if (!(navView.selectedItemId == R.id.navigation_formulas && enlace.btnNotas.translationX != 0f)) animacionSalidaBotonNotas.start()
    }

    override fun hacerVisibleBotonFlotante() {
        if (primerInicio || enlace.btnNotas.translationX == 0f) primerInicio = false
        else if (navView.selectedItemId == R.id.navigation_formulas) return
        else animacionEntradaBotonNotas.start()
    }

    override fun hacerInvisibleMenuInferior() {
        if (enlace.menuInferior.translationY == 0f) animacionSalidaMenuInferior.start()
    }

    override fun hacerVisibleMenuInferior() {
        if (navView.selectedItemId == R.id.cuentaFragment) return
        else if (enlace.menuInferior.translationY != 0f) animacionEntradaMenuInferior.start()
    }

    override fun cerrarSesion() {
        // SharedPreferences
        val sharedPreferences: SharedPreferences = getSharedPreferences(Constantes.CLAVE_PREFERENCIAS, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        // Borrar SharedPreferences
        editor.clear()
        editor.apply()

        // Cerrar sesión del usuario actual
        val auth = FirebaseAuth.getInstance()

        // Función signOut de la clase FirebaseAuth
        auth.signOut()
        Toast.makeText(applicationContext, Constantes.MENSAJE_SESION_CERRADA, Toast.LENGTH_SHORT).show()

        // Navegación a pantalla Login
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    // Recupear los datos del Usuario
    override fun obtenerEmailUsuario(): String {
        // SharedPreferences
        val sharedPreferences: SharedPreferences = getSharedPreferences(Constantes.CLAVE_PREFERENCIAS, Context.MODE_PRIVATE)
        val email = sharedPreferences.getString(Constantes.CLAVE_EMAIL, "")
        return email.toString()
    }

    // Doble pulsación del Botón Atrás
    override fun onBackPressed() {
        if (cerrarApp) {
            finishAffinity()
            return
        }
        cerrarApp = true
        Toast.makeText(this, Constantes.MENSAJE_SALIR, Toast.LENGTH_SHORT).show()
        // Retraso de tiempo de 2 segundos
        Handler(Looper.getMainLooper()).postDelayed({ cerrarApp = false }, 2000)
    }

}