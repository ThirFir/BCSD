package com.bcsd.bcsd_week3.week12

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WordEntity::class], version = 1)
abstract class Week12Database: RoomDatabase() {
    abstract fun wordDao(): WordDao

    companion object {
        private var INSTANCE: Week12Database? = null
        fun getInstance(context: Context): Week12Database? {
            if(INSTANCE == null) {
                synchronized(Week12Database::class) {
                    INSTANCE = androidx.room.Room.databaseBuilder(context.applicationContext,
                        Week12Database::class.java, "word_database")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}