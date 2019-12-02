package br.com.contacorrente.menu.fragment.myAccount;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import br.com.contacorrente.R;
import br.com.contacorrente.Singleton;
import br.com.contacorrente.model.User;
import br.com.contacorrente.util.Format;

public class MyAccountFragment extends Fragment implements MyAccountContract.View {

    private View view;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private TextView tvUserName;
    private TextView tvUserBalance;
    private Button btnExtract;
    private Button btnTransference;

    MyAccountContract.UserInteractions presenter;

    private void bind(){
        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshMyAccount);
        tvUserName = view.findViewById(R.id.tvUserName);
        tvUserBalance = view.findViewById(R.id.tvUserBalance);
        btnExtract = view.findViewById(R.id.btnExtract);
        btnTransference = view.findViewById(R.id.btnTransference);
    }

    public static MyAccountFragment newInstance() {
        return new MyAccountFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu, container, false);

        bind();
        bindListener();

        presenter = new MyAccountPresenter(this);
        presenter.loadUserAccount(Singleton.user.getEmail());

        return view;
    }

    private void bindListener() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadNewBalance();
            }
        });
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAccountDetails(User user) {
        tvUserBalance.setText( Format.currencyFormat(user.getBalance()));
        tvUserName.setText(user.getName());
    }

    @Override
    public void showNewBalance() {
        tvUserBalance.setText(Singleton.user.getBalance());
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
