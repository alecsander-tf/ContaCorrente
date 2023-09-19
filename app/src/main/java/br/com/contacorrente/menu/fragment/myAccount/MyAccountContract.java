package br.com.contacorrente.menu.fragment.myAccount;

public interface MyAccountContract {

    interface View{
        void showToast(String msg);
        void showAccountDetails(String clientName, String clientBalance);
        void showNewBalance();
    }

    interface ClientInteractions{
        void loadClientAccount(String email);
        void loadNewBalance();
    }

}
