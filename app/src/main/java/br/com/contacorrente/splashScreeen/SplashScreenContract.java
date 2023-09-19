package br.com.contacorrente.splashScreeen;

import android.content.Context;

public interface SplashScreenContract {

    interface View{
        void loadActivity(Class<?> args);
    }

    interface Presenter{
        void verifyClientLogged(Context context);
    }
}
