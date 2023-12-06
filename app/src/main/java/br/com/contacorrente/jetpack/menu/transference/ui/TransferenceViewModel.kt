package br.com.contacorrente.jetpack.menu.transference.ui

import br.com.contacorrente.base.BaseViewModel
import br.com.contacorrente.base.doIfSuccess
import br.com.contacorrente.jetpack.menu.transference.states.TransferenceState
import br.com.contacorrente.jetpack.menu.transference.usecase.ITransferenceUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TransferenceViewModel(
    private val transferenceUseCase: ITransferenceUseCase,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(TransferenceUiState())
    val uiState: StateFlow<TransferenceUiState> = _uiState.asStateFlow()

    fun processTransference() {

        if (!validateConfirmationPasswordField()) return

        launch {
            with(_uiState.value) {
                transferenceUseCase.execute(
                    clientIdSender = "2",
                    clientEmailReceiver = destinationTextFieldValue,
                    value = valueTextFieldValue,
                ).collect { state ->
                    state.doIfSuccess {
                        _uiState.value = _uiState.value.copy(
                            onError = true,
                            confirmationPasswordTextFieldValue = "",
                            transferenceState = TransferenceState.Success
                        )
                    }
                }
            }
        }
    }

    fun updateErrorState(boolean: Boolean) {
        _uiState.value = _uiState.value.copy(
            onError = boolean
        )
    }

    fun updateValueTextFieldValue(value: String) {
        _uiState.value = _uiState.value.copy(
            valueTextFieldValue = value,
            isValueTextFieldOnError = false
        )
    }

    fun updateDestinationTextFieldValue(value: String) {
        _uiState.value = _uiState.value.copy(
            destinationTextFieldValue = value,
            isDestinationTextFieldOnError = false
        )
    }

    fun updateConfirmationPasswordTextFieldValue(value: String) {
        _uiState.value = _uiState.value.copy(
            confirmationPasswordTextFieldValue = value,
            isConfirmationPasswordTextFieldOnError = false
        )
    }

    fun validateFields() {

        val isDestinationOnError = _uiState.value.destinationTextFieldValue.isEmpty()
        val isValueOnError = _uiState.value.valueTextFieldValue.isEmpty()

        _uiState.value = _uiState.value.copy(
            isDestinationTextFieldOnError = isDestinationOnError,
            isValueTextFieldOnError = isValueOnError,
            transferenceState = if (!(isDestinationOnError || isValueOnError))
                TransferenceState.Confirmation
            else
                TransferenceState.Transference
        )
    }

    private fun validateConfirmationPasswordField(): Boolean {

        val error = _uiState.value.confirmationPasswordTextFieldValue.isEmpty()

        _uiState.value = _uiState.value.copy(
            isConfirmationPasswordTextFieldOnError = error
        )

        return !error
    }

    fun dismissPasswordDialog() {
        _uiState.value = _uiState.value.copy(
            transferenceState = TransferenceState.Transference,
            confirmationPasswordTextFieldValue = "",
            isConfirmationPasswordTextFieldOnError = false
        )
    }
}