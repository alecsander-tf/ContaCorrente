package br.com.contacorrente.menu.transference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.com.contacorrente.Singleton;
import br.com.contacorrente.model.Status;
import br.com.contacorrente.model.Transference;
import br.com.contacorrente.model.Client;
import br.com.contacorrente.network.ClientService;
import br.com.contacorrente.network.ClientServiceImpl;
import br.com.contacorrente.network.exception.NotFoundException;
import timber.log.Timber;

public class TransferencePresenter implements TransferenceContract.ClientInteraction {

    private TransferenceContract.View view;
    private ClientServiceImpl mApi;

    private Transference transference;

    TransferencePresenter(TransferenceContract.View view) {
        mApi = new ClientServiceImpl();
        this.view = view;
        this.transference = new Transference();
    }

    @Override
    public void sendTransference(String clientToEmail, final String value) {

        if (clientToEmail.equals(Singleton.client.getEmail())) {
            view.showToast("Não é possível transferir valores para a própria conta!");
            return;
        }

        mApi.getClientByEmail(clientToEmail, new ClientService.ClientServiceCallback<>() {
            @Override
            public void onLoaded(Client client) {

                if (client == null) {
                    view.showToast("Email de destino não encontrado...");
                    return;
                }

                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

                transference.setData(df.format(c));
                transference.setClientRelated(client);
                transference.setValue(value);
                transference.setClientIdSender(Singleton.client.getClientId());
                transference.setClientIdReceiver(client.getClientId());

                view.next(client.getEmail(), value);
            }

            @Override
            public void onError() {
                view.showToast("Erro ao efetuar transferência");
            }

            @Override
            public void notFoundError() {
                Timber.e(new NotFoundException("Cliente não encontrado!"));
                view.showToast("Cliente não encontrado!");
            }
        });
    }

    @Override
    public void concludeTransference() {

        mApi.transfer(transference.getClientIdSender(), transference.getClientIdReceiver(), Double.parseDouble(transference.getValue()),
                new ClientService.ClientServiceCallback<>() {
                    @Override
                    public void onLoaded(Status status) {
                        if (status.isStatus()) {
                            view.finishTransference();
                        } else {
                            view.showToast(status.getError());
                        }
                    }

                    @Override
                    public void onError() {
                        view.showToast("Erro ao concluir transferência");
                    }

                    @Override
                    public void notFoundError() {
                        view.showToast("Cliente não encontrado!");
                    }
                }
        );
    }
}
