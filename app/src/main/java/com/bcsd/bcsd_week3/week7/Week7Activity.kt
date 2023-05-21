package com.bcsd.bcsd_week3.week7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bcsd.bcsd_week3.R
import com.bcsd.bcsd_week3.databinding.ActivityWeek7Binding

class Week7Activity : AppCompatActivity() {

    private lateinit var binding: ActivityWeek7Binding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeek7Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager.adapter = ViewPagerAdapter(this, 0)
        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.navigation_first -> {
                    binding.viewPager.adapter = ViewPagerAdapter(this, 0)
                    true
                }
                R.id.navigation_second -> {
                    binding.viewPager.adapter = ViewPagerAdapter(this, 1)
                    true
                }
                R.id.navigation_third -> {
                    binding.viewPager.adapter = ViewPagerAdapter(this, 2)
                    true
                }
                else -> false
            }
        }
    }
}


