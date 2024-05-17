package com.cinemate.cinemateapp.presentation.detail

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.cinemate.cinemateapp.data.model.Movie
import com.cinemate.cinemateapp.data.model.MovieDetail
import com.cinemate.cinemateapp.data.repository.detail.DetailMovieRepository
import com.cinemate.cinemateapp.data.repository.favorite.FavoriteRepository
import com.cinemate.cinemateapp.data.source.local.database.entity.AppEntity
import com.cinemate.cinemateapp.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers

class DetailViewModel(
    private val extras: Bundle?,
    private val detailMovieRepository : DetailMovieRepository,
    private val favoriteRepository: FavoriteRepository,
) : ViewModel() {

    val movie = extras?.getParcelable<Movie>(DetailFragment.EXTRAS_MOVIE)
    fun getDetail(id: Int): LiveData<ResultWrapper<MovieDetail>> {
        return detailMovieRepository.detailMovies(id).asLiveData(Dispatchers.IO)
    }

    fun addToFavorite(app: AppEntity?): LiveData<ResultWrapper<Boolean>> {
        return movie?.let {
                favoriteRepository.createFavorite(it).asLiveData(Dispatchers.IO)

        } ?: liveData { emit(ResultWrapper.Error(IllegalStateException("Catalog not found"))) }
    }
//    fun addToFavorite(data: Movie) = favoriteRepository.createFavorite(data).asLiveData(Dispatchers.IO)

    fun checkMovieList(favoriteId: Int?) = favoriteRepository.checkFavoriteById(favoriteId).asLiveData(Dispatchers.IO)

    fun removeFavoriteById(favoriteId: Int?) = favoriteRepository.removeFavoriteById(favoriteId).asLiveData(Dispatchers.IO)

}