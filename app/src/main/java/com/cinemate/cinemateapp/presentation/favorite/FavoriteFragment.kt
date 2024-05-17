package com.cinemate.cinemateapp.presentation.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import coil.load
import com.cinemate.cinemateapp.R
import com.cinemate.cinemateapp.data.model.Movie
import com.cinemate.cinemateapp.data.model.MovieDetail
import com.cinemate.cinemateapp.databinding.FragmentDetailBinding
import com.cinemate.cinemateapp.databinding.FragmentFavoriteBinding
import com.cinemate.cinemateapp.presentation.favorite.adapter.FavoriteListAdapter
import com.cinemate.cinemateapp.utils.proceedWhen
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        observeData()
        setClickListener()
    }


    private val FavoriteviewModel: FavoriteViewModel by viewModel()

    private val adapter: FavoriteListAdapter by lazy {
        FavoriteListAdapter { favorite ->
            detailMovie(favorite)
        }
    }


    private fun setClickListener() {

    }

    private fun setupList() {
        binding.rvFavorite.adapter = this@FavoriteFragment.adapter
    }


    private fun observeData() {
        FavoriteviewModel.getAllFavorites().observe(viewLifecycleOwner) {

            it.proceedWhen(doOnSuccess = { result ->
                binding.layoutState.root.isVisible = false
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.tvError.isVisible = false
                binding.rvFavorite.isVisible = true
                result.payload?.let { (cart) ->
                    adapter.submitData(cart)
                }
            }, doOnLoading = {
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = true
                binding.layoutState.tvError.isVisible = false
                binding.rvFavorite.isVisible = false
            }, doOnError = { err ->
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.tvError.isVisible = true
                binding.rvFavorite.isVisible = false
            }, doOnEmpty = { data ->
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.tvError.isVisible = true
                binding.rvFavorite.isVisible = false
            })
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
        FavoriteviewModel.getCoverPhoto(id).observe(viewLifecycleOwner) { result ->
            bottomSheetBinding.ivDetailMovie.load("https://image.tmdb.org/t/p/w500${result.payload?.coverImage}")
        }
    }

    private fun movieInFavorite(
        data: Movie,
        detailFragmentBinding: FragmentDetailBinding,
    ) {
        FavoriteviewModel.checkMovieList(data.id).observe(
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
        FavoriteviewModel.addToFavorite(data).observe(viewLifecycleOwner) {
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
        FavoriteviewModel.removeFavoriteById(movieId).observe(viewLifecycleOwner) {
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

}