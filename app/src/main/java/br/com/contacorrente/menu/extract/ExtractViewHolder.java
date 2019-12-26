package br.com.contacorrente.menu.extract;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import br.com.contacorrente.R;

class ExtractViewHolder extends RecyclerView.ViewHolder {

    TextView tvUserTransference;
    TextView tvUserAmount;

    ExtractViewHolder(View itemView) {
        super(itemView);
        tvUserAmount = itemView.findViewById(R.id.tvUserAmount);
        tvUserTransference = itemView.findViewById(R.id.tvUserTransference);
    }
}