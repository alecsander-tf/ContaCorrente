package br.com.contacorrente.login;

import br.com.contacorrente.Singleton;
import br.com.contacorrente.menu.MenuActivity;
import br.com.contacorrente.model.Status;
import br.com.contacorrente.model.User;
import br.com.contacorrente.network.UserService;
import br.com.contacorrente.network.UserServiceImpl;

public class LoginPresenter implements LoginContract.UserInteraction{

    //private Singleton singleton = Singleton.getInstance();
    private UserService api;
    private LoginContract.View view;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
        this.api = new UserServiceImpl();
    }

    @Override
    public void login(final User user) {
        api.checkLogin(user.getEmail(), user.getPassword(), new UserService.UserServiceCallback<Status>() {
            @Override
            public void onLoaded(Status status) {
                if (status.isStatus()){
                    Singleton.user.setEmail(user.getEmail());
                    view.loadActivity(MenuActivity.class);
                }else {
                    view.showToast("Usuário ou senha inválidos");
                }
            }

            @Override
            public void onError() {
                view.showToast("Não foi possível logar");
            }
        });
    }
}
