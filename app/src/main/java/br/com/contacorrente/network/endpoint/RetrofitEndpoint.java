package br.com.contacorrente.network.endpoint;

import java.util.List;

import br.com.contacorrente.model.Status;
import br.com.contacorrente.model.Transference;
import br.com.contacorrente.model.Client;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface RetrofitEndpoint {

    @Headers("Content-Type: application/json")
    @GET("./client/get-client")
    Call<Client> getClientById(@Query("id") Long id);

    @Headers("Content-Type: application/json")
    @GET("./client/get-client")
    Call<Client> getClientByEmail(@Query("email") String email);

    @Multipart
    @POST("./client/check-login")
    Call<Status> checkLogin(@Part MultipartBody.Part email, @Part MultipartBody.Part password);

    @GET("./transference/get-bank-statement")
    Call<List<Transference>> getBankStatement(@Query("clientId") Long clientId);

    @Multipart
    @POST("./transference/transfer")
    Call<Status> transfer(
            @Part MultipartBody.Part clientIdSender,
            @Part MultipartBody.Part clientIdReceiver,
            @Part MultipartBody.Part value
    );


}
