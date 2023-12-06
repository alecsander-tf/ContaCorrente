package br.com.contacorrente.menu.fragment.myAccount;

public interface MyAccountContract {

    interface View{
        void showToast(String msg);
        void showAccountDetails(String userName, String userBalance);
        void showNewBalance();
    }

    interface UserInteractions{
        void loadUserAccount(String email);
        void loadNewBalance();
    }

}
