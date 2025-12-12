package com.appis.android.domain.services

import android.content.Context
import android.os.PowerManager
import com.appis.android.utils.LogUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WakeLockManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private var wakeLock: PowerManager.WakeLock? = null

    fun acquire() {
        if (wakeLock == null) {
            val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
            wakeLock = powerManager.newWakeLock(
                PowerManager.PARTIAL_WAKE_LOCK,
                "Appis::PipelineWakeLock"
            )
        }
        if (wakeLock?.isHeld == false) {
            wakeLock?.acquire(10 * 60 * 1000L /*10 minutes*/)
            LogUtils.d("WakeLock acquired")
        }
    }

    fun release() {
        if (wakeLock?.isHeld == true) {
            wakeLock?.release()
            LogUtils.d("WakeLock released")
        }
    }
}
