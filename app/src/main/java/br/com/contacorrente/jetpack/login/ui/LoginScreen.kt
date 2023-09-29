package br.com.contacorrente.jetpack.login.ui

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.contacorrente.R
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = koinViewModel(),
    onNavigateToMenu: (userEmail: String) -> Unit
) {

    val loginUiState by loginViewModel.uiState.collectAsState()

    if (loginUiState.isLoggedIn) {
        LaunchedEffect(Unit) {
            onNavigateToMenu(loginViewModel.userEmail)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LoginLayout(
            email = loginViewModel.userEmail,
            password = loginViewModel.userPassword,
            onUserEmailChanged = {
                loginViewModel.userEmail = it
            },
            onUserPasswordChanged = {
                loginViewModel.userPassword = it
            },
            loginButtonClick = {
                loginViewModel.login()
            },
            loginUiState
        )
    }
}

@Composable
fun LoginLayout(
    email: String,
    password: String,
    onUserEmailChanged: (String) -> Unit,
    onUserPasswordChanged: (String) -> Unit,
    loginButtonClick: () -> Unit,
    loginUiState: LoginUiState
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

        TextFieldWithIcon(
            textValue = email,
            icon = Icons.Filled.Person,
            placeholder = stringResource(id = R.string.email),
            onValueChange = onUserEmailChanged
        )

        TextFieldWithIcon(
            textValue = password,
            icon = Icons.Filled.Lock,
            placeholder = stringResource(id = R.string.password),
            onValueChange = onUserPasswordChanged
        )

        Text(
            text = loginUiState.errorMessage,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Color.Red,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Button(
            onClick = {
                loginButtonClick()
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

@Composable
fun TextFieldWithIcon(
    textValue: String,
    icon: ImageVector,
    placeholder: String,
    onValueChange: (String) -> Unit
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