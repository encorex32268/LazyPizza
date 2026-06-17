package com.lihan.lazypizza.auth.presentation.util
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import com.google.i18n.phonenumbers.AsYouTypeFormatter
import com.google.i18n.phonenumbers.PhoneNumberUtil
import java.util.Locale

object PhoneNumberInputTransformation: InputTransformation {

    override fun TextFieldBuffer.transformInput() {
        val originalText = this.asCharSequence()

        // Extract only digits and the leading '+' sign
        val cleanBuilder = StringBuilder()
        originalText.forEachIndexed { index, char ->
            if (char.isDigit() || (index == 0 && char == '+')) {
                cleanBuilder.append(char)
            }
        }
        val cleanText = cleanBuilder.toString()

        val locale: Locale = Locale.getDefault()
        val countryCode = locale.country.ifBlank { "US" }.uppercase()

        if (cleanText.length <= 4 && cleanText.startsWith("+")) {
            this.replace(0, this.length, cleanText)
            return
        }

        // Initialize Google's real-time phone number formatter
        val phoneUtil = PhoneNumberUtil.getInstance()
        val formatter: AsYouTypeFormatter = phoneUtil.getAsYouTypeFormatter(countryCode)

        var formattedResult = ""

        // Feed characters one by one to dynamically compute the formatted string
        cleanText.forEach { char ->
            formattedResult = formatter.inputDigit(char)
        }

        // Update the TextFieldBuffer with the final formatted result
        this.replace(0, this.length, formattedResult)
    }
}