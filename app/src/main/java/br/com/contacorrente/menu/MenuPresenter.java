package br.com.contacorrente.menu;

import br.com.contacorrente.Singleton;
import br.com.contacorrente.model.User;
import br.com.contacorrente.network.UserService;
import br.com.contacorrente.network.UserServiceImpl;

public class MenuPresenter implements MenuContract.UserInteractions {

    private MenuContract.View view;
    private UserService mRetrofit;

    MenuPresenter(MenuContract.View view) {
        this.view = view;
        mRetrofit = new UserServiceImpl();
    }


    @Override
    public void loadUserAccount(String email) {
        mRetrofit.getUserByEmail(email, new UserService.UserServiceCallback<User>() {
            @Override
            public void onLoaded(User user) {
                Singleton.user = user;
                view.showAccountDetails(user);
            }

            @Override
            public void onError() {
                view.showToast("Usuário não carregado");
            }
        });
    }
}
