package com.amirhusseinsoori.shared_resource.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amirhusseinsoori.shared_resource.ui.event.SynchronizeEvent
import com.amirhusseinsoori.shared_resource.ui.state.Atomic
import com.amirhusseinsoori.shared_resource.ui.state.SemaphoreState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Semaphore


import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

@HiltViewModel
class SharedResourceViewModel @Inject constructor() : ViewModel() {

    private val _stateAtomic = MutableStateFlow<Atomic>(Atomic())
    val stateAtomic = _stateAtomic.asStateFlow()
    private val _stateSemaphore = MutableStateFlow<SemaphoreState>(SemaphoreState())
    val stateSemaphore = _stateSemaphore.asStateFlow()

    init {
        eventSynchronize(SynchronizeEvent.AtomicEvent)
        eventSynchronize(SynchronizeEvent.SemaphoreEvent(4))

    }


    private fun eventSynchronize(event: SynchronizeEvent) {
        when(event){
            is SynchronizeEvent.SemaphoreEvent ->{
                semaphore(event.permit)
            }
            is SynchronizeEvent.AtomicEvent ->{
                atomic()
            }
        }

    }


    private fun atomic(data: AtomicInteger = AtomicInteger(0)) {
        viewModelScope.launch {
            async {
                for (i in 0..10) {
                    data.set(i)
                    delay(500)
                    if (i != 10) {
                        _stateAtomic.value =
                            _stateAtomic.value.copy(increment = data.toInt())
                    }
                }
            }
            async {
                for (i in 10 downTo 0) {
                    data.set(i)
                    delay(500)
                    if (i != 0) {
                        _stateAtomic.value =
                            _stateAtomic.value.copy(decrement = data.toInt())

                    }
                }
            }


        }

    }


    private fun semaphore(permit: Int) {
        val semaphore = Semaphore(permit)
        viewModelScope.launch {
            async {
                semaphore.acquire()
                delay(2000)
                _stateSemaphore.value = _stateSemaphore.value.copy(permit1 = true)
                semaphore.release()

            }

            async {
                semaphore.acquire()
                delay(2000)
                _stateSemaphore.value = _stateSemaphore.value.copy(permit2 = true)
                semaphore.release()
            }
            async {
                semaphore.acquire()
                delay(2000)
                _stateSemaphore.value = _stateSemaphore.value.copy(permit3 = true)
                semaphore.release()

            }
            async {
                semaphore.acquire()
                delay(2000)
                _stateSemaphore.value = _stateSemaphore.value.copy(permit4 = true)
                semaphore.release()

            }
        }


    }


}