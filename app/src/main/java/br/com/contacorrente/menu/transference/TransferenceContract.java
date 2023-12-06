package br.com.contacorrente.menu.transference;

public interface TransferenceContract {

    interface View{
        void showToast(String msg);
        void next(String userRelatedEmail, String value);
        void finishTransference();
    }

    interface UserInteraction{
        void sendTransference(String userToEmail, String value);
        void concludeTransference(String idTo, String idFrom, String value);
    }
}
