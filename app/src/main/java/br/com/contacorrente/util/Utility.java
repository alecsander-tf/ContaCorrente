package br.com.contacorrente.util;


import android.annotation.SuppressLint;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;

public class Utility {

    @SuppressLint("SimpleDateFormat")
    public static Date convertDate(String date){

        Date date1 = null;

        try {

            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);

        } catch (ParseException e) {

            try {
                date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        return date1;
    }

    @SuppressLint("SimpleDateFormat")
    public static String parseDate(Date date){

        return new SimpleDateFormat("dd/MM/yyyy").format(date);
    }

    public static String currencyFormat(String value){
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setMaximumFractionDigits(2);
        format.setCurrency(Currency.getInstance("BRL"));

        return format.format(Double.valueOf(value));
    }
}
