package br.com.contacorrente.login;

import android.content.SharedPreferences;

import br.com.contacorrente.Singleton;
import br.com.contacorrente.factory.UserFactory;
import br.com.contacorrente.factory.UserLoginFactory;
import br.com.contacorrente.menu.MenuActivity;
import br.com.contacorrente.model.Status;
import br.com.contacorrente.model.User;
import br.com.contacorrente.network.UserService;
import br.com.contacorrente.network.UserServiceImpl;

public class LoginPresenter implements LoginContract.UserInteraction{

    private UserService api;
    private LoginContract.View view;

    LoginPresenter(LoginContract.View view) {
        this.view = view;
        this.api = new UserServiceImpl();
    }

    @Override
    public void login(String email, String password) {

        final User user = UserFactory.getUser(new UserLoginFactory(email, password));

        view.showLogging();
        api.checkLogin(user.getEmail(), user.getPassword(), new UserService.UserServiceCallback<Status>() {
            @Override
            public void onLoaded(Status status) {
                if (status.isStatus()){
                    Singleton.user.setEmail(user.getEmail());

                    addUserLogged(user.getEmail(), user.getPassword());
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
        });
    }

    private void addUserLogged(String userEmail, String userPassword) {
        SharedPreferences.Editor editor = Singleton.sharedPreferences.edit();
        editor.putBoolean("userLogged", true);
        editor.putString("userEmail", userEmail);
        editor.putString("userPassword", userPassword);
        editor.apply();
    }
}
