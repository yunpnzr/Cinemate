package com.cinemate.cinemateapp.presentation.more

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.PagingData
import com.cinemate.cinemateapp.data.model.Movie
import com.cinemate.cinemateapp.data.model.MovieDetail
import com.cinemate.cinemateapp.data.repository.detail.DetailMovieRepository
import com.cinemate.cinemateapp.data.repository.favorite.FavoriteRepository
import com.cinemate.cinemateapp.data.repository.seemore.SeeMoreRepository
import com.cinemate.cinemateapp.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers

class MoreListViewModel(
    //private val movieRepository: MovieRepository
    private val seeMoreRepository: SeeMoreRepository,
    private val repo: FavoriteRepository,
    private val detailMovieRepository: DetailMovieRepository
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
    fun addToFavorite(data: Movie) = repo.createFavorite(data).asLiveData(Dispatchers.IO)

    fun checkMovieList(favoriteId: Int?) = repo.checkFavoriteById(favoriteId).asLiveData(Dispatchers.IO)

    fun removeFavoriteById(favoriteId: Int?) = repo.removeFavoriteById(favoriteId).asLiveData(Dispatchers.IO)

    fun getCoverPhoto(id: Int): LiveData<ResultWrapper<MovieDetail>> {
        return detailMovieRepository.detailMovies(id).asLiveData(Dispatchers.IO)
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