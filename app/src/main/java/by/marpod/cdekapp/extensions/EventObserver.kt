package by.marpod.cdekapp.extensions

import androidx.lifecycle.Observer
import by.marpod.cdekapp.util.Event

class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        event?.getContentIfNotHandled()?.let(onEventUnhandledContent)
    }
}