package com.example.tomasNewsApp.utils.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.util.concurrent.Executors

const val DATABASE_VERSION = 7

@Database(entities = [SourceEntity::class, ArticleEntity::class], version = DATABASE_VERSION)
abstract class NewsDatabase : RoomDatabase() {
    abstract val sourceDao: SourceDao
    abstract val articleDao: ArticleDao

    companion object {
        // not needed
        var context: Context? = null
        fun getInstance(context: Context): NewsDatabase {
            if (this.context == null) {
                this.context = context/*.applicationContext*/
            }
            return Room.databaseBuilder(context, NewsDatabase::class.java, "main.db")

                .setQueryExecutor(Executors.newSingleThreadExecutor())
                .fallbackToDestructiveMigration()
                .build()
        }
//        fun getInstance(context: Context) =
//            Room.databaseBuilder(context, NewsDatabase::class.java, "main.db")
//                .setQueryExecutor(Executors.newSingleThreadExecutor())
//                .fallbackToDestructiveMigration()
//                .build()
    }
}