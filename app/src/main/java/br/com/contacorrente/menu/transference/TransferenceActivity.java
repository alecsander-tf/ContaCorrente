package br.com.contacorrente.menu.transference;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import br.com.contacorrente.concludeTransference.ConcludeActivity;
import br.com.contacorrente.R;
import br.com.contacorrente.model.Transference;

public class TransferenceActivity extends AppCompatActivity implements TransferenceContract.View {

    private TransferenceContract.UserInteraction presenter;

    private Toolbar toolbar;

    private EditText etUserTo;
    private EditText etValue;
    private Button btnSend;

    @Override
    public void onResume() {
        super.onResume();
        etUserTo.setFocusable(true);
        etUserTo.getText().clear();
        etValue.getText().clear();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transference);

        bind();
        bindToolbar();
        bindListener();

        presenter = new TransferencePresenter(this);
    }

    private void bindToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void bind() {
        toolbar = findViewById(R.id.toolbar);
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
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
