package com.lihan.lazypizza.core.domain.util

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

object TimerFlow  {
    fun timeAndEmit(): Flow<Duration> = flow{
        var currentTime = System.currentTimeMillis()
        while (true){
            delay(500L)
            val lastTime = System.currentTimeMillis()
            val elapsedTime = lastTime - currentTime
            emit(elapsedTime.milliseconds)
            currentTime = lastTime
        }
    }
}
