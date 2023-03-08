package com.dee.popularmovies.ui.movies.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dee.popularmovies.R
import com.dee.popularmovies.data.network.local.Constants
import com.dee.popularmovies.data.network.local.readBool
import com.dee.popularmovies.data.network.local.writeBool
import com.dee.popularmovies.databinding.FragmentSignInBinding
import com.dee.popularmovies.utils.isValidEmail


class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater, container, false)


        requireContext().readBool(Constants.IS_LOGGED_IN).observe(viewLifecycleOwner) {

            when (it) {
                true -> {
                    findNavController().navigate(R.id.action_signInFragment_to_movieListFragment)
                }
                false -> {

                }
                null -> {

                }
            }
        }


        binding.emailEdittext.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.isBtnEnabled = binding.emailEdittext.text.toString()
                    .isValidEmail() && !binding.passwordEdittext.text.isNullOrEmpty()

            }
        })

        binding.passwordEdittext.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.isBtnEnabled = binding.emailEdittext.text.toString()
                    .isValidEmail() && !binding.passwordEdittext.text.isNullOrEmpty()

            }
        })
        binding.signinButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launchWhenCreated {
                requireContext().writeBool(Constants.IS_LOGGED_IN, true)
            }
        }

        return binding.root
    }


}