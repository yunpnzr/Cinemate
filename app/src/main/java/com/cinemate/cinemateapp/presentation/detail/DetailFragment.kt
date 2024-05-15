package com.cinemate.cinemateapp.presentation.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.Coil
import coil.request.ImageRequest
import com.cinemate.cinemateapp.data.model.MovieDetail
import com.cinemate.cinemateapp.databinding.FragmentDetailBinding
import com.cinemate.cinemateapp.utils.ResultWrapper
import com.cinemate.cinemateapp.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModel {
        parametersOf(intent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        const val EXTRAS_MOVIE = "EXTRAS_MOVIE"
    }

}
