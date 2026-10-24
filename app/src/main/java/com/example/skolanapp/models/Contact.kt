package com.example.skolanapp.models

data class Contact(
    val id: Int,
    val name: String,
    val role: String,
    val email: String,
    val childId: Int? = null
)

