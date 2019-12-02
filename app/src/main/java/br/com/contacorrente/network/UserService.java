package br.com.contacorrente.network;

import java.util.List;

import br.com.contacorrente.model.Status;
import br.com.contacorrente.model.Transference;
import br.com.contacorrente.model.User;

public interface UserService  {

    interface UserServiceCallback<T>{
        void onLoaded(T user);
        void onError();
    }

    void transfer(int userFromId, int userToId, double value, UserServiceCallback<Status> callback);
    void getBankStatement(int userId, UserServiceCallback<List<Transference>> callback);
    void checkLogin(String email, String password, UserServiceCallback<Status> callback);
    void getUserById(int userId, UserServiceCallback<User> callback);
    void getUserByEmail(String email, UserServiceCallback<User> callback);

}
