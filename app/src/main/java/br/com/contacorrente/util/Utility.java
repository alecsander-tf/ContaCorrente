package br.com.contacorrente.util;


import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;

public class Utility {

    public static Date convertDate(String date){

        Date date1 = null;

        try {

            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date1;
    }

    public static String currencyFormat(String value){
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setMaximumFractionDigits(2);
        format.setCurrency(Currency.getInstance("BRL"));

        return format.format(Double.valueOf(value));
    }

    public static String maskValue(String value, String mask){
        StringBuilder status = new StringBuilder();
        int i = 0;
        for (char m : mask.toCharArray()) {
            if (m != '#') {
                status.append(m);
                continue;
            }
            try {
                status.append(value.charAt(i));
            } catch (Exception e) {
                break;
            }
            i++;
        }
        return status.toString();
    }

    public static void blockMultipleClicks(){


    }
}
