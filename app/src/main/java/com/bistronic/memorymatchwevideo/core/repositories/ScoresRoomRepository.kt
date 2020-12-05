package com.bistronic.memorymatchwevideo.core.repositories

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.bistronic.memorymatchwevideo.core.room.AppDatabase
import com.bistronic.memorymatchwevideo.core.room.ScoreboardDao
import com.bistronic.memorymatchwevideo.data.model.Score
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by paulbisioc on 12/4/2020.
 */
class ScoresRoomRepository(database: AppDatabase) {

    private val scoreboardDao: ScoreboardDao

    var allScores: LiveData<List<Score>>

    init {
        scoreboardDao = database.scoreboardDao()
        allScores = scoreboardDao.getAll()
    }

    fun forceGet() {
        allScores = scoreboardDao.getAll()
    }

    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    @WorkerThread
    suspend fun insert(score: Score) = withContext(Dispatchers.IO) {
        scoreboardDao.insert(score)
    }
}