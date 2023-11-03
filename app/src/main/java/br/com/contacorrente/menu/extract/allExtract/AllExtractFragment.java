package br.com.contacorrente.menu.extract.allExtract;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.contacorrente.R;
import br.com.contacorrente.menu.extract.ExtractAdapter;
import br.com.contacorrente.menu.extract.ExtractContract;
import br.com.contacorrente.model.Transference;
import br.com.contacorrente.util.Utility;

public class AllExtractFragment extends Fragment implements ExtractContract.View {

    private List<Transference> transferenceList;

    private ExtractAdapter mExtractAdapter;
    private ProgressBar progressBar;

    private View view;

    private void bind(){
        this.transferenceList = new ArrayList<>();
        progressBar = view.findViewById(R.id.progressBar);
        mExtractAdapter = new ExtractAdapter(new ArrayList<Transference>(0));

        RecyclerView recyclerViewFilmes = view.findViewById(R.id.transference_list);
        recyclerViewFilmes.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerViewFilmes.setAdapter(mExtractAdapter);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all_extract, container, false);

        bind();

        return view;
    }

    @Override
    public void noRecord() {
        view.findViewById(R.id.tvNoRecord).setVisibility(View.VISIBLE);
    }

    public void hideExtract(){
        view.findViewById(R.id.transference_list).setVisibility(View.GONE);
    }

    @Override
    public void showExtract(){
        progressBar.setVisibility(View.GONE);
        view.findViewById(R.id.tvNoRecord).setVisibility(View.GONE);
        view.findViewById(R.id.transference_list).setVisibility(View.VISIBLE);
    }

    private List<Transference> filterExtract(List<Transference> transferenceList) {

        List<Transference> newTransference = new ArrayList<>();
        for (Transference t: transferenceList) {

            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.ERA, cal.getActualMinimum(Calendar.ERA));
            cal.getTime();

            Date date1 = Utility.INSTANCE.convertDate(t.getData());
            t.setData(Utility.INSTANCE.parseDate(date1));
            if (cal.getTime().compareTo(date1) < 0){
                newTransference.add(t);
            }
        }

        return newTransference;
    }

    @Override
    public void updateExtract(List<Transference> transferenceList) {

        List<Transference> filterExtract = filterExtract(transferenceList);

        if (!this.transferenceList.equals(filterExtract)){
            this.transferenceList = filterExtract;

            mExtractAdapter.replaceData(this.transferenceList);
            showExtract();
        }

        if (mExtractAdapter.getTransferenceList().isEmpty()){
            hideExtract();
            progressBar.setVisibility(View.GONE);
            noRecord();
        }
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }
}
