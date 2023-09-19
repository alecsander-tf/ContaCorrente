package br.com.contacorrente;

import android.content.SharedPreferences;

import br.com.contacorrente.factory.ClientFactory;
import br.com.contacorrente.factory.ClientLoginFactory;
import br.com.contacorrente.model.Client;

public class Singleton {

    private static Singleton singleInstance = null;

    public static SharedPreferences sharedPreferences;

    public static Client client;

    private Singleton(){

    }

    public static void getInstance() {
        if (singleInstance == null){
            client = ClientFactory.getClient(new ClientLoginFactory("", ""));
            singleInstance = new Singleton();
        }
    }

    public static void darkMode(boolean status){
        if (sharedPreferences != null){
            SharedPreferences.Editor editor = Singleton.sharedPreferences.edit();
            editor.putBoolean("darkMode", status);
            editor.apply();
        }
    }

    public static void logout() {
        if (client != null){
            if (client.getClientId() != null){
                sharedPreferences.edit().clear().apply();
                singleInstance = null;
                client = null;
            }
        }
    }
}
