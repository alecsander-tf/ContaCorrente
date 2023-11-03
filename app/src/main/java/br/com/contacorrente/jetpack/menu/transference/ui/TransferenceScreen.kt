package br.com.contacorrente.jetpack.menu.transference.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.contacorrente.R
import br.com.contacorrente.jetpack.ui.ContaCorrenteMainTheme
import br.com.contacorrente.util.CurrencyMaskTransformation
import br.com.contacorrente.util.Utility
import org.koin.androidx.compose.koinViewModel

@Composable
fun TransferenceScreen(
    transferenceViewModel: TransferenceViewModel = koinViewModel()
) {

    TransferenceLayout(
        transferenceViewModel.transferenceDestination,
        transferenceViewModel.transferenceValue,
        onDestinationChange = {
            transferenceViewModel.transferenceDestination = it
        },
        onValueChange = {
            transferenceViewModel.transferenceValue = it
        }
    )

}

@Composable
fun TransferenceLayout(
    destination: String,
    value: String,
    onDestinationChange: (String) -> Unit,
    onValueChange: (String) -> Unit
) {

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
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            value = destination,
            onValueChange = onDestinationChange,
            singleLine = true,
            placeholder = {
                Text(
                    stringResource(
                        R.string.transference_destination_placeholder
                    )
                )
            }
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            singleLine = true,
            visualTransformation = CurrencyMaskTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword
            ),
            placeholder = {
                Text(
                    stringResource(
                        R.string.value_destination_placeholder
                    )
                )
            }
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            onClick = { /*TODO*/ },
        ) {
            Text(stringResource(R.string.transfer))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTransferenceScreen() {
    ContaCorrenteMainTheme {
        TransferenceLayout(
            "alecs@gmail.com", "1000", {}, {}
        )
    }
}