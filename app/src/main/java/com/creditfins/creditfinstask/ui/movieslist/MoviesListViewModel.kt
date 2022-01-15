package com.creditfins.creditfinstask.ui.movieslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.creditfins.creditfinstask.data.models.Resource
import com.creditfins.creditfinstask.data.repositories.MoviesListRepository
import kotlinx.coroutines.Dispatchers

class MoviesListViewModel(private val moviesListRepository: MoviesListRepository) : ViewModel() {
    private var page = 1;
    private var isLoading:Boolean = false

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
    fun getMovies() = liveData(Dispatchers.IO){
        emit(Resource.loading(null))
        emit(moviesListRepository.getMovies(page))
    }
}