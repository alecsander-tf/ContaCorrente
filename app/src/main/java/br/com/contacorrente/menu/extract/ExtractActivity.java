package br.com.contacorrente.menu.extract;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import br.com.contacorrente.R;
import br.com.contacorrente.menu.extract.allExtract.AllExtractFragment;
import br.com.contacorrente.menu.extract.monthExtract.MonthExtractFragment;
import br.com.contacorrente.menu.extract.weekExtract.WeekExtractFragment;

public class ExtractActivity extends AppCompatActivity {

    private ExtractContract.View view;
    private ExtractPresenter presenter;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private AllExtractFragment allExtractFragment;
    private WeekExtractFragment weekExtractFragment;
    private MonthExtractFragment monthExtractFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extract);

        bindToolbar();
        bind();
        bindTabLayout();
        bindListener();

        presenter = new ExtractPresenter(view);
        presenter.loadClientExtract();
    }

    private void bindToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void bind(){
        allExtractFragment = new AllExtractFragment();
        weekExtractFragment = new WeekExtractFragment();
        monthExtractFragment = new MonthExtractFragment();

        view = allExtractFragment;
    }

    private void bindTabLayout() {
        TabsAdapter adapter = new TabsAdapter(getSupportFragmentManager());

        adapter.add(allExtractFragment, "Todos");
        adapter.add(weekExtractFragment, "Esta semana");
        adapter.add(monthExtractFragment, "Este mês");

        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void bindListener() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                switch (tab.getPosition()){
                    case 0:
                        view = allExtractFragment;
                        presenter.updateExtract(allExtractFragment);
                        break;
                    case 1:
                        view = weekExtractFragment;
                        presenter.updateExtract(weekExtractFragment);
                        break;
                    case 2:
                        view = monthExtractFragment;
                        presenter.updateExtract(monthExtractFragment);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    class TabsAdapter extends FragmentPagerAdapter {

        private List<Fragment> listFragments = new ArrayList<>();
        private List<String> listFragmentsTitle =  new ArrayList<>();

        TabsAdapter(FragmentManager fm) {
            super(fm);
        }

        void add(Fragment frag, String title){
            this.listFragments.add(frag);
            this.listFragmentsTitle.add(title);
        }

        @NotNull
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
