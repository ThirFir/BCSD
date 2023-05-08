package com.bcsd.bcsd_week3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import com.bcsd.bcsd_week3.databinding.ActivityWeek5Binding

class Week5Activity : AppCompatActivity() {
    private lateinit var binding: ActivityWeek5Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeek5Binding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().add(R.id.layout_week5_activity, Week5MainFragment()).commit()
    }
}