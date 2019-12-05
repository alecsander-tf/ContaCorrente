package br.com.contacorrente.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import br.com.contacorrente.R;
import br.com.contacorrente.Singleton;
import br.com.contacorrente.menu.fragment.extract.ExtractFragment;
import br.com.contacorrente.menu.fragment.myAccount.MyAccountFragment;
import br.com.contacorrente.menu.fragment.transference.TransferenceFragment;

public class MenuActivity extends AppCompatActivity implements MenuContract.View, ParentActivityContract {

    private Toolbar toolbar;

    private TextView tvMenuDrawer_Name;
    private TextView tvMenuDrawer_Email;
    private CircularImageView circularImageView;

    private MenuContract.UserInteractions presenter;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        bind();
        loadNavigation();
        bindListener();

        presenter = new MenuPresenter(this);
        presenter.loadUserAccount(Singleton.user.getEmail());

        //Primeira Fragment que será carregada
        changeFragment(MyAccountFragment.newInstance(), "Minha Conta", 0);
    }

    private void bind(){

        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);

        //Elementos da NavigationDrawer
        circularImageView = navigationView.getHeaderView(0).findViewById(R.id.circularImageView);
        tvMenuDrawer_Name = navigationView.getHeaderView(0).findViewById(R.id.txtMenuDrawer_Name);
        tvMenuDrawer_Email = navigationView.getHeaderView(0).findViewById(R.id.txtMenuDrawer_Email);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        drawerLayout.openDrawer(Gravity.LEFT);

        return super.onOptionsItemSelected(item);
    }

    private void loadNavigation() {

        setSupportActionBar(toolbar);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (toolbar.getNavigationIcon() != null){
            toolbar.getNavigationIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        }

    }

    private void bindListener() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                switch(id){
                    case R.id.menu:
                        changeFragment(MyAccountFragment.newInstance(), item.getTitle().toString(), 0);
                        break;
                    case R.id.extract:
                        changeFragment(ExtractFragment.newInstance(), item.getTitle().toString(), 1);
                        break;
                    case R.id.transference:
                        changeFragment(TransferenceFragment.newInstance(), item.getTitle().toString(), 2);
                        break;
                    case R.id.logout:
                        logout();
                        return true;
                    default:
                        return true;
                }

                // Fecha o navigation drawer
                drawerLayout.closeDrawers();

                return true;
            }
        });
    }

    @Override
    public void showAccountDetails() {

        Picasso.get()
                .load(Singleton.user.getProfile())
                .fit()
                .centerCrop()
                .placeholder(R.drawable.ic_insert_photo)
                .into(circularImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        System.out.println("Sucesso");
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                });

        Log.d("ESPAÇO", " ");
        Picasso.get().setLoggingEnabled(true);

        tvMenuDrawer_Email.setText(Singleton.user.getEmail());
        tvMenuDrawer_Name.setText(Singleton.user.getName());
    }

    @Override
    public void showError(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * Muda a fragment a partir de um parâmetro, altera título da toolbar e da destaque a opção no menu drawer.
     * @param fragment Fragment que será iniciada
     */
    @Override
    public void changeFragment(Fragment fragment, String fragmentTitle, int menuDrawerItemIndex) {

        setTitle(fragmentTitle);
        navigationView.getMenu().getItem(menuDrawerItemIndex).setChecked(true);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
    }

    @Override
    public void logout() {
        Singleton.logout();
        finish();
    }
}
