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

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.ui.theme.accent
import com.example.androiddevchallenge.ui.theme.inactiveMark
import com.example.androiddevchallenge.ui.theme.primary
import com.example.androiddevchallenge.ui.theme.secondary
import id.rizmaulana.composetimer.util.Formatter
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun TimerGraphic(time: Long) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, bottom = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Chrono(seconds = ((time / 1000) % 60).toInt().inc(), primaryChrono = true)
        Chrono(seconds = (time % 60).toInt().inc(), primaryChrono = false)
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = Formatter.getTimerFormat(time),
                style = typography.subtitle1,
                fontSize = 54.sp,
                color = Color.White
            )
            Text(text = "Today's Running Time", style = typography.body1, color = Color.White)
        }
    }
}

@Composable
fun Chrono(
    seconds: Int,
    primaryChrono: Boolean,
    modifier: Modifier = Modifier
) {
    val nbMarker = 60
    val markerActives by animateFloatAsState(
        targetValue = nbMarker / 60f * seconds,
    )
    Box(
        modifier
            .fillMaxHeight()
            .aspectRatio(1f)
    ) {
        for (i in 0 until nbMarker) {
            Marker(
                angle = i * (360 / nbMarker),
                active = i < markerActives,
                majorMarker = i % 15 == 0,
                primaryChrono = primaryChrono
            )
        }
    }
}

/**
 * Credit to generate marker chrono from https://github.com/GerardPaligot/android-countdown
 */
@Composable
internal fun Marker(
    angle: Int,
    active: Boolean,
    majorMarker: Boolean,
    primaryChrono: Boolean,
    modifier: Modifier = Modifier
) {
    val color: Color
    val startFloatingPoint: Float
    val endFloatingPoint: Float
    val strokeWidth: Float
    if (primaryChrono) {
        startFloatingPoint = .72f
        if (majorMarker) {
            strokeWidth = 16f
            color = secondary
            endFloatingPoint = .90f
        } else {
            color = primary
            endFloatingPoint = .88f
            strokeWidth = 8f
        }
    } else {
        color = accent
        startFloatingPoint = .66f
        endFloatingPoint = .68f
        strokeWidth = 14f
    }

    Box(
        modifier
            .fillMaxSize()
            .drawBehind {
                val theta = (angle - 90) * PI.toFloat() / 180f
                val startRadius = size.width / 2 * startFloatingPoint
                val endRadius = size.width / 2 * endFloatingPoint
                val startPos = Offset(cos(theta) * startRadius, sin(theta) * startRadius)
                val endPos = Offset(cos(theta) * endRadius, sin(theta) * endRadius)
                drawLine(
                    color = if (active) color else inactiveMark,
                    start = center + startPos,
                    end = center + endPos,
                    strokeWidth = strokeWidth,
                    cap = StrokeCap.Round
                )
            }
    )
}

@Preview
@Composable
fun DigitalTimePreview() {
    TimerGraphic(160)
}
