package br.com.contacorrente.jetpack.menu.transference.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import br.com.contacorrente.base.BaseViewModel

class TransferenceViewModel : BaseViewModel() {
    var transferenceDestination by mutableStateOf("alecs@gmail.com")
    var transferenceValue by mutableStateOf("")
}