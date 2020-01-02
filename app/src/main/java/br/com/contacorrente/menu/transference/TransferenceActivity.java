package br.com.contacorrente.menu.transference;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;

import br.com.contacorrente.R;
import br.com.contacorrente.model.Transference;

public class TransferenceActivity extends AppCompatActivity implements TransferenceContract.View {

    final Context context = this;
    private Transference transference;

    private TransferenceContract.UserInteraction presenter;

    private Toolbar toolbar;

    private EditText etUserTo;
    private EditText etValue;
    private Button btnSend;

    //Dialog
    private Dialog dialog;
    private TextView txtConfirmDialogEmail;
    private TextView txtConfirmDialogValue;
    private MaterialButton btnConfirm;
    private MaterialButton btnCancel;

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

        bindDialog();
        bindDialogListener();

        presenter = new TransferencePresenter(this);
    }

    private void bindDialogListener() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.concludeTransference(transference);
            }
        });
    }

    private void bindDialog() {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.custom_confirm_dialog);

        txtConfirmDialogEmail = dialog.findViewById(R.id.confirmDialogEmail);

        txtConfirmDialogValue = dialog.findViewById(R.id.confirmDialogValue);

        btnConfirm = dialog.findViewById(R.id.confirmDialogBtnConfirm);
        btnCancel = dialog.findViewById(R.id.confirmDialogBtnCancel);
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

        this.transference = transference;

        txtConfirmDialogEmail.setText(transference.getUserRelated().getEmail());
        txtConfirmDialogValue.setText(String.format("R$ %s", transference.getValue()));

        dialog.show();
    }

    @Override
    public void finishTransference() {
        showToast("Sucesso!");
        finish();
    }
}
