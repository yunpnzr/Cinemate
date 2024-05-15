package com.cinemate.cinemateapp.presentation.more

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.cinemate.cinemateapp.R
import com.cinemate.cinemateapp.base.OnItemClickListener
import com.cinemate.cinemateapp.data.model.Movie
import com.cinemate.cinemateapp.databinding.ActivityMoreListBinding
import com.cinemate.cinemateapp.presentation.more.adapter.MoreListAdapter
import com.cinemate.cinemateapp.utils.ResultWrapper
import com.cinemate.cinemateapp.utils.proceedWhen
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
        supportActionBar?.let {actionBar ->
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_white)
        }
    }

    private fun setupAdapter() {
        moreAdapter = MoreListAdapter(
            listener = object: OnItemClickListener<Movie> {
                override fun onClicked(item: Movie) {
                    onItemClick(item)
                }
            }
        )

        binding.rvMoreList.apply {
            adapter = this@MoreListActivity.moreAdapter
            layoutManager = GridLayoutManager(this@MoreListActivity, 3)
        }
    }

    private fun onItemClick(item: Movie) {
//        val intent = Intent(this, DetailFragment::class.java)
//        intent.putExtra("EXTRAS", movie)
//        startActivity(intent)
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
                viewModel.nowPlaying(1).observe(this) { result ->
                    resultMovies(result)
                }
            }
            TYPE_POPULAR -> {
                supportActionBar?.title = getString(R.string.popular_movies_title)
                viewModel.getPopularMovie(1).observe(this) { result ->
                    resultMovies(result)
                }
            }
            TYPE_TOP_RATED -> {
                supportActionBar?.title = getString(R.string.top_rated_movies_title)
                viewModel.topRating(1).observe(this) { result ->
                    resultMovies(result)
                }
            }
            TYPE_UPCOMING -> {
                supportActionBar?.title = getString(R.string.upcoming_movies_title)
                viewModel.upcoming(1).observe(this) { result ->
                    resultMovies(result)
                }
            }
        }
    }

    private fun resultMovies(result: ResultWrapper<List<Movie>>) {
        result.proceedWhen (
            doOnSuccess = { it ->
                it.payload?.let {
                    setBindData(it)
                }
            }
        )
    }

    private fun setBindData(data: List<Movie>) {
        moreAdapter.submitData(data)
    }

    companion object {
        const val MOVIE_TYPE = "EXTRA_MOVIE_TYPE"
        const val TYPE_NOW_PLAYING = "now_playing"
        const val TYPE_POPULAR = "popular"
        const val TYPE_TOP_RATED = "top_rated"
        const val TYPE_UPCOMING = "upcoming"
    }
}