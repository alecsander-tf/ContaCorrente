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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.contacorrente.R;
import br.com.contacorrente.Singleton;
import br.com.contacorrente.model.Transference;

public class ExtractActivity extends AppCompatActivity implements ExtractContract.View {

    private ExtractContract.UserInteractions presenter;

    private RadioGroup radioGroup;
    private RadioButton radioAll;
    private RadioButton radioWeek;
    private RadioButton radioMonth;

    private ExtractAdapter mExtractAdapter;
    private ProgressBar progressBar;
    private int checkedRadioButtonId;

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

        radioAll = findViewById(R.id.toggleBtnTodos);
        radioWeek = findViewById(R.id.toggleBtnSemana);
        radioMonth = findViewById(R.id.toggleBtnMes);

        progressBar = findViewById(R.id.progressBar);
        radioGroup = findViewById(R.id.radioGroupFilter);
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

    private void hideExtract(){
        progressBar.setVisibility(View.VISIBLE);
        findViewById(R.id.transference_list).setVisibility(View.GONE);
    }

    @Override
    public void showExtract(List<Transference> transferenceList) {
        progressBar.setVisibility(View.GONE);
        findViewById(R.id.transference_list).setVisibility(View.VISIBLE);
        mExtractAdapter.replaceData(transferenceList);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
