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

class Week12Activity : AppCompatActivity() {
    lateinit var binding: ActivityWeek12Binding
    private lateinit var wordsViewModel: WordsViewModel
    private val headWordViewModel: HeadWordViewModel by viewModels()
    private var selectedPosition: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_week12)
        binding.word = headWordViewModel
        binding.lifecycleOwner = this

        val wordDao = Week12Database.getInstance(this)?.wordDao()
        val wordRepository = wordDao?.let { WordRepositoryImpl(it) }

        wordsViewModel = WordsViewModel(wordRepository!!)
        wordsViewModel.wordList.observe(this) {
            (binding.recyclerViewWords.adapter as WordAdapter).setWordList(it)
        }

        binding.recyclerViewWords.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewWords.adapter = WordAdapter(wordsViewModel.wordList.value ?: emptyList()) {
            headWordViewModel.setWord(it)
            selectedPosition = wordsViewModel.wordList.value?.indexOf(it)
            binding.headWrapper.visibility = View.VISIBLE
        }

        val addResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val name = it.data?.getStringExtra("name")
                val meaning = it.data?.getStringExtra("meaning")
                wordsViewModel.insertWord(Word(name!!, meaning!!))
            }
        }

        val updateResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val name = it.data?.getStringExtra("name")
                val meaning = it.data?.getStringExtra("meaning")
                wordsViewModel.updateWord(Word(name!!, meaning!!), selectedPosition!!)
                headWordViewModel.setWord(Word(name, meaning))
            }
        }

        binding.floatingActionButton.setOnClickListener {
            addResult.launch(Intent(this, WordAddActivity::class.java))
        }

        binding.imageViewDelete.setOnClickListener {
            wordsViewModel.deleteWord(headWordViewModel.word.value!!)
            headWordViewModel.clearWord()
            binding.headWrapper.visibility = View.GONE
            selectedPosition = null
        }

        binding.imageViewEdit.setOnClickListener {
            val intent = Intent(this, WordAddActivity::class.java)
            intent.putExtra("name", headWordViewModel.word.value!!.name)
            intent.putExtra("meaning", headWordViewModel.word.value!!.meaning)
            updateResult.launch(intent)
        }
    }
}