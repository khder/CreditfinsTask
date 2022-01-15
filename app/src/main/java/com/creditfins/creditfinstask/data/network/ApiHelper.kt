package com.creditfins.creditfinstask.data.network

class ApiHelper(private val apiService: ApiService) {
     fun getMovies(page:Int) = apiService.getMovies(page)
     fun getMovieDetails(movieId:Int) = apiService.getMovieDetails(movieId)
}