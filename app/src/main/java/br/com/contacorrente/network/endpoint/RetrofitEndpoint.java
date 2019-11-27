package br.com.contacorrente.network.endpoint;

import java.util.List;

import br.com.contacorrente.model.Login;
import br.com.contacorrente.model.Transference;
import br.com.contacorrente.model.User;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitEndpoint {

    @POST("./get-user")
    Call<User> getUserById(@Query("id_user")int userId);

    @POST("./get-user")
    Call<User> getUserByEmail(@Query("email")String email);

    @POST("./check-login")
    Call<Login> checkLogin(@Query("email")String email, @Query("password")String password);

    @POST("./get-bank-statement")
    Call<List<Transference>> getBankStatement(@Query("id_user")int userId);
}
