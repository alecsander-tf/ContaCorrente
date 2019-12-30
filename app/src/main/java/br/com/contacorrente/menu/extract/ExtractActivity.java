package br.com.contacorrente.menu.extract;

import android.os.Bundle;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import br.com.contacorrente.R;
import br.com.contacorrente.Singleton;
import br.com.contacorrente.menu.extract.allExtract.AllExtractFragment;
import br.com.contacorrente.menu.extract.mouthExtract.WeekExtractFragment;
import br.com.contacorrente.model.Transference;

public class ExtractActivity extends AppCompatActivity implements ExtractContract.View {

    private ExtractContract.UserInteractions presenter;

    private ExtractAdapter mExtractAdapter;
    private ProgressBar progressBar;
    private int checkedRadioButtonId;
    private RadioGroup radioGroup;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extract);

        Singleton.getInstance();
        Singleton.user.setEmail("alecsander.fernandes@evosystems.com.br");
        Singleton.user.setId("3");

        bind();
        bindListener();
        bindToolbar();

        checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();

        presenter = new ExtractPresenter(this);
        presenter.loadUserExtract();

        TabsAdapter adapter = new TabsAdapter( getSupportFragmentManager() );
        adapter.add( new AllExtractFragment() , "Todos");
        adapter.add( new WeekExtractFragment() , "Esta semana");

        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void bindListener() {

    }

    public void onToggle(View view) {

        if (checkedRadioButtonId == view.getId()){
            return;
        }

        checkedRadioButtonId = view.getId();

        if (view instanceof RadioButton){

            hideExtract();

            switch (view.getId()){
                case R.id.toggleBtnTodos:
                    presenter.loadUserExtract();
                    break;
                case R.id.toggleBtnSemana:
                    presenter.loadUserExtractWeek();
                    break;
                case R.id.toggleBtnMes:
                    presenter.loadUserExtractMonth();
                    break;

            }
        }
    }

    private void bind() {

        progressBar = findViewById(R.id.progressBar);
        mExtractAdapter = new ExtractAdapter(new ArrayList<Transference>(0));

        RecyclerView recyclerViewFilmes = findViewById(R.id.transference_list);
        recyclerViewFilmes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewFilmes.setAdapter(mExtractAdapter);

    }

    private void bindToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void addItemToExtract(Transference transference){
        if (progressBar.getVisibility() == View.VISIBLE){
            mExtractAdapter.newList();
        }
        progressBar.setVisibility(View.GONE);
        mExtractAdapter.addItem(transference);
    }

    @Override
    public void noRecord() {
        findViewById(R.id.tvNoRecord).setVisibility(View.VISIBLE);
    }

    private void hideExtract(){
        progressBar.setVisibility(View.VISIBLE);
        findViewById(R.id.transference_list).setVisibility(View.GONE);
        findViewById(R.id.tvNoRecord).setVisibility(View.GONE);
    }

    @Override
    public void showExtract(List<Transference> transferenceList) {
        findViewById(R.id.tvNoRecord).setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        findViewById(R.id.transference_list).setVisibility(View.VISIBLE);
        mExtractAdapter.replaceData(transferenceList);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    class TabsAdapter extends FragmentPagerAdapter {

        private List<Fragment> listFragments = new ArrayList<>();
        private List<String> listFragmentsTitle =  new ArrayList<>();

        public TabsAdapter(FragmentManager fm) {
            super(fm);
        }

        public void add(Fragment frag, String title){
            this.listFragments.add(frag);
            this.listFragmentsTitle.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return listFragments.get(position);
        }

        @Override
        public int getCount() {
            return listFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position){
            return listFragmentsTitle.get(position);
        }
    }

}
