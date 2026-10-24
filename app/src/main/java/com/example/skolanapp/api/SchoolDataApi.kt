package com.example.skolanapp.api

import com.example.skolanapp.models.SchoolData
import retrofit2.http.GET

interface SchoolDataApi {

    @GET("schooldata")
    suspend fun getSchoolData(): SchoolData
}