package br.com.contacorrente.menu.fragment.extract;

import java.util.List;

import br.com.contacorrente.Singleton;
import br.com.contacorrente.model.Transference;
import br.com.contacorrente.network.UserService;
import br.com.contacorrente.network.UserServiceImpl;

public class ExtractPresenter implements ExtractContract.UserInteractions {

    private ExtractContract.View view;
    private UserService mApi;

    ExtractPresenter(ExtractContract.View view) {
        mApi = new UserServiceImpl();
        this.view = view;
    }

    @Override
    public void loadUserExtract() {
        mApi.getBankStatement(Integer.parseInt(Singleton.user.getId()), new UserService.UserServiceCallback<List<Transference>>() {
            @Override
            public void onLoaded(List<Transference> user) {

            }

            @Override
            public void onError() {

            }
        });
    }
}
