package br.com.contacorrente.factory;

import br.com.contacorrente.model.User;

public class UserAccountFactory implements UserInterfaceFactory {

    private String id;
    private String name;
    private String email;
    private String profile;
    private String balance;

    public UserAccountFactory(String id, String name, String email, String profile, String balance) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.profile = profile;
        this.balance = balance;
    }

    @Override
    public User createUser() {
        return new User(id, name, email, profile, balance);
    }
}
