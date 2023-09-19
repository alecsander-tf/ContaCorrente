package br.com.contacorrente.menu.fragment.myAccount;

import br.com.contacorrente.Singleton;
import br.com.contacorrente.model.Client;
import br.com.contacorrente.network.ClientService;
import br.com.contacorrente.network.ClientServiceImpl;

public class MyAccountPresenter implements MyAccountContract.ClientInteractions {

    private final MyAccountContract.View view;
    private final ClientService mRetrofit;

    MyAccountPresenter(MyAccountContract.View view) {
        this.view = view;
        mRetrofit = new ClientServiceImpl();
    }

    @Override
    public void loadClientAccount(String email) {
        mRetrofit.getClientByEmail(email, new ClientService.ClientServiceCallback<>() {
            @Override
            public void onLoaded(Client client) {
                Singleton.client = client;
                view.showAccountDetails(client.getName(), client.getBalance());
            }

            @Override
            public void onError() {
                view.showToast("Usuário não carregado");
            }

            @Override
            public void notFoundError() {
                view.showToast("Cliente não encontrado!");
            }
        });
    }

    @Override
    public void loadNewBalance() {
        mRetrofit.getClientByEmail(Singleton.client.getEmail(), new ClientService.ClientServiceCallback<>() {
            @Override
            public void onLoaded(Client client) {
                Singleton.client = client;
                view.showNewBalance();
            }

            @Override
            public void onError() {
                view.showToast("Não foi possível atualizar seu saldo!");
            }

            @Override
            public void notFoundError() {
                view.showToast("Cliente não encontrado!");
            }
        });
    }
}
