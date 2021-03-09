package com.example.androiddevchallenge

import android.view.Window
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import id.rizmaulana.composetimer.compose.component.ActionButtons
import com.example.androiddevchallenge.component.LapList
import com.example.androiddevchallenge.component.TimerGraphic
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.SystemUiController
import com.example.androiddevchallenge.ui.theme.background

/**
 * rizmaulana 07/03/21.
 */


@ExperimentalAnimationApi
@Composable
fun TimerApp(viewModel: TimerViewModel, window: Window) {
    val systemUiController = remember { SystemUiController(window) }
    MyTheme {
        systemUiController.setNavigationBarColor(color = background)
        systemUiController.setSystemBarsColor(color = background)
        val times by viewModel.times.collectAsState()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = background)
        ) {
            Column {
                Box(modifier = Modifier.weight(3.0f, true)) {
                    TimerGraphic(time = times)
                }
                Box(
                    modifier = Modifier
                        .weight(2.0f, true)
                        .padding(bottom = 64.dp)
                ) {
                    val laps by viewModel.lap.collectAsState()
                    LapList(laps = laps)
                }
            }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                ActionButtons(
                    onStart = { viewModel.start() },
                    onStop = { viewModel.stop() },
                    onLap = { viewModel.lap() }
                )
            }
        }
    }
}