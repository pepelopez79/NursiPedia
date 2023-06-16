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
import com.torredelrey.nursipedia.databinding.FragmentGastoCardiacoBinding

class GastoCardiacoFragment : Fragment() {

    private lateinit var enlace: FragmentGastoCardiacoBinding
    private lateinit var padre: Utilidades

    override fun onAttach(context: Context) {
        super.onAttach(context)
        padre = context as Utilidades
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        enlace = FragmentGastoCardiacoBinding.inflate(inflater, container, false)
        return enlace.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        enlace.btnCalcularGastoCardiaco.setOnClickListener {
            val frecuenciaCardiaca = enlace.txtFC.text.toString().toDoubleOrNull()
            val volumenSistolico = enlace.txtVS.text.toString().toDoubleOrNull()

            if (frecuenciaCardiaca != null && volumenSistolico != null) {
                // Cálculo del gasto cardiaco
                val gastoCardiaco = frecuenciaCardiaca * volumenSistolico
                enlace.txtGC.setText(gastoCardiaco.toString())
            } else {
                Toast.makeText(context, Constantes.MENSAJE_RELLENAR_CAMPOS, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        padre.hacerVisibleMenuInferior()
    }

}
