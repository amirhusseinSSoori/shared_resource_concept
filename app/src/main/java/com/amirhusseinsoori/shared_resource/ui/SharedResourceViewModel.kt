package com.amirhusseinsoori.shared_resource.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amirhusseinsoori.shared_resource.ui.event.SynchronizeEvent
import com.amirhusseinsoori.shared_resource.ui.state.AtomicState
import com.amirhusseinsoori.shared_resource.ui.state.MutexState
import com.amirhusseinsoori.shared_resource.ui.state.SemaphoreState
import com.amirhusseinsoori.shared_resource.ui.state.SharedResourceResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.internal.synchronized
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withLock


import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

@HiltViewModel
class SharedResourceViewModel @Inject constructor() : ViewModel() {
    var mutex: Mutex = Mutex(true)
    private val _atomicState = MutableStateFlow<AtomicState>(AtomicState())
    private val _mutexState = MutableStateFlow<MutexState>(MutexState())
    private val _semaphoreState = MutableStateFlow<SemaphoreState>(SemaphoreState())
    private val _resultState =
        MutableStateFlow<SharedResourceResultState>(SharedResourceResultState())
    val resultState = _resultState.asStateFlow()


    init {
        // eventSynchronize(SynchronizeEvent.AtomicEvent)
        eventSynchronize(SynchronizeEvent.SemaphoreEvent(3))
        //  eventSynchronize(SynchronizeEvent.AtomicEvent)


        viewModelScope.launch {

            combine(_atomicState, _mutexState, _semaphoreState) { atomic, mutex, semaphore ->
                SharedResourceResultState(mutex, semaphore, atomic)
            }.collect {

                _resultState.value = it
            }
        }
    }


    private fun eventSynchronize(event: SynchronizeEvent) {
        when (event) {
            is SynchronizeEvent.SemaphoreEvent -> {

                semaphore(event.permit)
            }
            is SynchronizeEvent.AtomicEvent -> {
                atomic()
            }
            is SynchronizeEvent.MutexEvent -> {
                viewModelScope.launch {
                    mutex()
                    delay(2000)
                    mutex.unlock()
                }


            }
        }

    }


    private fun atomic(data: AtomicInteger = AtomicInteger(0)) {
        viewModelScope.launch {
            launch {
                for (i in 0..10) {
                    data.set(i)
                    delay(500)
                    if (i != 10) {
                        _atomicState.value =
                            _atomicState.value.copy(increment = data.toInt())
                    }
                }
            }
            launch {
                for (i in 10 downTo 0) {
                    data.set(i)
                    delay(500)
                    if (i != 0) {
                        _atomicState.value =
                            _atomicState.value.copy(decrement = data.toInt())

                    }
                }
            }


        }
    }

    private fun mutex() {
        viewModelScope.launch {
            launch {
                _mutexState.value = _mutexState.value.copy(block1 = true)
            }
            launch {
                mutex.withLock {
                    _mutexState.value = _mutexState.value.copy(block2 = true)
                }
            }
            launch {
                _mutexState.value = _mutexState.value.copy(block3 = true)
            }
            launch {
                mutex.withLock {
                    _mutexState.value = _mutexState.value.copy(block4 = true)
                }
            }
        }
    }


    private fun semaphore(permit: Int) {
        val semaphore = Semaphore(permit)
        viewModelScope.launch {
            launch {
                semaphore.acquire()
                delay(2000)
                _semaphoreState.value = _semaphoreState.value.copy(permit1 = true)
                semaphore.release()

            }
            launch {
                semaphore.acquire()
                delay(2000)
                _semaphoreState.value = _semaphoreState.value.copy(permit2 = true)
                semaphore.release()
            }
            launch {
                semaphore.acquire()
                delay(2000)
                _semaphoreState.value = _semaphoreState.value.copy(permit3 = true)
                semaphore.release()

            }
            launch {
                semaphore.acquire()
                delay(2000)
                _semaphoreState.value = _semaphoreState.value.copy(permit4 = true)
                semaphore.release()

            }
        }


    }


}

