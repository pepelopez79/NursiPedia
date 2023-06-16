package com.torredelrey.nursipedia.medicamentos

import android.animation.ObjectAnimator
import androidx.recyclerview.widget.RecyclerView
import android.view.animation.DecelerateInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import com.torredelrey.nursipedia.Constantes

class ReboteRecycledView: RecyclerView.OnScrollListener() {

    private var posicionAnterior = -1

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val layoutManager = recyclerView.layoutManager as LinearLayoutManager?
        val primeraPosicion = layoutManager!!.findFirstCompletelyVisibleItemPosition()
        val ultimaPosicion = layoutManager.findLastCompletelyVisibleItemPosition()
        val contadorMedicamentos = layoutManager.itemCount

        if (primeraPosicion == 0 || ultimaPosicion == contadorMedicamentos - 1) {
            // Comprobamos si hay movimiento
            val posicionActual = recyclerView.computeVerticalScrollOffset()

            if (posicionAnterior != -1 && posicionActual != posicionAnterior) {
                // Animación de rebote
                val animator = ObjectAnimator.ofFloat(recyclerView, Constantes.ANIMACION_EJE_Y, 0f, 50f, 0f)
                animator.interpolator = DecelerateInterpolator()
                animator.duration = 500
                animator.start()
            }
            posicionAnterior = posicionActual
        }
    }

}


