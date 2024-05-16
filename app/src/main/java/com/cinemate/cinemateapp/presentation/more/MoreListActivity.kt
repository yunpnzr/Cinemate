package com.cinemate.cinemateapp.presentation.more

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import com.cinemate.cinemateapp.R
import com.cinemate.cinemateapp.base.OnItemClickListener
import com.cinemate.cinemateapp.data.model.Movie
import com.cinemate.cinemateapp.databinding.ActivityMoreListBinding
import com.cinemate.cinemateapp.presentation.detail.DetailFragment
import com.cinemate.cinemateapp.presentation.more.adapter.MoreListAdapter
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

    companion object {
        const val MOVIE_TYPE = "EXTRA_MOVIE_TYPE"
        const val TYPE_NOW_PLAYING = "now_playing"
        const val TYPE_POPULAR = "popular"
        const val TYPE_TOP_RATED = "top_rated"
        const val TYPE_UPCOMING = "upcoming"
    }
}