package br.com.contacorrente.splashScreeen;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;

import br.com.contacorrente.Singleton;

import br.com.contacorrente.login.LoginApplicationActivity;
import br.com.contacorrente.menu.MenuActivity;
import br.com.contacorrente.model.Status;
import br.com.contacorrente.network.UserService;
import br.com.contacorrente.network.UserServiceImpl;

class SplashScreenPresenter implements SplashScreenContract.Presenter {

    SplashScreenContract.View view;
    UserService api;

    public SplashScreenPresenter(SplashScreenContract.View view) {
        this.view = view;
        this.api = new UserServiceImpl();
    }

    @Override
    public void verifyUserLogged(Context context) {
        Singleton.sharedPreferences = context.getSharedPreferences("userPreferences", MODE_PRIVATE);

        if (Singleton.sharedPreferences.contains("userLogged")) {

            final String userEmail = Singleton.sharedPreferences.getString("userEmail", "");
            final String userPassword = Singleton.sharedPreferences.getString("userPassword", "");

            api.checkLogin(userEmail, userPassword, new UserService.UserServiceCallback<>() {
                @Override
                public void onLoaded(Status status) {
                    if (status.getStatus()) {
                        Singleton.user.setEmail(userEmail);
                        view.loadActivity(MenuActivity.class);
                    } else {
                        view.loadActivity(LoginApplicationActivity.class);
                    }
                }

                @Override
                public void onError() {
                    view.loadActivity(LoginApplicationActivity.class);
                }
            });
        } else {
            view.loadActivity(LoginApplicationActivity.class);
        }
    }
}
