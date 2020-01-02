package br.com.contacorrente.menu.transference;

import br.com.contacorrente.model.Transference;

public interface TransferenceContract {

    interface View{
        void showToast(String msg);
        void next(Transference transference);
        void finishTransference();
    }

    interface UserInteraction{
        void sendTransference(String userToEmail, String value);
        void concludeTransference(Transference transference);
    }
}
