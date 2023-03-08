package com.dee.popularmovies.ui.movies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.dee.popularmovies.data.viewmodel.MainViewModel
import com.dee.popularmovies.databinding.FragmentMovieDetailsBinding
import com.dee.popularmovies.utils.loadImage
import kotlinx.coroutines.Job


class MovieDetailsFragment : Fragment() {
    private lateinit var binding: FragmentMovieDetailsBinding
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var movieDetailJob: Job
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)


        binding.back.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        movieDetailJob = viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel._movieDetail.observe(viewLifecycleOwner) {
                binding.movieDetails = it
                it?.posterPath?.let { path ->
                    binding.moviecover.loadImage("https://image.tmdb.org/t/p/w500/$path")
                }
            }
        }

        return binding.root
    }


}