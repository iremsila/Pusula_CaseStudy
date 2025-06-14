package com.dijitalgaraj.study.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

abstract class BaseFragment<out ViewModel : BaseViewModel<BaseActionState>, Binding : ViewBinding>(
    viewModelClass: Class<ViewModel>
) : Fragment() {

    private var _binding: Binding? = null
    val binding get() = _binding!!

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> Binding

    protected val viewModel: ViewModel by lazy {
        ViewModelProvider(this)[viewModelClass]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = bindingInflater(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.actionState
                .map { it.getContentIfNotHandled() }
                .onEach { actionState ->
                    renderActionState(actionState)
                }
                .launchIn(this)

            init()
        }
    }
    open fun init() {}

    abstract fun renderActionState(actionState: BaseActionState?)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
