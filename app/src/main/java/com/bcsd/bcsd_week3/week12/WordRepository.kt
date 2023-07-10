package com.bcsd.bcsd_week3.week12

interface WordRepository {
    suspend fun getWordList(): List<Word>
    suspend fun insertWord(word: Word)
    suspend fun deleteWord(word: Word)
    suspend fun updateWord(word: Word)
}