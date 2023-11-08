package br.com.contacorrente.model

data class TextInfo(
    val textValue: String,
    val supportingText: String,
    val isError: Boolean,
    val onValueChanged: (String) -> Unit,
)
