package com.vsa.filmoteca.presentation.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vsa.filmoteca.databinding.RowMovieBinding
import com.vsa.filmoteca.presentation.view.adapter.model.MovieViewModel

/**
 * Created by seldon on 27/03/15.
 */
class MoviesAdapter(context: Context,
                    private val callback: Callback)
    : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {


    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val movies: MutableList<MovieViewModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): MovieViewHolder =
            MovieViewHolder(RowMovieBinding.inflate(inflater, parent, false))

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(movieViewHolder: MovieViewHolder, position: Int) {
        with(movieViewHolder.binding) {
            textViewDate.text = movies[position].date
            textViewTitle.text = movies[position].title
            movies[position].place?.let { place ->
                textViewPlace.visibility = View.VISIBLE
                textViewPlace.text = place
            }
            wrapperContent.setOnClickListener {
                callback.onMovieClick(position)
            }
        }
    }

    fun update(movies: List<MovieViewModel>) {
        with(this.movies) {
            clear()
            addAll(movies)
        }
        notifyDataSetChanged()
    }

    class MovieViewHolder constructor(val binding: RowMovieBinding) : RecyclerView.ViewHolder(binding.root)

    interface Callback {

        fun onMovieClick(position: Int)

    }

}
