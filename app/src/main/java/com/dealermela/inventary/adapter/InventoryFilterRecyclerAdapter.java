package com.dealermela.inventary.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.dealermela.R;
import com.dealermela.inventary.activity.InventoryFilterAct;
import com.dealermela.inventary.model.InventoryFilterItem;

import java.util.List;

public class InventoryFilterRecyclerAdapter extends RecyclerView.Adapter<InventoryFilterRecyclerAdapter.ViewHolder> {

    private final Activity activity;
    private final List<InventoryFilterItem.OptionDatum> inventoryItemArrayList;

    public InventoryFilterRecyclerAdapter(Activity activity, List<InventoryFilterItem.OptionDatum> inventoryitemarraylist){
        super();
        this.activity=activity;
        this.inventoryItemArrayList=inventoryitemarraylist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.act_filter_item_recycler, viewGroup, false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.radioFilter.setText(inventoryItemArrayList.get(i).getLabel());
        if (inventoryItemArrayList.get(i).isSelected()) {
            viewHolder.radioFilter.setChecked(true);
        } else {
            viewHolder.radioFilter.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return inventoryItemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        final RadioButton radioFilter;

        ViewHolder(View itemView) {
            super(itemView);
            radioFilter = itemView.findViewById(R.id.radioFilter);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (inventoryItemArrayList.get(getAdapterPosition()).isSelected())
            {
                inventoryItemArrayList.get(getAdapterPosition()).setSelected(false);
                ((InventoryFilterAct) activity).updateFilterData(getAdapterPosition(), false);
                notifyItemChanged(getAdapterPosition());
            } else {
                inventoryItemArrayList.get(getAdapterPosition()).setSelected(true);
                ((InventoryFilterAct) activity).updateFilterData(getAdapterPosition(), true);
                notifyItemChanged(getAdapterPosition());
            }
//            ((InventoryFilterAct)activity).updatefiltercount(getAdapterPosition());
//             notifyItemChanged(getAdapterPosition());
            ((InventoryFilterAct) activity).countFilter();
//            ((InventoryFilterAct) activity).bindSelectFilter();
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
