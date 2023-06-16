package com.torredelrey.nursipedia.medicamentos

// Estructura de la respuesta recibida por la llamada a la API de OpenFDA
data class MedicamentoResponse(
    val results: List<Result>
)

data class Result (
    val purpose: List<String>? = null,
    val warnings: List<String>? = null,
    val openfda: Openfda?,
)

data class Openfda (
    val brand_name: List<String>? = null,
    val generic_name: List<String>? = null,
    val route: List<String>? = null,
)
