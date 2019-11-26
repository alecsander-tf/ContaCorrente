package br.com.contacorrente.login;

import android.content.Intent;

import br.com.contacorrente.model.User;

public interface LoginContract {

    interface View{
        void loadActivity(Class<?> intent);
        void showToast(String msg);
    }

    interface UserInteraction{
        void login(User user);
    }

}
