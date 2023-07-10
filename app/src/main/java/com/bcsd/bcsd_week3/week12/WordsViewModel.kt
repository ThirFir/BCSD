package com.bcsd.bcsd_week3.week12

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class WordsViewModel(private val wordRepository: WordRepository): ViewModel() {
    private val list = mutableListOf<Word>()

    private val _wordList = MutableLiveData<List<Word>>()
    val wordList : LiveData<List<Word>>
        get() = _wordList

    init {
        initWordList()
    }

    private fun initWordList() {
        viewModelScope.launch {
            list.addAll(wordRepository.getWordList())
            _wordList.value = list
            Log.d("Week12Activity", "list: $list")
        }
    }

    fun insertWord(word: Word) {
        list.add(word)
        _wordList.value = list
        viewModelScope.launch {
            wordRepository.insertWord(word)
        }
    }

    fun updateWord(word: Word, position: Int) {
        list[position] = word
        _wordList.value = list
        viewModelScope.launch {
            wordRepository.updateWord(word)
        }
    }

    fun deleteWord(word: Word) {
        list.remove(word)
        _wordList.value = list
        viewModelScope.launch {
            wordRepository.deleteWord(word)
        }
    }

}