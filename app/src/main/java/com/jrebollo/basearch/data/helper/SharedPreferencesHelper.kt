package com.jrebollo.basearch.data.helper

import android.content.Context
import android.content.SharedPreferences

private const val OWN_USER_DATA = "own_user_data"
private const val OWN_USER_TOKEN = "own_user_token"

class SharedPreferencesHelper(private val mContext: Context) {

    private fun getPreferences(preferencesName: String): SharedPreferences {
        return mContext.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
    }

    private fun getPreferencesEditor(preferencesName: String): SharedPreferences.Editor {
        return getPreferences(preferencesName).edit()
    }

    var token: String?
        get() = getPreferences(OWN_USER_DATA).getString(OWN_USER_TOKEN, null)
        set(value) {
            getPreferencesEditor(OWN_USER_DATA).putString(OWN_USER_TOKEN, value).apply()
        }
}