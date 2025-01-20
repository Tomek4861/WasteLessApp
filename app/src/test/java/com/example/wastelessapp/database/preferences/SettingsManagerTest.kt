package com.example.wastelessapp.database.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SettingsManagerTest {

    private lateinit var settingsManager: SettingsManager
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        settingsManager = SettingsManager(context)
    }

    @Test
    fun `setNotificationSetting saves and retrieves value correctly`() = runTest {
        val key = SettingsManager.NOTIFICATION_SETTING_3d

        settingsManager.setNotificationSetting(key, true)
        val result = settingsManager.getNotificationSetting(key).first()

        assertTrue(result)
    }

    @Test
    fun `setNotificationSound saves and retrieves value correctly`() = runTest {
        val testSound = "Bell"

        settingsManager.setNotificationSound(testSound)
        val result = settingsManager.getNotificationSound()

        assertEquals(testSound, result)
    }

    @Test
    fun `getNotificationSetting returns default false if not set`() = runTest {
        val key = booleanPreferencesKey("non_existing_key")

        val result = settingsManager.getNotificationSetting(key).first()

        assertEquals(false, result)
    }

    @Test
    fun `getNotificationSound returns default sound if not set`() = runTest {
        val result = settingsManager.getNotificationSound()

        assertEquals("Meow", result)
    }
}
