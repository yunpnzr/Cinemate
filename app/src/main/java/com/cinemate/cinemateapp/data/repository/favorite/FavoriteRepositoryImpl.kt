package com.cinemate.cinemateapp.data.repository.favorite
import com.cinemate.cinemateapp.data.datasource.favorite.FavoriteDataSource
import com.cinemate.cinemateapp.data.mapper.toAppEntity
import com.cinemate.cinemateapp.data.mapper.toFavoriteList
import com.cinemate.cinemateapp.data.model.Movie
import com.cinemate.cinemateapp.data.source.local.database.entity.AppEntity
import com.cinemate.cinemateapp.utils.ResultWrapper
import com.cinemate.cinemateapp.utils.proceed
import com.cinemate.cinemateapp.utils.proceedFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.lang.IllegalStateException

class FavoriteRepositoryImpl(private val dataSource: FavoriteDataSource): FavoriteRepository {

    override fun deleteFavorite(item: Movie): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { dataSource.deleteFavorite(item.toAppEntity()) > 0 }
    }
    override fun checkFavoriteById(movieId: Int?): Flow<List<AppEntity>> {
        return dataSource.checkFavoriteById(movieId)
    }


    override fun removeFavoriteById(favoriteId: Int?): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { dataSource.removeFavoriteById(favoriteId) > 0 }
    }


    override fun deleteAll(): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            dataSource.deleteAll()
            true
        }
    }

    override fun getAllFavorite(): Flow<ResultWrapper<Pair<List<Movie>, Double>>> {
        return dataSource.getAllFavorites()
            .map {
                // mapping into Favorite list and sum the total price
                proceed {
                    val result = it.toFavoriteList()
                    val totalPrice = result.sumOf { it.rating}
                    Pair(result, totalPrice)
                }
            }.map {
                // map to check when list is empty
                if (it.payload?.first?.isEmpty() == false) return@map it
                ResultWrapper.Empty(it.payload)
            }.onStart {
                emit(ResultWrapper.Loading())
                delay(300)
            }
    }



    override fun createFavorite(item: Movie): Flow<ResultWrapper<Boolean>> {
        return item.id?.let { itemId ->
            // when id is not null
            proceedFlow {
                val affectedRow =
                    dataSource.insertFavorite(
                        AppEntity(
                            movieId = itemId,
                            movieTitle = item.title,
                            movieImage = item.image,
                            movieRating = item.rating,
                            movieDesc = item.desc,
                            movieDate = item.date,
                        ),
                    )
                delay(100)
                affectedRow > 0
            }
        } ?: flow {
            emit(ResultWrapper.Error(IllegalStateException("catalog ID not found")))
        }
    }
}