package com.cinemate.cinemateapp.data.datasource.seemore.toprated

import androidx.paging.PagingSource
import com.cinemate.cinemateapp.data.mapper.toTopRatedMovie
import com.cinemate.cinemateapp.data.model.Movie
import com.cinemate.cinemateapp.data.source.network.model.toprating.ResultTopRatedResponse
import com.cinemate.cinemateapp.data.source.network.model.toprating.TopRatedResponse
import com.cinemate.cinemateapp.data.source.network.service.AppService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class TopRatedPagingSourceTest {
    @MockK
    lateinit var service: AppService
    private lateinit var ds: PagingSource<Int, Movie>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        ds = TopRatedPagingSource(service)
    }

    @Test
    fun load() {
        runBlocking {
            val page = 1
            val loadParams = mockk<PagingSource.LoadParams<Int>>(relaxed = true)
            val expectedTopRatedResponse =
                TopRatedResponse(
                    page = 1,
                    results =
                        listOf(
                            ResultTopRatedResponse(
                                adult = false,
                                backdropPath = "/backdrop1.jpg",
                                genreIds = listOf(28, 12, 14),
                                id = 123,
                                originalLanguage = "en",
                                originalTitle = "Original Title 1",
                                overview = "Overview of movie 1",
                                popularity = 123.45,
                                posterPath = "/poster1.jpg",
                                releaseDate = "2024-05-17",
                                title = "Movie 1",
                                video = false,
                                voteAverage = 7.8,
                                voteCount = 1000,
                            ),
                            ResultTopRatedResponse(
                                adult = false,
                                backdropPath = "/backdrop2.jpg",
                                genreIds = listOf(18, 36, 53),
                                id = 456,
                                originalLanguage = "en",
                                originalTitle = "Original Title 2",
                                overview = "Overview of movie 2",
                                popularity = 67.89,
                                posterPath = "/poster2.jpg",
                                releaseDate = "2024-05-18",
                                title = "Movie 2",
                                video = false,
                                voteAverage = 8.5,
                                voteCount = 1500,
                            ),
                        ),
                    totalPages = 5,
                    totalResults = 50,
                )
            coEvery { service.topRated(page) } returns expectedTopRatedResponse
            val loadResult = ds.load(loadParams)
            if (loadResult is PagingSource.LoadResult.Page) {
                assertEquals(
                    expectedTopRatedResponse.results?.map { it.toTopRatedMovie() } ?: expectedTopRatedResponse,
                    loadResult.data,
                )
                assertEquals(null, loadResult.prevKey)
                assertEquals(
                    if (expectedTopRatedResponse.results.isNullOrEmpty()) null else page + 1,
                    loadResult.nextKey,
                )
            }
        }
    }
}
