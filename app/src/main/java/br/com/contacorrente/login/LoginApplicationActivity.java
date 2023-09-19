package br.com.contacorrente.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.contacorrente.Singleton;
import br.com.contacorrente.R;

public class LoginApplicationActivity extends AppCompatActivity implements LoginContract.View {

    private Button btnLogin;
    private EditText etEmail;
    private EditText etPassword;

    private long mLastClickTime = 0;

    private LoginContract.ClientInteraction presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_application);

        bind();
        bindListener();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Singleton.getInstance();

        hideLogging();

        etEmail.getText().clear();
        etPassword.getText().clear();
    }

    private void bindListener() {

        btnLogin.setOnClickListener(v -> {

            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                return;
            }
            mLastClickTime = SystemClock.elapsedRealtime();

            presenter.login(etEmail.getText().toString(), etPassword.getText().toString());
        });
    }

    private void bind() {

        presenter = new LoginPresenter(this);

        this.btnLogin = findViewById(R.id.btnLogin_Login);
        this.etEmail = findViewById(R.id.txtLogin_Email);
        this.etPassword = findViewById(R.id.txtLogin_Password);
    }

    @Override
    public void showLogging() {
        findViewById(R.id.layoutLogging).setVisibility(View.VISIBLE);
        findViewById(R.id.layoutLogin).setVisibility(View.GONE);
    }

    @Override
    public void hideLogging() {
        findViewById(R.id.layoutLogging).setVisibility(View.GONE);
        findViewById(R.id.layoutLogin).setVisibility(View.VISIBLE);
    }

    @Override
    public void loadActivity(Class<?> args) {
        Intent intent = new Intent(this, args);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
