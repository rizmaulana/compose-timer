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
package com.example.androiddevchallenge.component.cell

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDownward
import androidx.compose.material.icons.rounded.ArrowUpward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.data.Lap
import com.example.androiddevchallenge.ui.theme.accent
import com.example.androiddevchallenge.ui.theme.foreground
import com.example.androiddevchallenge.ui.theme.secondary
import id.rizmaulana.composetimer.util.Formatter

@Composable
fun LapCell(index: Int, lap: Lap) {
    val color = when (lap.chart) {
        Lap.UP -> secondary
        Lap.DOWN -> accent
        else -> Color.White
    }
    Box(
        Modifier
            .fillMaxWidth()
            .padding(start = 38.dp, end = 38.dp)
    ) {
        Card(shape = RoundedCornerShape(16.dp), backgroundColor = foreground) {
            Box(
                modifier = Modifier.padding(all = 16.dp)
            ) {
                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text(text = index.inc().toString(), color = color)
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 32.dp, end = 16.dp)
                    ) {
                        Text(
                            text = Formatter.getTimerFormat(lap.time),
                            color = color,
                            style = typography.h5
                        )
                    }
                    when (lap.chart) {
                        Lap.UP -> {
                            Text(text = Formatter.getTimerFormat(lap.diff), color = color)
                            Icon(
                                imageVector = Icons.Rounded.ArrowUpward,
                                contentDescription = "Up",
                                tint = secondary,
                                modifier = Modifier
                                    .size(24.dp)
                            )
                        }
                        Lap.DOWN -> {
                            Text(text = Formatter.getTimerFormat(lap.diff), color = color)
                            Icon(
                                imageVector = Icons.Rounded.ArrowDownward,
                                contentDescription = "Down",
                                tint = accent,
                                modifier = Modifier
                                    .size(24.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
