package br.com.contacorrente.menu.extract.weekExtract;

import android.os.Bundle;

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
import java.util.List;

import br.com.contacorrente.R;
import br.com.contacorrente.menu.extract.ExtractAdapter;
import br.com.contacorrente.menu.extract.ExtractContract;
import br.com.contacorrente.model.Transference;

public class WeekExtractFragment extends Fragment implements ExtractContract.View {

    private ExtractAdapter mExtractAdapter;
    private ProgressBar progressBar;
    private View view;

    public WeekExtractFragment() {
    }

    public static WeekExtractFragment newInstance(String param1, String param2) {
        WeekExtractFragment fragment = new WeekExtractFragment();
        return fragment;
    }

    private void bind(){
        progressBar = view.findViewById(R.id.progressBar);
        mExtractAdapter = new ExtractAdapter(new ArrayList<Transference>(0));

        RecyclerView recyclerViewFilmes = view.findViewById(R.id.transference_list);
        recyclerViewFilmes.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerViewFilmes.setAdapter(mExtractAdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_week_extract, container, false);

        bind();

        return view;
    }

    @Override
    public void noRecord() {
        view.findViewById(R.id.tvNoRecord).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideExtract(){
        view.findViewById(R.id.transference_list).setVisibility(View.GONE);
    }

    @Override
    public void showExtract(){
        progressBar.setVisibility(View.GONE);
        view.findViewById(R.id.tvNoRecord).setVisibility(View.GONE);
        view.findViewById(R.id.transference_list).setVisibility(View.VISIBLE);
    }

    @Override
    public void updateExtract(List<Transference> transferenceList) {

        view.findViewById(R.id.transference_list).setVisibility(View.VISIBLE);
        mExtractAdapter.replaceData(transferenceList, Calendar.DAY_OF_WEEK);

        if (mExtractAdapter.getTransferenceList().isEmpty()){
            hideExtract();
            noRecord();
        }
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

}
