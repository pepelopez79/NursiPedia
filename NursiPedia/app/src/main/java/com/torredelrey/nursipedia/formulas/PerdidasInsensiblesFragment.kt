package com.torredelrey.nursipedia.formulas

import android.R
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.view.ViewGroup
import android.content.Context
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.torredelrey.nursipedia.Constantes
import com.torredelrey.nursipedia.Utilidades
import com.torredelrey.nursipedia.databinding.FragmentPerdidasInsensiblesBinding

class PerdidasInsensiblesFragment : Fragment() {

    private lateinit var enlace: FragmentPerdidasInsensiblesBinding
    private lateinit var padre: Utilidades

    override fun onAttach(context: Context) {
        super.onAttach(context)
        padre = context as Utilidades
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        enlace = FragmentPerdidasInsensiblesBinding.inflate(inflater, container, false)
        return enlace.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        // Opciones para el Spinner de Factor de Pérdidas
        val opcionesFP = arrayOf(
            Constantes.MENSAJE_FACTOR_PERDIDAS_BEBES,
            Constantes.MENSAJE_FACTOR_PERDIDAS_NINOS,
            Constantes.MENSAJE_FACTOR_PERDIDAS_ADULTOS
        )

        // Configurar el adaptador para el Spinner
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, opcionesFP)
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        enlace.spnFP.adapter = adapter

        // Acción del botón Calcular Pérdidas Insensibles
        enlace.btnCalcularPerdidasInsensibles.setOnClickListener {
            if (enlace.txtP.text.toString().isNotEmpty()) {
                // Obtener la opción seleccionada del Spinner
                val seleccion = enlace.spnFP.selectedItem.toString()

                // Obtener el factor correspondiente a la opción seleccionada
                val factor = obtenerFactor(seleccion)

                if (factor != null) {
                    // Calcular las pérdidas insensibles multiplicando el peso por el factor
                    val perdidasInsensibles = enlace.txtP.text.toString().toDouble() * factor
                    enlace.txtPI.setText(perdidasInsensibles.toString())
                } else {
                    Toast.makeText(context, Constantes.MENSAJE_SELECCIONAR_OPCION_VALIDA, Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, Constantes.MENSAJE_RELLENAR_CAMPO_PESO, Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Función para obtener el factor correspondiente a la opción seleccionada en el Spinner
    private fun obtenerFactor(opcion: String): Double? {
        return when (opcion) {
            Constantes.MENSAJE_FACTOR_PERDIDAS_BEBES -> 65.0
            Constantes.MENSAJE_FACTOR_PERDIDAS_NINOS -> 50.0
            Constantes.MENSAJE_FACTOR_PERDIDAS_ADULTOS -> 35.0
            else -> null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        padre.hacerVisibleMenuInferior()
    }

}