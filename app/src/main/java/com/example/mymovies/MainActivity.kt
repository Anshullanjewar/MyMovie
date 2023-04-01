package com.example.mymovies

import android.os.Build.VERSION_CODES.P
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.mymovies.Fragments.FavoriteFragment
import com.example.mymovies.Fragments.MovieFragment
import com.example.mymovies.Fragments.ProfileFragment
import com.example.mymovies.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

     private lateinit var  binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movie = MovieFragment()
        val fav= FavoriteFragment()
        val prof= ProfileFragment()

        replaceFragment(movie)
        binding.bottomNavView.setOnItemSelectedListener {

            when(it.itemId){
                R.id.Movie -> replaceFragment(movie)
                R.id.favorite -> replaceFragment(fav)
                R.id.profile -> replaceFragment(prof)

                else ->{
                }
            }
            true
        }

    }
    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction= fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.FragmentContainer,fragment)
        fragmentTransaction.commit()
    }
}