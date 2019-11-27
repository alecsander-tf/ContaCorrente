package br.com.contacorrente.network.endpoint;

import java.util.List;

import br.com.contacorrente.model.Login;
import br.com.contacorrente.model.Transference;
import br.com.contacorrente.model.User;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface RetrofitEndpoint {

    @Multipart
    @POST("./get-user")
    Call<User> getUserById(@Part MultipartBody.Part userId);

    @Multipart
    @POST("./get-user")
    Call<User> getUserByEmail(@Part MultipartBody.Part email);

    @Multipart
    @POST("./check-login")
    Call<Login> checkLogin(@Part MultipartBody.Part email, @Part MultipartBody.Part password);

    @POST("./get-bank-statement")
    Call<List<Transference>> getBankStatement(@Part MultipartBody.Part userId);
}
