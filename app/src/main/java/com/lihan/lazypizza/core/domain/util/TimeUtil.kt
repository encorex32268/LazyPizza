package com.lihan.lazypizza.core.domain.util

import java.time.LocalTime

object TimeUtil {
    fun getNowHour(): String = LocalTime.now().hour.toString()

    fun getNowMinute(): String = LocalTime.now().minute.toString()
}