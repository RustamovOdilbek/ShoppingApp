package com.exsample.unittest.utils

open class Event<out T>(private val content: T) {

    var hasBeenHandler = false

    private set

    fun getContentIfNotHamdler(): T?{
        return if (hasBeenHandler){
            null
        }else{
            hasBeenHandler = true
            content
        }
    }

    fun peekContent(): T = content

}