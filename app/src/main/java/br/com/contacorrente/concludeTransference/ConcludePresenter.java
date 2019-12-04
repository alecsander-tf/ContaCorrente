package br.com.contacorrente.concludeTransference;

import br.com.contacorrente.model.Status;
import br.com.contacorrente.model.Transference;
import br.com.contacorrente.network.UserService;
import br.com.contacorrente.network.UserServiceImpl;

public class ConcludePresenter implements ConcludeContract.UserInteraction {

    private UserService mApi;
    private ConcludeContract.View view;

    ConcludePresenter(ConcludeContract.View view) {
        this.view = view;
        mApi = new UserServiceImpl();
    }

    @Override
    public void concludeTransference(Transference transference) {

        String id_to = transference.getId_to();
        String id_from = transference.getId_from();
        String value = transference.getValue();

        mApi.transfer(Integer.parseInt(id_from), Integer.parseInt(id_to), Double.parseDouble(value),
                new UserService.UserServiceCallback<Status>() {
                    @Override
                    public void onLoaded(Status status) {
                        if (status.isStatus()){
                            view.finishTransference();
                        }else {
                            view.showToast(status.getError());
                        }
                    }
                    @Override
                    public void onError() {
                        view.showToast("Erro ao concluir transferência");
                    }
                });
    }
}
