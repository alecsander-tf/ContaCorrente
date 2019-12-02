package br.com.contacorrente.concludeTransference;

import br.com.contacorrente.model.Transference;

public interface ConcludeContract {

    interface View{
        void showToast(String msg);
        void finishTransference();
    }

    interface UserInteraction{
        void concludeTransference(Transference transference);
    }

}
