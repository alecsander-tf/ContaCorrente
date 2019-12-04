package br.com.contacorrente.menu.fragment.myAccount;

import br.com.contacorrente.Singleton;
import br.com.contacorrente.model.User;
import br.com.contacorrente.network.UserService;
import br.com.contacorrente.network.UserServiceImpl;

public class MyAccountPresenter implements MyAccountContract.UserInteractions {

    private MyAccountContract.View view;
    private UserService mRetrofit;

    MyAccountPresenter(MyAccountContract.View view) {
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

    @Override
    public void loadNewBalance() {
        mRetrofit.getUserByEmail(Singleton.user.getEmail(), new UserService.UserServiceCallback<User>() {
            @Override
            public void onLoaded(User user) {
                Singleton.user.setBalance(user.getBalance());
                view.showNewBalance();
            }

            @Override
            public void onError() {
                view.showToast("Não foi possível atualizar seu saldo!");
            }
        });
    }
}
