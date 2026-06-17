package com.lihan.lazypizza.auth.domain

import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import java.util.Locale

object PhoneNumberVerify {

    fun verify(phoneNumber: String): Boolean {
        val locale: Locale = Locale.getDefault()
        val countryCode = locale.country.ifBlank { "US" }
        println("PhoneNumberVerify Locale: $locale")
        println("PhoneNumberVerify Locale: $countryCode")
        println("PhoneNumberVerify ${phoneNumber}")
        val phoneUtil = PhoneNumberUtil.getInstance()
        return try {
            val cleanNumber = phoneNumber.filter { it.isDigit() || it == '+' }
            println("PhoneNumberVerify cleanNumber ${cleanNumber}")
            val numberProto = phoneUtil.parse(cleanNumber, countryCode)

            phoneUtil.isValidNumber(numberProto)
        } catch (e: NumberParseException) {
            e.printStackTrace()
            false
        }
    }
}
