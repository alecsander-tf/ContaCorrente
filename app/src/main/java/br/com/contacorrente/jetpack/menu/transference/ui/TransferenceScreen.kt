package br.com.contacorrente.jetpack.menu.transference.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import br.com.contacorrente.R
import br.com.contacorrente.jetpack.ui.ContaCorrenteMainTheme
import br.com.contacorrente.jetpack.ui.base.CustomTextField
import br.com.contacorrente.model.TextInfo
import br.com.contacorrente.util.CurrencyMaskTransformation
import org.koin.androidx.compose.koinViewModel

@Composable
fun TransferenceScreen(
    transferenceViewModel: TransferenceViewModel = koinViewModel(),
) {

    val transferenceUiState by transferenceViewModel.uiState.collectAsState()

    TransferenceLayout(
        destinationTextInfo = TextInfo(
            textValue = transferenceViewModel.transferenceDestination,
            supportingText = stringResource(id = R.string.invalid_destination_text_field),
            isError = transferenceUiState.isDestinationOnError
        ) {
            transferenceViewModel.transferenceDestination = it
        },
        valueTextInfo = TextInfo(
            textValue = transferenceViewModel.transferenceValue,
            supportingText = stringResource(id = R.string.invalid_value_text_field),
            isError = transferenceUiState.isValueOnError
        ) {
            transferenceViewModel.updateTransferenceValue(it)
        },
        passwordConfirmationTextInfo = TextInfo(
            textValue = transferenceViewModel.transferencePassword,
            supportingText = stringResource(id = R.string.invalid_password_text_field),
            isError = transferenceUiState.isPasswordOnError
        ) {
            transferenceViewModel.transferencePassword = it
        },
        onPasswordConfirmationClicked = transferenceViewModel::processTransference,
        onTransferButtonClicked = transferenceViewModel::validateFields
    )

}

@Composable
fun TransferenceLayout(
    destinationTextInfo: TextInfo,
    valueTextInfo: TextInfo,
    passwordConfirmationTextInfo: TextInfo,
    onTransferButtonClicked: () -> Unit,
    onPasswordConfirmationClicked: () -> Unit,
) {

    var openDialog by remember {
        mutableStateOf(false)
    }

    if (openDialog) {
        ConfirmationDialog(
            passwordConfirmationTextInfo,
            onDismissRequest = {
                openDialog = false
            },
            onConfirmListener = onPasswordConfirmationClicked
        )
    }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Enviar dinheiro",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
        )
        CustomTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            textInfo = destinationTextInfo,
            placeholder = stringResource(R.string.transference_destination_placeholder)
        )
        CustomTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            textInfo = valueTextInfo,
            visualTransformation = CurrencyMaskTransformation(),
            placeholder = stringResource(R.string.value_destination_placeholder),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword
            ),
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            onClick = onTransferButtonClicked,
        ) {
            Text(stringResource(R.string.transfer))
        }
    }
}

@Composable
fun ConfirmationDialog(
    passwordConfirmationTextInfo: TextInfo,
    onDismissRequest: () -> Unit,
    onConfirmListener: () -> Unit,
) {

    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
            ) {
                Text(
                    text = "Confirmação",
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
                    text = "Digite sua senha para efetuar a transferência"
                )
                CustomTextField(
                    textInfo = passwordConfirmationTextInfo,
                    placeholder = stringResource(R.string.password)
                )
                Row(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .align(Alignment.End)
                ) {
                    TextButton(onClick = onDismissRequest) {
                        Text("Cancelar")
                    }
                    TextButton(onClick = {
                        onConfirmListener()
                        onDismissRequest()
                    }) {
                        Text("Confirmar")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewConfirmationDialog() {
    ContaCorrenteMainTheme {
        ConfirmationDialog(
            passwordConfirmationTextInfo = TextInfo(
                "PreviewValue",
                "SupportingPreviewValue",
                isError = false
            ) {},
            onDismissRequest = {},
            onConfirmListener = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTransferenceScreen() {
    ContaCorrenteMainTheme {
        TransferenceLayout(
            destinationTextInfo = TextInfo(
                "PreviewValue",
                "SupportingPreviewValue",
                isError = false
            ) {},
            valueTextInfo = TextInfo(
                "PreviewValue",
                "SupportingPreviewValue",
                isError = false
            ) {},
            passwordConfirmationTextInfo = TextInfo(
                "PreviewValue",
                "SupportingPreviewValue",
                isError = false
            ) {},
            onTransferButtonClicked = {},
            onPasswordConfirmationClicked = {},
        )
    }
}