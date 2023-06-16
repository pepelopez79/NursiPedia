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
import com.torredelrey.nursipedia.databinding.FragmentInfusionesBinding

class InfusionesFragment : Fragment() {

    private lateinit var enlace: FragmentInfusionesBinding
    private lateinit var padre: Utilidades

    override fun onAttach(context: Context) {
        super.onAttach(context)
        padre = context as Utilidades
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        enlace = FragmentInfusionesBinding.inflate(inflater, container, false)
        return enlace.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        var vai: Double // Variable para almacenar el VAI (Volumen A Infundir)
        var v: Double // Variable para almacenar el V (Volumen)
        var h: Double // Variable para almacenar el H (Horas)

        enlace.btnCalcularInfusiones.setOnClickListener {
            when {
                enlace.txtVAI.text.toString().isNotEmpty() && enlace.txtV.text.toString().isNotEmpty() && enlace.txtH.text.toString().isNotEmpty() -> {
                    Toast.makeText(context, Constantes.MENSAJE_RELLENAR_DOS_CAMPOS, Toast.LENGTH_SHORT).show()
                }
                enlace.txtVAI.text.toString().isNotEmpty() && enlace.txtV.text.toString().isNotEmpty() -> {
                    // Calcula el valor de H al dividir el VAI entre V
                    vai = enlace.txtVAI.text.toString().toDouble()
                    v = enlace.txtV.text.toString().toDouble()
                    enlace.txtH.setText((vai / v).toString())
                }
                enlace.txtVAI.text.toString().isNotEmpty() && enlace.txtH.text.toString().isNotEmpty() -> {
                    // Calcula el valor de V al dividir el VAI entre H
                    vai = enlace.txtVAI.text.toString().toDouble()
                    h = enlace.txtH.text.toString().toDouble()
                    enlace.txtV.setText((vai / h).toString())
                }
                enlace.txtV.text.toString().isNotEmpty() && enlace.txtH.text.toString().isNotEmpty() -> {
                    // Calcula el valor de VAI al multiplicar V por H
                    v = enlace.txtV.text.toString().toDouble()
                    h = enlace.txtH.text.toString().toDouble()
                    enlace.txtVAI.setText((v * h).toString())
                }
                else -> {
                    Toast.makeText(context, Constantes.MENSAJE_RELLENAR_CAMPOS, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        padre.hacerVisibleMenuInferior()
    }

}