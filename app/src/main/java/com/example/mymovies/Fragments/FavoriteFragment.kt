package com.example.mymovies.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.mymovies.DB.MovieDao
import com.example.mymovies.DB.MovieDatabase
import com.example.mymovies.DB.MovieModel
import com.example.mymovies.FavoriteAdapter
import com.example.mymovies.R
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class FavoriteFragment : Fragment() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FavoriteAdapter

    private lateinit var database: MovieDatabase
    private lateinit var dao: MovieDao

    private val data = mutableListOf<MovieModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)

        database = Room.databaseBuilder(requireContext(), MovieDatabase::class.java, "app_database")
            .build()
        dao = database.movDao()

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = FavoriteAdapter(data,dao)
        recyclerView.adapter = adapter

        loadData()

        return view
    }

    private fun loadData() {
        dao.getFavoriteMovies().onEach { favoriteMovies ->
            Log.d("FavoriteFragment", "Loaded ${favoriteMovies.size} favorite movies from database")

            data.clear()
            data.addAll(favoriteMovies)
            adapter.notifyDataSetChanged()
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
}
