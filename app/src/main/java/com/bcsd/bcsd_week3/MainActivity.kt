package com.bcsd.bcsd_week3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bcsd.bcsd_week3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding = ActivityMainBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.root.setOnClickListener {  }
    }
}