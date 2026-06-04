package com.uteq.software.app4.Models
import kotlinx.serialization.Serializable
@Serializable
data class Materia(
    val id: Long,
    val nombre: String? = null,
    val nivel: Int? = null
)