package com.example.newdesign.model.docotorsearch


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("Items")
    val items: List<Item?>?,
    @SerializedName("TotalCount")
    val totalCount: Int?
)