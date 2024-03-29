@file:OptIn(ExperimentalMaterial3Api::class)

package br.com.contacorrente.jetpack.settings.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import br.com.contacorrente.R
import br.com.contacorrente.constants.AppThemeOptions
import br.com.contacorrente.jetpack.ui.PreviewContaCorrenteMainTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsLayout(
    settingsUiState: SettingsUiState,
    onConfirmListener: (AppThemeOptions) -> Unit
) {

    val openDialog = remember { mutableStateOf(false) }
    if (openDialog.value) {
        AppThemeDialog(
            defaultThemeOption = settingsUiState.theme,
            onDismissRequest = {
                openDialog.value = false
            },
            onConfirmListener = onConfirmListener
        )
    }

    Column {
        SettingsOption(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    openDialog.value = true
                }
        )
    }

}

@Composable
fun SettingsOption(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(16.dp)
    ) {
        Column(
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
}

@Composable
fun AppThemeDialog(
    defaultThemeOption: AppThemeOptions = AppThemeOptions.SYSTEM_DEFAULT,
    onDismissRequest: () -> Unit,
    onConfirmListener: (selectedOptionIndex: AppThemeOptions) -> Unit
) {

    val (selectedOption, setSelected) = remember {
        mutableStateOf(defaultThemeOption)
    }
    val radioOptions = listOf(
        Pair(AppThemeOptions.LIGHT, stringResource(id = R.string.light)),
        Pair(AppThemeOptions.DARK, stringResource(id = R.string.dark)),
        Pair(AppThemeOptions.SYSTEM_DEFAULT, stringResource(id = R.string.system_default)),
    )

    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

                DarkModeRadioGroup(
                    radioOptions = radioOptions,
                    selectedOption, setSelected
                )

                Row(
                    modifier = Modifier
                        .align(Alignment.End)
                ) {
                    TextButton(onClick = onDismissRequest) {
                        Text("Cancelar")
                    }
                    TextButton(onClick = {
                        onConfirmListener(selectedOption)
                        onDismissRequest()
                    }) {
                        Text("Ok")
                    }
                }
            }
        }
    }
}

@Composable
fun DarkModeRadioGroup(
    radioOptions: List<Pair<AppThemeOptions, String>>,
    selected: AppThemeOptions,
    setSelected: (selected: AppThemeOptions) -> Unit,
) {
    Column {
        radioOptions.forEach { item ->
            Row(
                modifier = Modifier
                    .clickable {
                        setSelected(item.first)
                    }
            ) {
                RadioButton(
                    selected = selected == item.first,
                    onClick = {
                        setSelected(item.first)
                    }
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterVertically),
                    text = item.second
                )
            }
        }
    }
}

@Preview
@Composable
fun Preview() {
    PreviewContaCorrenteMainTheme {
        AppThemeDialog(onDismissRequest = { }, onConfirmListener = {})
    }
}


@Composable
fun SettingsScreen(settingsViewModel: SettingsViewModel = koinViewModel()) {

    val settingsUiState by settingsViewModel.uiState.collectAsState()

    settingsViewModel.readTheme()

    SettingsLayout(settingsUiState) {
        settingsViewModel.setTheme(it)
    }
}