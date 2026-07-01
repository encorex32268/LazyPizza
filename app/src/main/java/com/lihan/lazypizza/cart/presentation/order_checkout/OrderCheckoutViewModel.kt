package com.lihan.lazypizza.cart.presentation.order_checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.play.integrity.internal.ac
import com.lihan.lazypizza.R
import com.lihan.lazypizza.cart.presentation.order_checkout.util.PickUpTimeType
import com.lihan.lazypizza.core.domain.util.TimeUtil
import com.lihan.lazypizza.core.presentation.ui.util.UiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.time.format.TextStyle
import java.util.Locale

class OrderCheckoutViewModel : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(OrderCheckoutState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                setEarliestAvailableTime()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = OrderCheckoutState()
        )

    fun onAction(action: OrderCheckoutAction) {
        when (action) {
            is OrderCheckoutAction.OnDateSelected -> {
                _state.update { it.copy(
                    dateLocalDate = action.dateLocalDate,
                    isShowDatePicker = false,
                    isShowTimePicker = true
                ) }
            }
            OrderCheckoutAction.OnDatePickerCancelClick,
            OrderCheckoutAction.OnDismissDatePicker -> {
                _state.update { it.copy(
                    isShowDatePicker = false,
                    pickUpTimeType = PickUpTimeType.EarliestAvailableTime,
                ) }
            }
            OrderCheckoutAction.OnDismissTimePicker ->{
                _state.update { it.copy(
                    isShowTimePicker = false,
                    pickUpTimeType = PickUpTimeType.EarliestAvailableTime,
                )}
            }
            OrderCheckoutAction.OnExpandClick -> {
                _state.update { it.copy(
                    isExpandDetail = !it.isExpandDetail
                ) }
            }
            is OrderCheckoutAction.OnPickupTimeTypeSelect -> {
                if (action.type == PickUpTimeType.EarliestAvailableTime){
                    setEarliestAvailableTime()
                }else{
                    _state.update { it.copy(
                        pickUpTime = null
                    ) }
                }

                _state.update { it.copy(
                    pickUpTimeType = action.type,
                    isShowDatePicker = action.type == PickUpTimeType.ScheduleTime
                ) }
            }
            is OrderCheckoutAction.OnTimeSelected -> {
                val localDate = state.value.dateLocalDate

                val monthName = localDate.month.getDisplayName(TextStyle.FULL, Locale.US)
                val fullName = "$monthName ${localDate.dayOfMonth}"

                _state.update { it.copy(
                    pickUpTime = UiText.DynamicString("$fullName, ${action.time}"),
                    isShowTimePicker = false
                ) }
            }
            OrderCheckoutAction.OnTimePickerCancelClick ->{
                _state.update { it.copy(
                    isShowTimePicker = false,
                    isShowDatePicker = true
                ) }
            }
            OrderCheckoutAction.OnBackClick -> Unit
        }
    }

    private fun setEarliestAvailableTime(){
        val hour = TimeUtil.getNowHour()
        val minute = TimeUtil.getEarliestAvailableTime()
        val formattedMinute = String.format(locale = Locale.getDefault(), "%02d", minute.toIntOrNull() ?: 0)

        val timeInt = (hour + formattedMinute).toInt()

        val result = if (timeInt !in 1015..2145){
            UiText.StringResource(R.string.timepicker_error_message)
        }else{
            UiText.DynamicString("$hour:$formattedMinute")
        }

        _state.update { it.copy(
            pickUpTime = result
        ) }


    }

}