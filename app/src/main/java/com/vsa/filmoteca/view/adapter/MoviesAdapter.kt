package com.vsa.filmoteca.view.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.vsa.filmoteca.R

import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.row_movie.*

/**
 * Created by seldon on 27/03/15.
 */
class MoviesAdapter(context: Context,
                    private val dataProvider: EventDataProvider,
                    private val callback: Callback)
    : androidx.recyclerview.widget.RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {


    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): MovieViewHolder =
            MovieViewHolder(inflater.inflate(R.layout.row_movie, parent, false))

    override fun getItemCount(): Int {
        return dataProvider.getSize()
    }

    override fun onBindViewHolder(movieViewHolder: MovieViewHolder, position: Int) {
        movieViewHolder.textViewDate.text = dataProvider.getDate(position)
        movieViewHolder.textViewTitle.text = dataProvider.getTitle(position)
        movieViewHolder.containerView.setOnClickListener {
            callback.onMovieClick(position)
        }
    }

    class MovieViewHolder constructor(override val containerView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(containerView), LayoutContainer

    interface Callback {

        fun onMovieClick(position: Int)

    }

}
