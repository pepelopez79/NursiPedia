package com.torredelrey.nursipedia.medicamentos

import android.view.*
import android.os.Bundle
import android.content.Context
import androidx.fragment.app.Fragment
import com.torredelrey.nursipedia.Constantes
import com.torredelrey.nursipedia.Utilidades
import com.torredelrey.nursipedia.databinding.FragmentDetalleMedicamentoBinding

class DetalleMedicamentoFragment : Fragment() {

    private lateinit var enlace: FragmentDetalleMedicamentoBinding
    private lateinit var padre: Utilidades

    override fun onAttach(context: Context) {
        super.onAttach(context)
        padre = context as Utilidades
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        enlace = FragmentDetalleMedicamentoBinding.inflate(inflater, container,false)
        return enlace.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        padre.hacerInvisibleBotonFlotante()
        padre.hacerInvisibleMenuInferior()

        // Recuperamos el medicamento que es un objeto Parcelable
        val medicamento = arguments?.getParcelable<Medicamento>(Constantes.CLAVE_MEDICAMENTO)
        if (medicamento != null) {
            // Mostramos sus datos por pantalla
            enlace.lblNombreMedicamento.text = medicamento.nombre.toString()
            enlace.lblNombreComercialMedicamento.text = medicamento.nombre_comercial.toString()
            val proposito = medicamento.proposito
            if (proposito == null) enlace.lblPropositoMedicamento.text = Constantes.MENSAJE_PROPOSITO_NO_ENCONTRADO
            else enlace.lblPropositoMedicamento.text = proposito
            enlace.lblViaMedicamento.text = medicamento.via.toString()
            val advertencia = medicamento.advertencia
            if (advertencia == null) enlace.lblAdvertenciaMedicamento.text = Constantes.MENSAJE_ADVERTENCIA_NO_ENCONTRADA
            else enlace.lblAdvertenciaMedicamento.text = advertencia
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        padre.hacerVisibleMenuInferior()
    }

}