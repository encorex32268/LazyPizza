package com.lihan.lazypizza

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.lihan.lazypizza.core.presentation.design_system.AppDatePickerRoot
import com.lihan.lazypizza.core.presentation.design_system.AppTimePickerRoot

@Composable
fun TestScreen(
    modifier: Modifier = Modifier
) {

    var isShowDatePicker by retain { mutableStateOf(false) }
    var isShowTimePicker by retain { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Button(
                onClick = {
                    isShowDatePicker = true
                }
            ) {
                Text(
                    text = "DatePicker"
                )
            }
        }

        if (isShowDatePicker){
            AppDatePickerRoot(
                onDismissRequest = {
                    isShowDatePicker = false
                    isShowTimePicker = true
                },
                onDateConfirm = {
                    println("DatePicked!: $it")
                    isShowDatePicker = false
                    isShowTimePicker = true
                },
                onCancel = {

                }
            )
        }

        if (isShowTimePicker){
            AppTimePickerRoot(
                onDismissRequest = {
                    isShowTimePicker = false
                },
                onTimeConfirm = {
                    println("TimePicked!: $it")
                    isShowTimePicker = false
                },
                onCancel = {

                }
            )
        }
    }


}


@Preview(showBackground = true)
@Composable
private fun TestScreenPreview() {

}