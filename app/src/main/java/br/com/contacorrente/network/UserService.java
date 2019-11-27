package br.com.contacorrente.network;

import java.util.List;

import br.com.contacorrente.model.Login;
import br.com.contacorrente.model.Transference;
import br.com.contacorrente.model.User;

public interface UserService  {

    interface UserServiceCallback<T>{
        void onLoaded(T user);
        void onError();
    }

    void getBankStatement(int userId, UserServiceCallback<List<Transference>> callback);
    void checkLogin(String email, String password, UserServiceCallback<Login> callback);
    void getUserById(int userId, UserServiceCallback<User> callback);
    void getUserByEmail(String email, String password, UserServiceCallback<User> callback);

}
