package com.amirhusseinsoori.shared_resource.ui.event

sealed class SynchronizeEvent {
    object AtomicEvent:SynchronizeEvent()
    object MutexEvent:SynchronizeEvent()
    data class SemaphoreEvent(var permit: Int):SynchronizeEvent()
}