package com.cinemate.cinemateapp.data.repository.movie

import com.cinemate.cinemateapp.data.datasource.movie.MovieDataSource
import com.cinemate.cinemateapp.data.mapper.toMovieNowPlayed
import com.cinemate.cinemateapp.data.mapper.toMoviePopular
import com.cinemate.cinemateapp.data.mapper.toMovieTopRated
import com.cinemate.cinemateapp.data.mapper.toMovieUpcoming
import com.cinemate.cinemateapp.data.model.Movie
import com.cinemate.cinemateapp.utils.ResultWrapper
import com.cinemate.cinemateapp.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

class MovieRepositoryImpl(private val dataSource: MovieDataSource) : MovieRepository {
    override fun getNowPlaying(page: Int): Flow<ResultWrapper<List<Movie>>> {
        return proceedFlow {
            dataSource.getNowPlaying(page).results.toMovieNowPlayed()
        }
    }

    override fun getPopular(page: Int): Flow<ResultWrapper<List<Movie>>> {
        return proceedFlow {
            dataSource.getPopular(page).results.toMoviePopular()
        }
    }

    override fun getTopRating(page: Int): Flow<ResultWrapper<List<Movie>>> {
        return proceedFlow {
            dataSource.getTopRating(page).results.toMovieTopRated()
        }
    }

    override fun getUpcoming(page: Int): Flow<ResultWrapper<List<Movie>>> {
        return proceedFlow {
            dataSource.getUpcoming(page).results.toMovieUpcoming()
        }
    }
}
