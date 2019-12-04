package br.com.contacorrente.menu;

import br.com.contacorrente.Singleton;
import br.com.contacorrente.model.User;
import br.com.contacorrente.network.UserService;
import br.com.contacorrente.network.UserServiceImpl;

public class MenuPresenter implements MenuContract.UserInteractions {

    private UserService mApi;
    private MenuContract.View view;

    MenuPresenter(MenuContract.View view){
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
                view.showError("Não foi possível carregar os seus dados!");
            }
        });
    }
}
