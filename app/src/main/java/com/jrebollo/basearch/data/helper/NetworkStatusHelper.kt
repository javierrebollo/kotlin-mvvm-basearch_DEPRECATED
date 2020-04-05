package com.jrebollo.basearch.data.helper

import android.content.Context
import android.net.ConnectivityManager

class NetworkStatusHelper(context: Context) {

    private val mContext = context

    val isOnline: Boolean
        get() {
            val cm = mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            return netInfo != null && netInfo.isConnected
        }
}