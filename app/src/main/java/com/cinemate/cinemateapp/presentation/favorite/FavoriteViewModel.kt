package com.cinemate.cinemateapp.presentation.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cinemate.cinemateapp.data.model.Movie
import com.cinemate.cinemateapp.data.model.MovieDetail
import com.cinemate.cinemateapp.data.repository.detail.DetailMovieRepository
import com.cinemate.cinemateapp.data.repository.favorite.FavoriteRepository
import com.cinemate.cinemateapp.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class FavoriteViewModel(
    private val repo: FavoriteRepository,
    private val detailMovieRepository: DetailMovieRepository
) : ViewModel() {
    fun getAllFavorites() = repo.getAllFavorite().asLiveData(Dispatchers.IO)

    fun removeFavorite(item: Movie) {
        viewModelScope.launch(Dispatchers.IO) { repo.deleteFavorite(item).collect() }
    }
    fun addToFavorite(data: Movie) = repo.createFavorite(data).asLiveData(Dispatchers.IO)

    fun checkMovieList(favoriteId: Int?) = repo.checkFavoriteById(favoriteId).asLiveData(Dispatchers.IO)

    fun removeFavoriteById(favoriteId: Int?) = repo.removeFavoriteById(favoriteId).asLiveData(Dispatchers.IO)

    fun getCoverPhoto(id: Int): LiveData<ResultWrapper<MovieDetail>> {
        return detailMovieRepository.detailMovies(id).asLiveData(Dispatchers.IO)
    }

}