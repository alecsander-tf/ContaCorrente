package br.com.contacorrente.menu.fragment.extract;

import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.contacorrente.R;
import br.com.contacorrente.model.DetailedTransference;

public class ExtractFragment extends Fragment implements ExtractContract.View {

    private TransferenceAdapter mTransfereceAdapter;

    private View view;
    private ExtractContract.UserInteractions presenter;

    public static ExtractFragment newInstance() {
        return new ExtractFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_extract, container, false);

        presenter = new ExtractPresenter(this);

        RecyclerView recyclerViewFilmes = view.findViewById(R.id.transference_list);

        recyclerViewFilmes.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        recyclerViewFilmes.setAdapter(mTransfereceAdapter);

        recyclerViewFilmes.setHasFixedSize(true);

        return view;
    }

    @Override
    public void showExtract(List<DetailedTransference> detailedTransferences) {

    }
}
