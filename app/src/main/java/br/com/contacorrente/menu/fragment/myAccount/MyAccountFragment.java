package br.com.contacorrente.menu.fragment.myAccount;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import br.com.contacorrente.R;
import br.com.contacorrente.Singleton;
import br.com.contacorrente.menu.MenuActivity;
import br.com.contacorrente.menu.ParentActivityContract;
import br.com.contacorrente.menu.extract.ExtractActivity;
import br.com.contacorrente.menu.transference.TransferenceActivity;
import br.com.contacorrente.util.Utility;

public class MyAccountFragment extends Fragment implements MyAccountContract.View {

    private ParentActivityContract parentActivityContract;

    private View view;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private TextView tvClientName;
    private TextView tvClientBalance;
    private Button btnExtract;
    private Button btnTransference;
    private Button btnLogout;

    private MyAccountContract.ClientInteractions presenter;

    public static MyAccountFragment newInstance() {
        return new MyAccountFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_account, container, false);

        bind();
        bindListener();

        presenter = new MyAccountPresenter(this);
        presenter.loadClientAccount(Singleton.client.getEmail());

        return view;
    }

    private void bind() {

        parentActivityContract = (MenuActivity) getActivity();

        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshMyAccount);
        tvClientName = view.findViewById(R.id.tvClientName);
        tvClientBalance = view.findViewById(R.id.tvClientBalance);
        btnExtract = getActivity().findViewById(R.id.btnExtract);
        btnTransference = getActivity().findViewById(R.id.btnTransference);
        btnLogout = getActivity().findViewById(R.id.btnLogout);
    }

    private void bindListener() {
        mSwipeRefreshLayout.setOnRefreshListener(() -> presenter.loadNewBalance());

        btnExtract.setOnClickListener(v -> parentActivityContract.changeActivity(ExtractActivity.class));
        btnTransference.setOnClickListener(v -> parentActivityContract.changeActivity(TransferenceActivity.class));
        btnLogout.setOnClickListener(v -> parentActivityContract.logout());
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showAccountDetails(String clientName, String clientBalance) {
        tvClientBalance.setText(Utility.currencyFormat(clientBalance));
        tvClientName.setText(clientName);
    }

    @Override
    public void showNewBalance() {
        tvClientBalance.setText(Utility.currencyFormat(Singleton.client.getBalance()));
        tvClientName.setText(Singleton.client.getName());
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
