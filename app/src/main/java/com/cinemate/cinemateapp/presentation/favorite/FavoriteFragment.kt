package com.cinemate.cinemateapp.presentation.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.cinemate.cinemateapp.R
import com.cinemate.cinemateapp.data.model.Favorite
import com.cinemate.cinemateapp.data.model.Movie
import com.cinemate.cinemateapp.databinding.FragmentFavoriteBinding
import com.cinemate.cinemateapp.presentation.detail.DetailFragment
import com.cinemate.cinemateapp.presentation.favorite.adapter.FavoriteListAdapter
import com.cinemate.cinemateapp.presentation.favorite.adapter.FavoriteListener
import com.cinemate.cinemateapp.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var favoriteShowDetailAdapter: FavoriteListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
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


    private val ViewModel: FavoriteViewModel by viewModel()

    private val adapter: FavoriteListAdapter by lazy {
        FavoriteListAdapter(
//            object : FavoriteListener {
//                override fun onRemoveFaforiteClicked(item: Favorite) {
//                    ViewModel.removeFavorite(item)
//                }
//            },
        )
    }

    private fun onItemClick(movie: Movie) {
        val detailFragment = DetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable(DetailFragment.EXTRAS_MOVIE, movie)
            }
        }

        detailFragment.show(parentFragmentManager, DetailFragment::class.java.simpleName)
    }

    private fun setClickListener() {

    }

    private fun setupList() {
            binding.rvFavorite.itemAnimator = null
            binding.rvFavorite.adapter = adapter
    }


    private fun observeData() {
        ViewModel.getAllFavorites().observe(viewLifecycleOwner) {

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
}