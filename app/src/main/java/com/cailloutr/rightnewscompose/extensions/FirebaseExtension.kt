package com.cailloutr.rightnewscompose.extensions

import com.google.android.gms.tasks.Task
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun <T> Task<T>.await(): T {
    if (isComplete) {
        val e = exception
        if (e != null) {
            throw e
        } else {
            return result!!
        }
    }

    return suspendCancellableCoroutine { continuation ->
        addOnCompleteListener { task ->
            val e = task.exception
            if (e != null) {
                continuation.resumeWithException(e)
            } else {
                continuation.resume(task.result!!) {}
            }
        }
    }
}