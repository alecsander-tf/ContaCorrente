package br.com.contacorrente.network;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import br.com.contacorrente.model.Status;
import br.com.contacorrente.model.Transference;
import br.com.contacorrente.model.Client;
import br.com.contacorrente.network.endpoint.RetrofitEndpoint;
import br.com.contacorrente.network.exception.NotFoundException;
import br.com.contacorrente.network.model.MyCallback;
import br.com.contacorrente.network.model.StatusResponseError;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientServiceImpl implements ClientService {

    private final RetrofitEndpoint mRetrofit;

    public ClientServiceImpl() {
        this.mRetrofit = RetrofitClient.getClient().create(RetrofitEndpoint.class);
    }

    @Override
    public void transfer(Long clientIdSender, Long clientIdReceiver, double valueTransference, final ClientServiceCallback<Status> callback) {

        MultipartBody.Part partClientIdSender =
                MultipartBody.Part.createFormData("clientIdSender", clientIdSender.toString());

        MultipartBody.Part partClientIdReceiver =
                MultipartBody.Part.createFormData("clientIdReceiver", clientIdReceiver.toString());

        MultipartBody.Part value =
                MultipartBody.Part.createFormData("value", Double.toString(valueTransference));

        Call<Status> call = mRetrofit.transfer(partClientIdSender, partClientIdReceiver, value);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NotNull Call<Status> call, @NotNull Response<Status> response) {
                Status status = response.body();
                callback.onLoaded(status);
            }

            @Override
            public void onFailure(@NotNull Call<Status> call, @NotNull Throwable t) {
                callback.onError();
            }
        });
    }

    @Override
    public void getBankStatement(Long clientId, final ClientServiceCallback<List<Transference>> callback) {

        Call<List<Transference>> call = mRetrofit.getBankStatement(clientId);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NotNull Call<List<Transference>> call, @NotNull Response<List<Transference>> response) {
                if (!response.isSuccessful()) {
                    callback.onError();
                    return;
                }
                List<Transference> transferenceList = response.body();
                callback.onLoaded(transferenceList);
            }

            @Override
            public void onFailure(@NotNull Call<List<Transference>> call, @NotNull Throwable t) {
                callback.onError();
            }
        });
    }

    @Override
    public void checkLogin(String email, String password, final ClientServiceCallback<Status> callback) {

        // Cria os dados como form-data, para serem mandados no corpo da requisição e não como parte da URI
        MultipartBody.Part emailAux = MultipartBody.Part.createFormData("email", email);
        MultipartBody.Part passwordAux = MultipartBody.Part.createFormData("password", password);

        Call<Status> call = mRetrofit.checkLogin(emailAux, passwordAux);
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NotNull Call<Status> call, @NotNull Response<Status> response) {
                if (!response.isSuccessful()) {
                    callback.onError();
                    return;
                }
                if (response.code() == 200) {
                    Status status = response.body();
                    callback.onLoaded(status);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Status> call, @NotNull Throwable t) {
                callback.onError();
            }
        });
    }

    @Override
    public void getClientById(Long clientId, final ClientServiceCallback<Client> callback) {

        Call<Client> callClient = mRetrofit.getClientById(clientId);

        try {
            callClient.enqueue(new Callback<>() {
                @Override
                public void onResponse(@NotNull Call<Client> call, @NotNull Response<Client> response) {
                    if (!response.isSuccessful()) {
                        callback.onError();
                        return;
                    }
                    if (response.code() == 200) {
                        Client client = response.body();
                        callback.onLoaded(client);
                    }
                }

                @Override
                public void onFailure(@NotNull Call<Client> call, @NotNull Throwable t) {
                    callback.onError();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getClientByEmail(String clientEmail, final ClientServiceCallback<Client> callback) {

        Call<Client> callClient = mRetrofit.getClientByEmail(clientEmail);
        callClient.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NotNull Call<Client> call, @NotNull Response<Client> response) {
                if (!response.isSuccessful()) {
                    callback.onError();
                    return;
                }
                if (response.code() == 200) {
                    Client client = response.body();
                    callback.onLoaded(client);
                }
            }

            @Override
            public void onFailure(@NotNull Call<Client> call, @NotNull Throwable t) {
                if (t instanceof NotFoundException) {
                    callback.notFoundError();
                }
            }
        });
    }
}
