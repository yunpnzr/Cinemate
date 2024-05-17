package com.cinemate.cinemateapp.data.datasource.favorite

import com.cinemate.cinemateapp.data.source.local.database.dao.AppDao
import com.cinemate.cinemateapp.data.source.local.database.entity.AppEntity
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class FavoriteDataSourceImplTest {
    @MockK
    lateinit var dao: AppDao
    private lateinit var ds: FavoriteDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        ds = FavoriteDataSourceImpl(dao)
    }

    @Test
    fun getAllFavorites() =
        runTest {
            val expectedFavorites =
                listOf(
                    AppEntity(
                        1,
                        "Movie 1",
                        "2024-05-17",
                        7.5,
                        "Description 1",
                        "/path/to/image1.jpg",
                    ),
                    AppEntity(
                        2,
                        "Movie 2",
                        "2024-05-18",
                        8.0,
                        "Description 2",
                        "/path/to/image2.jpg",
                    ),
                )
            coEvery { dao.getAllFavorites() } returns flowOf(expectedFavorites)
            val actualFavorites = ds.getAllFavorites().toList().flatten()
            Assert.assertEquals(expectedFavorites, actualFavorites)
        }

    @Test
    fun insertFavorite() =
        runTest {
            val favorite =
                AppEntity(
                    1,
                    "Movie 1",
                    "2024-05-17",
                    7.5,
                    "Description 1",
                    "/path/to/image1.jpg",
                )
            val expectedId = 1L
            coEvery { dao.insertFavorite(favorite) } returns expectedId
            val actualId = ds.insertFavorite(favorite)
            Assert.assertEquals(expectedId, actualId)
        }

    @Test
    fun deleteFavorite() =
        runTest {
            val favorite =
                AppEntity(
                    1,
                    "Movie 1",
                    "2024-05-17",
                    7.5,
                    "Description 1",
                    "/path/to/image1.jpg",
                )
            val expectedRowCount = 1
            coEvery { dao.deleteFavorite(favorite) } returns expectedRowCount
            val actualRowCount = ds.deleteFavorite(favorite)
            Assert.assertEquals(expectedRowCount, actualRowCount)
        }

    @Test
    fun deleteAll() =
        runTest {
            coEvery { dao.deleteAll() } returns Unit
            ds.deleteAll()
        }
}
