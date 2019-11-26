package br.com.contacorrente.network;

import br.com.contacorrente.model.User;

public interface UserService  {

    interface UserServiceCallback<T>{
        void onLoaded(T user);
        void onError();
    }

    void getUserById(int userId, UserServiceCallback<User> callback);
    void getUserByEmail(String userEmail, UserServiceCallback<User> callback);

}
