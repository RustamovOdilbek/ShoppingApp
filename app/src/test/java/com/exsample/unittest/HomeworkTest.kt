package com.exsample.unittest

import com.exsample.unittest.utils.Homework
import com.google.common.truth.Truth
import org.junit.Test

class HomeworkTest{

    @Test
    fun `fib(0)`() {
        val result = Homework.fib(
           0
        )
        Truth.assertThat(result).isEqualTo(0L)
    }

    @Test
    fun `fib(1)`() {
        val result = Homework.fib(
            1
        )
        Truth.assertThat(result).isEqualTo(1L)
    }

    @Test
    fun `fib(n) = fib(n - 1) + fib(n - 2)`() {
        val result = Homework.fib(
            3
        )
        Truth.assertThat(result).isEqualTo(2L)
    }


    @Test
    fun `checks if the braces are set correctly`() {
        val result = Homework.checkBraces(
            "(())"
        )
        Truth.assertThat(result).isTrue()
    }

    @Test
    fun `checks if the braces are not set correctly`() {
        val result = Homework.checkBraces(
            "(()"
        )
        Truth.assertThat(result).isFalse()
    }

}