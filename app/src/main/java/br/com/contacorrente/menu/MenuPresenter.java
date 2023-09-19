package br.com.contacorrente.menu;

import br.com.contacorrente.Singleton;
import br.com.contacorrente.model.Client;
import br.com.contacorrente.network.ClientService;
import br.com.contacorrente.network.ClientServiceImpl;

public class MenuPresenter implements MenuContract.ClientInteractions {

    private ClientService mApi;
    private MenuContract.View view;

    MenuPresenter(MenuContract.View view){
        this.view = view;
        mApi = new ClientServiceImpl();
    }

    @Override
    public void loadClientAccount(String email) {
        mApi.getClientByEmail(email, new ClientService.ClientServiceCallback<Client>() {
            @Override
            public void onLoaded(Client client) {
                Singleton.client = client;
                view.showAccountDetails();
            }

            @Override
            public void onError() {
                view.showError("Não foi possível carregar os seus dados!");
            }

            @Override
            public void notFoundError() {
                view.showError("Cliente não encontrado!");
            }
        });
    }
}
