package vlad.makarenko.notes.base

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class Reducer<S : UiState, in E : UiEvent>(initialState: S) {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S>
        get() = _state

    fun sendEvent(event: E) {
        reduce(oldState = _state.value, event = event)
    }

    protected fun setState(newState: S) {
        _state.tryEmit(newState)
    }

    abstract fun reduce(oldState: S, event: E)
}