package com.cinemate.cinemateapp.presentation.more

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.cinemate.cinemateapp.R
import com.cinemate.cinemateapp.base.OnItemClickListener
import com.cinemate.cinemateapp.data.model.Movie
import com.cinemate.cinemateapp.databinding.ActivityMoreListBinding
import com.cinemate.cinemateapp.databinding.FragmentDetailBinding
import com.cinemate.cinemateapp.presentation.detail.DetailFragment
import com.cinemate.cinemateapp.presentation.more.adapter.MoreListAdapter
import com.cinemate.cinemateapp.utils.proceedWhen
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.androidx.viewmodel.ext.android.viewModel

class MoreListActivity : AppCompatActivity() {

    private val binding: ActivityMoreListBinding by lazy {
        ActivityMoreListBinding.inflate(layoutInflater)
    }

    private val viewModel: MoreListViewModel by viewModel()

    private lateinit var moreAdapter: MoreListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setAppBar()
        getMoreListType()
        setupAdapter()
    }


    private fun setAppBar() {
        setSupportActionBar(binding.toolbarMoreList)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_back_white)
        }
        binding.toolbarMoreList.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setupAdapter() {
        moreAdapter = MoreListAdapter(
            listener = object: OnItemClickListener<Movie> {
                override fun onClicked(item: Movie) {
                    detailMovie(item)
                }
            }
        )

        binding.rvMoreList.apply {
            adapter = this@MoreListActivity.moreAdapter
            layoutManager = GridLayoutManager(this@MoreListActivity, 3)
        }
    }

    private fun onItemClick(item: Movie) {
        val detailFragment = DetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable(DetailFragment.EXTRAS_MOVIE, item)
            }
        }

        detailFragment.show(supportFragmentManager, DetailFragment::class.java.simpleName)
    }

    private fun getMoreListType() {
        val movieType = intent.getStringExtra(MOVIE_TYPE)
        movieType?.let {
            observeMovies(it)
        }
    }

    private fun observeMovies(movieType: String) {
        when (movieType) {
            TYPE_NOW_PLAYING -> {
                supportActionBar?.title = getString(R.string.now_playing_title)
                viewModel.nowPlaying().observe(this) { result ->
                    resultMovies(result)
                }
            }
            TYPE_POPULAR -> {
                supportActionBar?.title = getString(R.string.popular_movies_title)
                viewModel.getPopularMovie().observe(this) { result ->
                    resultMovies(result)
                }
            }
            TYPE_TOP_RATED -> {
                supportActionBar?.title = getString(R.string.top_rated_movies_title)
                viewModel.topRating().observe(this) { result ->
                    resultMovies(result)
                }
            }
            TYPE_UPCOMING -> {
                supportActionBar?.title = getString(R.string.upcoming_movies_title)
                viewModel.upcoming().observe(this) { result ->
                    resultMovies(result)
                }
            }
        }
    }

    private fun resultMovies(result: PagingData<Movie>) {
        moreAdapter.submitData(lifecycle,result)
    }

    private fun detailMovie(movie: Movie) {
        val detailFragmentDialog = BottomSheetDialog(this)
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
        viewModel.getCoverPhoto(id).observe(this) { result ->
            bottomSheetBinding.ivDetailMovie.load("https://image.tmdb.org/t/p/w500${result.payload?.coverImage}")
        }
    }
    private fun movieInFavorite(
        data: Movie,
        detailFragmentBinding: FragmentDetailBinding,
    ) {
        viewModel.checkMovieList(data.id).observe(
            this,
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
        viewModel.addToFavorite(data).observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    Toast.makeText(
                        this,
                        getString(R.string.text_succes_add_to_favorite),
                        Toast.LENGTH_SHORT,
                    ).show()
                },
                doOnError = {
                    Toast.makeText(
                        this,
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
        viewModel.removeFavoriteById(movieId).observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    Toast.makeText(
                        this,
                        getString(R.string.text_succes_delete_to_favorite),
                        Toast.LENGTH_SHORT,
                    ).show()
                },
                doOnError = {
                    Toast.makeText(
                        this,
                        getString(R.string.text_faliled_delete_to_favorite),
                        Toast.LENGTH_SHORT,
                    )
                        .show()
                },
                doOnLoading = {
                    Toast.makeText(
                        this,
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
        const val MOVIE_TYPE = "EXTRA_MOVIE_TYPE"
        const val TYPE_NOW_PLAYING = "now_playing"
        const val TYPE_POPULAR = "popular"
        const val TYPE_TOP_RATED = "top_rated"
        const val TYPE_UPCOMING = "upcoming"
    }
}