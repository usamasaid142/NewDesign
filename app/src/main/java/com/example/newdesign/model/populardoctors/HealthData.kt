package com.example.newdesign.model.populardoctors

import java.io.Serializable

data class HealthData(
    val details: String?,
    val imageURL: String?,
    val title: String?
): Serializable
