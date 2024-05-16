package com.cinemate.cinemateapp.presentation.more.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.cinemate.cinemateapp.base.OnItemClickListener
import com.cinemate.cinemateapp.data.model.Movie
import com.cinemate.cinemateapp.databinding.ItemMovieListBinding

class MoreListAdapter(
    private val listener: OnItemClickListener<Movie>
): PagingDataAdapter<Movie, MoreListAdapter.MoreListViewHolder>(DIFF_CALLBACK) {

    private val data = mutableListOf<Movie>()

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(
                oldItem: Movie,
                newItem: Movie
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Movie,
                newItem: Movie
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoreListViewHolder {
        return MoreListViewHolder(ItemMovieListBinding.inflate(LayoutInflater.from(parent.context), parent, false), listener)
    }

    override fun onBindViewHolder(holder: MoreListViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    inner class MoreListViewHolder(
        private val binding: ItemMovieListBinding,
        private val listener: OnItemClickListener<Movie>
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie){
            binding.ivMovieListPopular.load("https://image.tmdb.org/t/p/w500/${item.image}") {
                crossfade(true)
            }
            binding.tvRating.text = item.rating.toString()
            itemView.setOnClickListener {
                listener.onClicked(item)
            }
        }
    }

}