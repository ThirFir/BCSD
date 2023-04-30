package com.bcsd.bcsd_week3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bcsd.bcsd_week3.databinding.ActivityWeek4Binding
import com.bcsd.bcsd_week3.databinding.ActivityWeek4RandomBinding
import kotlin.random.Random

class Week4RandomActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWeek4RandomBinding
    var random: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeek4RandomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val r = intent.getIntExtra("count", 1)
        random = if (r != 0) Random.nextInt(r) else 1
        binding.textRandomNumber.text = random.toString()
        binding.textRandomText.text = "Random Number between 0 and $r"
        intent.putExtra("random", random)
        setResult(RESULT_OK, intent)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}