package br.com.contacorrente.menu.extract;

import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.contacorrente.Singleton;
import br.com.contacorrente.model.Transference;
import br.com.contacorrente.model.User;
import br.com.contacorrente.network.UserService;
import br.com.contacorrente.network.UserServiceImpl;
import br.com.contacorrente.util.Utility;

public class ExtractPresenter implements ExtractContract.UserInteractions {

    static private List<Transference> transferenceList;

    private int loadedTransferences = 0;
    static private boolean error;

    private ExtractContract.View view;
    private UserService mApi;

    ExtractPresenter(ExtractContract.View view) {
        mApi = new UserServiceImpl();
        this.view = view;
    }

    @Override
    public void loadUserExtractWeek() {
        mApi.getBankStatement(Integer.parseInt(Singleton.user.getId()), new UserService.UserServiceCallback<List<Transference>>() {
            @Override
            public void onLoaded(List<Transference> transferences) {

                transferenceList = transferences;
                List<Transference> newTransference = new ArrayList<>();

                for (Transference t: transferences) {

                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.DAY_OF_WEEK, cal.getActualMinimum(Calendar.DAY_OF_WEEK));
                    cal.getTime();

                    Date date1 = Utility.convertDate(t.getData().substring(0, t.getData().indexOf(" ")));
                    if (cal.getTime().compareTo(date1) == -1){
                        newTransference.add(t);
                    }
                }

                transferenceList = newTransference;

                loadUserExtractDetails();
            }

            @Override
            public void onError() {
                transferenceList = new ArrayList<>();
            }
        });
    }

    @Override
    public void loadUserExtractMonth() {
        mApi.getBankStatement(Integer.parseInt(Singleton.user.getId()), new UserService.UserServiceCallback<List<Transference>>() {
            @Override
            public void onLoaded(List<Transference> transferences) {

                transferenceList = transferences;
                List<Transference> newTransference = new ArrayList<>();

                for (Transference t: transferences) {

                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
                    cal.getTime();

                    Date date1 = Utility.convertDate(t.getData().substring(0, t.getData().indexOf(" ")));
                    if (cal.getTime().compareTo(date1) == -1){
                        newTransference.add(t);
                    }
                }

                transferenceList = newTransference;

                loadUserExtractDetails();
            }

            @Override
            public void onError() {
                transferenceList = new ArrayList<>();
            }
        });
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
        loadedTransferences = 0;

        if (transferenceList.isEmpty()){
            view.showExtract(new ArrayList<Transference>());
            view.noRecord();
            return;
        }

        for (final Transference t : transferenceList) {
            String idToBeLoaded = verifyIdToBeLoaded(t);
                mApi.getUserById(Integer.parseInt(idToBeLoaded), new UserService.UserServiceCallback<User>() {
                    @Override
                    public void onLoaded(User user) {
                        t.setUserRelated(user);
                        prepareExtract();
                        //view.addItemToExtract(t);
                    }
                    @Override
                    public void onError() {
                        error = true;
                    }
                });
        }
    }

    /**
     * Retorna a lista para a view somente depois de todas as transferências estiverem carregadas
     * */
    private void prepareExtract(){
        loadedTransferences++;
        if (error){
            view.showToast("Erro ao carregar extrato");
            return;
        }
        if (loadedTransferences == transferenceList.size()){
            view.showExtract(transferenceList);
        }
    }
}
