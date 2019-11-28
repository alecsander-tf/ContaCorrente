package br.com.contacorrente.menu.fragment.myAccount;

import br.com.contacorrente.model.User;

public interface MyAccountContract {

    interface View{
        void showToast(String msg);
        void showAccountDetails(User user);
    }

    interface UserInteractions{
        void loadUserAccount(String email);
    }

}
