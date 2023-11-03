package br.com.contacorrente.util

import android.health.connect.datatypes.units.Length
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.substring
import java.lang.Integer.max
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

class CurrencyMaskTransformation : VisualTransformation {
    /*override fun filter(text: AnnotatedString): TransformedText {
        val cleanText = text.text.filter { it.isDigit() } // Remover caracteres não numéricos
        val length = cleanText.length

        return if (length <= 2) {
            TransformedText(AnnotatedString("0,"), OffsetMapping.Identity)
        } else {
            val wholePart = cleanText.substring(0, length - 2)
            val decimalPart = cleanText.substring(length - 2)
            val formattedText = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(wholePart)
                }
                append(",")
                append(decimalPart)
            }
            TransformedText(formattedText, OffsetMapping.Identity)
        }
    }*/


    override fun filter(text: AnnotatedString): TransformedText {

        if (text.text.isNotEmpty()) {
            //val newText = getFloat(text.text)
            val newText = text.text

            return TransformedText(
                text = AnnotatedString(Utility.floatCurrencyFormat(newText)),
                offsetMapping = object : OffsetMapping {
                    override fun originalToTransformed(offset: Int): Int {
                        return Utility.floatCurrencyFormat(newText).length
                    }

                    override fun transformedToOriginal(offset: Int): Int {
                        return newText.length
                    }
                }
            )
        }

        return TransformedText(
            text = AnnotatedString(text.text, text.spanStyles, text.paragraphStyles),
            offsetMapping = OffsetMapping.Identity
        )

    }

    fun getFloat(text: String): String {
        val cleanText = text.filter { it.isDigit() }.padStart(3, '0')

        val intPart = cleanText.substring(0, cleanText.length - 2)
        val fractionPart = cleanText.substring(cleanText.length - 2)

        return "$intPart.$fractionPart"
    }

}

fun Long?.formatWithComma(): String =
    NumberFormat.getNumberInstance(Locale.getDefault()).format(this ?: 0)

class ThousandSeparatorOffsetMapping(
    val originalIntegerLength: Int
) : OffsetMapping {

    override fun originalToTransformed(offset: Int): Int =
        when (offset) {
            0, 1, 2 -> 4
            else -> offset + 1 + calculateThousandsSeparatorCount(originalIntegerLength)
        }

    override fun transformedToOriginal(offset: Int): Int {
        return originalIntegerLength + calculateThousandsSeparatorCount(originalIntegerLength) +
                2
    }

    private fun calculateThousandsSeparatorCount(intDigitCount: Int): Int {
        return max((intDigitCount - 1) / 3, 0)
    }
}

/*
val inputText = text.text
val intPart = if (inputText.length > 2) {
    inputText.subSequence(0, inputText.length - 2)
} else {
    "0"
}
var fractionPart = if (inputText.length >= 2) {
    inputText.subSequence(inputText.length - 2, inputText.length)
} else {
    inputText
}
// Add zeros if the fraction part length is not 2
if (fractionPart.length < 2) {
    fractionPart = fractionPart.padStart(2, '0')
}
val thousandsReplacementPattern = Regex("\\B(?=(?:\\d{3})+(?!\\d))")
val formattedIntWithThousandsSeparator =
    intPart.replace(
        thousandsReplacementPattern,
        thousandsSeparator.toString()
    )
val newText = AnnotatedString(
    formattedIntWithThousandsSeparator + decimalSeparator + fractionPart,
    text.spanStyles,
    text.paragraphStyles
)

val offsetMapping = ThousandSeparatorOffsetMapping(
    originalIntegerLength = intPart.length
)
return TransformedText(newText, offsetMapping)*/
