package br.com.contacorrente.menu.extract;

import java.util.ArrayList;
import java.util.List;

import br.com.contacorrente.Singleton;
import br.com.contacorrente.model.Transference;
import br.com.contacorrente.model.Client;
import br.com.contacorrente.network.ClientService;
import br.com.contacorrente.network.ClientServiceImpl;
import br.com.contacorrente.network.exception.NotFoundException;
import timber.log.Timber;

public class ExtractPresenter implements ExtractContract.ClientInteractions {

    static private List<Transference> transferenceList;
    private final ExtractContract.View view;

    private int loadedTransferenceList = 0;
    static private boolean error;

    private final ClientService mApi;

    ExtractPresenter(ExtractContract.View view) {
        mApi = new ClientServiceImpl();
        this.view = view;
    }

    @Override
    public void updateExtract(ExtractContract.View fragment) {
        //fragment.showExtract();
        fragment.updateExtract(transferenceList);
    }

    @Override
    public void loadClientExtract() {
        mApi.getBankStatement(Singleton.client.getClientId(), new ClientService.ClientServiceCallback<>() {
            @Override
            public void onLoaded(List<Transference> transferences) {
                transferenceList = transferences;
                loadClientExtractDetails();
            }

            @Override
            public void onError() {
                transferenceList = new ArrayList<>();
            }

            @Override
            public void notFoundError() {
                Timber.e(new NotFoundException("Cliente não encontrado!"));
            }
        });
    }

    /**
     * Retorna o id que interagiu(mandou/recebeu algum valor) com a conta logada
     **/
    private Long verifyIdToBeLoaded(Transference t) {

        if (t.getClientIdSender().equals(Singleton.client.getClientId())) {
            return t.getClientIdReceiver();
        } else {
            return t.getClientIdSender();
        }
    }

    @Override
    public void loadClientExtractDetails() {
        loadedTransferenceList = 0;

        for (final Transference t : transferenceList) {
            Long idToBeLoaded = verifyIdToBeLoaded(t);
            mApi.getClientById(idToBeLoaded, new ClientService.ClientServiceCallback<>() {
                @Override
                public void onLoaded(Client client) {
                    t.setClientRelated(client);
                    prepareExtract();
                    //view.addItemToExtract(t);
                }

                @Override
                public void onError() {
                    error = true;
                }

                @Override
                public void notFoundError() {
                    error = true;
                    Timber.e(new NotFoundException("Cliente não encontrado!"));
                }
            });
        }
    }

    /**
     * Retorna a lista para a view somente depois de todas as transferências estiverem carregadas
     */
    private void prepareExtract() {
        loadedTransferenceList++;
        if (error) {
            view.showToast("Erro ao carregar extrato");
            return;
        }
        if (loadedTransferenceList == transferenceList.size()) {
            //view.showExtract();
            view.updateExtract(transferenceList);
        }
    }
}
