package br.com.contacorrente.jetpack.ui.base

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.VisualTransformation
import br.com.contacorrente.model.TextFieldInfo

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    prefix: String = "",
    textFieldInfo: TextFieldInfo,
    placeholder: String,
) {

    val source = remember { MutableInteractionSource() }
    var textFieldWasTyped by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf(true) }

    error = textFieldInfo.isError

    val shouldShowError = (textFieldInfo.textValue.isEmpty() && textFieldWasTyped) || error

    OutlinedTextField(
        modifier = modifier,
        visualTransformation = visualTransformation,
        isError = shouldShowError,
        value = textFieldInfo.textValue,
        keyboardOptions = keyboardOptions,
        interactionSource = source,
        singleLine = true,
        label = {
            Text(text = placeholder)
        },
        supportingText = {
            if (shouldShowError) Text(text = textFieldInfo.supportingText)
        },
        onValueChange = { newValue ->
            textFieldWasTyped = true
            error = false
            textFieldInfo.onValueChanged(newValue)
        },
        prefix = if (prefix.isNotEmpty()) {
            {
                Text(
                    text = prefix,
                    color = if (shouldShowError) {
                        MaterialTheme.colorScheme.error
                    } else Color.Unspecified
                )
            }
        } else null,
        leadingIcon = if (icon != null) {
            {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (shouldShowError) {
                        MaterialTheme.colorScheme.error
                    } else {
                        if (source.collectIsFocusedAsState().value) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurface
                        }
                    }
                )
            }
        } else null
    )
}