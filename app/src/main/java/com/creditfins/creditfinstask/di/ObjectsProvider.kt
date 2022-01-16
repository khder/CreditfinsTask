package com.creditfins.creditfinstask.di

import android.content.Context
import com.creditfins.creditfinstask.data.network.ApiHelper
import com.creditfins.creditfinstask.data.network.RetrofitBuilder
import com.creditfins.creditfinstask.data.repositories.MoviesListRepository

object ObjectsProvider {
    fun getMoviesListRepository(appContext: Context):MoviesListRepository{
        return MoviesListRepository(getApiHelper(),appContext)
    }
    fun getApiHelper():ApiHelper{
        return ApiHelper(RetrofitBuilder.apiService)
    }
}