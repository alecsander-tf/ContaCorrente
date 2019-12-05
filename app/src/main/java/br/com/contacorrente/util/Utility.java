package br.com.contacorrente.util;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.NumberFormat;
import java.util.Currency;

public class Utility {

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

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        if (imm.isActive()){
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
