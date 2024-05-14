package com.cinemate.cinemateapp.data.source.network.service

import com.cinemate.cinemateapp.BuildConfig
import com.cinemate.cinemateapp.data.source.network.model.detail.DetailMovieResponse
import com.cinemate.cinemateapp.data.source.network.model.nowplaying.NowPlayingResponse
import com.cinemate.cinemateapp.data.source.network.model.popular.PopularMovieResponse
import com.cinemate.cinemateapp.data.source.network.model.toprating.TopRatedResponse
import com.cinemate.cinemateapp.data.source.network.model.upcoming.UpcomingMovieResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface AppService {

    @Headers("Authorization: Bearer ${BuildConfig.KEY}")
    @GET("now_playing")
    suspend fun nowPlaying(
        @Query("page") page: Int
    ): NowPlayingResponse

    @Headers("Authorization: Bearer ${BuildConfig.KEY}")
    @GET("popular")
    suspend fun popular(
        @Query("page") page: Int
    ): PopularMovieResponse

    @Headers("Authorization: Bearer ${BuildConfig.KEY}")
    @GET("top_rated")
    suspend fun topRated(
        @Query("page") page: Int
    ): TopRatedResponse

    @Headers("Authorization: Bearer ${BuildConfig.KEY}")
    @GET("upcoming")
    suspend fun upcoming(
        @Query("page") page: Int
    ): UpcomingMovieResponse

    @Headers("Authorization: Bearer ${BuildConfig.KEY}")
    @GET("{movie_id}")
    suspend fun detail(
        @Path("movie_id") movieId: Int? = null
    ): DetailMovieResponse

    companion object {
        @JvmStatic
        operator fun invoke(): AppService {
            val levelInterceptor = HttpLoggingInterceptor.Level.BODY
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(levelInterceptor)
            val okHttpClient =
                OkHttpClient.Builder()
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(100, TimeUnit.SECONDS)
                    .addInterceptor(loggingInterceptor)
                    .build()
            val retrofit =
                Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            return retrofit.create(AppService::class.java)
        }
    }
}