package br.com.contacorrente.factory;

import br.com.contacorrente.model.User;

public class UserFactory {

    public static User getUser(UserInterfaceFactory factory){
        return factory.createUser();
    }

}
