package com.cinemate.cinemateapp.presentation.detail

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cinemate.cinemateapp.data.model.Movie
import com.cinemate.cinemateapp.data.model.MovieDetail
import com.cinemate.cinemateapp.data.repository.detail.DetailMovieRepository
import com.cinemate.cinemateapp.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(
    private val extras: Bundle?,
    private val detailMovieRepository : DetailMovieRepository
) : ViewModel() {
    private val movie = extras?.getParcelable<Movie>(DetailFragment.EXTRAS_MOVIE)

    fun getDetailMovie() = movie?.let { detailMovieRepository.detailMovies(it.id).asLiveData(Dispatchers.IO) }
}