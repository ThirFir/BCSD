package com.bcsd.bcsd_week3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bcsd.bcsd_week3.databinding.ActivityWeek4Binding

class Week4Activity : AppCompatActivity() {
    private lateinit var binding: ActivityWeek4Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeek4Binding.inflate(layoutInflater)
        setContentView(binding.root)

        var count = 0

        binding.buttonToast.setOnClickListener {
            Toast.makeText(this@Week4Activity, "Toast : ${binding.textCountNumber.text}", Toast.LENGTH_SHORT).show()
        }
        binding.buttonCount.setOnClickListener {
            ++count
            binding.textCountNumber.text = count.toString()
        }

        val activityResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == RESULT_OK){
                count = it.data?.getIntExtra("random", count) ?: count
                binding.textCountNumber.text = count.toString()
            }
        }
        binding.buttonRandom.setOnClickListener {
            val intent = Intent(this, Week4RandomActivity::class.java).apply {
                putExtra("count", count)
            }
            activityResult.launch(intent)
        }
    }
}