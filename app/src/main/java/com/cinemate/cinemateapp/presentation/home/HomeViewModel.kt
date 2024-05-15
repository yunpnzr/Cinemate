package com.cinemate.cinemateapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.cinemate.cinemateapp.data.repository.movie.MovieRepository
import kotlinx.coroutines.Dispatchers

class HomeViewModel (
    private val movieRepository: MovieRepository
) : ViewModel() {
    fun getMovieNowPlaying() = movieRepository.getNowPlaying(page = 1).asLiveData(Dispatchers.IO)
    fun getMoviePopular() = movieRepository.getPopular(1).asLiveData(Dispatchers.IO)
    fun getMovieUpcoming() = movieRepository.getUpcoming(1).asLiveData(Dispatchers.IO)
    fun getMovieTopRating() = movieRepository.getTopRating(1).asLiveData(Dispatchers.IO)
}