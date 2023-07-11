package com.bcsd.bcsd_week3.week12

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HeadWordViewModel: ViewModel() {

    private val _word = MutableLiveData<Word>()
    val word : LiveData<Word>
        get() = _word

    fun setWord(word: Word) {
        _word.value = word
    }

    fun clearWord() {
        _word.value = null
    }
}