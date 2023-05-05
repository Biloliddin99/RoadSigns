package com.example.yolbelgilari

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.yolbelgilari.databinding.ActivityMainBinding
import com.example.yolbelgilari.fragments.AboutFragment
import com.example.yolbelgilari.fragments.HomeFragment
import com.example.yolbelgilari.fragments.LikeFragment


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var homeFragment: HomeFragment
    private lateinit var likeFragment: LikeFragment
    private lateinit var aboutFragment: AboutFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        homeFragment = HomeFragment()
        likeFragment = LikeFragment()
        aboutFragment = AboutFragment()
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
            .add(binding.myContainer.id, homeFragment)
            .commit()

        binding.btmNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    fragmentManager.beginTransaction()
                        .replace(binding.myContainer.id, homeFragment)
                        .commit()
                }

                R.id.like -> {
                    fragmentManager.beginTransaction()
                        .replace(binding.myContainer.id, likeFragment)
                        .commit()
                }

                R.id.about -> {
                    fragmentManager.beginTransaction()
                        .replace(binding.myContainer.id, aboutFragment)
                        .commit()
                }
            }
            true
        }
    }

    override fun onBackPressed() {
        finish()
    }

}