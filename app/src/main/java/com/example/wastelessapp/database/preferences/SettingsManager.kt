package com.example.wastelessapp.database.preferences

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.preferenceDataStore by preferencesDataStore(name = "settings")

class SettingsManager(private val context: Context) {

    companion object {
        val NOTIFICATION_SETTING_3d = booleanPreferencesKey("notification_setting_3d")
        val NOTIFICATION_SETTING_2d = booleanPreferencesKey("notification_setting_2d")
        val NOTIFICATION_SETTING_1d = booleanPreferencesKey("notification_setting_1d")
        val NOTIFICATION_SETTING_currD = booleanPreferencesKey("notification_setting_currD")
        val NOTIFICATION_SOUND = stringPreferencesKey("notification_sound")
    }

    suspend fun setNotificationSetting(key: Preferences.Key<Boolean>, value: Boolean) {
        context.preferenceDataStore.edit { preferences ->
            preferences[key] = value
            println("Saved ${key.name} = $value")
        }
    }

    fun getNotificationSetting(key: Preferences.Key<Boolean>): Flow<Boolean> {
        return context.preferenceDataStore.data.map { preferences ->
            val value = preferences[key] ?: false
            println("Loaded ${key.name} = $value")
            value
        }
    }

    suspend fun setNotificationSound(sound: String) {
        context.preferenceDataStore.edit { preferences ->
            preferences[NOTIFICATION_SOUND] = sound
            println("Saved notification_sound = $sound")
        }
    }

    val notificationSound: Flow<String> = context.preferenceDataStore.data.map { preferences ->
        val value = preferences[NOTIFICATION_SOUND] ?: "Meow"
        println("Loaded sound = $value")
        value
    }

    suspend fun getNotificationSound(): String {
        return notificationSound.first()
    }

}