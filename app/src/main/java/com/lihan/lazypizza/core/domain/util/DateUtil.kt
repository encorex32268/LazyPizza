package com.lihan.lazypizza.core.domain.util

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

object DateUtil {
    fun getWeekDaysList(): List<String> {
        return listOf(
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY,
            DayOfWeek.SATURDAY,
            DayOfWeek.SUNDAY
        ).map { day ->
            day.getDisplayName(TextStyle.SHORT, Locale.US).take(1)
        }
    }

    fun getMonthDays(
        visibleMonth: YearMonth,
        selectedDate: LocalDate?
    ): List<DateItem> {
        val today: LocalDate = LocalDate.now()
        val lengthOfMonth = visibleMonth.lengthOfMonth()
        val firstDayOfWeek = visibleMonth.atDay(1).dayOfWeek.value

        val emptyDateItemCount = firstDayOfWeek - 1
        val emptyDateItems = List(emptyDateItemCount) {
            DateItem(
                day = "",
                isSelected = false,
                enabled = false,
                isToday = false
            )
        }

        val dateItems = (1..lengthOfMonth).map { day ->
            val currentDate = visibleMonth.atDay(day)
            DateItem(
                day = day.toString(),
                isSelected = currentDate == selectedDate,
                enabled = currentDate >= today,
                isToday = currentDate == today
            )
        }

        return emptyDateItems + dateItems
    }
}

data class DateItem(
    val day: String,
    val isSelected: Boolean,
    val enabled: Boolean,
    val isToday: Boolean
)
