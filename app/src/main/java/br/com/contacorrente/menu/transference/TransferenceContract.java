package br.com.contacorrente.menu.transference;

public interface TransferenceContract {

    interface View {
        void showToast(String msg);

        void next(String clientRelatedEmail, String value);

        void finishTransference();
    }

    interface ClientInteraction {
        void sendTransference(String clientToEmail, String value);

        void concludeTransference();
    }
}
