
package com.example.skolanapp.api

import com.example.skolanapp.models.News
import retrofit2.http.GET

interface NewsApi {

    @GET("news")
    suspend fun getNews(): List<News>
}