package br.com.contacorrente.factory;

import br.com.contacorrente.model.Client;

public class ClientFactory {

    public static Client getClient(ClientInterfaceFactory factory){
        return factory.createClient();
    }

}
