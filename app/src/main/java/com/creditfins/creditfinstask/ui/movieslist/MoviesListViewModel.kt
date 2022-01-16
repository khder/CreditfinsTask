package com.creditfins.creditfinstask.ui.movieslist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.creditfins.creditfinstask.data.models.Movie
import com.creditfins.creditfinstask.data.models.Resource
import com.creditfins.creditfinstask.data.repositories.MoviesListRepository
import kotlinx.coroutines.Dispatchers

class MoviesListViewModel(private val moviesListRepository: MoviesListRepository) : ViewModel() {
    private var page = 1;
    private var isLoading:Boolean = false
    var moviesLiveData:MutableLiveData<Resource<List<Movie>>> = MutableLiveData()

    fun isFirstPage():Boolean{
        return page==1
    }
    fun isLoading():Boolean{
        return isLoading
    }
    fun setIsLoading(isLoading:Boolean){
        this.isLoading = isLoading
    }
    fun increasePages(){
        page++;
    }
    fun getMovies(){
        moviesLiveData = liveData(Dispatchers.IO){
            emit(Resource.loading(null))
            emit(moviesListRepository.getMovies(page))
        } as MutableLiveData<Resource<List<Movie>>>
    }
}