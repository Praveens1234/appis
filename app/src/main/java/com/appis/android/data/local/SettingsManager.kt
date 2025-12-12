package com.appis.android.data.local

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        "secret_shared_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveNvidiaKey(key: String) {
        sharedPreferences.edit().putString("nvidia_key", key).apply()
    }

    fun getNvidiaKey(): String? = sharedPreferences.getString("nvidia_key", null)

    fun saveGithubToken(token: String) {
        sharedPreferences.edit().putString("github_token", token).apply()
    }

    fun getGithubToken(): String? = sharedPreferences.getString("github_token", null)

    fun saveGithubUsername(username: String) {
        sharedPreferences.edit().putString("github_username", username).apply()
    }

    fun getGithubUsername(): String? = sharedPreferences.getString("github_username", null)

    fun setSimulationMode(enabled: Boolean) {
        sharedPreferences.edit().putBoolean("simulation_mode", enabled).apply()
    }

    fun isSimulationMode(): Boolean = false // Forced Real Mode
}
