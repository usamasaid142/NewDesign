package com.example.newdesign.model.register

import java.io.Serializable

data class UserForgetInfo(
    val code: Int,
    val phone: String,
): Serializable
