package br.com.contacorrente.util;

import java.text.NumberFormat;
import java.util.Currency;

public class Format {

    public static String currencySendFormat(String value){
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setMaximumFractionDigits(2);
        format.setCurrency(Currency.getInstance("BRL"));

        return "- " + format.format(Double.valueOf(value));
    }

    public static String currencyFormat(String value){
        NumberFormat format = NumberFormat.getCurrencyInstance();
        format.setMaximumFractionDigits(2);
        format.setCurrency(Currency.getInstance("BRL"));

        return format.format(Double.valueOf(value));
    }

    public static String maskValue(String value, String mask){
        String status = "";
        int i = 0;
        // vamos iterar a mascara, para descobrir quais caracteres vamos adicionar e quando...
        for (char m : mask.toCharArray()) {
            if (m != '#') { // se não for um #, vamos colocar o caracter informado na máscara
                status += m;
                continue;
            }
            // Senão colocamos o valor que será formatado
            try {
                status += value.charAt(i);
            } catch (Exception e) {
                break;
            }
            i++;
        }
        return status;
    }

}
