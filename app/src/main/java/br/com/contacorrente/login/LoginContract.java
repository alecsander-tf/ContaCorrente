package br.com.contacorrente.login;

public interface LoginContract {

    interface View{
        void showLogging();
        void hideLogging();
        void loadActivity(Class<?> intent);
        void showToast(String msg);
    }

    interface ClientInteraction{
        void login(String email, String password);
    }

}
