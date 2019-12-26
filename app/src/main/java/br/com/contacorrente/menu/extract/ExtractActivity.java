package br.com.contacorrente.menu.extract;

import android.os.Bundle;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import br.com.contacorrente.R;
import br.com.contacorrente.Singleton;
import br.com.contacorrente.model.Transference;

public class ExtractActivity extends AppCompatActivity implements ExtractContract.View {

    private ExtractContract.UserInteractions presenter;

    private ExtractAdapter mExtractAdapter;
    private ProgressBar progressBar;

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

        presenter = new ExtractPresenter(this);
        presenter.loadUserExtract();
    }

    private void bindListener() {
    }

    public void onToggle(View view) {
        ((RadioGroup)view.getParent()).check(view.getId());

        if (view instanceof RadioButton){

            switch (view.getId()){
                case R.id.toggleBtnTodos:

                    break;
                case R.id.toggleBtnSemana:
                    presenter.loadUserExtract(new java.util.Date());
                    break;
                case R.id.toggleBtnMes:

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

    public void addItemToExtract(Transference transference){
        progressBar.setProgress(100);
        progressBar.setVisibility(View.GONE);
        mExtractAdapter.addItem(transference);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
