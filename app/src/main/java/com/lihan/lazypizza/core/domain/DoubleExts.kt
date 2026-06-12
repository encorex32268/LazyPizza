package com.lihan.lazypizza.core.domain

import java.text.DecimalFormat
import java.util.Locale.getDefault

fun Double.formatToTwoDecimals(): String {
    return String.format(getDefault(),"%.2f", this)
}

// if 1.0 -> 1
fun Double.formatToTwoDecimals2(): String {
    return if (this % 1.0 == 0.0) {
        this.toLong().toString() // "1"
    } else {
        DecimalFormat("0.00").format(this) // "0.50"
    }
}