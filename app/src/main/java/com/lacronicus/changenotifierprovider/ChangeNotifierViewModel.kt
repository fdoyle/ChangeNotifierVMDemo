package com.lacronicus.changenotifierprovider

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

//based on Flutter ChangeNotifier
interface ChangeNotifier {
    fun notifyListeners()
}

open class ChangeNotifierViewModel : ViewModel(), ChangeNotifier {
    val listeners: MutableSet<Listener> = mutableSetOf()

    fun addListener(listener: Listener) {
        listeners.add(listener)
    }

    fun removeListener(listener: Listener) {
        listeners.remove(listener)
    }

    override fun notifyListeners() {
        listeners.forEach {
            it.onDataChanged()
        }
    }

    override fun equals(other: Any?): Boolean {
        return false // compose will ignore updates if the new value is equal to the old. skip this check
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}

fun interface Listener {
    fun onDataChanged()
}

@Composable
inline fun <reified T : ChangeNotifierViewModel> withChangeNotifier(vm: T = viewModel()): T {
    val vmState = remember { mutableStateOf(vm) }
    DisposableEffect(vmState) {
        val listener = Listener { vmState.value = vmState.value }
        vm.addListener(listener)

        onDispose {
            vm.removeListener(listener)
        }
    }
    return vmState.value
}