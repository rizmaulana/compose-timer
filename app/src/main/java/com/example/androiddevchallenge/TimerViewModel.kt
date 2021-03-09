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
package com.example.androiddevchallenge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androiddevchallenge.data.Lap
import com.example.androiddevchallenge.util.Constant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class TimerViewModel : ViewModel() {
    private var job: Job? = null

    private val _times = MutableStateFlow(0L)
    val times = _times.asStateFlow()

    private val lapList = mutableListOf<Lap>()
    private val _lap = MutableStateFlow(mutableListOf<Lap>())
    val lap = _lap.asStateFlow()

    fun start(withDelay: Long = 350) {
        job?.cancel()
        _times.value = 0
        if (withDelay > 0) {
            lapList.clear()
            _lap.value = lapList
        }
        job = viewModelScope.launch(Dispatchers.IO) {
            delay(timeMillis = withDelay)
            while (isActive) {
                delay(timeMillis = Constant.MILISECOND_TICKER)
                _times.value += Constant.MILISECOND_TICKER
            }
        }
    }

    fun stop() {
        job?.cancel()
    }

    fun lap() {
        val diff: Long
        val chart: Int
        if (_lap.value.isEmpty()) {
            chart = Lap.STATE
            diff = 0
        } else {
            val valueBefore = _lap.value.last()
            when {
                valueBefore.time == _times.value -> {
                    chart = Lap.STATE
                    diff = 0
                }
                valueBefore.time > _times.value -> {
                    chart = Lap.UP
                    diff = valueBefore.time - _times.value
                }
                else -> {
                    chart = Lap.DOWN
                    diff = _times.value - valueBefore.time
                }
            }
        }
        val newLap = Lap(
            time = _times.value,
            chart = chart,
            diff = diff
        )
        lapList.add(newLap)
        _lap.value = lapList
        stop()
        start(withDelay = 0)
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}
