package br.com.contacorrente.menu.fragment.extract;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.com.contacorrente.R;

public class TranferenceViewHolder extends RecyclerView.ViewHolder {

    TextView tvUserTransference;
    TextView tvUserAmount;

    public TranferenceViewHolder(@NonNull View itemView) {
        super(itemView);
        tvUserAmount = itemView.findViewById(R.id.tvUserAmount);
        tvUserTransference = itemView.findViewById(R.id.tvUserTransference);
    }
}
