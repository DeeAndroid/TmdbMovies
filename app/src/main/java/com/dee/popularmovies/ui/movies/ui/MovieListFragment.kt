package com.dee.popularmovies.ui.movies.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dee.popularmovies.R
import com.dee.popularmovies.data.res.ResultsItem
import com.dee.popularmovies.data.viewmodel.MainViewModel
import com.dee.popularmovies.databinding.FragmentMovieListBinding
import com.dee.popularmovies.di.utility.Resource
import com.dee.popularmovies.ui.movies.adapter.MoviesAdapter
import com.dee.popularmovies.ui.movies.listneres.OnItemClickListener
import kotlinx.coroutines.Job


class MovieListFragment : Fragment(), OnItemClickListener {
    private lateinit var binding: FragmentMovieListBinding
    private val viewModel: MainViewModel by activityViewModels()
    var page = 1
    private var isScroll = false
    private lateinit var adapter: MoviesAdapter
    private var totalPages: Int? = 0
    private lateinit var moviesjob: Job
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieListBinding.inflate(inflater, container, false)

        adapter = MoviesAdapter(this)
        binding.apply {
            rvMovies.adapter = adapter
            rvMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)

                    if (isLastVisible() && isScroll) {
                        totalPages?.let {
                            if (page < it) {
                                page += 1
                                callMovieListApi()
                            }
                        }
                    }
                }
            })
        }

        callMovieListApi()

        return binding.root
    }

    private fun callMovieListApi() {
        Log.d("TAG", "callMovieListApi: $page")
        viewModel.getMovieList(resources.getString(R.string.api_key), page)
        getMovieListResponse()
    }

    private fun getMovieListResponse() {
        moviesjob = viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.movieList.collect() {
                isScroll = when (it) {
                    Resource.Empty -> {
                        false
                    }
                    is Resource.Failure -> {
                        binding.isProgressVisible = false
                        false
                    }
                    Resource.Loading -> {
                        binding.isProgressVisible = true
                        false
                    }
                    is Resource.Success -> {
                        binding.isProgressVisible = false
                        it.value.totalPages?.let { total_page ->
                            totalPages = total_page
                        }

                        Log.d("TAG", "getMovieListResponse: ${it.value.results?.size}")
                        it.value.results?.let { it1 -> adapter.setModelArrayList(it1) }

                        moviesjob.cancel()
                        true
                    }
                }
            }
        }
    }

    fun isLastVisible(): Boolean {
        binding.apply {
            val layoutManager = rvMovies.layoutManager as LinearLayoutManager
            val pos = layoutManager.findLastCompletelyVisibleItemPosition()
            val numItems: Int = rvMovies.adapter?.itemCount ?: 0
            return pos >= numItems - 1
        }
    }

    override fun onItemClick(movielist: ResultsItem?, position: Int) {
        movielist?.let {
            viewModel.setMovieDetails(it)
            findNavController().navigate(R.id.action_movieListFragment_to_movieDetailsFragment)
        }

    }

}