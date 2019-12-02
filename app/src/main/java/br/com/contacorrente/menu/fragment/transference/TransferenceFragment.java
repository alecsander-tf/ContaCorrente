package br.com.contacorrente.menu.fragment.transference;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import br.com.contacorrente.Singleton;
import br.com.contacorrente.concludeTransference.ConcludeActivity;
import br.com.contacorrente.R;
import br.com.contacorrente.model.Transference;

public class TransferenceFragment extends Fragment implements TransferenceContract.View{

    private View view;

    private TransferenceContract.UserInteraction presenter;

    private EditText etUserTo;
    private EditText etValue;
    private Button btnSend;

    public static Fragment newInstance() {
        return new TransferenceFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_transference, container, false);

        bind();
        bindListener();

        if (Singleton.test){
            etUserTo.setText("bruna.silva@evosystems.com.br");
            etValue.setText("11");
        }

        presenter = new TransferencePresenter(this);

        return view;
    }

    private void bind() {
        btnSend = view.findViewById(R.id.btnTransference_Send);
        etUserTo = view.findViewById(R.id.etTransference_UserTo);
        etValue = view.findViewById(R.id.etTransference_Value);
    }

    private void bindListener(){
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.sendTransference(etUserTo.getText().toString(), etValue.getText().toString());
            }
        });
    }



    @Override
    public void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void next(Transference transference) {

        Intent intent = new Intent(getContext(), ConcludeActivity.class);
        intent.putExtra("transference", transference);
        intent.putExtra("userRelated", transference.getUserRelated());

        startActivity(intent);
    }
}
