package com.creditfins.creditfinstask.ui.movieslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.creditfins.creditfinstask.data.repositories.MoviesListRepository

class MoviesListViewModelFactory(private val moviesListRepository: MoviesListRepository)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoviesListViewModel::class.java)) {
            return MoviesListViewModel(moviesListRepository) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}