@file:OptIn(ExperimentalMaterial3Api::class)

package com.lihan.lazypizza.core.presentation.design_system

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lihan.lazypizza.R
import com.lihan.lazypizza.core.domain.util.DateUtil
import com.lihan.lazypizza.core.presentation.ChevronLeft
import com.lihan.lazypizza.core.presentation.ChevronRight
import com.lihan.lazypizza.core.presentation.components.AppIconBackgroundButton
import com.lihan.lazypizza.core.presentation.ui.theme.LazyPizzaTheme
import com.lihan.lazypizza.core.presentation.ui.theme.body3Medium
import com.lihan.lazypizza.core.presentation.ui.theme.label2SemiBold
import com.lihan.lazypizza.core.presentation.ui.theme.outline50
import com.lihan.lazypizza.core.presentation.ui.theme.primaryGradient
import com.lihan.lazypizza.core.presentation.ui.theme.surfaceHighest
import com.lihan.lazypizza.core.presentation.ui.theme.title1SemiBold
import java.time.LocalDate

private val DialogContainerWidth = 360.dp
private val DialogContainerMaxHeight = 568.dp

@Composable
fun AppDatePickerRoot(
    onDismissRequest: () -> Unit,
    onCancel: () -> Unit,
    onDateConfirm: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    state: AppDatePickerState = rememberAppDatePickerState()
){
    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = modifier
    ) {
        AppDatePicker(
            onDateConfirm = onDateConfirm,
            onCancel = onCancel,
            state = state
        )
    }
}


@Composable
fun AppDatePicker(
    onCancel: () -> Unit,
    onDateConfirm: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    state: AppDatePickerState
) {
    Surface(
        modifier = Modifier
            .requiredWidth(DialogContainerWidth)
            .heightIn(max = DialogContainerMaxHeight),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceHighest
    ) {
        Column {
            AppDatePickerContent(
                state = state,
                modifier = modifier
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.outline50)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 12.dp,
                        horizontal = 16.dp
                    )
                    .height(40.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AppButton(
                    text = stringResource(R.string.cancel),
                    onClick = onCancel,
                    type = ButtonType.Text
                )
                AppButton(
                    text = stringResource(R.string.ok),
                    onClick = {
                        state.selectedDate?.let { onDateConfirm(it) }
                    },
                    type = ButtonType.Filled,
                    enabled = state.selectedDate != null
                )
            }

        }
    }

}

@Composable
fun AppDatePickerContent(
    state: AppDatePickerState,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surfaceHighest
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.select_date),
                style = MaterialTheme.typography.label2SemiBold.copy(
                    color = MaterialTheme.colorScheme.secondary
                )
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = state.fullDayName.ifEmpty { stringResource(R.string.select_date) },
                style = MaterialTheme.typography.title1SemiBold.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
        }
        HorizontalDivider(color = MaterialTheme.colorScheme.outline50)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = state.fullMonthName.uppercase(),
                style = MaterialTheme.typography.label2SemiBold.copy(
                    color = MaterialTheme.colorScheme.secondary
                )
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AppIconBackgroundButton(
                    onClick = { state.moveToPreviousMonth() },
                    imageVector = ChevronLeft,
                    iconTintColor = MaterialTheme.colorScheme.secondary,
                    backgroundColor = Color.Transparent,
                    shape = RoundedCornerShape(8.dp),
                    borderColor = MaterialTheme.colorScheme.outline50,
                    size = 22.dp
                )
                AppIconBackgroundButton(
                    onClick = { state.moveToNextMonth() },
                    imageVector = ChevronRight,
                    iconTintColor = MaterialTheme.colorScheme.secondary,
                    backgroundColor = Color.Transparent,
                    shape = RoundedCornerShape(8.dp),
                    borderColor = MaterialTheme.colorScheme.outline50,
                    size = 22.dp
                )
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.surfaceHighest
                )
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            items(
                DateUtil.getWeekDaysList()
            ) { dayOfWeek ->
                Text(
                    modifier = Modifier.size(30.dp),
                    text = dayOfWeek,
                    style = MaterialTheme.typography.label2SemiBold.copy(
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Center
                    )
                )
            }

            items(
                DateUtil.getMonthDays(state.visibleMonth, state.selectedDate)
            ) { dateItem ->
                if (dateItem.day.isEmpty()) {
                    Spacer(modifier = Modifier.size(40.dp))
                } else {
                    val isSelected = dateItem.isSelected
                    val isToday = dateItem.isToday
                    val enabled = dateItem.enabled

                    val textColor = when {
                        isSelected -> MaterialTheme.colorScheme.onPrimary
                        isToday -> MaterialTheme.colorScheme.primary
                        !enabled -> MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
                        else -> MaterialTheme.colorScheme.onBackground
                    }

                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .clickable(enabled = enabled) {
                                state.selectedDate = state.visibleMonth.atDay(dateItem.day.toInt())
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        when {
                            isSelected -> {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .background(
                                            brush = MaterialTheme.colorScheme.primaryGradient,
                                            shape = CircleShape
                                        )
                                )
                            }

                            isToday -> {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .border(
                                            width = 1.dp,
                                            color = MaterialTheme.colorScheme.primary,
                                            shape = CircleShape
                                        )
                                )
                            }
                        }
                        Text(
                            text = dateItem.day,
                            style = MaterialTheme.typography.body3Medium.copy(
                                color = textColor,
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppDatePickerPreview() {
    LazyPizzaTheme {
        AppDatePickerContent(
            state = rememberAppDatePickerState(
                initialSelectedDate = LocalDate.now().plusDays(2)
            )
        )
    }
}