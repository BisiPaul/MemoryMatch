package com.bistronic.memorymatchwevideo.core

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bistronic.memorymatchwevideo.components.game.GameViewModel
import com.bistronic.memorymatchwevideo.components.scoreboard.ScoreboardViewModel
import com.bistronic.memorymatchwevideo.components.settings.SettingsViewModel
import com.bistronic.memorymatchwevideo.core.repositories.LoremPicsumRepository
import com.bistronic.memorymatchwevideo.core.repositories.ScoresRoomRepository
import com.bistronic.memorymatchwevideo.core.room.AppDatabase

/**
 * Created by paulbisioc on 11/28/2020.
 */

class ViewModelFactory(
    private val applicationContext: Context
) : ViewModelProvider.Factory {
    private val database by lazy { AppDatabase.getDatabase(applicationContext) }


    private val loremPicsumRepository by lazy { LoremPicsumRepository() }

    private val scoresRoomRepository by lazy {
        ScoresRoomRepository(database)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(GameViewModel::class.java) -> {
                GameViewModel(loremPicsumRepository) as T
            }
            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> {
                SettingsViewModel() as T
            }
            modelClass.isAssignableFrom(ScoreboardViewModel::class.java) -> {
                ScoreboardViewModel(scoresRoomRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
}