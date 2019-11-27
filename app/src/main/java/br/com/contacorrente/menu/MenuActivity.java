package br.com.contacorrente.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import br.com.contacorrente.R;
import br.com.contacorrente.Singleton;
import br.com.contacorrente.login.LoginContract;
import br.com.contacorrente.model.User;

public class MenuActivity extends AppCompatActivity implements MenuContract.View{

    TextView tvUserName;
    TextView tvUserBalance;
    Button btnExtract;
    Button btnTransference;

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    MenuContract.UserInteractions presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dl = (DrawerLayout)findViewById(R.id.activity_menu);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.account:
                        Toast.makeText(MenuActivity.this, "My Account",Toast.LENGTH_SHORT).show();break;
                    case R.id.settings:
                        Toast.makeText(MenuActivity.this, "Settings",Toast.LENGTH_SHORT).show();break;
                    default:
                        return true;
                }


                return true;

            }
        });

        bind();
    }

    private void bind(){

        presenter = new MenuPresenter(this);

        tvUserName = findViewById(R.id.tvUserName);
        tvUserBalance = findViewById(R.id.tvUserBalance);
        btnExtract = findViewById(R.id.btnExtract);
        btnTransference = findViewById(R.id.btnTransference);

        presenter.loadUserAccount(Singleton.user.getEmail());

    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadActivity(Class<?> activity) {
        startActivity(new Intent(this, activity));
    }

    @Override
    public void showAccountDetails(User user) {
        tvUserBalance.setText(user.getBalance());
        tvUserName.setText(user.getName());
    }
}
