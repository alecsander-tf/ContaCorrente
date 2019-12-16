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
import br.com.contacorrente.factory.UserFactory;
import br.com.contacorrente.factory.UserLoginFactory;

public class LoginApplicationActivity extends AppCompatActivity implements LoginContract.View {

    private Button btnLogin;
    private EditText etEmail;
    private EditText etPassword;

    private long mLastClickTime = 0;

    private LoginContract.UserInteraction presenter;

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

        etEmail.getText().clear();
        etPassword.getText().clear();
    }

    private void bindListener() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                presenter.login(UserFactory.getUser(new UserLoginFactory(email, password)));
            }
        });
    }

    private void bind() {

        presenter = new LoginPresenter(this);

        this.btnLogin = findViewById(R.id.btnLogin_Login);
        this.etEmail = findViewById(R.id.txtLogin_Email);
        this.etPassword = findViewById(R.id.txtLogin_Password);
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
