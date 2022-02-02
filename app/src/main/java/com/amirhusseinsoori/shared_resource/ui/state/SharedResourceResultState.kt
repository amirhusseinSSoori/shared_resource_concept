package com.amirhusseinsoori.shared_resource.ui.state

data class SharedResourceResultState(
    val mutex: MutexState = MutexState(),
    val semaphore: SemaphoreState = SemaphoreState(),
    val atomicState: AtomicState = AtomicState()
)