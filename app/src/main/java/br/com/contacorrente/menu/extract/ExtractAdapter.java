package br.com.contacorrente.menu.extract;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

        if (transference.getUserRelated() != null){

            // Se o valor saiu conta, o campo de texto fica em vermelho
            if (transference.getId_from().equals(Singleton.user.getId())){

                String transferenceValue = "- " + Utility.currencyFormat(transference.getValue());

                holder.tvUserAmount.setTextColor(Color.parseColor("#FF0000"));
                holder.tvUserAmount.setText(transferenceValue);
            }else {
                holder.tvUserAmount.setTextColor(Color.parseColor("#369B5E"));
                holder.tvUserAmount.setText(Utility.currencyFormat(transference.getValue()));
            }
            holder.tvTransferenceDate.setText(transference.getData());
            holder.tvUserTransference.setText(transference.getUserRelated().getName());
        }
    }

    private void filter(int filter){

        List<Transference> newTransference = new ArrayList<>();
        for (Transference t: transferenceList) {

            Calendar cal = Calendar.getInstance();
            cal.set(filter, cal.getActualMinimum(filter));
            cal.getTime();

            Date date1 = Utility.convertDate(t.getData());
            t.setData(Utility.parseDate(date1));
            if (cal.getTime().compareTo(date1) < 0){
                newTransference.add(t);
            }
        }

        transferenceList = newTransference;
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

    public void replaceData(List<Transference> transferenceList, int filter) {

        this.transferenceList = transferenceList;
        filter(filter);
        notifyDataSetChanged();
    }
}
