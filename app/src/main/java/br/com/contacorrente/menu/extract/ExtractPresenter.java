package br.com.contacorrente.menu.extract;

import java.util.ArrayList;
import java.util.List;

import br.com.contacorrente.Singleton;
import br.com.contacorrente.model.Transference;
import br.com.contacorrente.model.User;
import br.com.contacorrente.network.UserService;
import br.com.contacorrente.network.UserServiceImpl;

public class ExtractPresenter implements ExtractContract.UserInteractions {

    static private List<Transference> transferenceList;

    private int loadedTransferences;
    static private boolean error;

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
            public void onLoaded(List<Transference> transferences) {
                transferenceList = transferences;
                loadUserExtractDetails();
            }

            @Override
            public void onError() {
                transferenceList = new ArrayList<>();
            }
        });
    }

    /**
     * Retorna o id que interagiu(mandou/recebeu algum valor) com a conta logada
     **/
    private String verifyIdToBeLoaded(Transference t){

        if (t.getId_from().equals(Singleton.user.getId())){
            return t.getId_to();
        }else {
            return t.getId_from();
        }
    }

    @Override
    public void loadUserExtractDetails() {

        for (final Transference t : transferenceList) {
            String idToBeLoaded = verifyIdToBeLoaded(t);
                mApi.getUserById(Integer.parseInt(idToBeLoaded), new UserService.UserServiceCallback<User>() {
                    @Override
                    public void onLoaded(User user) {
                        t.setUserRelated(user);
                        view.addItemToExtract(t);
                    }
                    @Override
                    public void onError() {
                        error = true;
                    }
                });
            }
    }
}
