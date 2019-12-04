package br.com.contacorrente.menu;

public interface MenuContract {

    interface View{
        void showAccountDetails();
        void showError(String msg);
    }

    interface UserInteractions{
        void loadUserAccount(String email);
    }
}
