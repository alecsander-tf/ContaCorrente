package br.com.contacorrente.jetpack.menu.transference.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import br.com.contacorrente.base.BaseViewModel
import br.com.contacorrente.jetpack.menu.transference.usecase.ITransferenceUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TransferenceViewModel(
    private val transferenceUseCase: ITransferenceUseCase,
) : BaseViewModel() {

    var transferenceDestination by mutableStateOf("alecs@gmail.com")
    var transferenceValue by mutableStateOf("")

    var transferencePassword by mutableStateOf("")

    private val _uiState = MutableStateFlow(TransferenceUiState())
    val uiState: StateFlow<TransferenceUiState> = _uiState.asStateFlow()

    fun processTransference() {
        launch {
            transferenceUseCase.execute(
                clientIdSender = "2",
                clientEmailReceiver = transferenceDestination,
                value = transferenceValue
            ).collect {

            }
        }
    }

    fun updateTransferenceValue(value: String) {
        /* TODO: Melhorar lógica para alterar o isValueOnError na classe CustomTextField ou no set da propriedade */
        transferenceValue = value
        _uiState.value = _uiState.value.copy(
            isValueOnError = false
        )
    }

    fun validateFields() {

        val isDestinationOnError = transferenceDestination.isEmpty()
        val isValueOnError = transferenceValue.isEmpty()

        _uiState.value = _uiState.value.copy(
            isDestinationOnError = isDestinationOnError,
            isValueOnError = isValueOnError
        )

        val validFields = !(isDestinationOnError || isValueOnError)
    }
}