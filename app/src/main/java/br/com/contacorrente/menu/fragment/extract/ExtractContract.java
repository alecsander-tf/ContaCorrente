package br.com.contacorrente.menu.fragment.extract;

import java.util.List;

import br.com.contacorrente.model.DetailedTransference;

public interface ExtractContract {

    interface View {
        void showExtract(List<DetailedTransference> detailedTransferences);
    }

    interface UserInteractions {
        void loadUserExtract();
    }

}
