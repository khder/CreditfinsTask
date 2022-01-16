package com.creditfins.creditfinstask.data.repositories

import android.content.Context
import android.util.Log
import kotlinx.coroutines.flow.onEach
import okhttp3.ResponseBody
import retrofit2.Response
import android.net.NetworkInfo

import androidx.core.content.ContextCompat.getSystemService

import android.net.ConnectivityManager
import androidx.core.content.ContextCompat
import com.creditfins.creditfinstask.data.models.Movie
import com.creditfins.creditfinstask.data.models.Resource
import com.creditfins.creditfinstask.data.network.ApiHelper
import com.creditfins.creditfinstask.data.utils.JsonParserUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class MoviesListRepository(private val apiHelper: ApiHelper,
                           private val appContext: Context) {
      fun getMovies(page: Int): Resource<List<Movie>> {
         if(appContext.isNetworkConnected()){
             return getArticlesFromNetwork(page)
         }else{
             return Resource.error(null,"Check Network Connection")
         }
     }

    private fun getArticlesFromNetwork(page: Int):Resource<List<Movie>>{
        val response: Response<String>
        try {
            response = apiHelper.getMovies(page).execute()
        } catch (t: Throwable) {
            return Resource.error(null,t.message!!)
        }

        if (!response.isSuccessful) {
            return Resource.error(null,"Error Happened")
        } else {
            if (response.body() == null) {
                return Resource.error(null,"Error Happened")
            }
        }
        return Resource.success(JsonParserUtils.parseJson(response.body().toString()?:""))
    }
}

private fun Context.isNetworkConnected(): Boolean {
    val connectivityManager =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}
