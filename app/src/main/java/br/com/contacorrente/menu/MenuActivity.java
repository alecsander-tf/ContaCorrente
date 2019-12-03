package br.com.contacorrente.menu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import br.com.contacorrente.R;
import br.com.contacorrente.Singleton;
import br.com.contacorrente.login.LoginApplicationActivity;
import br.com.contacorrente.menu.fragment.extract.ExtractFragment;
import br.com.contacorrente.menu.fragment.myAccount.MyAccountFragment;
import br.com.contacorrente.menu.fragment.myAccount.ParentActivityContract;
import br.com.contacorrente.menu.fragment.transference.TransferenceFragment;

public class MenuActivity extends AppCompatActivity implements MenuContract.View, ParentActivityContract {

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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        drawerLayout.openDrawer(Gravity.LEFT);

        return super.onOptionsItemSelected(item);
    }

    private void loadNavigation() {
        //final Drawable upArrow = getResources().getDrawable(R.drawable.ic_menu);

        setTitle("Minha Conta");
        setSupportActionBar(toolbar);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
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

        Picasso.get()
                .load(Singleton.user.getProfile())
                .fit().centerCrop()
                .placeholder(R.drawable.ic_insert_photo)
                .into(circularImageView);

        tvMenuDrawer_Email.setText(Singleton.user.getEmail());
        tvMenuDrawer_Name.setText(Singleton.user.getName());
    }

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

    @Override
    public void logout() {
        Singleton.logout();
        finish();
    }
}
