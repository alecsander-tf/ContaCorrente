package br.com.contacorrente.jetpack.menu.transference.error.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import br.com.contacorrente.jetpack.ui.ContaCorrenteMainTheme

@Composable
fun TransferenceErrorScreen(
    transferenceErrorMessage: String,
    onButtonClicked: () -> Unit
) {
    TransferenceErrorLayout(transferenceErrorMessage, onButtonClicked)
}

@Composable
fun TransferenceErrorLayout(
    transferenceErrorMessage: String,
    onButtonClicked: () -> Unit
) {
    Column {
        Text(
            text = transferenceErrorMessage,
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp
        )
        Text(text ="")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTransferenceErrorScreen() {
    ContaCorrenteMainTheme {
        TransferenceErrorScreen("Erro ao efetuar transferência") {

        }
    }
}