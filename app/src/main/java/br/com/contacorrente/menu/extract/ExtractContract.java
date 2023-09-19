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

    interface ClientInteractions {
        void loadClientExtract();
        void loadClientExtractDetails();
        void updateExtract(View fragment);
    }

}
