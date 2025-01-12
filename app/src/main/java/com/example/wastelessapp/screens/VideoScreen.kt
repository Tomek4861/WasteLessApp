package com.example.wastelessapp.screens

import android.content.Context
import android.net.Uri
import android.widget.VideoView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.wastelessapp.R
import com.example.wastelessapp.ui.components.SecondaryButton
import kotlinx.serialization.Serializable

@Serializable
object VideoScreen

@Composable
fun VideoScreen(
    navController: NavHostController
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        LocalVideoPlayer(
            context = LocalContext.current,
            videoResId = R.raw.video,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) { SecondaryButton(text = "Close", onClick = { navController.navigate(HomeScreen) }) }
    }
}

@Composable
fun LocalVideoPlayer(
    context: Context, videoResId: Int, modifier: Modifier = Modifier
) {
    AndroidView(
        factory = { ctx ->
            VideoView(ctx).apply {

                setZOrderOnTop(false)
                setZOrderMediaOverlay(false)
                val videoUri = Uri.parse("android.resource://${ctx.packageName}/$videoResId")
                setVideoURI(videoUri)
                setOnPreparedListener { it.isLooping = true }
                start()
            }
        }, modifier = modifier
    )
}


