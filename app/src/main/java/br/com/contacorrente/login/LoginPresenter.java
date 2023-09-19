package br.com.contacorrente.login;

import android.content.SharedPreferences;

import br.com.contacorrente.Singleton;
import br.com.contacorrente.factory.ClientFactory;
import br.com.contacorrente.factory.ClientLoginFactory;
import br.com.contacorrente.menu.MenuActivity;
import br.com.contacorrente.model.Status;
import br.com.contacorrente.model.Client;
import br.com.contacorrente.network.ClientService;
import br.com.contacorrente.network.ClientServiceImpl;

public class LoginPresenter implements LoginContract.ClientInteraction{

    private ClientService api;
    private LoginContract.View view;

    LoginPresenter(LoginContract.View view) {
        this.view = view;
        this.api = new ClientServiceImpl();
    }

    @Override
    public void login(String email, String password) {

        final Client client = ClientFactory.getClient(new ClientLoginFactory(email, password));

        view.showLogging();
        api.checkLogin(client.getEmail(), client.getPassword(), new ClientService.ClientServiceCallback<Status>() {
            @Override
            public void onLoaded(Status status) {
                if (status.isStatus()){
                    Singleton.client.setEmail(client.getEmail());

                    addClientLogged(client.getEmail(), client.getPassword());
                    view.loadActivity(MenuActivity.class);
                }else {
                    view.showToast("Usuário ou senha inválidos");
                }
                view.hideLogging();
            }
            @Override
            public void onError() {
                view.hideLogging();
                view.showToast("Não foi possível logar");
            }

            @Override
            public void notFoundError() {
                view.hideLogging();
                view.showToast("Cliente não encontrado!");
            }
        });
    }

    private void addClientLogged(String clientEmail, String clientPassword) {
        SharedPreferences.Editor editor = Singleton.sharedPreferences.edit();
        editor.putBoolean("clientLogged", true);
        editor.putString("clientEmail", clientEmail);
        editor.putString("clientPassword", clientPassword);
        editor.apply();
    }
}
