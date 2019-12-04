package br.com.contacorrente.menu.fragment.extract;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import br.com.contacorrente.R;

class TranferenceViewHolder extends RecyclerView.ViewHolder {

    TextView tvUserTransference;
    TextView tvUserAmount;

    TranferenceViewHolder(View itemView) {
        super(itemView);
        tvUserAmount = itemView.findViewById(R.id.tvUserAmount);
        tvUserTransference = itemView.findViewById(R.id.tvUserTransference);
    }
}
