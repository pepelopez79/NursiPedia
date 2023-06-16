package com.torredelrey.nursipedia.medicamentos

import android.os.Parcel
import android.os.Parcelable

// Medicamento es un objeto Parcelable para que se pueda pasar entre componentes de la aplicación
data class Medicamento(
    val nombre: String? = null,
    val nombre_comercial: String? = null,
    val via: String? = null,
    val proposito: String? = null,
    val advertencia: String? = null
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeString(nombre_comercial)
        parcel.writeString(via)
        parcel.writeString(proposito)
        parcel.writeString(advertencia)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Medicamento> {
        override fun createFromParcel(parcel: Parcel): Medicamento {
            return Medicamento(parcel)
        }

        override fun newArray(size: Int): Array<Medicamento?> {
            return arrayOfNulls(size)
        }
    }

}
