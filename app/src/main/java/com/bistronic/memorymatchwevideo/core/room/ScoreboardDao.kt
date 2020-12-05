package com.bistronic.memorymatchwevideo.core.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bistronic.memorymatchwevideo.data.model.Score

/**
 * Created by paulbisioc on 12/4/2020.
 */
@Dao
interface ScoreboardDao {
    @Query("SELECT * FROM scores ORDER BY gameType ASC, score DESC")
    fun getAll(): LiveData<List<Score>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: Score)
}