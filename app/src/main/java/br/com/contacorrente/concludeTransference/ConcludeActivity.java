package br.com.contacorrente.concludeTransference;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import br.com.contacorrente.R;
import br.com.contacorrente.model.Transference;
import br.com.contacorrente.model.User;
import br.com.contacorrente.util.Format;

public class ConcludeActivity extends AppCompatActivity implements ConcludeContract.View {

    private Transference transference;
    private TextView date;
    TextView userToName;
    TextView value;
    Button btnConclude;

    ConcludeContract.UserInteraction presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conclude);

        transference = getIntent().getParcelableExtra("transference");
        transference.setUserRelated((User) getIntent().getParcelableExtra("userRelated"));

        bind();
        bindListener();
        loadTransference();
    }

    private void bind(){

        presenter = new ConcludePresenter(this);

        date = findViewById(R.id.tvDate);
        userToName = findViewById(R.id.tvUserName);
        value = findViewById(R.id.tvValue);
        btnConclude = findViewById(R.id.btnConcludeTransference);
    }

    private void bindListener(){
        btnConclude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.concludeTransference(transference);
            }
        });
    }

    private void loadTransference(){
        date.setText(transference.getData());
        userToName.setText(transference.getUserRelated().getName());
        value.setText(Format.currencyFormat(transference.getValue()));
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void finishTransference() {
        Toast.makeText(this, "Sucesso!", Toast.LENGTH_LONG).show();
        finish();
    }
}
