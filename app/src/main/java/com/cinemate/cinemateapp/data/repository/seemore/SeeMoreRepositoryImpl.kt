package com.cinemate.cinemateapp.data.repository.seemore

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.cinemate.cinemateapp.data.datasource.seemore.playnow.PlayNowPagingSource
import com.cinemate.cinemateapp.data.datasource.seemore.popular.PopularPagingSource
import com.cinemate.cinemateapp.data.datasource.seemore.toprated.TopRatedPagingSource
import com.cinemate.cinemateapp.data.datasource.seemore.upcoming.UpcomingPagingSource
import com.cinemate.cinemateapp.data.model.Movie
import com.cinemate.cinemateapp.data.source.network.service.AppService
import kotlinx.coroutines.flow.Flow

class SeeMoreRepositoryImpl(private val service: AppService) : SeeMoreRepository {
    override fun getAllPopularMovie(): Flow<PagingData<Movie>> {
        return Pager(
            config =
                PagingConfig(
                    pageSize = 20,
                ),
            pagingSourceFactory = {
                PopularPagingSource(service)
            },
        ).flow
    }

    override fun getAllPlayNowMovie(): Flow<PagingData<Movie>> {
        return Pager(
            config =
                PagingConfig(
                    pageSize = 20,
                ),
            pagingSourceFactory = {
                PlayNowPagingSource(service)
            },
        ).flow
    }

    override fun getAllTopRatedMovie(): Flow<PagingData<Movie>> {
        return Pager(
            config =
                PagingConfig(
                    pageSize = 20,
                ),
            pagingSourceFactory = {
                TopRatedPagingSource(service)
            },
        ).flow
    }

    override fun getAllUpcomingMovie(): Flow<PagingData<Movie>> {
        return Pager(
            config =
                PagingConfig(
                    pageSize = 20,
                ),
            pagingSourceFactory = {
                UpcomingPagingSource(service)
            },
        ).flow
    }
}
