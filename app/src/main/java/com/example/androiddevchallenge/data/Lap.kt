package com.example.androiddevchallenge.data

data class Lap (
    val time: Long,
    val diff: Long,
    val chart: Int

){
    companion object{
        const val STATE = 0
        const val UP = 1
        const val DOWN = 2
    }
}

