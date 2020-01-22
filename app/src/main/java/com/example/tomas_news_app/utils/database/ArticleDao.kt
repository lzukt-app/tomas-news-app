package com.example.tomas_news_app.utils.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(articles: List<ArticleEntity>)

    @Query(
        value = """
                SELECT * FROM ArticleEntity WHERE sourceId = :sourceId AND chipId = :chipId
            """
    )
    fun query(sourceId: String, chipId: Int): List<ArticleEntity>
}
