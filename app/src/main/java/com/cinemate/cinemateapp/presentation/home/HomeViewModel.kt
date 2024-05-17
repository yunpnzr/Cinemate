package com.cinemate.cinemateapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.cinemate.cinemateapp.data.model.Movie
import com.cinemate.cinemateapp.data.repository.favorite.FavoriteRepository
import com.cinemate.cinemateapp.data.repository.movie.MovieRepository
import kotlinx.coroutines.Dispatchers

class HomeViewModel (
    private val movieRepository: MovieRepository,
    private val repo: FavoriteRepository,
) : ViewModel() {
    fun getMovieNowPlaying() = movieRepository.getNowPlaying(page = 1).asLiveData(Dispatchers.IO)
    fun getMoviePopular() = movieRepository.getPopular(1).asLiveData(Dispatchers.IO)
    fun getMovieUpcoming() = movieRepository.getUpcoming(1).asLiveData(Dispatchers.IO)
    fun getMovieTopRating() = movieRepository.getTopRating(1).asLiveData(Dispatchers.IO)

    fun addToFavorite(data: Movie) = repo.createFavorite(data).asLiveData(Dispatchers.IO)

    fun checkMovieList(favoriteId: Int?) = repo.checkFavoriteById(favoriteId).asLiveData(Dispatchers.IO)

    fun removeFavoriteById(favoriteId: Int?) = repo.removeFavoriteById(favoriteId).asLiveData(Dispatchers.IO)
}