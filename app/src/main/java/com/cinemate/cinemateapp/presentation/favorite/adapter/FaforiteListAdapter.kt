package com.cinemate.cinemateapp.presentation.favorite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.cinemate.cinemateapp.data.model.Favorite
import com.cinemate.cinemateapp.data.model.Movie
import com.cinemate.cinemateapp.databinding.ItemMovieBinding
import com.cinemate.cinemateapp.presentation.home.adapters.movie.MovieAdapter


class FavoriteListAdapter(private val favoriteListener: FavoriteListener? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val dataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<Favorite>() {
                override fun areItemsTheSame(
                    oldItem: Favorite,
                    newItem: Favorite,
                ): Boolean {
                    return oldItem.movieId == newItem.movieId && oldItem.movieId == newItem.movieId
                }

                override fun areContentsTheSame(
                    oldItem: Favorite,
                    newItem: Favorite,
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            },
        )


    fun submitData(data: List<Favorite>) {
        dataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        return if (favoriteListener != null) {
            FavoriteViewHolder(
                ItemMovieBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                ),
                favoriteListener,
            )
        } else {
            FavoriteViewHolder(
                ItemMovieBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                ),
                favoriteListener,
            )
        }
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        (holder as ViewHolderBinder<Favorite>).bind(dataDiffer.currentList[position])
    }
}

class FavoriteViewHolder(
    private val binding: ItemMovieBinding,
    private val favoriteListener: FavoriteListener?,
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Favorite> {
    override fun bind(item: Favorite) {
        setCartData(item)
        setClickListeners(item)
    }

    private fun setCartData(item: Favorite) {
        with(binding) {
            binding.ivMovieImage.load("https://image.tmdb.org/t/p/w500/${item.movieImage}") {
                crossfade(true)
            }
        }
    }

    private fun setClickListeners(item: Favorite) {
        // ntar buat close dari layout favorite nya
//        with(binding) {
//            ivRemoveFavorite.setOnClickListener { favoriteListener?.onRemoveFavoriteClicked(item) }
//        }
    }
}

interface FavoriteListener {
    fun onRemoveFavoriteClicked(item: Favorite)
}

interface ViewHolderBinder<T> {
    fun bind(item: T)
}