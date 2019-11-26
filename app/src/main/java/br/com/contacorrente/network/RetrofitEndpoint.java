package br.com.contacorrente.network;

import br.com.contacorrente.model.User;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitEndpoint {

    @POST("./get-user")
    Call<User> getUserById(@Query("id_user") int id);

    @POST("./get-userById")
    Call<User> getUserByEmail(@Query("email") String email);


}
