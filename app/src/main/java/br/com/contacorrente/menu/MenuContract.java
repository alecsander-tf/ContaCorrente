package br.com.contacorrente.menu;

import br.com.contacorrente.model.User;

public interface MenuContract {

    interface View{
        void showToast(String msg);
        void loadActivity(Class<?> activity);
        void showAccountDetails(User user);
    }

    interface UserInteractions{
        void loadUserAccount(String email);
    }

}
