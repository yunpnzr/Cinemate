package com.cinemate.cinemateapp.data.datasource.seemore.playnow

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cinemate.cinemateapp.data.mapper.toMovieNowPlayed
import com.cinemate.cinemateapp.data.model.Movie
import com.cinemate.cinemateapp.data.source.network.service.AppService

class PlayNowPagingSource(private val service: AppService): PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val response = service.nowPlaying(page)

            LoadResult.Page(
                data = response.results.toMovieNowPlayed() ?: emptyList(),
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.results.isNullOrEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}