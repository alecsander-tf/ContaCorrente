package br.com.contacorrente.menu.extract;

import java.util.Date;
import java.util.List;

import br.com.contacorrente.model.Transference;

public interface ExtractContract {

    interface View {
        void showExtract(List<Transference> transferenceList);
        void showToast(String msg);
        void addItemToExtract(Transference transference);
    }

    interface UserInteractions {
        void loadUserExtractMonth();
        void loadUserExtract();
        void loadUserExtractDetails();
        void loadUserExtractWeek();
    }

}
