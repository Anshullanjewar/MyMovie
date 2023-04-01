package com.example.mymovies

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovies.DB.MovieDao
import com.example.mymovies.DB.MovieModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MovieAdapter(private val mList: List<MovieModel>, private val dao: MovieDao):RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    val itemViewScope: CoroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = mList[position]
        Picasso.get().load(movie.MoviePoster).into(holder.poster)

        holder.title.setText(movie.Title)
        holder.releaseDate.setText(movie.Year)
        holder.runTime.setText(movie.Runtime)
        holder.cast.setText(movie.Cast)

        holder.favoriteButton.setOnClickListener {
            itemViewScope.launch {
                movie.favorite = !movie.favorite
                dao.update(movie)
            }
        }

            holder.deleteButton.setOnClickListener {
                itemViewScope.launch {
                    dao.deleteMovie(movie)
                }
            }

    }

        override fun getItemCount(): Int {
            return mList.size
        }

        class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
            val poster: ImageView = ItemView.findViewById(R.id.movie_poster)
            val title: TextView = ItemView.findViewById(R.id.movie_title)
            val releaseDate: TextView = ItemView.findViewById(R.id.movie_release_year)
            val runTime: TextView = ItemView.findViewById(R.id.movie_runtime)
            val cast: TextView = ItemView.findViewById(R.id.movie_cast)
            val favoriteButton: Button = itemView.findViewById(R.id.fav)
            val deleteButton: Button = itemView.findViewById(R.id.delete)
        }


}