package com.amirhusseinsoori.shared_resource.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.sync.Semaphore


import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

@HiltViewModel
class SharedResourceViewModel @Inject constructor() : ViewModel() {

    private val mutableStateFlow = MutableStateFlow<Atomic>(Atomic())
    val stateFlow = mutableStateFlow.asStateFlow()

    init {
        atomic()
    }




    private fun atomic(data: AtomicInteger = AtomicInteger(0)) {
        viewModelScope.launch {
          async {
                for (i in 0..10) {
                    data.set(i)
                    delay(500)
                    if (i != 10) {
                        mutableStateFlow.value =
                            mutableStateFlow.value.copy(increment = data.toInt())
                    }


                }
            }
            async {
                for (i in 10 downTo 0) {
                    data.set(i)
                    delay(500)
                    if (i != 0) {
                        mutableStateFlow.value =
                            mutableStateFlow.value.copy(decrement = data.toInt())

                    }
                }
            }


        }

    }

    data class Atomic(var increment: Int = 0, val decrement: Int = 10)


}