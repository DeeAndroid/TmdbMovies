package com.dee.popularmovies.data.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dee.popularmovies.data.repository.MainRepository
import com.dee.popularmovies.data.res.MovieListResponse
import com.dee.popularmovies.data.res.ResultsItem
import com.dee.popularmovies.di.utility.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {


    //to get movie List
    private val _movieList: MutableStateFlow<Resource<MovieListResponse>> = MutableStateFlow(
        Resource.Empty
    )
    val movieList: Flow<Resource<MovieListResponse>> get() = _movieList
    fun getMovieList(
        api_key: String,
        page: Int
    ) {
        viewModelScope.launch {
            _movieList.value = Resource.Loading
            _movieList.value = repository.getMovieListRepo(api_key, page)
        }
    }


    val _movieDetail = MutableLiveData<ResultsItem>()
    fun setMovieDetails(resultsItem: ResultsItem) {
        _movieDetail.value = resultsItem
    }


}