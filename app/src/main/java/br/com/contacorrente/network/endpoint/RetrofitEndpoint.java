package br.com.contacorrente.network.endpoint;

import java.util.List;

import br.com.contacorrente.model.Status;
import br.com.contacorrente.model.Transference;
import br.com.contacorrente.model.User;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface RetrofitEndpoint {

    @GET("./get-user")
    Call<User> getUserById(@Query("id") Long id);

    @GET("./get-user")
    Call<User> getUserByEmail(@Query("email") String email);

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
