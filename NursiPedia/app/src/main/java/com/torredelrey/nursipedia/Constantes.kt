package com.torredelrey.nursipedia

object Constantes {

    // Mensajes
    const val MENSAJE_ERROR_EMAIL = "Introduce un correo electrónico válido"
    const val MENSAJE_ERROR_COINCIDENCIA_EMAIL = "El email no coincide"
    const val MENSAJE_ERROR_CONTRASENA = "La contraseña debe tener al menos 8 caracteres, una letra mayúscula, una letra minúscula, un número y un caracter especial"
    const val MENSAJE_ERROR_COINCIDENCIA_CONTRASENA = "La contraseña no coincide"
    const val MENSAJE_INICIO_SESION_EXITOSO = "Inicio de sesión exitoso"
    const val MENSAJE_ERROR_INICIO_SESION = "Error al iniciar sesión. El usuario o la contraseña son incorrectos"
    const val MENSAJE_REGISTRO_EXITOSO = "Usuario registrado correctamente"
    const val MENSAJE_ERROR_REGISTRO = "Error al registrar usuario"
    const val MENSAJE_SESION_CERRADA = "Sesión cerrada correctamente"
    const val MENSAJE_SALIR = "Presiona de nuevo para salir"
    const val MENSAJE_SELECCIONAR_OPCION_VALIDA = "Seleccione una opción válida"
    const val MENSAJE_RELLENAR_CAMPOS = "Rellena los campos necesarios"
    const val MENSAJE_RELLENAR_DOS_CAMPOS = "Rellena solo dos campos"
    const val MENSAJE_RELLENAR_CAMPO_PESO = "Rellena el campo de peso"
    const val MENSAJE_INGRESAR_TRES_VALORES = "Ingresa 3 valores"
    const val MENSAJE_ERROR_CARGAR_NOTAS = "Error al cargar las notas"
    const val MENSAJE_NOTA_ELIMINADA = "Nota eliminada"
    const val MENSAJE_ERROR_ELIMINAR_NOTA = "Error al eliminar la nota"
    const val MENSAJE_BUSCAR_NOTA = "Error al buscar la nota"
    const val MENSAJE_ERROR_API = "API Error"
    const val MENSAJE_ERROR_DESCONOCIDO = "Error desconocido"
    const val MENSAJE_LISTA_MEDICAMENTOS_VACIA = "Lista de medicamentos vacía"
    const val MENSAJE_PROPOSITO_NO_ENCONTRADO = "Propósito o finalidad no espeficificada"
    const val MENSAJE_ADVERTENCIA_NO_ENCONTRADA = "Sin advertencias de uso"
    const val MENSAJE_FACTOR_PERDIDAS_BEBES = "Para recién nacidos y lactantes: 65 ml/kg/día"
    const val MENSAJE_FACTOR_PERDIDAS_NINOS = "Para niños de 1 año a 10 años: 50 ml/kg/día"
    const val MENSAJE_FACTOR_PERDIDAS_ADULTOS = "Para mayores de 10 años y adultos: 35 ml/kg/día"
    const val MENSAJE_IMC_BAJO_PESO = "Bajo peso"
    const val MENSAJE_IMC_PESO_SALUDABLE = "Peso saludable"
    const val MENSAJE_IMC_SOBREPESO = "Sobrepeso"
    const val MENSAJE_IMC_OBESIDAD_1 = "Obesidad grado 1"
    const val MENSAJE_IMC_OBESIDAD_2 = "Obesidad grado 2"
    const val MENSAJE_IMC_OBESIDAD_3 = "Obesidad grado 3"

    // SharedPreferences
    const val CLAVE_PREFERENCIAS = "preferences"
    const val CLAVE_EMAIL = "email"
    const val CLAVE_SESION_INICIADA = "sesionIniciada"

    // API
    const val URL_BASE = "https://api.fda.gov"
    const val GET_MEDICAMENTOS = "/drug/label.json?limit=100"

    // Formatos
    const val FORMATO_EMAIL = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,})+$"
    const val FORMATO_CONTRASENA = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[\\W_]).{8,}$"
    const val FORMATO_FECHA = "dd/MM/yyyy"

    // Títulos
    const val TITULO_DETALLE_NOTA = "Nota"
    const val TITULO_NUEVA_NOTA = "Nueva Nota"
    const val TITULO_NOTAS = "Notas"

    // Animaciones
    const val ANIMACION_EJE_X = "translationX"
    const val ANIMACION_EJE_Y = "translationY"

    // CLAVES
    const val CLAVE_NOTA = "nota"
    const val CLAVE_NOTA_ID = "nota.id"
    const val CLAVE_NOTA_EMAIL_USUARIO = "nota.emailUsuario"
    const val CLAVE_NOTAS = "notas"
    const val CLAVE_MEDICAMENTO = "medicamento"

    // Otros
    const val CANAL_RECORDATORIO = "recordatorio_channel"

}