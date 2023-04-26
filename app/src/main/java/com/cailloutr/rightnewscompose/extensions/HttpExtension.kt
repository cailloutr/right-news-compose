package com.cailloutr.rightnewscompose.extensions

import android.util.Log
import com.cailloutr.rightnewscompose.data.remote.TAG
import com.cailloutr.rightnewscompose.other.Resource
import io.ktor.client.HttpClient
import io.ktor.client.features.ClientRequestException
import io.ktor.client.features.RedirectResponseException
import io.ktor.client.features.ServerResponseException

inline fun <reified T> HttpClient.getResponse(httpRequest: (HttpClient) -> T): Resource<T> {
    return try {
        Resource.success(
            data = httpRequest(this)
        )
    } catch (e: RedirectResponseException) {
        // 3xx - responses
        Log.e(TAG, e.response.status.description)
        Resource.error(msg = e.response.status.description, data = null)
    } catch (e: ClientRequestException) {
        // 4xx - responses
        Log.e(TAG, e.response.status.description)
        Resource.error(msg = e.response.status.description, data = null)
    } catch (e: ServerResponseException) {
        // 5xx - responses
        Log.e(TAG, e.response.status.description)
        Resource.error(msg = e.response.status.description, data = null)
    } catch (e: Exception) {
        // 5xx - responses
        Log.e(TAG, e.message.toString())
        Resource.error(msg = e.message.toString(), data = null)
    }
}