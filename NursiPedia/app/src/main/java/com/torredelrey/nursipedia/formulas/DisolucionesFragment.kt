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
import com.torredelrey.nursipedia.databinding.FragmentDisolucionesBinding

class DisolucionesFragment : Fragment() {

    private lateinit var enlace: FragmentDisolucionesBinding
    private lateinit var padre: Utilidades

    override fun onAttach(context: Context) {
        super.onAttach(context)
        padre = context as Utilidades
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        enlace = FragmentDisolucionesBinding.inflate(inflater, container, false)
        return enlace.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        enlace.btnCalcularDisoluciones.setOnClickListener {
            val medicamento = enlace.txtCM.text.toString().toDoubleOrNull()
            val volumenDisolucion = enlace.txtVD.text.toString().toDoubleOrNull()

            if (medicamento != null && volumenDisolucion != null) {
                // Calcula la concentración de la disolución dividiendo el medicamento entre el volumen de la disolución
                val concentracionDisolucion = medicamento / volumenDisolucion
                enlace.txtCD.setText(concentracionDisolucion.toString())
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