package com.jrebollo.data.network

import com.jrebollo.data.helper.NetworkStatusHelper
import com.jrebollo.domain.Tracker
import com.jrebollo.domain.exception.WithoutConnectivityException
import com.jrebollo.domain.response.ErrorResult
import com.jrebollo.domain.response.SuccessResult
import com.jrebollo.domain.response.TaskResult
import okhttp3.*
import java.util.*

private val JSON = MediaType.parse("application/json; charset=utf-8")

class ServerClient(
    private val client: OkHttpClient,
    private val networkStatusHelper: NetworkStatusHelper,
    private val tracker: Tracker
) {
    private val TAG: String = this::class.java.simpleName

    private val builder: Request.Builder
        get() = Request.Builder()//Add here your common heads

    fun <T> execute(
        baseServerRequest: BaseServerRequest<T>,
        extraHeaders: HashMap<String, String>? = null
    ): TaskResult<T> {

        return try {
            SuccessResult(executeWithExceptions(baseServerRequest, extraHeaders))
        } catch (exception: Exception) {
            ErrorResult(exception)
        }
    }

    private fun <T> executeWithExceptions(
        baseServerRequest: BaseServerRequest<T>,
        extraHeaders: HashMap<String, String>? = null
    ): T {

        if (networkStatusHelper.isOnline) {
            val requestBuilder = builder
            val url = baseServerRequest.httpUrl
            tracker.logDebug(TAG, "Call to: ${url.uri()}")

            if (extraHeaders != null) {
                for ((key, value) in extraHeaders) {
                    requestBuilder.addHeader(key, value)
                }
            }

            requestBuilder.url(url).apply {
                if (baseServerRequest is RequestServerPost && baseServerRequest.type == BaseServerRequest.Type.POST) {
                    val stringBody = baseServerRequest.buildBody().toString()
                    tracker.logDebug(TAG, stringBody)
                    post(RequestBody.create(JSON, stringBody))
                }
            }

            var response: Response? = null

            try {
                response = client.newCall(requestBuilder.build()).execute()
                response.body()!!.use { responseBody ->
                    val responseString = responseBody.source().readUtf8()
                    tracker.logDebug(TAG, responseString)

                    return baseServerRequest.parse(responseString, tracker)
                }

            } catch (e: Exception) {
                tracker.logWarning(
                    Tracker.SERVER_REQUEST_ERROR, "Request onFailure " + e.localizedMessage, e, TAG
                )
                throw e
            }

        } else {
            throw WithoutConnectivityException("Without connection")
        }
    }
}
