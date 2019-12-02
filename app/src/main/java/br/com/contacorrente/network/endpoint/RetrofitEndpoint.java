package br.com.contacorrente.network.endpoint;

import java.util.List;

import br.com.contacorrente.model.Status;
import br.com.contacorrente.model.Transference;
import br.com.contacorrente.model.User;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetrofitEndpoint {

    @Multipart
    @POST("./get-user")
    Call<User> getUserById(@Part MultipartBody.Part id_user);

    @Multipart
    @POST("./get-user")
    Call<User> getUserByEmail(@Part MultipartBody.Part email);

    @Multipart
    @POST("./check-login")
    Call<Status> checkLogin(@Part MultipartBody.Part email, @Part MultipartBody.Part password);

    @Multipart
    @POST("./get-bank-statement")
    Call<List<Transference>> getBankStatement(@Part MultipartBody.Part id_user);

    @Multipart
    @POST("./transfer")
    Call<Status> transfer(
            @Part MultipartBody.Part id_user_from,
            @Part MultipartBody.Part id_user_to,
            @Part MultipartBody.Part value);


}
