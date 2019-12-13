package br.com.contacorrente.menu.extract;

import android.os.Bundle;

import android.os.PersistableBundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.contacorrente.R;
import br.com.contacorrente.model.Transference;

public class ExtractActivity extends AppCompatActivity implements ExtractContract.View {

    private TransferenceAdapter mTransfereceAdapter;
    private ProgressBar progressBar;

    private ExtractContract.UserInteractions presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        presenter = new ExtractPresenter(this);

        progressBar = findViewById(R.id.progressBar);

        RecyclerView recyclerViewFilmes = findViewById(R.id.transference_list);
        recyclerViewFilmes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewFilmes.setAdapter(mTransfereceAdapter);
        recyclerViewFilmes.setHasFixedSize(true);

        presenter.loadUserExtract();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTransfereceAdapter = new TransferenceAdapter(new ArrayList<Transference>(0));
    }

    @Override
    public void showExtract(List<Transference> list) {
        progressBar.setProgress(100);
        progressBar.setVisibility(View.GONE);
        mTransfereceAdapter.replaceData(list);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
