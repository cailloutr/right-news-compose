package com.cailloutr.rightnewscompose.other

import com.cailloutr.rightnewscompose.R

data class Resource<out T>(
    val status: Status,
    val data: T?,
    val message: String?,
) {

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}

enum class Status {
    SUCCESS, ERROR, LOADING
}

fun Status.getNetworkMessage() = when (this) {
    Status.ERROR -> {
        R.string.network_connection_error
    }

    Status.SUCCESS -> {
        R.string.up_to_date
    }

    else -> {
        null
    }
}
