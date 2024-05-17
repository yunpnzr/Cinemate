package com.cinemate.cinemateapp.presentation.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import coil.load
import com.cinemate.cinemateapp.R
import com.cinemate.cinemateapp.data.model.Movie
import com.cinemate.cinemateapp.data.model.MovieDetail
import com.cinemate.cinemateapp.data.source.local.database.entity.AppEntity
import com.cinemate.cinemateapp.databinding.FragmentDetailBinding
import com.cinemate.cinemateapp.utils.proceedWhen
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class DetailFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentDetailBinding
    private var currentBannerMovie: MovieDetail? = null
    private var appEntity: AppEntity? = null
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
        setClickAction()

    }

    private fun setClickAction() {
        binding.btnShare.setOnClickListener {
            currentBannerMovie?.let { movie ->
                shareMovie(movie)
            }
        }
        binding.btnMyList.setOnClickListener {
            addMovieToFavorite()
        }
    }

    private fun addMovieToFavorite() {
        viewModel.addToFavorite(appEntity).observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.text_succes_add_to_favorite),
                        Toast.LENGTH_SHORT,
                    ).show()
                },
                doOnError = {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.text_faliled_add_to_favorite),
                        Toast.LENGTH_SHORT,
                    )
                        .show()
                },
                doOnLoading = {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.text_load_add_to_favorite),
                        Toast.LENGTH_SHORT,
                    ).show()
                },
            )
        }
    }

    private fun shareMovie(movie: MovieDetail?) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "Watch this movie! ${movie?.title}\nhttps://image.tmdb.org/t/p/w500/${movie?.image}")
        }
        startActivity(Intent.createChooser(shareIntent, "Share movie via"))
    }

    private fun observeData() {
        viewModel.movie?.id.let {
            viewModel.getDetail(it!!).observe(viewLifecycleOwner) {result ->
                result.proceedWhen(
                    doOnSuccess = { success ->
                        success.payload.let {movDetail ->
                            currentBannerMovie = movDetail
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
