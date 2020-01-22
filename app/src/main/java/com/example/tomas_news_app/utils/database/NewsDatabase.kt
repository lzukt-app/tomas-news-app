package com.example.tomas_news_app.utils.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SourceEntity::class], version = 2)
abstract class NewsDatabase : RoomDatabase() {
    abstract val sourceDao: SourceDao

    companion object {
        fun getInstance(context: Context) =
            Room.databaseBuilder(context, NewsDatabase::class.java, "main.db")
                .fallbackToDestructiveMigration()
                .build()
    }
}