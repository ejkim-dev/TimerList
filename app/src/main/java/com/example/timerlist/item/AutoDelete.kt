package com.example.timerlist.item

data class AutoDelete(
    val id: Int,
    val name: String,
    val time: Int = 3,
    val isRunning: Boolean
)
