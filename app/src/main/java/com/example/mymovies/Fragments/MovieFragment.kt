package com.example.mymovies.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.mymovies.*
import com.example.mymovies.DB.DataBase
import com.example.mymovies.DB.MovieDao
import com.example.mymovies.DB.MovieDatabase
import com.example.mymovies.DB.MovieModel
import com.example.mymovies.databinding.FragmentMovieBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MovieAdapter

    private lateinit var retrofit: Retrofit
    private lateinit var service: ServiceInterface

    private lateinit var database: MovieDatabase
    private lateinit var dao: MovieDao

    private val data = mutableListOf<MovieModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_movie, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)

        retrofit = Retrofit.Builder()
            .baseUrl("http://task.auditflo.in/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(ServiceInterface::class.java)

        database = Room.databaseBuilder(requireContext(), MovieDatabase::class.java, "app_database")
            .build()
        dao = database.movDao()

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = MovieAdapter(data, dao)
        recyclerView.adapter = adapter

        loadData()

        return view
    }

    private fun loadData() {
        service.getData().enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val mov = response.body()?.MovieList
                    if (mov != null) {
                        data.addAll(mov)
                        adapter.notifyDataSetChanged()
                    }
                    dao.getAllMovies()
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Error loading data", Toast.LENGTH_SHORT).show()
            }
        })

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount
                if (lastVisibleItemPosition == totalItemCount - 1) {
                    loadMoreData()
                }
            }
        })
    }

    private fun loadMoreData() {
        service.getMoreData().enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val mov = response.body()?.MovieList
                    if (mov != null) {
                        data.addAll(mov)
                        adapter.notifyDataSetChanged()
                    }
                    dao.getAllMovies()
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Error loading more data", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
