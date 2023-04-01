package com.example.mymovies

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymovies.DB.MovieDao
import com.example.mymovies.DB.MovieModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FavoriteAdapter(
    private val data: List<MovieModel>,
    private val dao: MovieDao

) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.movie_title)
        val year: TextView = view.findViewById(R.id.movie_release_year)
        val poster: ImageView = view.findViewById(R.id.movie_poster)
        val favorite: CheckBox = view.findViewById(R.id.fav)

    }
    val itemViewScope: CoroutineScope = CoroutineScope(Dispatchers.Main)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.favorite_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.title.text = item.Title
        holder.year.text = item.Year
        holder.favorite.isChecked = true

        Glide.with(holder.itemView.context)
            .load(item.MoviePoster)
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.poster)

        holder.favorite.setOnCheckedChangeListener { _, isChecked ->
            item.favorite = isChecked
            itemViewScope.launch {
                dao.update(item)
            }
        }
        Log.d("FavoriteAdapter", "Bound movie: ${item.Title}")

    }

    override fun getItemCount() = data.size
}
