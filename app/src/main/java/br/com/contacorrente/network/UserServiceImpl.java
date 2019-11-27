package br.com.contacorrente.network;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import br.com.contacorrente.model.Login;
import br.com.contacorrente.model.Transference;
import br.com.contacorrente.model.User;
import br.com.contacorrente.network.endpoint.RetrofitEndpoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserServiceImpl implements UserService{

    private RetrofitEndpoint mRetrofit;

    public UserServiceImpl() {
        this.mRetrofit = RetrofitClient.getClient().create(RetrofitEndpoint.class);
    }

    @Override
    public void getBankStatement(int userId, final UserServiceCallback<List<Transference>> callback) {
        Call<List<Transference>> call = mRetrofit.getBankStatement(userId);
        call.enqueue(new Callback<List<Transference>>() {
            @Override
            public void onResponse(Call<List<Transference>> call, Response<List<Transference>> response) {

                //Gson gson = new Gson();
                //Type type = new TypeToken<ArrayList<Transference>>() {
                //}.getType();

                //gson.fromJson(response.body().toString(), type);

                List<Transference> transferences = response.body();
                callback.onLoaded(transferences);
            }

            @Override
            public void onFailure(Call<List<Transference>> call, Throwable t) {
                callback.onError();
            }
        });
    }

    @Override
    public void checkLogin(String email, String password, final UserServiceCallback<Login> callback) {
        Call<Login> call = mRetrofit.checkLogin(email, password);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.code() == 200){
                    Login login = response.body();
                    callback.onLoaded(login);
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                callback.onError();
            }
        });
    }

    @Override
    public void getUserById(int userId, final UserServiceCallback<User> callback) {
        Call<User> callUser = mRetrofit.getUserById(userId);
        callUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200){
                    User user = response.body();
                    callback.onLoaded(user);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callback.onError();
            }
        });
    }

    @Override
    public void getUserByEmail(String userEmail, String password, final UserServiceCallback<User> callback) {
        Call<User> callUser = mRetrofit.getUserByEmail(userEmail);
        callUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200){
                    User user = response.body();
                    callback.onLoaded(user);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callback.onError();
            }
        });
    }
}
