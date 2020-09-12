package com.dealermela.listing_and_detail.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.dealermela.R;
import com.dealermela.listing_and_detail.activity.FilterAct;
import com.dealermela.listing_and_detail.model.FilterItem;
import com.dealermela.util.AppLogger;

import java.util.List;

import static com.dealermela.listing_and_detail.activity.FilterAct.filterCurrentPosition;
import static com.dealermela.listing_and_detail.activity.ListAct.filterSelectItems;

public class FilterRecyclerAdapter extends RecyclerView.Adapter<FilterRecyclerAdapter.ViewHolder> {

    private final Activity activity;
    private final List<FilterItem.OptionDatum> itemArrayList;

    public FilterRecyclerAdapter(Activity activity, List<FilterItem.OptionDatum> itemArrayList) {
        super();
        this.activity = activity;
        this.itemArrayList = itemArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.act_filter_item_recycler, viewGroup, false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {

        holder.radioFilter.setText(itemArrayList.get(i).getLabel());
        if (itemArrayList.get(i).isSelected()) {
            holder.radioFilter.setChecked(true);
        } else {
            holder.radioFilter.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
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

            if (itemArrayList.get(getAdapterPosition()).isSelected()) {
                AppLogger.e("if title position", "------------" + filterCurrentPosition);
                AppLogger.e("if current position", "------------" + getAdapterPosition());
                itemArrayList.get(getAdapterPosition()).setSelected(false);
                notifyItemChanged(getAdapterPosition());
                ((FilterAct) activity).updateFilterData(getAdapterPosition(), false);
//                filterSelectItems.get(filterCurrentPosition).getOptionData().get(getAdapterPosition()).setSelected(true);

            } else {
                AppLogger.e("else title position", "------------" + filterCurrentPosition);
                AppLogger.e("else current position", "------------" + getAdapterPosition());
                itemArrayList.get(getAdapterPosition()).setSelected(true);
                notifyItemChanged(getAdapterPosition());
                ((FilterAct) activity).updateFilterData(getAdapterPosition(), true);
//                filterSelectItems.get(filterCurrentPosition).getOptionData().get(getAdapterPosition()).setSelected(false);

            }
            ((FilterAct) activity).countFilter();
//            ((FilterAct) activity).bindSelectFilter();
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
