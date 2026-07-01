package com.lihan.lazypizza.core.domain.util

import java.time.LocalTime

object TimeUtil {
    fun getNowHour(): String = LocalTime.now().hour.toString()

    fun getNowMinute(): String = LocalTime.now().minute.toString()

    //plus 15 minutes
    fun getEarliestAvailableTime(): String = LocalTime.now().plusMinutes(15).minute.toString()
}