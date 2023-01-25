package com.example.newdesign.model.register


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class GetCountry(
    @SerializedName("Data")
    val `data`: List<DataCountry>
):Serializable

data class DataCountry(
    @SerializedName("Id")
    val id: Int,
    @SerializedName("Isdefault")
    val isdefault: Boolean,
    @SerializedName("Name")
    val name: String
): Serializable