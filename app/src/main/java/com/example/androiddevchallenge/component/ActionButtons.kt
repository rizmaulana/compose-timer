/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.ui.theme.accent
import com.example.androiddevchallenge.ui.theme.background
import com.example.androiddevchallenge.ui.theme.background50
import com.example.androiddevchallenge.ui.theme.primary
import com.example.androiddevchallenge.ui.theme.secondary

@ExperimentalAnimationApi
@Composable
fun ActionButtons(
    onStart: () -> Unit = {},
    onLap: () -> Unit = {},
    onStop: () -> Unit = {}
) {
    var startVisibility by remember { mutableStateOf(true) }
    var stopVisibility by remember { mutableStateOf(false) }
    var lapVisibility by remember { mutableStateOf(false) }
    val gradient = Brush.verticalGradient(listOf(background50, background, background))
    Box(
        modifier = Modifier
            .padding(bottom = 20.dp, top = 20.dp)
            .background(gradient)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedVisibility(
                visible = stopVisibility,
                enter = getEnterStopAnimation(),
                exit = getExitStopAnimation()
            ) {
                TimerButton(label = "STOP", color = accent) {
                    stopVisibility = false
                    lapVisibility = false
                    startVisibility = true
                    onStop.invoke()
                }
            }
            AnimatedVisibility(
                visible = lapVisibility,
                enter = getEnterLapAnimation(),
                exit = getExitLapAnimation()
            ) {
                TimerButton(label = "LAP", color = secondary, action = onLap)
            }
        }
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
            AnimatedVisibility(
                visible = startVisibility,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                TimerButton(label = "START", color = primary) {
                    stopVisibility = true
                    lapVisibility = true
                    startVisibility = false
                    onStart.invoke()
                }
            }
        }
    }
}

@ExperimentalAnimationApi
internal fun getEnterStopAnimation(): EnterTransition =
    slideInHorizontally(initialOffsetX = { 120 })

@ExperimentalAnimationApi
internal fun getExitStopAnimation(): ExitTransition =
    slideOutHorizontally(targetOffsetX = { 120 }) + fadeOut()

@ExperimentalAnimationApi
internal fun getEnterLapAnimation(): EnterTransition =
    slideInHorizontally(initialOffsetX = { -120 })

@ExperimentalAnimationApi
internal fun getExitLapAnimation(): ExitTransition =
    slideOutHorizontally(targetOffsetX = { -120 }) + fadeOut()

@Composable
internal fun TimerButton(
    label: String,
    color: Color,
    action: () -> Unit = {}
) {
    Box(modifier = Modifier.padding(start = 32.dp, end = 32.dp)) {
        Box(
            Modifier
                .background(color = color, shape = CircleShape)
                .clip(CircleShape)
                .width(80.dp)
                .aspectRatio(1f)
                .clickable(
                    onClick = action
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(text = label, style = typography.button, color = Color.White, fontSize = 14.sp)
        }
    }
}

@ExperimentalAnimationApi
@Preview
@Composable
fun ActionButtonsPreview() {
    ActionButtons()
}
