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
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

        // Cria os dados como form-data, para serem mandados no corpo da requisição e não como parte da URI
        MultipartBody.Part id_user = MultipartBody.Part.createFormData("id_user", Integer.toString(userId));

        Call<List<Transference>> call = mRetrofit.getBankStatement(id_user);
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

        // Cria os dados como form-data, para serem mandados no corpo da requisição e não como parte da URI
        MultipartBody.Part emailAux = MultipartBody.Part.createFormData("email", email);
        MultipartBody.Part passwordAux = MultipartBody.Part.createFormData("password", password);

        Call<Login> call = mRetrofit.checkLogin(emailAux, passwordAux);
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

        // Cria os dados como form-data, para serem mandados no corpo da requisição e não como parte da URI
        MultipartBody.Part id_user = MultipartBody.Part.createFormData("id_user", Integer.toString(userId));


        Call<User> callUser = mRetrofit.getUserById(id_user);

        try{
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
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getUserByEmail(String userEmail, final UserServiceCallback<User> callback) {

        // Cria os dados como form-data, para serem mandados no corpo da requisição e não como parte da URI
        MultipartBody.Part email = MultipartBody.Part.createFormData("email", userEmail);

        Call<User> callUser = mRetrofit.getUserByEmail(email);
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
