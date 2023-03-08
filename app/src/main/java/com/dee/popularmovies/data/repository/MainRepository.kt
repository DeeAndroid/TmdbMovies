package com.dee.popularmovies.data.repository

import com.dee.popularmovies.data.service.ServiceApi
import com.dee.popularmovies.di.utility.ResponseReceiver
import javax.inject.Inject

class MainRepository @Inject constructor(private val api: ServiceApi) : ResponseReceiver {

    suspend fun getMovieListRepo(
        api_key: String,
        page: Int
    ) = callApi {
        api.getMovieList(api_key, page)
    }

}