package br.com.contacorrente.jetpack.menu.transference.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.contacorrente.R
import br.com.contacorrente.jetpack.menu.transference.states.TransferenceState
import br.com.contacorrente.jetpack.ui.ContaCorrenteMainTheme
import br.com.contacorrente.jetpack.ui.base.CustomTextField
import br.com.contacorrente.model.TextFieldInfo
import br.com.contacorrente.util.CurrencyMaskTransformation
import br.com.contacorrente.util.Utility
import org.koin.androidx.compose.koinViewModel

@Composable
fun TransferenceScreen(
    transferenceViewModel: TransferenceViewModel = koinViewModel(),
) {

    val transferenceUiState by transferenceViewModel.uiState.collectAsState()

    TransferenceLayout(
        transferenceTargetState = transferenceUiState.transferenceState,
        destinationTextFieldInfo = TextFieldInfo(
            textValue = transferenceUiState.destinationTextFieldValue,
            supportingText = stringResource(id = R.string.invalid_destination_text_field),
            isError = transferenceUiState.isDestinationTextFieldOnError
        ) {
            transferenceViewModel.updateDestinationTextFieldValue(it)
        },
        valueTextFieldInfo = TextFieldInfo(
            textValue = transferenceUiState.valueTextFieldValue,
            supportingText = stringResource(id = R.string.invalid_value_text_field),
            isError = transferenceUiState.isValueTextFieldOnError
        ) {
            transferenceViewModel.updateValueTextFieldValue(it)
        },
        passwordConfirmationTextFieldInfo = TextFieldInfo(
            textValue = transferenceUiState.confirmationPasswordTextFieldValue,
            supportingText = stringResource(id = R.string.invalid_password_text_field),
            isError = transferenceUiState.isConfirmationPasswordTextFieldOnError
        ) {
            transferenceViewModel.updateConfirmationPasswordTextFieldValue(it)
        },
        onTransferButtonClicked = transferenceViewModel::validateFields,
        onDismissRequest = transferenceViewModel::dismissPasswordDialog,
        onConfirmListener = transferenceViewModel::processTransference
    )
}

@Composable
fun TransferenceLayout(
    transferenceTargetState: TransferenceState,
    destinationTextFieldInfo: TextFieldInfo,
    valueTextFieldInfo: TextFieldInfo,
    passwordConfirmationTextFieldInfo: TextFieldInfo,
    onTransferButtonClicked: () -> Unit,
    onDismissRequest: () -> Unit,
    onConfirmListener: () -> Unit
) {

    AnimatedContent(
        targetState = transferenceTargetState,
        transitionSpec = {
            if (targetState.ordinal > initialState.ordinal) {
                fadeIn() + slideInVertically(
                    animationSpec = tween(400),
                    initialOffsetY = { fullHeight -> fullHeight }
                ) togetherWith fadeOut(animationSpec = tween(400))
            } else {
                fadeIn(animationSpec = tween(400)) togetherWith slideOutVertically(
                    animationSpec = tween(400),
                    targetOffsetY = { fullHeight -> fullHeight }
                ) + fadeOut(animationSpec = tween(400))
            }
        },
        label = "",
    ) { transferenceStateResult ->

        when (transferenceStateResult) {
            TransferenceState.Error -> {/*TODO()*/
            }

            TransferenceState.Success -> {/*TODO()*/
            }

            TransferenceState.Confirmation -> TransferenceConfirmationContent(
                destinationTextFieldInfo = destinationTextFieldInfo,
                valueTextFieldInfo = valueTextFieldInfo,
                passwordConfirmationTextFieldInfo = passwordConfirmationTextFieldInfo,
                onDismissRequest = onDismissRequest,
                onConfirmListener = onConfirmListener,
            )

            TransferenceState.Transference -> TransferenceContent(
                destinationTextFieldInfo = destinationTextFieldInfo,
                valueTextFieldInfo = valueTextFieldInfo,
                onTransferButtonClicked = onTransferButtonClicked,
            )
        }
    }
}

@Composable
fun TransferenceContent(
    destinationTextFieldInfo: TextFieldInfo,
    valueTextFieldInfo: TextFieldInfo,
    onTransferButtonClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
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
            textFieldInfo = destinationTextFieldInfo,
            placeholder = stringResource(R.string.transference_destination_placeholder)
        )
        CustomTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            textFieldInfo = valueTextFieldInfo,
            visualTransformation = CurrencyMaskTransformation(),
            placeholder = stringResource(R.string.value_destination_placeholder),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword
            ),
            prefix = "R$"
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
fun TransferenceConfirmationContent(
    destinationTextFieldInfo: TextFieldInfo,
    valueTextFieldInfo: TextFieldInfo,
    passwordConfirmationTextFieldInfo: TextFieldInfo,
    onDismissRequest: () -> Unit,
    onConfirmListener: () -> Unit,
) {

    Column(
        modifier = Modifier
            .padding(top = 30.dp, start = 16.dp, end = 16.dp)
            .fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(id = R.string.confirmation),
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp
        )

        Card(
            modifier = Modifier
                .padding(top = 32.dp)
                .shadow(4.dp, shape = MaterialTheme.shapes.medium)
        ) {
            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally),
                text = "Dados da sua transferência",
                fontWeight = FontWeight.SemiBold,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 16.dp,
                        start = 16.dp, end = 16.dp
                    ),
            ) {
                Text(
                    text = "Para"
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    maxLines = 1,
                    softWrap = false,
                    modifier = Modifier.weight(3f, fill = false),
                    overflow = TextOverflow.Ellipsis,
                    text = destinationTextFieldInfo.textValue
                )
            }

            Row(
                modifier = Modifier.padding(
                    top = 8.dp, bottom = 16.dp,
                    start = 16.dp, end = 16.dp
                )
            ) {
                Text(
                    text = "Valor"
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = Utility.currencyFormat(valueTextFieldInfo.textValue)
                )
            }
        }
        CustomTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            textFieldInfo = passwordConfirmationTextFieldInfo,
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
            }) {
                Text("Confirmar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewConfirmationDialog() {
    ContaCorrenteMainTheme {
        TransferenceConfirmationContent(
            passwordConfirmationTextFieldInfo = TextFieldInfo(
                "123",
                "SupportingPreviewValue",
                isError = false
            ) {},
            valueTextFieldInfo = TextFieldInfo(
                "123",
                "SupportingPreviewValue",
                isError = false
            ) {},
            destinationTextFieldInfo = TextFieldInfo(
                "alecsander.t.fernandes@bradesco.com.br",
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
            destinationTextFieldInfo = TextFieldInfo(
                "alecsander.t.fernandes@bradesco.com.br",
                "SupportingPreviewValue",
                isError = false
            ) {},
            valueTextFieldInfo = TextFieldInfo(
                "123",
                "SupportingPreviewValue",
                isError = false
            ) {},
            onTransferButtonClicked = {},
            passwordConfirmationTextFieldInfo = TextFieldInfo(
                "123",
                "SupportingPreviewValue",
                isError = false
            ) {},
            onDismissRequest = {},
            transferenceTargetState = TransferenceState.Transference
        ) {}
    }
}