package com.jrebollo.data.controller

import com.jrebollo.domain.controller.UserController
import com.jrebollo.domain.helper.SharedPreferencesHelper

class UserControllerImpl(
    private val sharedPreferencesHelper: SharedPreferencesHelper
) : UserController {

    var token: String?
        get() = sharedPreferencesHelper.token
        set(value) {
            sharedPreferencesHelper.token = value
        }
}