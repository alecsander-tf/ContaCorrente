package br.com.contacorrente.splashScreeen;

import android.content.Context;

import br.com.contacorrente.Singleton;
import br.com.contacorrente.login.LoginApplicationActivity;
import br.com.contacorrente.menu.MenuActivity;
import br.com.contacorrente.model.Status;
import br.com.contacorrente.network.ClientService;
import br.com.contacorrente.network.ClientServiceImpl;
import br.com.contacorrente.network.exception.NotFoundException;
import timber.log.Timber;

import static android.content.Context.MODE_PRIVATE;

class SplashScreenPresenter implements SplashScreenContract.Presenter {

    SplashScreenContract.View view;
    ClientService api;

    public SplashScreenPresenter(SplashScreenContract.View view) {
        this.view = view;
        this.api = new ClientServiceImpl();
    }

    @Override
    public void verifyClientLogged(Context context) {
        Singleton.sharedPreferences = context.getSharedPreferences("clientPreferences", MODE_PRIVATE);

        boolean clientLogged = Singleton.sharedPreferences.contains("clientLogged");

        if (clientLogged) {

            final String clientEmail = Singleton.sharedPreferences.getString("clientEmail", "");
            final String clientPassword = Singleton.sharedPreferences.getString("clientPassword", "");

            api.checkLogin(clientEmail, clientPassword, new ClientService.ClientServiceCallback<>() {
                @Override
                public void onLoaded(Status status) {
                    if (status.isStatus()) {
                        Singleton.client.setEmail(clientEmail);
                        view.loadActivity(MenuActivity.class);
                    } else {
                        view.loadActivity(LoginApplicationActivity.class);
                    }
                }

                @Override
                public void onError() {
                    view.loadActivity(LoginApplicationActivity.class);
                }

                @Override
                public void notFoundError() {
                    Timber.e(new NotFoundException("Cliente não encontrado!"));
                }
            });
        } else {
            view.loadActivity(LoginApplicationActivity.class);
        }
    }
}
