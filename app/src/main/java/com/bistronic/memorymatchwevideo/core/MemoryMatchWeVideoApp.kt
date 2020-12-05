package com.bistronic.memorymatchwevideo.core

import android.app.Application
import com.bistronic.memorymatchwevideo.utils.SharedPreferencesUtils

/**
 * Created by paulbisioc on 12/4/2020.
 */
class MemoryMatchWeVideoApp: Application() {
    override fun onCreate() {
        super.onCreate()
        SharedPreferencesUtils.init(this)
    }
}