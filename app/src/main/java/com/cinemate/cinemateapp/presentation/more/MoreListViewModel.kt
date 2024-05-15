package com.cinemate.cinemateapp.presentation.more

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.cinemate.cinemateapp.data.model.Movie
import com.cinemate.cinemateapp.data.repository.movie.MovieRepository
import com.cinemate.cinemateapp.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers

class MoreListViewModel(
    private val movieRepository: MovieRepository
): ViewModel() {

    fun nowPlaying(page: Int): LiveData<ResultWrapper<List<Movie>>> {
        return movieRepository.getNowPlaying(page).asLiveData(Dispatchers.IO)
    }

    fun getPopularMovie(page: Int): LiveData<ResultWrapper<List<Movie>>> {
        return movieRepository.getPopular(page).asLiveData(Dispatchers.IO)
    }

    fun topRating(page: Int): LiveData<ResultWrapper<List<Movie>>> {
        return movieRepository.getTopRating(page).asLiveData(Dispatchers.IO)
    }

    fun upcoming(page: Int): LiveData<ResultWrapper<List<Movie>>> {
        return movieRepository.getUpcoming(page).asLiveData(Dispatchers.IO)
    }
}