package com.bcsd.bcsd_week3.week12

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bcsd.bcsd_week3.R
import com.bcsd.bcsd_week3.databinding.ActivityWordAddBinding

class WordAddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWordAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWordAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var name = intent.getStringExtra("name")
        var meaning = intent.getStringExtra("meaning")

        binding.textInputWordName.editText?.setText(name)
        binding.textInputWordMeaning.editText?.setText(meaning)

        binding.textButton.setOnClickListener {
            name = binding.textInputWordName.editText?.text.toString()
            meaning = binding.textInputWordMeaning.editText?.text.toString()
            intent.putExtra("name", name)
            intent.putExtra("meaning", meaning)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}