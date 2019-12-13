package br.com.contacorrente.menu.transference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.com.contacorrente.Singleton;
import br.com.contacorrente.model.Transference;
import br.com.contacorrente.model.User;
import br.com.contacorrente.network.UserService;
import br.com.contacorrente.network.UserServiceImpl;

public class TransferencePresenter implements TransferenceContract.UserInteraction {

    private TransferenceContract.View view;
    private UserServiceImpl mApi;

    private Transference transference;

    TransferencePresenter(TransferenceContract.View view) {
        mApi = new UserServiceImpl();
        this.view = view;
        this.transference = new Transference();
    }

    @Override
    public void sendTransference(String userToEmail, final String value) {

        mApi.getUserByEmail(userToEmail, new UserService.UserServiceCallback<User>() {
            @Override
            public void onLoaded(User user) {

                if (user == null){
                    view.showToast("Email de destino não encontrado...");
                    return;
                }

                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

                transference.setData(df.format(c));
                transference.setUserRelated(user);
                transference.setValue(value);
                transference.setId_from(Singleton.user.getId());
                transference.setId_to(user.getId());

                view.next(transference);
            }

            @Override
            public void onError() {
                view.showToast("Erro ao efetuar transferência");
            }
        });
    }
}