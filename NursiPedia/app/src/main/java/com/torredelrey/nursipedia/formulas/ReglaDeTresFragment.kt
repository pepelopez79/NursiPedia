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
import com.torredelrey.nursipedia.databinding.FragmentReglaDeTresBinding

class ReglaDeTresFragment : Fragment() {

    private lateinit var enlace: FragmentReglaDeTresBinding
    private lateinit var padre: Utilidades

    override fun onAttach(context: Context) {
        super.onAttach(context)
        padre = context as Utilidades
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        enlace = FragmentReglaDeTresBinding.inflate(inflater, container, false)
        return enlace.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        enlace.btnCalcularReglaDeTres.setOnClickListener {
            val valorA = enlace.txtA.text.toString().toDoubleOrNull()
            val valorB = enlace.txtB.text.toString().toDoubleOrNull()
            val valorC = enlace.txtC.text.toString().toDoubleOrNull()
            val valorD = enlace.txtX.text.toString().toDoubleOrNull()

            // Se cuenta la cantidad de valores conocidos
            val valoresConocidos = listOf(valorA, valorB, valorC, valorD).count { it != null }

            if (valoresConocidos == 3) {
                // Se realiza la regla de tres para obtener el valor desconocido y se asigna al campo correspondiente
                if (valorA == null) {
                    val resultadoA = (valorB!! * valorC!!) / valorD!!
                    enlace.txtA.setText(resultadoA.toString())
                } else if (valorB == null) {
                    val resultadoB = (valorA * valorD!!) / valorC!!
                    enlace.txtB.setText(resultadoB.toString())
                } else if (valorC == null) {
                    val resultadoC = (valorA * valorD!!) / valorB
                    enlace.txtC.setText(resultadoC.toString())
                } else if (valorD == null) {
                    val resultadoD = (valorB * valorC) / valorA
                    enlace.txtX.setText(resultadoD.toString())
                }
            } else {
                // Si no se han introducido 3 valores se muestra un mensaje de error
                Toast.makeText(context, Constantes.MENSAJE_INGRESAR_TRES_VALORES, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        padre.hacerVisibleMenuInferior()
    }

}