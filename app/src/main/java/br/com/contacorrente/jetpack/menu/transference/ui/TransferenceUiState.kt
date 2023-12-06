package br.com.contacorrente.jetpack.menu.transference.ui

import br.com.contacorrente.jetpack.menu.transference.states.TransferenceState

data class TransferenceUiState(
    val transferenceState: TransferenceState = TransferenceState.Transference,

    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val onError: Boolean = false,

    val destinationTextFieldValue: String = "alecs@gmail.com",
    val valueTextFieldValue: String = "123",
    val confirmationPasswordTextFieldValue: String = "123",

    val isDestinationTextFieldOnError: Boolean = false,
    val isValueTextFieldOnError: Boolean = false,
    val isConfirmationPasswordTextFieldOnError: Boolean = false,
)