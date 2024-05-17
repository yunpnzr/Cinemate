package com.cinemate.cinemateapp.di

import android.os.Bundle
import com.cinemate.cinemateapp.data.datasource.detail.DetailDataSource
import com.cinemate.cinemateapp.data.datasource.detail.DetailDataSourceImpl
import com.cinemate.cinemateapp.data.datasource.favorite.FavoriteDataSource
import com.cinemate.cinemateapp.data.datasource.favorite.FavoriteDataSourceImpl
import com.cinemate.cinemateapp.data.datasource.movie.MovieDataSource
import com.cinemate.cinemateapp.data.datasource.movie.MovieDataSourceImpl
import com.cinemate.cinemateapp.data.datasource.seemore.playnow.PlayNowPagingSource
import com.cinemate.cinemateapp.data.datasource.seemore.popular.PopularPagingSource
import com.cinemate.cinemateapp.data.datasource.seemore.toprated.TopRatedPagingSource
import com.cinemate.cinemateapp.data.datasource.seemore.upcoming.UpcomingPagingSource
import com.cinemate.cinemateapp.data.repository.detail.DetailMovieRepository
import com.cinemate.cinemateapp.data.repository.detail.DetailMovieRepositoryImpl
import com.cinemate.cinemateapp.data.repository.favorite.FavoriteRepository
import com.cinemate.cinemateapp.data.repository.favorite.FavoriteRepositoryImpl
import com.cinemate.cinemateapp.data.repository.movie.MovieRepository
import com.cinemate.cinemateapp.data.repository.movie.MovieRepositoryImpl
import com.cinemate.cinemateapp.data.repository.seemore.SeeMoreRepository
import com.cinemate.cinemateapp.data.repository.seemore.SeeMoreRepositoryImpl
import com.cinemate.cinemateapp.data.source.local.database.AppDatabase
import com.cinemate.cinemateapp.data.source.local.database.dao.AppDao
import com.cinemate.cinemateapp.data.source.network.service.AppService
import com.cinemate.cinemateapp.presentation.detail.DetailViewModel
import com.cinemate.cinemateapp.presentation.favorite.FavoriteViewModel
import com.cinemate.cinemateapp.presentation.home.HomeViewModel
import com.cinemate.cinemateapp.presentation.main.MainViewModel
import com.cinemate.cinemateapp.presentation.more.MoreListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModule {
    private val localModule =
        module {
            single<AppDatabase> {
                AppDatabase.createInstance(androidContext())
            }
            single<AppDao> { get<AppDatabase>().appDao() }
        }
    private val networkModule =
        module {
            single<AppService> {
                AppService.invoke()
            }
        }
    private val dataSourceModule =
        module {
            single<MovieDataSource> {
                MovieDataSourceImpl(get())
            }
            single<DetailDataSource> {
                DetailDataSourceImpl(get())
            }
            single<FavoriteDataSource> {
                FavoriteDataSourceImpl(get())
            }
            single<PlayNowPagingSource> {
                PlayNowPagingSource(get())
            }
            single<PopularPagingSource> {
                PopularPagingSource(get())
            }
            single<TopRatedPagingSource> {
                TopRatedPagingSource(get())
            }
            single<UpcomingPagingSource> {
                UpcomingPagingSource(get())
            }
        }
    private val repositoryModule =
        module {
            single<MovieRepository> {
                MovieRepositoryImpl(get())
            }
            single<DetailMovieRepository> {
                DetailMovieRepositoryImpl(get())
            }
            single<FavoriteRepository> {
                FavoriteRepositoryImpl(get())
            }
            single<SeeMoreRepository> {
                SeeMoreRepositoryImpl(get())
            }
        }
    private val viewModelModule =
        module {
            viewModel { MainViewModel(get(), get()) }
            viewModelOf(::HomeViewModel)
            viewModel { FavoriteViewModel(get(), get()) }
            viewModelOf(::MoreListViewModel)
            viewModelOf(::DetailViewModel)
            viewModel { (extras: Bundle?) ->
                DetailViewModel(extras, get(), get())
            }
        }

    val modules =
        listOf<Module>(
            localModule,
            networkModule,
            dataSourceModule,
            repositoryModule,
            viewModelModule,
        )
}
