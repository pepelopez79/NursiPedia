package com.torredelrey.nursipedia.formulas

import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.view.ViewGroup
import android.content.Context
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.torredelrey.nursipedia.Constantes
import com.torredelrey.nursipedia.Utilidades
import com.torredelrey.nursipedia.databinding.FragmentIMCBinding

class IMCFragment : Fragment() {

    private lateinit var enlace: FragmentIMCBinding
    private lateinit var padre: Utilidades

    override fun onAttach(context: Context) {
        super.onAttach(context)
        padre = context as Utilidades
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        enlace = FragmentIMCBinding.inflate(inflater, container, false)
        return enlace.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        enlace.btnCalcularIMC.setOnClickListener {
            // Obtener el peso y la altura
            val peso = enlace.txtPeso.text.toString().toDoubleOrNull()
            val altura = enlace.txtAltura.text.toString().toDoubleOrNull()

            if (peso != null && altura != null) {
                // Calcular el IMC
                val imc = calcularIMC(peso, altura)
                enlace.txtIMC.setText(imc.toString())

                // Obtener el estado del IMC
                val estadoIMC = obtenerEstadoIMC(imc)
                enlace.txtEstadoIMC.text = estadoIMC
            } else {
                Toast.makeText(context, Constantes.MENSAJE_RELLENAR_CAMPOS, Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Función para calcular el IMC con el peso y la altura
    private fun calcularIMC(peso: Double, altura: Double): Double {
        return peso / (altura * altura)
    }

    // Función para obtener el estado del IMC según su valor
    private fun obtenerEstadoIMC(imc: Double): String {
        return when {
            imc < 18.5 -> Constantes.MENSAJE_IMC_BAJO_PESO
            imc < 24.9 -> Constantes.MENSAJE_IMC_PESO_SALUDABLE
            imc < 29.9 -> Constantes.MENSAJE_IMC_SOBREPESO
            imc < 34.9 -> Constantes.MENSAJE_IMC_OBESIDAD_1
            imc < 39.9 -> Constantes.MENSAJE_IMC_OBESIDAD_2
            else -> Constantes.MENSAJE_IMC_OBESIDAD_3
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        padre.hacerVisibleMenuInferior()
    }

}