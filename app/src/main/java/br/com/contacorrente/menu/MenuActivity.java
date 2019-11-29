package br.com.contacorrente.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.mikhaellopez.circularimageview.CircularImageView;

import br.com.contacorrente.R;
import br.com.contacorrente.Singleton;
import br.com.contacorrente.login.LoginApplicationActivity;
import br.com.contacorrente.menu.fragment.extract.ExtractFragment;
import br.com.contacorrente.menu.fragment.myAccount.MyAccountFragment;

public class MenuActivity extends AppCompatActivity implements MenuContract.View {

    Fragment fragment = null;
    Class fragmentClass;

    Toolbar toolbar;

    TextView tvMenuDrawer_Name;
    TextView tvMenuDrawer_Email;
    CircularImageView circularImageView;

    MenuContract.UserInteractions presenter;

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

        //First fragment to be called
        fragmentClass = MyAccountFragment.class;

        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);

        //NavigationDrawer elements
        circularImageView = navigationView.getHeaderView(0).findViewById(R.id.circularImageView);
        tvMenuDrawer_Name = navigationView.getHeaderView(0).findViewById(R.id.txtMenuDrawer_Name);
        tvMenuDrawer_Email = navigationView.getHeaderView(0).findViewById(R.id.txtMenuDrawer_Email);
    }

    private void loadNavigation() {
        setSupportActionBar(toolbar);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

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

    private void bindListener() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.menu:
                        fragmentClass = MyAccountFragment.class;
                        break;
                    case R.id.extract:
                        fragmentClass = ExtractFragment.class;
                        break;
                    case R.id.transference:
                        break;
                    case R.id.logout:
                        Singleton.logout();
                        startActivity(new Intent(getApplicationContext(), LoginApplicationActivity.class));
                        break;
                    default:
                        return true;
                }

                changeFragment();
                // Highlight the selected item has been done by NavigationView
                //item.setChecked(true);
                // Set action bar title
                setTitle(item.getTitle());
                // Close the navigation drawer
                drawerLayout.closeDrawers();

                return true;
            }
        });
    }

    @Override
    public void showAccountDetails() {
        tvMenuDrawer_Email.setText(Singleton.user.getEmail());
        tvMenuDrawer_Name.setText(Singleton.user.getName());
    }
}
