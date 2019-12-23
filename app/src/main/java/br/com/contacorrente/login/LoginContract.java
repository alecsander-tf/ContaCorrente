package br.com.contacorrente.login;

import br.com.contacorrente.model.User;

public interface LoginContract {

    interface View{
        void showLogging();
        void hideLogging();
        void loadActivity(Class<?> intent);
        void showToast(String msg);
    }

    interface UserInteraction{
        void login(User user);
    }

}
