package br.com.contacorrente.menu.extract;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import br.com.contacorrente.R;
import br.com.contacorrente.Singleton;
import br.com.contacorrente.model.Transference;
import br.com.contacorrente.util.Utility;

public class ExtractAdapter extends RecyclerView.Adapter<ExtractViewHolder> {

    private List<Transference> transferenceList;

    public ExtractAdapter(List<Transference> transferenceList) {
        this.transferenceList = transferenceList;
    }

    @NotNull
    @Override
    public ExtractViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View noteView = inflater.inflate(R.layout.transference_item, parent, false);

        return new ExtractViewHolder(noteView);
    }

    @Override
    public void onBindViewHolder(@NotNull ExtractViewHolder holder, int position) {
        Transference transference = transferenceList.get(position);

        if (transference.getClientRelated() != null){

            // Se o valor saiu conta, o campo de texto fica em vermelho
            if (transference.getClientIdSender().equals(Singleton.client.getClientId())){

                String transferenceValue = "- " + Utility.currencyFormat(transference.getValue());

                holder.tvClientAmount.setTextColor(Color.parseColor("#FF0000"));
                holder.tvClientAmount.setText(transferenceValue);
            }else {
                holder.tvClientAmount.setTextColor(Color.parseColor("#369B5E"));
                holder.tvClientAmount.setText(Utility.currencyFormat(transference.getValue()));
            }
            holder.tvTransferenceDate.setText(transference.getData());
            holder.tvClientTransference.setText(transference.getClientRelated().getName());
        }
    }

    @Override
    public int getItemCount() {
        if (transferenceList == null || transferenceList.size() == 0){
            return 0;
        }
        return transferenceList.size();
    }

    public List<Transference> getTransferenceList() {
        return transferenceList;
    }

    public void replaceData(List<Transference> transferenceList) {

        this.transferenceList = transferenceList;
        notifyDataSetChanged();
    }
}
