package com.torredelrey.nursipedia.medicamentos

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData

class MedicamentosViewModel : ViewModel() {

    private val _listaMedicamentos = MutableLiveData<List<Medicamento>>()
    val listaMedicamentos: LiveData<List<Medicamento>>
        get() = _listaMedicamentos

    fun actualizarLista(medicamentos: List<Medicamento>) {
        _listaMedicamentos.value = medicamentos
    }

}