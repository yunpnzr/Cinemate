package com.cinemate.cinemateapp.presentation.detail

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.cinemate.cinemateapp.data.model.Movie
import com.cinemate.cinemateapp.data.model.MovieDetail
import com.cinemate.cinemateapp.data.repository.detail.DetailMovieRepository
import com.cinemate.cinemateapp.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers

class DetailViewModel(
    private val extras: Bundle?,
    private val detailMovieRepository : DetailMovieRepository
) : ViewModel() {

    val movie = extras?.getParcelable<Movie>(DetailFragment.EXTRAS_MOVIE)

    /*private var movie: Movie? = null

    fun setExtras(extras: Bundle) {
        movie = extras.getParcelable(DetailFragment.EXTRAS_MOVIE)
    }*/

    fun getDetail(id: Int): LiveData<ResultWrapper<MovieDetail>> {
        return detailMovieRepository.detailMovies(id).asLiveData(Dispatchers.IO)
    }

    //fun getDetailMovie() = movie?.let { detailMovieRepository.detailMovies(it.id).asLiveData(Dispatchers.IO) }
}