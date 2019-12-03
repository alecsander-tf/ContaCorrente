package br.com.contacorrente.menu.fragment.extract;

import android.os.Bundle;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.contacorrente.R;
import br.com.contacorrente.model.Transference;

public class ExtractFragment extends Fragment implements ExtractContract.View {

    private TransferenceAdapter mTransfereceAdapter;
    private ProgressBar progressBar;

    private View view;
    private ExtractContract.UserInteractions presenter;

    public static ExtractFragment newInstance() {
        return new ExtractFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_extract, container, false);

        presenter = new ExtractPresenter(this);

        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setProgress(50);
        RecyclerView recyclerViewFilmes = view.findViewById(R.id.transference_list);
        recyclerViewFilmes.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerViewFilmes.setAdapter(mTransfereceAdapter);
        recyclerViewFilmes.setHasFixedSize(true);

        presenter.loadUserExtract();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTransfereceAdapter = new TransferenceAdapter(new ArrayList<Transference>(0));
    }

    @Override
    public void showExtract(List<Transference> list) {
        mTransfereceAdapter.replaceData(list);
    }
}
