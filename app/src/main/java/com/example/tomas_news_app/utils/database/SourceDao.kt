package com.example.tomas_news_app.utils.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SourceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sources: List<SourceEntity>)

    @Query(
        """
        SELECT * FROM SourceEntity ORDER BY id DESC
    """
    )
    fun query(): List<SourceEntity>
}
