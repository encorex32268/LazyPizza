package com.lihan.lazypizza.cart.presentation.order_checkout

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.play.integrity.internal.ac
import com.lihan.lazypizza.R
import com.lihan.lazypizza.cart.presentation.mapper.toDomainOrderHistory
import com.lihan.lazypizza.cart.presentation.model.CartItemWithToppingsUi
import com.lihan.lazypizza.cart.presentation.order_checkout.util.PickUpTimeType
import com.lihan.lazypizza.core.domain.CartRepository
import com.lihan.lazypizza.core.domain.OrderRepository
import com.lihan.lazypizza.core.domain.UserDataStore
import com.lihan.lazypizza.core.domain.util.TimeUtil
import com.lihan.lazypizza.core.domain.util.onFailure
import com.lihan.lazypizza.core.domain.util.onSuccess
import com.lihan.lazypizza.core.presentation.ui.util.UiText
import com.lihan.lazypizza.core.presentation.ui.util.UiText.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.format.TextStyle
import java.util.Locale

class OrderCheckoutViewModel(
    private val orderRepository: OrderRepository,
    private val cartRepository: CartRepository,
    private val userDataStore: UserDataStore,
) : ViewModel() {

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
                _state.update {
                    it.copy(
                        dateLocalDate = action.dateLocalDate,
                        isShowDatePicker = false,
                        isShowTimePicker = true
                    )
                }
            }

            OrderCheckoutAction.OnDatePickerCancelClick,
            OrderCheckoutAction.OnDismissDatePicker -> {
                _state.update {
                    it.copy(
                        isShowDatePicker = false,
                        pickUpTimeType = PickUpTimeType.EarliestAvailableTime,
                    )
                }
            }

            OrderCheckoutAction.OnDismissTimePicker -> {
                _state.update {
                    it.copy(
                        isShowTimePicker = false,
                        pickUpTimeType = PickUpTimeType.EarliestAvailableTime,
                    )
                }
            }

            OrderCheckoutAction.OnExpandClick -> {
                _state.update {
                    it.copy(
                        isExpandDetail = !it.isExpandDetail
                    )
                }
            }

            is OrderCheckoutAction.OnPickupTimeTypeSelect -> {
                if (action.type == PickUpTimeType.EarliestAvailableTime) {
                    setEarliestAvailableTime()
                } else {
                    _state.update {
                        it.copy(
                            pickUpTime = null
                        )
                    }
                }

                _state.update {
                    it.copy(
                        pickUpTimeType = action.type,
                        isShowDatePicker = action.type == PickUpTimeType.ScheduleTime
                    )
                }
            }

            is OrderCheckoutAction.OnTimeSelected -> {
                val localDate = state.value.dateLocalDate
                val monthName = localDate.month.getDisplayName(TextStyle.FULL, Locale.US)
                val fullName = "$monthName ${localDate.dayOfMonth}"

                _state.update {
                    it.copy(
                        pickUpTime = DynamicString("$fullName, ${action.time}"),
                        isShowTimePicker = false
                    )
                }
            }

            OrderCheckoutAction.OnTimePickerCancelClick -> {
                _state.update {
                    it.copy(
                        isShowTimePicker = false,
                        isShowDatePicker = true
                    )
                }
            }

            is OrderCheckoutAction.OnPlaceOrderClick -> placeOrder(action.cartItems, action.pickupTime)
            OrderCheckoutAction.OnBackClick -> Unit
            OrderCheckoutAction.OnNavigateToMenu -> Unit
        }
    }

    private fun placeOrder(cartItems: List<CartItemWithToppingsUi>,pickupTime: String?) {
        viewModelScope.launch {
            val currentState = state.value
            val history = cartItems.toDomainOrderHistory()

            orderRepository.create(
                orderHistory = history
            ).first()
                .onSuccess {


                    val placedPickUpTime = if (currentState.pickUpTimeType == PickUpTimeType.EarliestAvailableTime){
                        val localDate = state.value.dateLocalDate
                        val monthName = localDate.month.getDisplayName(TextStyle.FULL, Locale.US)
                        UiText.DynamicString(
                            "$monthName ${localDate.dayOfMonth}, ${pickupTime?:""}"
                        )

                    }else{
                        currentState.pickUpTime
                    }

                    _state.update { it.copy(
                        orderNumber = "#${history.orderNumber}",
                        isSentOrder = true,
                        placedPickUpTime =placedPickUpTime
                    ) }
                    cartRepository.cleanCart(
                        cartItems.first().cartItem.id.toString()
                    )
                    userDataStore.setOrderId(
                        userDataStore.getOrderId().first() + 1
                    )
                }
                .onFailure {
                    println("Error ${it}")
                }


        }
    }

    private fun setEarliestAvailableTime() {
        val hour = TimeUtil.getNowHour()
        val minute = TimeUtil.getEarliestAvailableTime()
        val formattedMinute =
            String.format(locale = Locale.getDefault(), "%02d", minute.toIntOrNull() ?: 0)

        val timeInt = (hour + formattedMinute).toInt()

        val result = if (timeInt !in 1015..2145) {
            UiText.StringResource(R.string.timepicker_error_message)
        } else {
            UiText.DynamicString("$hour:$formattedMinute")
        }

        _state.update {
            it.copy(
                pickUpTime = result
            )
        }


    }

}