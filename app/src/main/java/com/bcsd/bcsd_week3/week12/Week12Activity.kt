package com.bcsd.bcsd_week3.week12

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bcsd.bcsd_week3.R
import com.bcsd.bcsd_week3.databinding.ActivityWeek12Binding
import com.bumptech.glide.Glide

class Week12Activity : AppCompatActivity() {
    lateinit var binding: ActivityWeek12Binding
    private lateinit var wordsViewModel: WordsViewModel
    private val headWordViewModel: HeadWordViewModel by viewModels()
    private var selectedPosition: Int? = null

    private lateinit var onFloatingActionButtonClickListener: View.OnClickListener
    private lateinit var onDeleteClickListener: View.OnClickListener
    private lateinit var onEditClickListener: View.OnClickListener

    private val addResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            val name = it.data?.getStringExtra("name")
            val meaning = it.data?.getStringExtra("meaning")
            val imageUri = it.data?.getStringExtra("imageUri")
            wordsViewModel.insertWord(Word(name!!, meaning!!, imageUri))
        }
    }
    private val updateResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            val name = it.data?.getStringExtra("name")
            val meaning = it.data?.getStringExtra("meaning")
            val imageUri = it.data?.getStringExtra("imageUri")
            wordsViewModel.updateWord(Word(name!!, meaning!!, imageUri), selectedPosition!!)
            headWordViewModel.setWord(Word(name, meaning, imageUri))
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_week12)
        binding.word = headWordViewModel
        binding.lifecycleOwner = this

        wordsViewModel = WordsViewModel(WordRepositoryImpl(application))
        wordsViewModel.wordList.observe(this) {
            (binding.recyclerViewWords.adapter as WordAdapter).setWordList(it)
        }

        binding.recyclerViewWords.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewWords.adapter = WordAdapter(wordsViewModel.wordList.value ?: emptyList()) {
            headWordViewModel.setWord(it)
            selectedPosition = wordsViewModel.wordList.value?.indexOf(it)
            binding.headWrapper.visibility = View.VISIBLE
            Glide.with(this).load(it.imageUri).into(binding.imageViewHeadWord)
        }

        initClickListeners()
        registerOnClickListeners()

    }

    private fun initClickListeners() {
        onFloatingActionButtonClickListener = View.OnClickListener {
            val intent = Intent(this, WordAddActivity::class.java)
            addResult.launch(intent)
        }

        onDeleteClickListener = View.OnClickListener {
            wordsViewModel.deleteWord(headWordViewModel.word.value!!)
            headWordViewModel.clearWord()
            binding.headWrapper.visibility = View.GONE
            selectedPosition = null
        }

        onEditClickListener = View.OnClickListener {
            val intent = Intent(this, WordAddActivity::class.java)
            intent.putExtra("name", headWordViewModel.word.value!!.name)
            intent.putExtra("meaning", headWordViewModel.word.value!!.meaning)
            intent.putExtra("imageUri", headWordViewModel.word.value!!.imageUri)
            updateResult.launch(intent)
        }
    }

    private fun registerOnClickListeners() {
        binding.floatingActionButton.setOnClickListener(onFloatingActionButtonClickListener)
        binding.imageViewDelete.setOnClickListener(onDeleteClickListener)
        binding.imageViewEdit.setOnClickListener(onEditClickListener)
    }
}