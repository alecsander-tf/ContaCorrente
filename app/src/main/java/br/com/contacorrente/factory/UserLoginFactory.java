package br.com.contacorrente.factory;

import br.com.contacorrente.model.User;

public class UserLoginFactory implements UserInterfaceFactory{

    private String email;
    private String password;

    public UserLoginFactory(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public User createUser() {
        return new User("", "", email, "", password, "");
    }
}
