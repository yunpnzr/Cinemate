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
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class DetailFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentDetailBinding
    private var currentBannerMovie: MovieDetail? = null
    private var currentDataMovie: Movie? = null
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

        currentDataMovie?.let { movie ->
            detailMovie (movie)
        }
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


    private fun detailMovie(movie: Movie) {
        val detailFragmentDialog = BottomSheetDialog(requireContext())
        val bottomSheetBinding = FragmentDetailBinding.inflate(layoutInflater)
        bottomSheetBinding.apply {
            //ivDetailMovie.load("https://image.tmdb.org/t/p/w500${movie.image}")
            ivMovie.load("https://image.tmdb.org/t/p/w500${movie.image}")
            tvTittleMovie.text = movie.title
            tvDescMovie.text = movie.desc
            tvMovieRelease.text = movie.date
            tvMovieRate.text = movie.rating.toString()
        }
        movieInFavorite(movie, bottomSheetBinding)

        getDetailPhoto(movie.id, bottomSheetBinding)

        detailFragmentDialog.setContentView(bottomSheetBinding.root)
        detailFragmentDialog.show()
    }

    private fun getDetailPhoto(id: Int, bottomSheetBinding: FragmentDetailBinding) {
        viewModel.getCoverPhoto(id).observe(viewLifecycleOwner) { result ->
            bottomSheetBinding.ivDetailMovie.load("https://image.tmdb.org/t/p/w500${result.payload?.coverImage}")
        }
    }

    private fun movieInFavorite(
        data: Movie,
        detailFragmentBinding: FragmentDetailBinding,
    ) {
        viewModel.checkMovieList(data.id).observe(
            viewLifecycleOwner,
        ) { movieFavorite ->
            if (movieFavorite.isEmpty()) {
                detailFragmentBinding.btnMyList.setImageResource(R.drawable.ic_add)
                clickAddToFavorite(data, detailFragmentBinding)
            } else {
                detailFragmentBinding.btnMyList.setImageResource(R.drawable.ic_check)
                clickRemoveFavorite(data.id, detailFragmentBinding)
            }
            clickAddToShare(data,detailFragmentBinding)
        }
    }
    private fun clickAddToFavorite(
        data: Movie,
        detailFragmentBinding: FragmentDetailBinding,
    ) {
        detailFragmentBinding.btnMyList.setOnClickListener {
            addToFavorite(data)
        }
    }
    private fun addToFavorite(data: Movie) {
        viewModel.addToFavorite(data).observe(viewLifecycleOwner) {
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
                    ).show()
                },
            )
        }
    }

    private fun clickRemoveFavorite(
        dataMovie: Int?,
        detailFragmentBinding: FragmentDetailBinding,
    ) {
        detailFragmentBinding.btnMyList.setOnClickListener {
            removeMovieInFavorite(dataMovie)
        }
    }

    private fun removeMovieInFavorite(movieId: Int?) {
        viewModel.removeFavoriteById(movieId).observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.text_succes_delete_to_favorite),
                        Toast.LENGTH_SHORT,
                    ).show()
                },
                doOnError = {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.text_faliled_delete_to_favorite),
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

    private fun clickAddToShare(
        data: Movie,
        detailFragmentBinding: FragmentDetailBinding,
    ) {
        detailFragmentBinding.btnShare.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "Watch this movie! ${data.title}\nhttps://image.tmdb.org/t/p/w500/${data.image}")
            }
            startActivity(Intent.createChooser(shareIntent, "Share movie via"))
        }
    }

    companion object {
        const val EXTRAS_MOVIE = "EXTRAS_MOVIE"
    }

}
