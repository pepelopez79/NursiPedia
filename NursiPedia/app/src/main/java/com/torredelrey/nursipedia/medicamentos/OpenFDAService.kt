package com.torredelrey.nursipedia.medicamentos

import retrofit2.http.GET
import com.torredelrey.nursipedia.Constantes

interface OpenFDAService {
    @GET(Constantes.GET_MEDICAMENTOS)
    suspend fun obtenerMedicamentos(): MedicamentoResponse
}