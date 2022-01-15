package com.creditfins.creditfinstask.data.network

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    fun getMovies(@Query("page") page:Int): Call<String>

    @GET("/movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movieId:Int)


}