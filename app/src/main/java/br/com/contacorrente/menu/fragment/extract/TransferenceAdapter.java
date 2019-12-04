package br.com.contacorrente.menu.fragment.extract;

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
import br.com.contacorrente.util.Format;

public class TransferenceAdapter extends RecyclerView.Adapter<TranferenceViewHolder> {

    private List<Transference> transferenceList;

    TransferenceAdapter(List<Transference> transferenceList) {
        this.transferenceList = transferenceList;
    }

    @NotNull
    @Override
    public TranferenceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View noteView = inflater.inflate(R.layout.transference_item, parent, false);

        return new TranferenceViewHolder(noteView);
    }

    @Override
    public void onBindViewHolder(@NotNull TranferenceViewHolder holder, int position) {
        Transference transference = transferenceList.get(position);

        if (transference.getUserRelated() != null){

            // Se o valor saiu conta, o campo de texto fica em vermelho
            if (transference.getId_from().equals(Singleton.user.getId())){

                String transferenceValue = "- " + Format.currencyFormat(transference.getValue());

                holder.tvUserAmount.setTextColor(Color.parseColor("#FF0000"));
                holder.tvUserAmount.setText(transferenceValue);
            }else {
                holder.tvUserAmount.setTextColor(Color.parseColor("#369B5E"));
                holder.tvUserAmount.setText(Format.currencyFormat(transference.getValue()));
            }

            holder.tvUserTransference.setText(transference.getUserRelated().getName());
        }
    }

    @Override
    public int getItemCount() {
        if (transferenceList == null || transferenceList.size() == 0){
            return 0;
        }
        return transferenceList.size();
    }

    void replaceData(List<Transference> list) {
        transferenceList = list;
        notifyDataSetChanged();
    }
}
