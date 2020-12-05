package com.bistronic.memorymatchwevideo.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by paulbisioc on 12/4/2020.
 */
object SharedPreferencesUtils {
    private lateinit var preferences: SharedPreferences
    private const val MODE = Context.MODE_PRIVATE
    private const val NAME = "memory_match_we_video_shared_preferences"

    // KEYS
    private const val GRID_SIZE = "grid_size"
    private const val SCORE = "score"
    private const val GAME_TYPE = "game_type"

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var gridSize: Float
        get() = preferences.getFloat(GRID_SIZE, 4F)
        set(value) = preferences.edit { it.putFloat(GRID_SIZE, value) }

    var score: Int
        get() = preferences.getInt(SCORE, 0)
        set(value) = preferences.edit { it.putInt(SCORE, value) }

    var gameType: String
        get() = preferences.getString(GAME_TYPE, "").toString()
        set(value) = preferences.edit { it.putString(GAME_TYPE, value) }
}