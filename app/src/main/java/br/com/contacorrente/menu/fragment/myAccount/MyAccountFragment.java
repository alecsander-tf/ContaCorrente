package br.com.contacorrente.menu.fragment.myAccount;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import br.com.contacorrente.R;
import br.com.contacorrente.Singleton;
import br.com.contacorrente.model.User;

public class MyAccountFragment extends Fragment implements MyAccountContract.View {

    private View view;

    private TextView tvUserName;
    private TextView tvUserBalance;
    private Button btnExtract;
    private Button btnTransference;

    MyAccountContract.UserInteractions presenter;

    private void bind(){
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
        presenter = new MyAccountPresenter(this);
        presenter.loadUserAccount(Singleton.user.getEmail());

        return view;
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showAccountDetails(User user) {
        tvUserBalance.setText(user.getBalance());
        tvUserName.setText(user.getName());
    }
}
