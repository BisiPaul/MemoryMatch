package com.bistronic.memorymatchwevideo.core.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bistronic.memorymatchwevideo.data.model.Score

/**
 * Created by paulbisioc on 12/4/2020.
 */
@Database(
    entities = [
        Score::class
    ],
    version = AppDatabase.DB_VERSION,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun scoreboardDao(): ScoreboardDao

    companion object {
        @Volatile
        private var databaseInstance: AppDatabase? = null
        private const val DATABASE_NAME = "app_database"
        private val LOCK = Any()
        const val DB_VERSION = 1

        fun getDatabase(context: Context): AppDatabase =
            databaseInstance
                ?: synchronized(LOCK) {
                    databaseInstance
                        ?: Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            DATABASE_NAME
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                }
    }
}