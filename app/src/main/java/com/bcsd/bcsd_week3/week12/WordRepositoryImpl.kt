package com.bcsd.bcsd_week3.week12

import android.app.Application
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WordRepositoryImpl(private val application: Application): WordRepository {
    private val appDatabase = Week12Database.getInstance(application)
    private val dao = appDatabase?.wordDao()
    private val ioDispatcher = Dispatchers.IO
    override suspend fun getWordList(): List<Word> {
        return withContext(ioDispatcher) {
            dao?.getWordList()?.map { it.toWord() }
        } ?: emptyList()
    }

    override suspend fun insertWord(word: Word) {
        withContext(ioDispatcher) {
            dao?.insertWord(word.toWordEntity())
        }
    }

    override suspend fun deleteWord(word: Word) {
        withContext(ioDispatcher) {
            dao?.deleteWord(word.toWordEntity())
        }
    }

    override suspend fun updateWord(word: Word) {
        withContext(ioDispatcher) {
            dao?.updateWord(word.toWordEntity())
        }
    }
}