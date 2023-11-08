package br.com.contacorrente.jetpack.login.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.contacorrente.R
import br.com.contacorrente.jetpack.ui.ContaCorrenteMainTheme
import br.com.contacorrente.jetpack.ui.base.CustomTextField
import br.com.contacorrente.model.TextInfo
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel = koinViewModel(),
    onNavigateToMenu: (userEmail: String) -> Unit,
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
            emailTextInfo = TextInfo(
                loginViewModel.userEmail,
                stringResource(id = R.string.invalid_email_text_field),
                loginUiState.isEmailOnError
            ) {
                loginViewModel.userEmail = it
            },
            passwordTextInfo = TextInfo(
                loginViewModel.userPassword,
                stringResource(id = R.string.invalid_password_text_field),
                loginUiState.isPasswordOnError
            ) {
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
    emailTextInfo: TextInfo,
    passwordTextInfo: TextInfo,
    loginButtonClick: () -> Unit,
    loginUiState: LoginUiState,
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

        CustomTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            textInfo = emailTextInfo,
            icon = Icons.Filled.Person,
            placeholder = stringResource(id = R.string.email)
        )

        CustomTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            textInfo = passwordTextInfo,
            icon = Icons.Filled.Lock,
            placeholder = stringResource(id = R.string.password)
        )

        if (loginUiState.onError) {
            ErrorText(
                errorMessage = loginUiState.errorMessage
            )
        }

        Button(
            onClick = {
                loginButtonClick()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = stringResource(id = R.string.login),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewErrorText() {
    ContaCorrenteMainTheme {
        ErrorText(errorMessage = "Email ou senha inválidos")
    }
}

@Composable
fun ErrorText(
    errorMessage: String,
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        text = errorMessage,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.error
    )
}