package com.example.wastelessapp.ui.notifications

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer

@SuppressLint("DiscouragedApi") //  getIdentifier is deprecated but cant find a better way
fun playSound(context: Context, soundName: String) {
    try {

        val soundId = context.resources.getIdentifier(soundName.lowercase(), "raw", context.packageName)

        if (soundId == 0) {
            println("Sound not found: $soundName")
            return
        }

        val mediaPlayer = MediaPlayer.create(context, soundId)
        mediaPlayer?.start()

        mediaPlayer?.setOnCompletionListener {
            it.release()
        }
        println("Playing sound: $soundName")
    } catch (e: Exception) {
        e.printStackTrace()
        println("Error playing sound: ${e.message}")
    }
}
