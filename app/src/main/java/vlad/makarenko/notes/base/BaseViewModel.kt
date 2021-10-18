package vlad.makarenko.notes.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<S : UiState, in E : UiEvent> : ViewModel() {
    val state: StateFlow<S>
        get() = reducer.state

    protected abstract val reducer: Reducer<S, E>
}