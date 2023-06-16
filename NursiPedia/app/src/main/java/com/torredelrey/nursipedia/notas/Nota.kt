package com.torredelrey.nursipedia.notas

import java.util.*
import kotlin.collections.HashMap

data class Nota(
    // Identificador Aleatorio
    var id: String = UUID.randomUUID().toString(),
    var titulo: String = "Nota",
    var contenido: String = "Contenido Nota",
    var fecha: String = "99/99/99",
    var emailUsuario: String = "Email Usuario"
) {

    // Damos a cada atributo una clave y un valor
    fun fromHashMap(data: HashMap<*, *>) {
        id = data["id"] as? String ?: UUID.randomUUID().toString()
        titulo = data["titulo"] as? String ?: "Nota"
        contenido = data["contenido"] as? String ?: "Contenido Nota"
        fecha = data["fecha"] as? String ?: "99/99/99"
        emailUsuario = data["emailUsuario"] as? String ?: "Email Usuario"
    }

}