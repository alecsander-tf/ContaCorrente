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

    public static Singleton getInstance() {
        if (singleInstance == null){
            user = UserFactory.getUser(new UserLoginFactory("", ""));
            singleInstance = new Singleton();
        }
        return singleInstance;
    }

    public static void logout() {
        singleInstance = null;
        user = null;
    }
}
