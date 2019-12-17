package br.com.contacorrente.menu.extract;

import android.os.Bundle;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.contacorrente.R;
import br.com.contacorrente.model.Transference;

public class ExtractActivity extends AppCompatActivity implements ExtractContract.View {

    private TransferenceAdapter mTransferenceAdapter;
    private ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extract);

        bind();
        bindToolbar();

        ExtractContract.UserInteractions presenter = new ExtractPresenter(this);
        presenter.loadUserExtract();
    }

    private void bind() {
        progressBar = findViewById(R.id.progressBar);

        mTransferenceAdapter = new TransferenceAdapter(new ArrayList<Transference>(0));

        RecyclerView recyclerViewFilmes = findViewById(R.id.transference_list);
        recyclerViewFilmes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewFilmes.setAdapter(mTransferenceAdapter);
        recyclerViewFilmes.setHasFixedSize(true);
    }

    private void bindToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void showExtract(List<Transference> list) {
        progressBar.setProgress(100);
        progressBar.setVisibility(View.GONE);
        mTransferenceAdapter.replaceData(list);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
