package br.com.contacorrente.login;

import br.com.contacorrente.menu.MenuActivity;
import br.com.contacorrente.model.User;
import br.com.contacorrente.network.UserService;
import br.com.contacorrente.network.UserServiceImpl;

public class LoginPresenter implements LoginContract.UserInteraction{

    private UserService api;
    private LoginContract.View view;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
        this.api = new UserServiceImpl();
    }

    @Override
    public void login(User user) {
        api.getUserByEmail(user.getEmail(), user.getPassword(), new UserService.UserServiceCallback<User>() {
            @Override
            public void onLoaded(User user) {
                view.loadActivity(MenuActivity.class);
            }

            @Override
            public void onError() {
                view.showToast("Não foi possível logar");
            }
        });
    }
}
