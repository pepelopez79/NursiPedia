package com.torredelrey.nursipedia.usuarios

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.content.Context
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.torredelrey.nursipedia.Utilidades
import com.torredelrey.nursipedia.databinding.FragmentCuentaBinding

class CuentaFragment : Fragment() {

    private lateinit var enlace: FragmentCuentaBinding
    private lateinit var padre: Utilidades

    override fun onAttach(context: Context) {
        super.onAttach(context)
        padre = context as Utilidades
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        enlace = FragmentCuentaBinding.inflate(inflater, container,false)
        return enlace.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        padre.hacerInvisibleMenuInferior()
        padre.hacerInvisibleBotonFlotante()

        // Método de la interfaz Utilidades para obtener datos del usuario
        enlace.txtEmailUsuario.text = padre.obtenerEmailUsuario()

        enlace.btnCerrarSesion.setOnClickListener {
            // Método de la interfaz Utilidades para cerrar sesión
            padre.cerrarSesion()
        }
    }

}
