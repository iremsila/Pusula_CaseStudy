package com.dijitalgaraj.study.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dijitalgaraj.study.utils.extensions.EventWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<ActionState : BaseActionState>(
    baseActionState: ActionState
) : ViewModel() {

    private val mActionState: MutableStateFlow<EventWrapper<ActionState>> =
        MutableStateFlow(EventWrapper(baseActionState))
    val actionState: StateFlow<EventWrapper<ActionState>>
        get() = mActionState

    fun updateActionState(state: ActionState) {
        viewModelScope.launch {
            mActionState.value = EventWrapper(state)
        }
    }
}
