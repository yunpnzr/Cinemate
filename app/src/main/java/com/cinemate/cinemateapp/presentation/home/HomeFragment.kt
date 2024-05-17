package com.cinemate.cinemateapp.presentation.home

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
import com.cinemate.cinemateapp.databinding.FragmentDetailBinding
import com.cinemate.cinemateapp.databinding.FragmentHomeBinding
import com.cinemate.cinemateapp.presentation.detail.DetailFragment
import com.cinemate.cinemateapp.presentation.home.adapters.movie.MovieAdapter
import com.cinemate.cinemateapp.presentation.home.adapters.movie.OnItemClickedListener
import com.cinemate.cinemateapp.presentation.more.MoreListActivity
import com.cinemate.cinemateapp.utils.proceedWhen
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModel()
    private var currentBannerMovie: Movie? = null
    private lateinit var nowPlayingAdapter: MovieAdapter
    private lateinit var popularAdapter: MovieAdapter
    private lateinit var upcomingAdapter: MovieAdapter
    private lateinit var topRatedAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapters()
        observeMovieNowPlayingData()
        observeMoviePopularData()
        observeMovieUpcomingData()
        observeTopRatedData()
        setClickAction()
        nowPlayingAdapter.setOnMovieClickListener { movie ->
            onItemClick(movie)
        }
        popularAdapter.setOnMovieClickListener { movie ->
            onItemClick(movie)
        }
        upcomingAdapter.setOnMovieClickListener { movie ->
            onItemClick(movie)
        }
        topRatedAdapter.setOnMovieClickListener { movie ->
            onItemClick(movie)
        }
    }

    private fun shareMovie(movie: Movie) {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "Watch this movie! ${movie.title}\nhttps://image.tmdb.org/t/p/w500/${movie.image}")
        }
        startActivity(Intent.createChooser(shareIntent, "Share movie via"))
    }

    private fun setClickAction() {
        binding.layoutBanner.btnInfo.setOnClickListener {
            val randomMovie = currentBannerMovie

            randomMovie?.let {
                val bottomSheetFragment = DetailFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(DetailFragment.EXTRAS_MOVIE, it)
                    }
                }
                bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
            }
        }
        binding.layoutBanner.btnShare.setOnClickListener {
            currentBannerMovie?.let { movie -> shareMovie(movie)}
        }
        binding.ivMoreNowPlaying.setOnClickListener {
            navigateToMoreListActivity(MoreListActivity.TYPE_NOW_PLAYING)
        }
        binding.ivMorePopular.setOnClickListener {
            navigateToMoreListActivity(MoreListActivity.TYPE_POPULAR)
        }
        binding.ivMoreUpcomingMovies.setOnClickListener {
            navigateToMoreListActivity(MoreListActivity.TYPE_UPCOMING)
        }
        binding.ivMoreTopRated.setOnClickListener {
            navigateToMoreListActivity(MoreListActivity.TYPE_TOP_RATED)
        }
    }

    private fun navigateToMoreListActivity(movieType: String) {
        val intent = Intent(requireContext(), MoreListActivity::class.java).apply {
            putExtra(MoreListActivity.MOVIE_TYPE, movieType)
        }
        startActivity(intent)
    }

    private fun onItemClick(movie: Movie) {
        detailMovie(movie)

        /*val detailFragment = DetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable(DetailFragment.EXTRAS_MOVIE, movie)
            }
        }

        detailFragment.show(parentFragmentManager, DetailFragment::class.java.simpleName)*/
    }

    private fun bindDataMovie(movie: List<Movie>) {
        val randomMovieIndex = movie.indices.random()
        val randomMovie = movie[randomMovieIndex]

        currentBannerMovie = randomMovie

        binding.layoutBanner.tvMovieTittle.text = randomMovie.title
        binding.layoutBanner.tvMovieDescription.text = randomMovie.desc
        binding.layoutBanner.ivBanner.load("https://image.tmdb.org/t/p/w500/${randomMovie.image}") {
            crossfade(true)
        }
    }

    private fun setupAdapters() {
        val itemClickListener = object : OnItemClickedListener<Movie> {
            override fun onItemClicked(item: Movie) {
                onItemClick(item)
            }
        }

        nowPlayingAdapter = MovieAdapter(itemClickListener)
        popularAdapter = MovieAdapter(itemClickListener)
        upcomingAdapter = MovieAdapter(itemClickListener)
        topRatedAdapter = MovieAdapter(itemClickListener)

        binding.rvMovieNowPlaying.adapter = nowPlayingAdapter
        binding.rvMoviePopular.adapter = popularAdapter
        binding.rvMovieUpcomingMovies.adapter = upcomingAdapter
        binding.rvMovieTopRated.adapter = topRatedAdapter
    }

    private fun observeMovieNowPlayingData() {
        homeViewModel.getMovieNowPlaying().observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    //binding.pbLoadingNowPlaying.isVisible = false
                    binding.rvMovieNowPlaying.isVisible = true
                    binding.shimmerNowPlaying.isVisible = false
                    binding.shimmerNowPlaying.stopShimmer()
                    it.payload?.let { data ->
                        nowPlayingAdapter.submitData(data)
                    }
                },
                doOnLoading = {
                    //binding.pbLoadingNowPlaying.isVisible = true
                    binding.rvMovieNowPlaying.isVisible = false
                    binding.shimmerNowPlaying.isVisible = true
                    binding.shimmerNowPlaying.startShimmer()
                },
                doOnError = {
                    //binding.pbLoadingNowPlaying.isVisible = false
                    binding.shimmerNowPlaying.isVisible = false
                    binding.shimmerNowPlaying.stopShimmer()
                },
            )
        }
    }

    private fun observeMoviePopularData() {
        homeViewModel.getMoviePopular().observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    //binding.pbLoadingPopular.isVisible = false
                    binding.layoutBanner.ivBanner.isVisible = true
                    binding.layoutBanner.tvMovieTittle.isVisible = true
                    binding.layoutBanner.tvMovieDescription.isVisible = true
                    binding.layoutBanner.btnInfo.isVisible = true
                    binding.layoutBanner.btnShare.isVisible = true

                    //binding.layoutBannerShimmer.ivBanner.isVisible = false

                    binding.shimmerPopular.isVisible = false
                    binding.shimmerPopular.stopShimmer()

                    binding.rvMoviePopular.isVisible = true
                    it.payload?.let { data ->
                        bindDataMovie(data)
                        popularAdapter.submitData(data)
                    }
                },
                doOnLoading = {
                    //binding.pbLoadingPopular.isVisible = true

                    binding.layoutBanner.ivBanner.isVisible = false
                    binding.layoutBanner.tvMovieTittle.isVisible = false
                    binding.layoutBanner.tvMovieDescription.isVisible = false
                    binding.layoutBanner.btnInfo.isVisible = false
                    binding.layoutBanner.btnShare.isVisible = false

                    //binding.layoutBannerShimmer.ivBanner.isVisible = true

                    binding.shimmerPopular.isVisible = true
                    binding.shimmerPopular.startShimmer()

                    binding.rvMoviePopular.isVisible = false
                },
                doOnError = {
                    //binding.pbLoadingPopular.isVisible = false

                    binding.layoutBanner.ivBanner.isVisible = true
                    binding.layoutBanner.tvMovieTittle.isVisible = true
                    binding.layoutBanner.tvMovieDescription.isVisible = true
                    binding.layoutBanner.btnInfo.isVisible = true
                    binding.layoutBanner.btnShare.isVisible = true

                    //binding.layoutBannerShimmer.ivBanner.isVisible = false

                    binding.shimmerPopular.isVisible = false
                    binding.shimmerPopular.stopShimmer()
                },
            )
        }
    }

    private fun observeMovieUpcomingData() {
        homeViewModel.getMovieUpcoming().observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    //binding.pbLoadingUpcomingMovies.isVisible = false

                    binding.shimmerUpcoming.isVisible = false
                    binding.shimmerUpcoming.stopShimmer()

                    binding.rvMovieUpcomingMovies.isVisible = true
                    it.payload?.let { data ->
                        upcomingAdapter.submitData(data)
                    }
                },
                doOnLoading = {
                    //binding.pbLoadingUpcomingMovies.isVisible = true
                    binding.rvMovieUpcomingMovies.isVisible = false

                    binding.shimmerUpcoming.isVisible = true
                    binding.shimmerUpcoming.startShimmer()
                },
                doOnError = {
                    //binding.pbLoadingUpcomingMovies.isVisible = false
                    binding.shimmerUpcoming.isVisible = false
                    binding.shimmerUpcoming.stopShimmer()
                },
            )
        }
    }

    private fun observeTopRatedData() {
        homeViewModel.getMovieTopRating().observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    //binding.pbLoadingTopRated.isVisible = false

                    binding.shimmerTopRated.isVisible = false
                    binding.shimmerTopRated.stopShimmer()

                    binding.rvMovieTopRated.isVisible = true
                    it.payload?.let { data ->
                        topRatedAdapter.submitData(data)
                    }
                },
                doOnLoading = {
                    //binding.pbLoadingTopRated.isVisible = true

                    binding.shimmerTopRated.isVisible = true
                    binding.shimmerTopRated.startShimmer()

                    binding.rvMovieTopRated.isVisible = false
                },
                doOnError = {
                    //binding.pbLoadingTopRated.isVisible = false

                    binding.shimmerTopRated.isVisible = false
                    binding.shimmerTopRated.stopShimmer()
                },
            )
        }
    }



    private fun detailMovie(movie: Movie) {
        val detailFragmentDialog = BottomSheetDialog(requireContext())
        val bottomSheetBinding = FragmentDetailBinding.inflate(layoutInflater)
        bottomSheetBinding.apply {
            ivDetailMovie.load("https://image.tmdb.org/t/p/w500${movie.image}")
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
        homeViewModel.getCoverPhoto(id).observe(viewLifecycleOwner) { result ->
            bottomSheetBinding.ivDetailMovie.load("https://image.tmdb.org/t/p/w500${result.payload?.coverImage}")
        }
    }

    private fun movieInFavorite(
        data: Movie,
        detailFragmentBinding: FragmentDetailBinding,
    ) {
        homeViewModel.checkMovieList(data.id).observe(
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
        homeViewModel.addToFavorite(data).observe(viewLifecycleOwner) {
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
        homeViewModel.removeFavoriteById(movieId).observe(viewLifecycleOwner) {
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