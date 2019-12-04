package br.com.contacorrente;

import br.com.contacorrente.factory.UserFactory;
import br.com.contacorrente.factory.UserLoginFactory;
import br.com.contacorrente.model.User;

public class Singleton {

    private static Singleton singleInstance = null;

    public static boolean test = false;
    public static User user;

    private Singleton(){

    }

    public static void getInstance() {
        if (singleInstance == null){
            user = UserFactory.getUser(new UserLoginFactory("", ""));
            singleInstance = new Singleton();
        }
    }

    public static void logout() {
        singleInstance = null;
        user = null;
    }
}
