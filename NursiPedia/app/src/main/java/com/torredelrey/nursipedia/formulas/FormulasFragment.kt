package com.torredelrey.nursipedia.formulas

import android.view.*
import android.os.Bundle
import android.animation.*
import android.widget.Button
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import com.torredelrey.nursipedia.Utilidades
import androidx.navigation.fragment.findNavController
import com.torredelrey.nursipedia.databinding.FragmentFormulasBinding

class FormulasFragment : Fragment() {

    private lateinit var enlace: FragmentFormulasBinding
    private lateinit var padre: Utilidades

    override fun onAttach(context: Context) {
        super.onAttach(context)
        padre = context as Utilidades
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        enlace = FragmentFormulasBinding.inflate(inflater, container,false)
        return enlace.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        padre.hacerInvisibleBotonFlotante()

        // Animaciones
        val animacionInfusiones = animacionBotones(enlace.btnInfusiones, FormulasFragmentDirections.actionNavigationFormulasToInfusionesFragment())
        enlace.btnInfusiones.setOnClickListener {
            animacionInfusiones.start()
        }

        val animacionDisoluciones = animacionBotones(enlace.btnDisoluciones, FormulasFragmentDirections.actionNavigationFormulasToDisolucionesFragment())
        enlace.btnDisoluciones.setOnClickListener {
            animacionDisoluciones.start()
        }

        val animacionPerdidasInsensibles = animacionBotones(enlace.btnPerdidasInsensibles, FormulasFragmentDirections.actionNavigationFormulasToPerdidasInsensiblesFragment())
        enlace.btnPerdidasInsensibles.setOnClickListener {
            animacionPerdidasInsensibles.start()
        }

        val animacionIMC = animacionBotones(enlace.btnIMC, FormulasFragmentDirections.actionNavigationFormulasToIMCFragment())
        enlace.btnIMC.setOnClickListener {
            animacionIMC.start()
        }

        val animacionReglaDeTres = animacionBotones(enlace.btnReglaDeTres, FormulasFragmentDirections.actionNavigationFormulasToReglaDeTresFragment())
        enlace.btnReglaDeTres.setOnClickListener {
            animacionReglaDeTres.start()
        }

        val animacionGastoCardiaco = animacionBotones(enlace.btnGastoCardiaco, FormulasFragmentDirections.actionNavigationFormulasToGastoCardiacoFragment())
        enlace.btnGastoCardiaco.setOnClickListener {
            animacionGastoCardiaco.start()
        }
    }

    fun animacionBotones(boton: Button, siguientePantalla: NavDirections): ObjectAnimator {
        // Animación que hace que el botón se haga más grande y vuelva a su estado original
        val animacion = ObjectAnimator.ofPropertyValuesHolder(
            boton,
            PropertyValuesHolder.ofFloat(View.SCALE_X, 1f, 1.3f, 1f),
            PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f, 1.3f, 1f),
        )
        animacion.addListener(object: Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) { }
            override fun onAnimationEnd(animation: Animator) {
                // Al finalizar la animación navega a la siguiente pantalla
                findNavController().navigate(siguientePantalla)
                padre.hacerInvisibleMenuInferior()
            }
            override fun onAnimationCancel(animation: Animator) { }
            override fun onAnimationRepeat(animation: Animator) { }
        })
        return animacion
    }


}