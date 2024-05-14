package com.cinemate.cinemateapp.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.cinemate.cinemateapp.R
import com.cinemate.cinemateapp.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUpBottomNav()
        //tryConn()
    }

    private fun setUpBottomNav() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        binding.navBottomMain.setupWithNavController(navController)
    }

    /*private fun tryConn(){
        viewModel.getNowPlaying(1).observe(this){

        }
        viewModel.getPopular(1).observe(this) {

        }
        viewModel.getTopRating(1).observe(this) {

        }
        viewModel.getUpcoming(1).observe(this) {

        }
        viewModel.getDetailMovie(653346).observe(this) {

        }
    }*/
}