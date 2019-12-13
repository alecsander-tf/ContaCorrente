package br.com.contacorrente.menu.transference;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import br.com.contacorrente.Singleton;
import br.com.contacorrente.concludeTransference.ConcludeActivity;
import br.com.contacorrente.R;
import br.com.contacorrente.model.Transference;

public class TransferenceActivity extends AppCompatActivity implements TransferenceContract.View {

    private TransferenceContract.UserInteraction presenter;

    private EditText etUserTo;
    private EditText etValue;
    private Button btnSend;

    @Override
    public void onResume() {
        super.onResume();
        etUserTo.getText().clear();
        etValue.getText().clear();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        bind();
        bindListener();

        if (Singleton.test){
            etUserTo.setText("bruna.silva@evosystems.com.br");
            etValue.setText("11");
        }

        presenter = new TransferencePresenter(this);
    }

    private void bind() {
        btnSend = findViewById(R.id.btnTransference_Send);
        etUserTo = findViewById(R.id.etTransference_UserTo);
        etValue = findViewById(R.id.etTransference_Value);
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
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void next(Transference transference) {

        Intent intent = new Intent(this, ConcludeActivity.class);
        intent.putExtra("transference", transference);
        intent.putExtra("userRelated", transference.getUserRelated());

        startActivity(intent);
    }
}
