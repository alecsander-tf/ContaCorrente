package br.com.contacorrente.jetpack.login.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.contacorrente.R
import br.com.contacorrente.jetpack.ui.ApplicationMainTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ApplicationMainTheme {
                LoginLayout()
            }
        }

    }
}

@Composable
fun LoginLayout(loginViewModel: LoginViewModel = hiltViewModel()) {

    val loginUiState by loginViewModel.uiState.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        Column(verticalArrangement = Arrangement.Center) {

            Text(
                text = stringResource(R.string.account_access),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            UserTextField()
            PasswordTextField()

            Button(
                onClick = {
                    loginViewModel.login("", "")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.login),
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Composable
fun UserTextField() {
    var textValue by remember { mutableStateOf(TextFieldValue()) }

    TextFieldWithIcon(
        textValue = textValue,
        icon = Icons.Filled.Person,
        placeholder = stringResource(id = R.string.user),
        onValueChange = {
            textValue = it
        }
    )
}

@Composable
fun PasswordTextField() {
    var textValue by remember { mutableStateOf(TextFieldValue()) }

    TextFieldWithIcon(
        textValue = textValue,
        icon = Icons.Filled.Lock,
        placeholder = stringResource(id = R.string.password),
        onValueChange = {
            textValue = it
        }
    )
}

@Composable
fun TextFieldWithIcon(
    textValue: TextFieldValue = TextFieldValue(),
    icon: ImageVector,
    placeholder: String,
    onValueChange: (TextFieldValue) -> Unit
) {

    val source = remember { MutableInteractionSource() }

    OutlinedTextField(
        value = textValue,
        onValueChange = { newValue ->
            onValueChange(newValue)
        },
        label = {
            Text(text = placeholder)
        },
        interactionSource = source,
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (source.collectIsFocusedAsState().value) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
            )
        },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)

    )
}