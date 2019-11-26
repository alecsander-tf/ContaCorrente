package br.com.contacorrente.network;

import br.com.contacorrente.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserServiceImpl implements UserService{

    RetrofitEndpoint mRetrofit;

    public UserServiceImpl() {
        this.mRetrofit = RetrofitClient.getClient().create(RetrofitEndpoint.class);
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
    public void getUserByEmail(String userEmail, final UserServiceCallback<User> callback) {
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
