package br.com.contacorrente.menu.extract;

import java.util.Date;

import br.com.contacorrente.model.Transference;

public interface ExtractContract {

    interface View {
        void showToast(String msg);
        void addItemToExtract(Transference transference);
    }

    interface UserInteractions {
        void loadUserExtract(Date date);
        void loadUserExtract();
        void loadUserExtractDetails();
    }

}
