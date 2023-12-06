package br.com.contacorrente.jetpack.menu.transference.success.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SupervisedUserCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import br.com.contacorrente.jetpack.ui.ContaCorrenteMainTheme

@Composable
fun TransferenceSuccessScreen(onButtonClicked: () -> Unit) {
    TransferenceSuccessLayout(onButtonClicked)
}

@Composable
fun TransferenceSuccessLayout(
    onButtonClicked: () -> Unit
) {
    Column {
        Icon(
            imageVector = Icons.Filled.SupervisedUserCircle,
            contentDescription = ""
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTransferenceSuccessScreen() {
    ContaCorrenteMainTheme {
        TransferenceSuccessScreen {}
    }
}