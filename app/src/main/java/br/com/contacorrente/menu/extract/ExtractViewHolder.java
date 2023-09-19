package br.com.contacorrente.menu.extract;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import br.com.contacorrente.R;

class ExtractViewHolder extends RecyclerView.ViewHolder {

    TextView tvClientTransference;
    TextView tvClientAmount;
    TextView tvTransferenceDate;

    ExtractViewHolder(View itemView) {
        super(itemView);
        tvTransferenceDate = itemView.findViewById(R.id.tvTransferenceDate);
        tvClientAmount = itemView.findViewById(R.id.tvClientAmount);
        tvClientTransference = itemView.findViewById(R.id.tvClientTransference);
    }
}
