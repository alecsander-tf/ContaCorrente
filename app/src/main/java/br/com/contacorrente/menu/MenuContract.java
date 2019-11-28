package br.com.contacorrente.menu;

import br.com.contacorrente.model.User;

public interface MenuContract {

    interface View{
        void showAccountDetails();
    }

    interface UserInteractions{
        void loadUserAccount(String email);
    }
}
