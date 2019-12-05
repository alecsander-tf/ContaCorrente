package br.com.contacorrente.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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
import br.com.contacorrente.menu.fragment.myAccount.ParentActivityContract;
import br.com.contacorrente.menu.fragment.transference.TransferenceFragment;

public class MenuActivity extends AppCompatActivity implements MenuContract.View, ParentActivityContract {

    private Fragment fragment = null;
    private Class fragmentClass;

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

        changeFragment();

        presenter = new MenuPresenter(this);
        presenter.loadUserAccount(Singleton.user.getEmail());
    }

    private void bind(){

        //Primeira Fragment que será carregada
        fragmentClass = MyAccountFragment.class;

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

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (toolbar.getNavigationIcon() != null){
            toolbar.getNavigationIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        }

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


    }

    private void bindListener() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id){
                    case R.id.menu:
                        fragmentClass = MyAccountFragment.class;
                        break;
                    case R.id.extract:
                        fragmentClass = ExtractFragment.class;
                        break;
                    case R.id.transference:
                        fragmentClass = TransferenceFragment.class;
                        break;
                    case R.id.logout:
                        logout();
                        return true;
                    default:
                        return true;
                }

                changeFragment();
                // Muda o título da action bar
                setTitle(item.getTitle());
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
     * Muda a fragment a partir de um parâmetro.
     * Este método é chamado a partir de uma classe controladora
     * @param fragment Fragment que será iniciada
     */
    @Override
    public void changeFragment(Fragment fragment) {
        this.fragmentClass = fragment.getClass();
        if (fragment instanceof ExtractFragment){
            setTitle("Extrato");
            navigationView.getMenu().getItem(1).setChecked(true);
        }else if (fragment instanceof TransferenceFragment){
            setTitle("Transferência");
            navigationView.getMenu().getItem(2).setChecked(true);
        }
        changeFragment();
    }

    /**
     * Muda a fragment a partir de uma variável da própria classe
     * */
    private void changeFragment(){

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameLayout, fragment).commit();
    }

    @Override
    public void logout() {
        Singleton.logout();
        finish();
    }
}
