package br.com.contacorrente.factory;

import br.com.contacorrente.model.Client;

public class ClientAccountFactory implements ClientInterfaceFactory {

    private final Long id;
    private final String name;
    private final String email;
    private final String profile;
    private final String balance;

    public ClientAccountFactory(Long id, String name, String email, String profile, String balance) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.profile = profile;
        this.balance = balance;
    }

    @Override
    public Client createClient() {
        return new Client(id, name, email, profile, balance);
    }
}
