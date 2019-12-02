package br.com.contacorrente.network;

import java.util.List;

import br.com.contacorrente.model.Status;
import br.com.contacorrente.model.Transference;
import br.com.contacorrente.model.User;
import br.com.contacorrente.network.endpoint.RetrofitEndpoint;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserServiceImpl implements UserService{

    private RetrofitEndpoint mRetrofit;

    public UserServiceImpl() {
        this.mRetrofit = RetrofitClient.getClient().create(RetrofitEndpoint.class);
    }

    @Override
    public void transfer(int userFromId, int userToId, double valueTransference, final UserServiceCallback<Status> callback) {

        MultipartBody.Part id_user_from =
                MultipartBody.Part.createFormData("id_user_from", Integer.toString(userFromId));

        MultipartBody.Part id_user_to =
                MultipartBody.Part.createFormData("id_user_to", Integer.toString(userToId));

        MultipartBody.Part value =
                MultipartBody.Part.createFormData("value", Double.toString(valueTransference));

        Call<Status> call = mRetrofit.transfer(id_user_from, id_user_to, value);
        call.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                Status status = response.body();
                callback.onLoaded(status);
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
                callback.onError();
            }
        });
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
    public void checkLogin(String email, String password, final UserServiceCallback<Status> callback) {

        // Cria os dados como form-data, para serem mandados no corpo da requisição e não como parte da URI
        MultipartBody.Part emailAux = MultipartBody.Part.createFormData("email", email);
        MultipartBody.Part passwordAux = MultipartBody.Part.createFormData("password", password);

        Call<Status> call = mRetrofit.checkLogin(emailAux, passwordAux);
        call.enqueue(new Callback<Status>() {
            @Override
            public void onResponse(Call<Status> call, Response<Status> response) {
                if (response.code() == 200){
                    Status status = response.body();
                    callback.onLoaded(status);
                }
            }

            @Override
            public void onFailure(Call<Status> call, Throwable t) {
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
