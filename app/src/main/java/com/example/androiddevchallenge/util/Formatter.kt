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
package id.rizmaulana.composetimer.util

object Formatter {

    fun getTimerFormat(timeInMilies: Long): String {
        val timeInSec = timeInMilies.div(1000)
        val minutes = (timeInSec % 3600) / 60
        val second = timeInSec % 60
        val milSec = timeInMilies % 60
        return String.format("%02d:%02d:%02d", minutes, second, milSec)
    }
}
