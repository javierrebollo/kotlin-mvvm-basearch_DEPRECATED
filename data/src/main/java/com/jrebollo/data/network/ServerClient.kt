package com.jrebollo.data.network

import com.jrebollo.data.helper.NetworkStatusHelper
import com.jrebollo.domain.Tracker
import com.jrebollo.domain.exception.WithoutConnectivityException
import com.jrebollo.domain.response.ErrorResult
import com.jrebollo.domain.response.SuccessResult
import com.jrebollo.domain.response.TaskResult
import okhttp3.*
import java.util.*

private const val TAG: String = "ServerClient"
private val JSON = MediaType.parse("application/json; charset=utf-8")

class ServerClient(
    client: OkHttpClient,
    networkStatusHelper: NetworkStatusHelper,
    tracker: Tracker
) {

    private val mClient: OkHttpClient = client
    private val mNetworkStatusHelper: NetworkStatusHelper = networkStatusHelper
    private val mTracker: Tracker = tracker

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

        if (mNetworkStatusHelper.isOnline) {
            val requestBuilder = builder
            val url = baseServerRequest.httpUrl
            mTracker.logDebug(TAG, "Call to: ${url.uri()}")

            if (extraHeaders != null) {
                for ((key, value) in extraHeaders) {
                    requestBuilder.addHeader(key, value)
                }
            }

            requestBuilder.url(url).apply {
                if (baseServerRequest is RequestServerPost && baseServerRequest.type == BaseServerRequest.Type.POST) {
                    post(RequestBody.create(JSON, baseServerRequest.buildBody().toString()))
                }
            }

            var response: Response? = null

            try {
                response = mClient.newCall(requestBuilder.build()).execute()
                response.body()!!.use { responseBody ->
                    val responseString = responseBody.source().readUtf8()
                    mTracker.logDebug(TAG, responseString)

                    return baseServerRequest.parse(responseString, mTracker)
                }

            } catch (e: Exception) {
                mTracker.logWarning(
                    Tracker.SERVER_REQUEST_ERROR, "Request onFailure " + e.localizedMessage, e, TAG
                )
                throw e
            }

        } else {
            throw WithoutConnectivityException("Without connection")
        }
    }
}
