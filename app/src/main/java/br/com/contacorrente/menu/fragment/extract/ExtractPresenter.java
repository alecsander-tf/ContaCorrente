package br.com.contacorrente.menu.fragment.extract;

import java.util.ArrayList;
import java.util.List;

import br.com.contacorrente.Singleton;
import br.com.contacorrente.model.Transference;
import br.com.contacorrente.model.User;
import br.com.contacorrente.network.UserService;
import br.com.contacorrente.network.UserServiceImpl;

public class ExtractPresenter implements ExtractContract.UserInteractions {

    private List<Transference> transferenceList;
    private List<User> loadedUserList;

    private ExtractContract.View view;
    private UserService mApi;

    ExtractPresenter(ExtractContract.View view) {
        mApi = new UserServiceImpl();
        this.view = view;
        loadedUserList = new ArrayList<>();
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
     * Verifica o id que mandou/recebeu algum valor em relação ao usuário logado,
     * caso os detalhes deste id não esjam carregados, retorna o id para ser carregado
     * ou nulo se os detalhes já foram carregados.
     *
     * */
    private String verifyIdToBeLoaded(Transference t){

        String idToBeLoad;

        //Verifica qual id interagiu(mandou/recebeu algum valor) com a conta logada
        if (t.getId_from().equals(Singleton.user.getId())){
            idToBeLoad = t.getId_to();
        }else {
            idToBeLoad = t.getId_from();
        }

        //Verifica se os detalhes do id que interagiu já estão carregados
        for (User user : loadedUserList){
            if (user.getId().equals(idToBeLoad)){
                return "";
            }
        }

        return idToBeLoad;
    }

    @Override
    public void loadUserExtractDetails() {

        for (final Transference t : transferenceList) {
            String idToBeLoaded = verifyIdToBeLoaded(t);
            if (!idToBeLoaded.equals("")) {
                mApi.getUserById(Integer.parseInt(idToBeLoaded), new UserService.UserServiceCallback<User>() {
                    @Override
                    public void onLoaded(User user) {
                        t.setUserRelated(user);
                        loadedUserList.add(user);
                        if (transferenceList.get(transferenceList.size() - 1).getId().equals(t.getId())){
                            view.showExtract(transferenceList);
                        }
                    }

                    @Override
                    public void onError() {

                    }
                });
            }
        }
    }
}
