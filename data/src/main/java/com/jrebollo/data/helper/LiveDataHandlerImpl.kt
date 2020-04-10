package com.jrebollo.data.helper

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.observe
import com.jrebollo.domain.helper.LiveDataHandler

class LiveDataHandlerImpl<T>(
    private val defaultLiveData: LiveData<T>? = null
) : LiveDataHandler<T> {

    private val _liveData: MutableLiveData<T> = MutableLiveData()
    val liveData: LiveData<T>
        get() {
            return defaultLiveData ?: _liveData
        }

    override fun postValue(value: T) {
        _liveData.postValue(value)
    }
}

fun <T> LiveDataHandler<T>.observe(
    viewLifecycleOwner: LifecycleOwner,
    block: (result: T) -> Unit
) =
    (this as LiveDataHandlerImpl).liveData.observe(viewLifecycleOwner) { result ->
        block(result)
    }
