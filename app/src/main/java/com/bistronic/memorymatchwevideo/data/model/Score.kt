package com.bistronic.memorymatchwevideo.data.model

import androidx.room.Entity

/**
 * Created by paulbisioc on 12/4/2020.
 */
@Entity(tableName = "scores", primaryKeys = ["id"])
data class Score(
    var id: Int,
    var gameType: String,
    var name: String,
    var score: Int
)