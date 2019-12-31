package br.com.contacorrente.menu.extract;

import java.util.List;

import br.com.contacorrente.model.Transference;

public interface ExtractContract {

    interface View {
        void updateExtract(List<Transference> transferenceList);
        void showToast(String msg);
        void noRecord();
        void hideExtract();
        void showExtract();
    }

    interface UserInteractions {
        void loadUserExtract();
        void loadUserExtractDetails();
        void updateExtract(View fragment);
    }

}
