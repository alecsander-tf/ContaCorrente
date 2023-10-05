package br.com.contacorrente.jetpack.settings.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.contacorrente.jetpack.ui.ContaCorrenteMainTheme

@Preview(showBackground = true)
@Composable
fun PreviewSettingsLayout() {
    ContaCorrenteMainTheme {
        SettingsLayout()
    }
}

@Composable
fun SettingsLayout() {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "Tema escuro", fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(
                vertical = 8.dp
            ),
        )
        Text("Diminui o bilho e melhora a visibilidade em ambientes escuros")
    }
}

@Composable
fun SettingsScreen() {
    SettingsLayout()
}