package com.exsample.unittest.utils

object Homework {

    fun fib(n: Int): Long{
        if (n == 0 || n == 1){
            return n.toLong()
        }

        var a = 0L
        var b = 1L
        var c = 1L
        (1..n-1).forEach { i ->
            c = a + b
            a = b
            b = c
        }
        return c
    }

    fun checkBraces(string: String): Boolean{
        return string.count { it == '(' } == string.count { it == ')' }
    }

}