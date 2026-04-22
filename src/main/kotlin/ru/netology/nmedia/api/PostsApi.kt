package ru.netology.nmedia.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PostsApi {
    private const val BASE_URL = "http://127.0.0.1:9999/"

    val service: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
