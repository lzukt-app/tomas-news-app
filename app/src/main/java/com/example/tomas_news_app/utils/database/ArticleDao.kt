package com.example.tomas_news_app.utils.database

import androidx.room.*

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(articles: List<ArticleEntity>)

    @Delete
    fun delete(articles: List<ArticleEntity>)

    @Query(
        value = """
                SELECT * FROM ArticleEntity WHERE sourceId = :sourceId AND chipId = :chipId ORDER BY publishedAt DESC
            """
    )
    fun query(sourceId: String, chipId: Int): List<ArticleEntity>

    @Query(
        """
            UPDATE ArticleEntity SET favorite = NOT favorite WHERE url = :url
        """
    )
    fun changeFavoriteStatus(url: String)

    @Query(
        """
            DELETE FROM ArticleEntity WHERE chipId = :chipId AND sourceId = :sourceId AND NOT favorite
        """
    )
    fun deleteAll(sourceId: String, chipId: Int)

    @Query(
        value = """
                SELECT * FROM ArticleEntity WHERE favorite
            """
    )
    fun getFavorite(): List<ArticleEntity>

}
