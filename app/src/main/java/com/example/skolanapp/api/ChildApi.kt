package com.example.skolanapp.api

import com.example.skolanapp.models.Child
import retrofit2.http.GET

interface ChildApi {

    @GET("children")
    suspend fun getChildren(): List<Child>
}