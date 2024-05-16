package com.cinemate.cinemateapp.presentation.more

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.PagingData
import com.cinemate.cinemateapp.data.model.Movie
import com.cinemate.cinemateapp.data.repository.seemore.SeeMoreRepository
import kotlinx.coroutines.Dispatchers

class MoreListViewModel(
    //private val movieRepository: MovieRepository
    private val seeMoreRepository: SeeMoreRepository
): ViewModel() {

    fun nowPlaying(): LiveData<PagingData<Movie>> {
        return seeMoreRepository.getAllPopularMovie().asLiveData(Dispatchers.IO)
    }

    fun getPopularMovie(): LiveData<PagingData<Movie>> {
        return seeMoreRepository.getAllPopularMovie().asLiveData(Dispatchers.IO)
    }

    fun topRating(): LiveData<PagingData<Movie>> {
        return seeMoreRepository.getAllTopRatedMovie().asLiveData(Dispatchers.IO)
    }

    fun upcoming(): LiveData<PagingData<Movie>> {
        return seeMoreRepository.getAllUpcomingMovie().asLiveData(Dispatchers.IO)
    }

    /*fun nowPlaying(page: Int): LiveData<ResultWrapper<List<Movie>>> {
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
    }*/
}