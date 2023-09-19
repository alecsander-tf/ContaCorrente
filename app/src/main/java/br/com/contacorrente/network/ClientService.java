package br.com.contacorrente.network;

import java.util.List;

import br.com.contacorrente.model.Status;
import br.com.contacorrente.model.Transference;
import br.com.contacorrente.model.Client;

public interface ClientService {

    interface ClientServiceCallback<T>{
        void onLoaded(T client);
        void onError();

        void notFoundError();
    }

    void transfer(Long clientIdSender, Long clientIdReceiver, double value, ClientServiceCallback<Status> callback);
    void getBankStatement(Long clientId, ClientServiceCallback<List<Transference>> callback);
    void checkLogin(String email, String password, ClientServiceCallback<Status> callback);
    void getClientById(Long clientId, ClientServiceCallback<Client> callback);
    void getClientByEmail(String email, ClientServiceCallback<Client> callback);

}
