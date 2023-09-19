package br.com.contacorrente.splashScreeen;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import br.com.contacorrente.R;
import br.com.contacorrente.Singleton;
import br.com.contacorrente.jetpack.login.LoginActivity;

public class SplashScreen extends AppCompatActivity implements SplashScreenContract.View {

    SplashScreenContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Singleton.getInstance();

        presenter = new SplashScreenPresenter(this);

        presenter.verifyClientLogged(this);
    }

    @Override
    public void loadActivity(Class<?> args) {

        verifyDarkMode();

        startActivity(new Intent(this, args));
        finish();
    }

    private void verifyDarkMode() {
        if (Singleton.sharedPreferences.contains("clientLogged")){
            if (Singleton.sharedPreferences.getBoolean("darkMode", false)){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }
    }
}
