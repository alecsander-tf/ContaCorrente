package br.com.contacorrente.util

import android.annotation.SuppressLint
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Currency
import java.util.Date

object Utility {
    @SuppressLint("SimpleDateFormat")
    fun convertDate(date: String?): Date? {
        if (date.isNullOrEmpty()) return null
        var date1: Date? = null
        try {
            date1 = SimpleDateFormat("yyyy-MM-dd").parse(date)
        } catch (e: ParseException) {
            try {
                date1 = SimpleDateFormat("dd/MM/yyyy").parse(date)
            } catch (ex: ParseException) {
                ex.printStackTrace()
            }
        }
        return date1
    }

    @SuppressLint("SimpleDateFormat")
    fun parseDate(date: Date?): String? {
        if (date == null) return ""
        date.let {
            return SimpleDateFormat("dd/MM/yyyy").format(it)
        }
    }

    fun currencyFormat(value: String = "0"): String {
        return NumberFormat.getCurrencyInstance().apply {
            maximumFractionDigits = 2
            currency = Currency.getInstance("BRL")
        }.format(value.toDouble())
    }
}