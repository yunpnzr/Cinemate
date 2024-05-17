package com.cinemate.cinemateapp.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.cinemate.cinemateapp.data.model.Movie
import com.cinemate.cinemateapp.data.model.MovieDetail
import com.cinemate.cinemateapp.data.repository.detail.DetailMovieRepository
import com.cinemate.cinemateapp.data.repository.movie.MovieRepository
import com.cinemate.cinemateapp.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers

class MainViewModel(
    private val movieRepository: MovieRepository,
    private val detailMovieRepository: DetailMovieRepository,
) : ViewModel() {
    fun getNowPlaying(page: Int): LiveData<ResultWrapper<List<Movie>>> {
        return movieRepository.getNowPlaying(page).asLiveData(Dispatchers.IO)
    }

    fun getPopular(page: Int): LiveData<ResultWrapper<List<Movie>>> {
        return movieRepository.getPopular(page).asLiveData(Dispatchers.IO)
    }

    fun getTopRating(page: Int): LiveData<ResultWrapper<List<Movie>>> {
        return movieRepository.getTopRating(page).asLiveData(Dispatchers.IO)
    }

    fun getUpcoming(page: Int): LiveData<ResultWrapper<List<Movie>>> {
        return movieRepository.getUpcoming(page).asLiveData(Dispatchers.IO)
    }

    fun getDetailMovie(movieId: Int): LiveData<ResultWrapper<MovieDetail>> {
        return detailMovieRepository.detailMovies(movieId).asLiveData(Dispatchers.IO)
    }
}
