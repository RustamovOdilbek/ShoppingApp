package com.exsample.unittest

import com.exsample.unittest.utils.RegistrationUtil
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RegistrationTest {
    @Test
    fun `empty username returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "",
            "123",
            "123"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `valid username and correctly repeated password return true`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "Somthing8",
            "123",
            "123"
        )
        assertThat(result).isTrue()
    }

    @Test
    fun `username already exits return false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "Somthing",
            "123",
            "123"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `empty password return false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "Somthing",
            "",
            "123"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `confirmed password repeated incorrectly returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "Somthing",
            "123",
            "321"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `password contains less than 2 digits returns false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "Somthing",
            "1jdsu",
            "1jdsu"
        )
        assertThat(result).isFalse()
    }
}