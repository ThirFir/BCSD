package com.bcsd.bcsd_week3.week12

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface WordDao {
    @Insert
    fun insertWord(word: WordEntity)

    @Query("select * from words")
    fun getWordList(): List<WordEntity>

    @Delete
    fun deleteWord(word: WordEntity)

    @Update
    fun updateWord(word: WordEntity)
}