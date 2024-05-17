package com.cinemate.cinemateapp.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cinemate.cinemateapp.data.model.Movie
import com.cinemate.cinemateapp.data.repository.favorite.FavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class FavoriteViewModel(
    private val repo: FavoriteRepository,
) : ViewModel() {
    fun getAllFavorites() = repo.getAllFavorite().asLiveData(Dispatchers.IO)

    fun removeFavorite(item: Movie) {
        viewModelScope.launch(Dispatchers.IO) { repo.deleteFavorite(item).collect() }
    }
    fun addToFavorite(data: Movie) = repo.createFavorite(data).asLiveData(Dispatchers.IO)

    fun checkMovieList(favoriteId: Int?) = repo.checkFavoriteById(favoriteId).asLiveData(Dispatchers.IO)

    fun removeFavoriteById(favoriteId: Int?) = repo.removeFavoriteById(favoriteId).asLiveData(Dispatchers.IO)


}