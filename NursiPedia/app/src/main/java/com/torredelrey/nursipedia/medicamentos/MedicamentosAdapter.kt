package com.torredelrey.nursipedia.medicamentos

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.view.LayoutInflater
import com.torredelrey.nursipedia.R
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.fragment.findNavController

class MedicamentosAdapter(listaMedicamentos: List<Medicamento>, private val padre: Fragment) : RecyclerView.Adapter<MedicamentosAdapter.Holder>() {

    // Para no mostrar los medicamentos nulos
    val medicamentosFiltrados = listaMedicamentos.filter { medicamento ->
        !medicamento.nombre.isNullOrEmpty()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val vista = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.linea_medicamentos, parent, false)
        return Holder(vista)
    }

    override fun onBindViewHolder(holder: Holder, posicion: Int) {
        val medicamento = medicamentosFiltrados[posicion]
        holder.rellena(medicamento)
    }

    override fun getItemCount(): Int {
        return medicamentosFiltrados.size
    }

    inner class Holder(vista: View): RecyclerView.ViewHolder(vista) {
        private val nombre = vista.findViewById<TextView>(R.id.lblTitulo)

        init {
            vista.setOnClickListener {
                // Navegación a la pantalla Detalle Medicamente pasándole el objeto medicamento completo
                padre.findNavController().navigate(MedicamentosFragmentDirections.actionNavigationMedicamentosToDetalleMedicamentoFragment(
                    medicamentosFiltrados[adapterPosition]
                ))
            }
        }

        fun rellena(medicamento: Medicamento) {
            val nombre = medicamento.nombre
            // No mostramos el nombre del medicamento si tiene el nombre vacío
            if (nombre != null && nombre.isNotEmpty()) {
                this.nombre.text = nombre
            }
        }
    }

}