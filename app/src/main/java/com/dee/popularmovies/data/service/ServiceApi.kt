package com.dee.popularmovies.data.service

import com.dee.popularmovies.data.res.MovieListResponse
import retrofit2.http.*

interface ServiceApi {

    @GET(EndPoint.POPULAR)
    suspend fun getMovieList(
        @Query("api_key") api_key: String,
        @Query("page") page: Int
    ): MovieListResponse

}