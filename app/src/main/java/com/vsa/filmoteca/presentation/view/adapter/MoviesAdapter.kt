package com.vsa.filmoteca.presentation.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vsa.filmoteca.databinding.RowMovieBinding

/**
 * Created by seldon on 27/03/15.
 */
class MoviesAdapter(context: Context,
                    private val dataProvider: EventDataProvider,
                    private val callback: Callback)
    : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {


    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): MovieViewHolder =
            MovieViewHolder(RowMovieBinding.inflate(inflater, parent, false))

    override fun getItemCount(): Int {
        return dataProvider.getSize()
    }

    override fun onBindViewHolder(movieViewHolder: MovieViewHolder, position: Int) {
        with(movieViewHolder.binding) {
            textViewDate.text = dataProvider.getDate(position)
            textViewTitle.text = dataProvider.getTitle(position)
            wrapperContent.setOnClickListener {
                callback.onMovieClick(position)
            }
        }
    }

    class MovieViewHolder constructor(val binding: RowMovieBinding) : RecyclerView.ViewHolder(binding.root)

    interface Callback {

        fun onMovieClick(position: Int)

    }

}
