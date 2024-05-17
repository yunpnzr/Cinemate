package com.cinemate.cinemateapp.data.datasource.movie

import com.cinemate.cinemateapp.data.source.network.model.nowplaying.DatesNowPlayingResponse
import com.cinemate.cinemateapp.data.source.network.model.nowplaying.NowPlayingResponse
import com.cinemate.cinemateapp.data.source.network.model.nowplaying.ResultNowPlayingResponse
import com.cinemate.cinemateapp.data.source.network.model.popular.PopularMovieResponse
import com.cinemate.cinemateapp.data.source.network.model.popular.ResultPopularMovieResponse
import com.cinemate.cinemateapp.data.source.network.model.toprating.ResultTopRatedResponse
import com.cinemate.cinemateapp.data.source.network.model.toprating.TopRatedResponse
import com.cinemate.cinemateapp.data.source.network.model.upcoming.DatesUpcomingResultResponse
import com.cinemate.cinemateapp.data.source.network.model.upcoming.ResultUpcomingMovieResponse
import com.cinemate.cinemateapp.data.source.network.model.upcoming.UpcomingMovieResponse
import com.cinemate.cinemateapp.data.source.network.service.AppService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class MovieDataSourceImplTest {
    @MockK
    lateinit var service: AppService
    private lateinit var ds: MovieDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        ds = MovieDataSourceImpl(service)
    }

    @Test
    fun getNowPlaying() =
        runTest {
            val page = 1
            val expectedNowPlayingResponse =
                NowPlayingResponse(
                    dates =
                        DatesNowPlayingResponse(
                            maximum = "2024-05-17",
                            minimum = "2024-05-01",
                        ),
                    page = 1,
                    results =
                        listOf(
                            ResultNowPlayingResponse(
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
                            ResultNowPlayingResponse(
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
            coEvery { service.nowPlaying(page) } returns expectedNowPlayingResponse
            val actualResponse = ds.getNowPlaying(page)
            Assert.assertEquals(expectedNowPlayingResponse, actualResponse)
        }

    @Test
    fun getPopular() =
        runTest {
            val page = 1
            val expectedPopularMovieResponse =
                PopularMovieResponse(
                    page = 1,
                    results =
                        listOf(
                            ResultPopularMovieResponse(
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
                            ResultPopularMovieResponse(
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
            coEvery { service.popular(page) } returns expectedPopularMovieResponse
            val actualResponse = ds.getPopular(page)
            Assert.assertEquals(expectedPopularMovieResponse, actualResponse)
        }

    @Test
    fun getTopRating() =
        runTest {
            val page = 1
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
            val actualResponse = ds.getTopRating(page)
            Assert.assertEquals(expectedTopRatedResponse, actualResponse)
        }

    @Test
    fun getUpcoming() =
        runTest {
            val page = 1
            val expectedUpcomingMovieResponse =
                UpcomingMovieResponse(
                    dates =
                        DatesUpcomingResultResponse(
                            maximum = "2024-05-17",
                            minimum = "2024-05-01",
                        ),
                    page = 1,
                    results =
                        listOf(
                            ResultUpcomingMovieResponse(
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
                            ResultUpcomingMovieResponse(
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
            coEvery { service.upcoming(page) } returns expectedUpcomingMovieResponse
            val actualResponse = ds.getUpcoming(page)
            Assert.assertEquals(expectedUpcomingMovieResponse, actualResponse)
        }
}
