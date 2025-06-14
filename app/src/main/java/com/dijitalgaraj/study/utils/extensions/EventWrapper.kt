package com.dijitalgaraj.study.utils.extensions

open class EventWrapper<out T>(private val content: T) {

    var hasBeenHandled = false

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }


    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}