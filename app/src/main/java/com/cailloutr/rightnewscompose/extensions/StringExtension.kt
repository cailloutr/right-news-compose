package com.cailloutr.rightnewscompose.extensions

fun String.toRouteId(): String {
    return this.replace("/", "_")
}

fun String.fromRouteId(): String {
    return this.replace("_", "/")
}