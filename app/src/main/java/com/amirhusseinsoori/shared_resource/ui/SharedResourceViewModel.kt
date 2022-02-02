package com.amirhusseinsoori.shared_resource.ui

import androidx.compose.animation.defaultDecayAnimationSpec
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amirhusseinsoori.shared_resource.ui.event.SynchronizeEvent
import com.amirhusseinsoori.shared_resource.ui.state.AtomicState
import com.amirhusseinsoori.shared_resource.ui.state.MutexState
import com.amirhusseinsoori.shared_resource.ui.state.SemaphoreState
import com.amirhusseinsoori.shared_resource.ui.state.SharedResourceResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withLock


import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

@HiltViewModel
class SharedResourceViewModel @Inject constructor() : ViewModel() {
    var mutex: Mutex = Mutex(true)
    private val _stateAtomic = MutableStateFlow<AtomicState>(AtomicState())
    private val _stateMutex = MutableStateFlow<MutexState>(MutexState())
    private val _stateSemaphore = MutableStateFlow<SemaphoreState>(SemaphoreState())
    private val state = MutableStateFlow<SharedResourceResultState>(SharedResourceResultState())
    val _state=state.asStateFlow()


    init {
//        eventSynchronize(SynchronizeEvent.AtomicEvent)
        //     eventSynchronize(SynchronizeEvent.SemaphoreEvent(3))
        eventSynchronize(SynchronizeEvent.MutexEvent)

        viewModelScope.launch {
            combine(_stateAtomic, _stateMutex, _stateSemaphore) { atomic, mutex, semaphore ->
                SharedResourceResultState(mutex, semaphore, atomic)
            }.collect {
                state.value=it
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
                        _stateAtomic.value =
                            _stateAtomic.value.copy(increment = data.toInt())
                    }
                }
            }
            launch {
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

    private fun mutex() {
        viewModelScope.launch {
            launch {
                _stateMutex.value = _stateMutex.value.copy(block1 = true)
            }
            launch {
                mutex.withLock {
                    _stateMutex.value = _stateMutex.value.copy(block2 = true)
                }
            }
            launch {
                _stateMutex.value = _stateMutex.value.copy(block3 = true)
            }
            launch {
                mutex.withLock {
                    _stateMutex.value = _stateMutex.value.copy(block4 = true)
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
                _stateSemaphore.value = _stateSemaphore.value.copy(permit1 = true)
                semaphore.release()

            }
            launch {
                semaphore.acquire()
                delay(2000)
                _stateSemaphore.value = _stateSemaphore.value.copy(permit2 = true)
                semaphore.release()
            }
            launch {
                semaphore.acquire()
                delay(2000)
                _stateSemaphore.value = _stateSemaphore.value.copy(permit3 = true)
                semaphore.release()

            }
            launch {
                semaphore.acquire()
                delay(2000)
                _stateSemaphore.value = _stateSemaphore.value.copy(permit4 = true)
                semaphore.release()

            }
        }


    }


}

