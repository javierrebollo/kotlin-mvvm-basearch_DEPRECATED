package com.jrebollo.basearch

import android.app.Application
import com.jrebollo.domain.Tracker

class MyApplication : Application() {
    companion object {
        lateinit var instance: MyApplication
            private set
        lateinit var tracker: Tracker
            private set
    }

    override fun onCreate() {
        super.onCreate()

        instance = this
        tracker = TrackerImpl()
    }
}