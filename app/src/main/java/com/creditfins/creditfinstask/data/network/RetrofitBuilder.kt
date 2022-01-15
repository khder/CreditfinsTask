package com.creditfins.creditfinstask.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitBuilder {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor(QueryInterceptor()).build())
            .build()
    }
    val apiService:ApiService = getRetrofit().create(ApiService::class.java)
}