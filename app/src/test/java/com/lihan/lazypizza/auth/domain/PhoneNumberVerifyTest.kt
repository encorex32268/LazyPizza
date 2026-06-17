package com.lihan.lazypizza.auth.domain

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PhoneNumberVerifyTest {

    @Test
    fun `verify - valid phone numbers - return true`() {
        assertTrue(PhoneNumberVerify.verify("+886912345678"))
        assertTrue(PhoneNumberVerify.verify("+123456789"))
        assertTrue(PhoneNumberVerify.verify("+1234567"))
    }

    @Test
    fun `verify - too short or too long - return false`() {
        // Too short (less than 8 chars including +)
        assertFalse(PhoneNumberVerify.verify("+123456"))
        // Too long (more than 15 chars including +)
        assertFalse(PhoneNumberVerify.verify("+1234567890123456"))
    }

    @Test
    fun `verify - missing plus sign - return false`() {
        assertFalse(PhoneNumberVerify.verify("886912345678"))
    }

    @Test
    fun `verify - invalid characters - return false`() {
        assertFalse(PhoneNumberVerify.verify("+886-912-345"))
        assertFalse(PhoneNumberVerify.verify("+886 912 345"))
        assertFalse(PhoneNumberVerify.verify("+886912a345"))
    }

    @Test
    fun `verify - only plus sign - return false`() {
        assertFalse(PhoneNumberVerify.verify("+"))
    }

    @Test
    fun `verify - multiple plus signs - return false`() {
        assertFalse(PhoneNumberVerify.verify("++886912345"))
        assertFalse(PhoneNumberVerify.verify("+88+6912345"))
    }
}
