package com.cinemate.cinemateapp.presentation.favorite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.cinemate.cinemateapp.data.model.Movie
import com.cinemate.cinemateapp.databinding.ItemMovieBinding
import com.cinemate.cinemateapp.presentation.home.adapters.movie.MovieAdapter


class FavoriteListAdapter(private val itemClick: (Movie) -> Unit) :
    RecyclerView.Adapter<FavoriteListAdapter.FavoriteViewHolder>()  {
    private val dataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<Movie>() {
                override fun areItemsTheSame(
                    oldItem: Movie,
                    newItem: Movie,
                ): Boolean {
                    return oldItem.id == newItem.id && oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Movie,
                    newItem: Movie,
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            },
        )



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): FavoriteViewHolder {
        val binding =
                ItemMovieBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
        return FavoriteViewHolder(binding, itemClick)

    }
    fun submitData(data: List<Movie>) {
        dataDiffer.submitList(data)
    }



    override fun getItemCount(): Int = dataDiffer.currentList.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(dataDiffer.currentList[position])
    }

    class FavoriteViewHolder(
        private val binding: ItemMovieBinding,
        val itemClick: (Movie) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Movie> {

        override fun bind(item: Movie) {
            with(item) {
                binding.ivMovieImage.load("https://image.tmdb.org/t/p/w500/${item.image}") {
                    crossfade(true)
                }
                itemView.setOnClickListener { itemClick(this) }
            }
        }

    }
}





interface ViewHolderBinder<T> {
    fun bind(item: T)
}