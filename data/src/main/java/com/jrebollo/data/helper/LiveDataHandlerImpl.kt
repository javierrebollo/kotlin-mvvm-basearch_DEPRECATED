package com.jrebollo.data.helper

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.observe
import com.jrebollo.domain.helper.LiveDataHandler

class LiveDataHandlerImpl<T>(
    val liveData: LiveData<T>
) : LiveDataHandler<T>

fun <T> LiveDataHandler<T>.observe(
    viewLifecycleOwner: LifecycleOwner,
    block: (result: T) -> Unit
) =
    (this as LiveDataHandlerImpl).liveData.observe(viewLifecycleOwner) { result ->
        block(result)
    }
