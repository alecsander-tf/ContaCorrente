package br.com.contacorrente.jetpack.menu.transference.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import br.com.contacorrente.base.BaseViewModel
import br.com.contacorrente.jetpack.menu.transference.usecase.ITransferenceUseCase
import kotlinx.coroutines.launch

class TransferenceViewModel(
    private val transferenceUseCase: ITransferenceUseCase
) : BaseViewModel() {
    var transferenceDestination by mutableStateOf("alecs@gmail.com")
    var transferenceValue by mutableStateOf("")

    fun processTransference() {
        launch {

            transferenceUseCase.execute(
                from = "",
                to = transferenceDestination,
                value = transferenceValue.toDouble()
            )
        }
    }
}