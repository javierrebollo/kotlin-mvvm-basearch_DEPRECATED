package com.jrebollo.basearch.data.network

import com.jrebollo.basearch.data.helper.NetworkStatusHelper
import com.jrebollo.basearch.exception.WithoutConnectivityException
import com.jrebollo.basearch.utils.Tracker
import okhttp3.*
import java.util.*

private val JSON = MediaType.parse("application/json; charset=utf-8")

class ServerClient(
    private val client: OkHttpClient,
    private val networkStatusHelper: NetworkStatusHelper
) {
    private val TAG: String = this::class.java.simpleName

    private val builder: Request.Builder
        get() = Request.Builder()//Add here your common heads

    fun <T> execute(
        baseServerRequest: BaseServerRequest<T>,
        extraHeaders: HashMap<String, String>? = null
    ): TaskResult<T> {

        return try {
            TaskResult.SuccessResult(executeWithExceptions(baseServerRequest, extraHeaders))
        } catch (exception: Exception) {
            TaskResult.ErrorResult(exception)
        }
    }

    private fun <T> executeWithExceptions(
        baseServerRequest: BaseServerRequest<T>,
        extraHeaders: HashMap<String, String>? = null
    ): T {

        if (networkStatusHelper.isOnline) {
            val requestBuilder = builder
            val url = baseServerRequest.httpUrl
            Tracker.logDebug(TAG, "Call to: ${url.uri()}")

            if (extraHeaders != null) {
                for ((key, value) in extraHeaders) {
                    requestBuilder.addHeader(key, value)
                }
            }

            requestBuilder.url(url).apply {
                if (baseServerRequest is RequestServerPost && baseServerRequest.type == BaseServerRequest.Type.POST) {
                    val stringBody = baseServerRequest.buildBody().toString()
                    Tracker.logDebug(TAG, stringBody)
                    post(RequestBody.create(JSON, stringBody))
                }
            }

            var response: Response? = null

            try {
                response = client.newCall(requestBuilder.build()).execute()
                response.body()!!.use { responseBody ->
                    val responseString = responseBody.source().readUtf8()
                    Tracker.logDebug(TAG, responseString)

                    return baseServerRequest.parse(responseString, Tracker)
                }

            } catch (e: Exception) {
                Tracker.logWarning(
                    Tracker.SERVER_REQUEST_ERROR, "Request onFailure " + e.localizedMessage, e, TAG
                )
                throw e
            }

        } else {
            throw WithoutConnectivityException("Without connection")
        }
    }
}
