package com.cinemate.cinemateapp.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.cinemate.cinemateapp.data.model.Movie
import com.cinemate.cinemateapp.data.model.MovieDetail
import com.cinemate.cinemateapp.databinding.FragmentDetailBinding
import com.cinemate.cinemateapp.utils.proceedWhen
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class DetailFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentDetailBinding

    private val viewModel: DetailViewModel by viewModel {
        parametersOf(arguments)
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

        observeData()
    }

    private fun observeData() {
        viewModel.movie?.id.let {
            viewModel.getDetail(it!!).observe(viewLifecycleOwner) {result ->
                result.proceedWhen(
                    doOnSuccess = { success ->
                        success.payload.let {movDetail ->
                            setBind(movDetail)
                        }
                    }
                )
            }
        }
    }


    private fun setBind(menu: MovieDetail?) {
        menu?.let {_ ->
            binding.ivMovie.load("https://image.tmdb.org/t/p/w500/${menu.image}"){
                crossfade(true)
            }
            binding.tvTittleMovie.text = menu.title
            binding.tvMovieRelease.text = menu.date
            binding.tvMovieRate.text = menu.rating.toString()
            binding.tvDescMovie.text = menu.desc
            binding.ivDetailMovie.load("https://image.tmdb.org/t/p/w500/${menu.coverImage}"){
                crossfade(true)
            }
        }
    }

    companion object {
        const val EXTRAS_MOVIE = "EXTRAS_MOVIE"
    }

}
