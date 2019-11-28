package br.com.contacorrente.menu;

import br.com.contacorrente.Singleton;
import br.com.contacorrente.model.User;
import br.com.contacorrente.network.UserService;
import br.com.contacorrente.network.UserServiceImpl;

public class MenuPresenter implements MenuContract.UserInteractions {

    UserService mApi;
    MenuContract.View view;

    public MenuPresenter(MenuContract.View view){
        this.view = view;
        mApi = new UserServiceImpl();
    }

    @Override
    public void loadUserAccount(String email) {
        mApi.getUserByEmail(email, new UserService.UserServiceCallback<User>() {
            @Override
            public void onLoaded(User user) {
                Singleton.user = user;
                view.showAccountDetails();
            }

            @Override
            public void onError() {

            }
        });
    }
}
