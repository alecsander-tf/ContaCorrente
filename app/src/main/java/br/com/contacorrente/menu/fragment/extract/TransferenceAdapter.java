package br.com.contacorrente.menu.fragment.extract;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.contacorrente.R;
import br.com.contacorrente.model.Transference;

public class TransferenceAdapter extends RecyclerView.Adapter<TranferenceViewHolder> {

    private List<Transference> transferenceList;

    public TransferenceAdapter(List<Transference> transferenceList) {
        this.transferenceList = transferenceList;
    }

    @Override
    public TranferenceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View noteView = inflater.inflate(R.layout.transference_item, parent, false);

        return new TranferenceViewHolder(noteView);
    }

    @Override
    public void onBindViewHolder(@NonNull TranferenceViewHolder holder, int position) {
        Transference transference = transferenceList.get(position);

        if (transference.getUserRelated() != null){
            holder.tvUserTransference.setText(transference.getUserRelated().getName());
            holder.tvUserAmount.setText(transference.getValue());
        }
    }

    @Override
    public int getItemCount() {
        return transferenceList.size();
    }

    public void replaceData(List<Transference> list) {
        transferenceList = list;
        notifyDataSetChanged();
    }
}
