package com.cinemate.cinemateapp.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import coil.load
import com.cinemate.cinemateapp.R
import com.cinemate.cinemateapp.data.model.Movie
import com.cinemate.cinemateapp.databinding.FragmentHomeBinding
import com.cinemate.cinemateapp.presentation.detail.DetailFragment
import com.cinemate.cinemateapp.presentation.home.adapters.movie.MovieAdapter
import com.cinemate.cinemateapp.presentation.home.adapters.movie.OnItemClickedListener
import com.cinemate.cinemateapp.presentation.more.MoreListActivity
import com.cinemate.cinemateapp.utils.proceedWhen
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
        val detailFragment = DetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable(DetailFragment.EXTRAS_MOVIE, movie)
            }
        }

        detailFragment.show(parentFragmentManager, DetailFragment::class.java.simpleName)
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
                    binding.pbLoadingNowPlaying.isVisible = false
                    binding.rvMovieNowPlaying.isVisible = true
                    it.payload?.let { data ->
                        nowPlayingAdapter.submitData(data)
                    }
                },
                doOnLoading = {
                    binding.pbLoadingNowPlaying.isVisible = true
                    binding.rvMovieNowPlaying.isVisible = false
                },
                doOnError = {
                    binding.pbLoadingNowPlaying.isVisible = false
                },
            )
        }
    }

    private fun observeMoviePopularData() {
        homeViewModel.getMoviePopular().observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.pbLoadingPopular.isVisible = false
                    binding.rvMoviePopular.isVisible = true
                    it.payload?.let { data ->
                        bindDataMovie(data)
                        popularAdapter.submitData(data)
                    }
                },
                doOnLoading = {
                    binding.pbLoadingPopular.isVisible = true
                    binding.rvMoviePopular.isVisible = false
                },
                doOnError = {
                    binding.pbLoadingPopular.isVisible = false
                },
            )
        }
    }

    private fun observeMovieUpcomingData() {
        homeViewModel.getMovieUpcoming().observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.pbLoadingUpcomingMovies.isVisible = false
                    binding.rvMovieUpcomingMovies.isVisible = true
                    it.payload?.let { data ->
                        upcomingAdapter.submitData(data)
                    }
                },
                doOnLoading = {
                    binding.pbLoadingUpcomingMovies.isVisible = true
                    binding.rvMovieUpcomingMovies.isVisible = false
                },
                doOnError = {
                    binding.pbLoadingUpcomingMovies.isVisible = false
                },
            )
        }
    }

    private fun observeTopRatedData() {
        homeViewModel.getMovieTopRating().observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.pbLoadingTopRated.isVisible = false
                    binding.rvMovieTopRated.isVisible = true
                    it.payload?.let { data ->
                        topRatedAdapter.submitData(data)
                    }
                },
                doOnLoading = {
                    binding.pbLoadingTopRated.isVisible = true
                    binding.rvMovieTopRated.isVisible = false
                },
                doOnError = {
                    binding.pbLoadingTopRated.isVisible = false
                },
            )
        }
    }
}