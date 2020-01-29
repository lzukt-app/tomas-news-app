package com.example.tomasNewsApp.utils.database

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
        SELECT * FROM SourceEntity ORDER BY title ASC
    """
    )
    fun query(): List<SourceEntity>

    @Query(
        """
        SELECT * FROM SourceEntity ORDER BY title DESC
    """
    )
    fun queryDESC(): List<SourceEntity>

    @Query(
        """
            SELECT * FROM SourceEntity WHERE upper(title) LIKE '%' || upper(:searchText) || '%'
        """
    )
    fun getSourcesBySearch(searchText: String): List<SourceEntity>
}
