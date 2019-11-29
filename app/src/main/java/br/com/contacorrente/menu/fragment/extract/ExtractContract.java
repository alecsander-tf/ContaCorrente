package br.com.contacorrente.menu.fragment.extract;

import java.util.List;

import br.com.contacorrente.model.Transference;

public interface ExtractContract {

    interface View {
        void showExtract(List<Transference> transferenceList);

    }

    interface UserInteractions {
        void loadUserExtract();
        void loadUserExtractDetails();
    }

}
