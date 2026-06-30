package com.lihan.lazypizza.core.presentation.design_system

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Stable
class AppDatePickerState(
    initialSelectedDate: LocalDate? = null,
    initialVisibleMonth: YearMonth = YearMonth.now()
) {
    var selectedDate by mutableStateOf(initialSelectedDate)
    var visibleMonth by mutableStateOf(initialVisibleMonth)

    val fullDayName: String
        get() = selectedDate?.let {
            val monthName = it.month.getDisplayName(TextStyle.FULL, Locale.US)
            "$monthName ${it.dayOfMonth}"
        } ?: ""

    val fullMonthName: String
        get() {
            val monthName = visibleMonth.month.getDisplayName(TextStyle.FULL, Locale.US)
            return "$monthName ${visibleMonth.year}"
        }

    fun moveToPreviousMonth() {
        visibleMonth = visibleMonth.minusMonths(1)
    }

    fun moveToNextMonth() {
        visibleMonth = visibleMonth.plusMonths(1)
    }
}

@Composable
fun rememberAppDatePickerState(
    initialSelectedDate: LocalDate? = null,
    initialVisibleMonth: YearMonth = YearMonth.now()
): AppDatePickerState {
    return rememberSaveable(
        saver = Saver(
            save = { listOf(it.selectedDate?.toString(), it.visibleMonth.toString()) },
            restore = { value ->
                val list = value as List<String?>
                AppDatePickerState(
                    initialSelectedDate = list[0]?.let { LocalDate.parse(it) },
                    initialVisibleMonth = list[1]?.let { YearMonth.parse(it) } ?: YearMonth.now()
                )
            }
        )
    ) {
        AppDatePickerState(initialSelectedDate, initialVisibleMonth)
    }
}
