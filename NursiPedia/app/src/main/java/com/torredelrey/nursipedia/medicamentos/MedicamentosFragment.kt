package com.torredelrey.nursipedia.medicamentos

import android.view.*
import android.util.Log
import android.os.Bundle
import retrofit2.Retrofit
import android.content.Context
import kotlinx.coroutines.launch
import androidx.fragment.app.Fragment
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import androidx.fragment.app.viewModels
import kotlinx.coroutines.CoroutineScope
import com.torredelrey.nursipedia.Constantes
import com.torredelrey.nursipedia.Utilidades
import retrofit2.converter.gson.GsonConverterFactory
import androidx.recyclerview.widget.LinearLayoutManager
import com.torredelrey.nursipedia.databinding.FragmentMedicamentosBinding

class MedicamentosFragment : Fragment() {

    private lateinit var enlace: FragmentMedicamentosBinding
    private lateinit var padre: Utilidades
    private val vm: MedicamentosViewModel by viewModels()

    // Configuración de retrofit
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constantes.URL_BASE)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service: OpenFDAService = retrofit.create(OpenFDAService::class.java)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        padre = context as Utilidades
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        enlace = FragmentMedicamentosBinding.inflate(inflater, container,false)
        return enlace.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        padre.hacerVisibleBotonFlotante()

        // LLamada a la API
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = service.obtenerMedicamentos()
                if (response.results.isNotEmpty()) {
                    // Creamos un objeto Medicamento con los datos que nos interesan de la API
                    val medicamentos = response.results.map { result ->
                        Medicamento(
                            nombre = result.openfda?.generic_name?.joinToString(", "),
                            nombre_comercial = result.openfda?.brand_name?.joinToString(", "),
                            via = result.openfda?.route?.joinToString(", "),
                            proposito = result.purpose?.joinToString(", "),
                            advertencia = result.warnings?.joinToString(", ")
                        )
                    }
                    withContext(Dispatchers.Main) {
                        vm.actualizarLista(medicamentos)
                    }
                } else {
                    Log.e(Constantes.MENSAJE_ERROR_API, Constantes.MENSAJE_LISTA_MEDICAMENTOS_VACIA)
                }
            } catch (exception: Exception) {
                Log.e(Constantes.MENSAJE_ERROR_API, exception.message ?: Constantes.MENSAJE_ERROR_DESCONOCIDO)
            }
        }

        // Configuración del RecycledView
        enlace.listaMedicamentos.layoutManager = LinearLayoutManager(context)
        enlace.listaMedicamentos.adapter = MedicamentosAdapter(vm.listaMedicamentos.value.orEmpty(), this@MedicamentosFragment)

        // Animación RecycledView
        val rebote = ReboteRecycledView()
        enlace.listaMedicamentos.addOnScrollListener(rebote)
    }
    
}