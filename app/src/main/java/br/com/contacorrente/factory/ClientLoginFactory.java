package br.com.contacorrente.factory;

import br.com.contacorrente.model.Client;

public class ClientLoginFactory implements ClientInterfaceFactory{

    private String email;
    private String password;

    public ClientLoginFactory(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public Client createClient() {
        return new Client(email, password);
    }
}
