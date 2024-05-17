package com.cinemate.cinemateapp.data.datasource.detail

import com.cinemate.cinemateapp.data.source.network.model.detail.DetailMovieResponse
import com.cinemate.cinemateapp.data.source.network.model.detail.Genre
import com.cinemate.cinemateapp.data.source.network.model.detail.ProductionCompany
import com.cinemate.cinemateapp.data.source.network.model.detail.ProductionCountry
import com.cinemate.cinemateapp.data.source.network.model.detail.SpokenLanguage
import com.cinemate.cinemateapp.data.source.network.service.AppService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert

import org.junit.Before
import org.junit.Test

class DetailDataSourceImplTest {
    @MockK
    lateinit var service: AppService
    private lateinit var ds: DetailDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        ds = DetailDataSourceImpl(service)
    }

    @Test
    fun detailMovies() = runTest {
        val movieId = 123
        val expectedResponse = DetailMovieResponse(
            adult = false,
            backdropPath = "/path/to/backdrop.jpg",
            belongsToCollection = null,
            budget = 10000000,
            genres = listOf(
                Genre(id = 1, name = "Action"),
                Genre(id = 2, name = "Adventure")
            ),
            homepage = "http://example.com",
            id = 123,
            imdbId = "tt1234567",
            originCountry = listOf("US", "CA"),
            originalLanguage = "en",
            originalTitle = "Original Title",
            overview = "Movie overview",
            popularity = 123.45,
            posterPath = "/path/to/poster.jpg",
            productionCompanies = listOf(
                ProductionCompany(id = 1, name = "Company 1", logoPath = "gwbrb", originCountry = "uecgugc"),
                ProductionCompany(id = 2, name = "Company 2", logoPath = "uwycqge", originCountry = "eygqw")
            ),
            productionCountries = listOf(
                ProductionCountry(iso31661 = "US", name = "United States of America"),
                ProductionCountry(iso31661 = "CA", name = "Canada")
            ),
            releaseDate = "2024-05-17",
            revenue = 50000000,
            runtime = 120,
            spokenLanguages = listOf(
                SpokenLanguage(iso6391 = "en", name = "English", englishName = "h"),
                SpokenLanguage(iso6391 = "fr", name = "French", englishName = "aewfb")
            ),
            status = "Released",
            tagline = "Movie tagline",
            title = "Movie Title",
            video = false,
            voteAverage = 7.8,
            voteCount = 1000
        )
        coEvery { service.detail(movieId) } returns expectedResponse
        val actualResponse = ds.detailMovies(movieId)
        Assert.assertEquals(expectedResponse, actualResponse)
    }
}