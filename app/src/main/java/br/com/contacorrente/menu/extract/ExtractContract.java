package br.com.contacorrente.menu.extract;

import br.com.contacorrente.model.Transference;

public interface ExtractContract {

    interface View {
        void showToast(String msg);
        void addItemToExtract(Transference transference);
    }

    interface UserInteractions {
        void loadUserExtract();
        void loadUserExtractDetails();
    }

}
