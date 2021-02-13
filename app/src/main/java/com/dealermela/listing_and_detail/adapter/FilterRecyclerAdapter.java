package com.dealermela.listing_and_detail.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
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
import static com.dealermela.listing_and_detail.adapter.FilterTitleListAdapter.tvinvcount;

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
//        //add this condition here bcz checking counting instance update set at right place or not
//        if (filterSelectItems.get(filterCurrentPosition).getFiltercount() == 0) {
//            tvinvcount.setVisibility(View.GONE);
//        } else {
//            tvinvcount.setVisibility(View.VISIBLE);
//            tvinvcount.setText(String.valueOf(filterSelectItems.get(filterCurrentPosition).getFiltercount()));
//        }
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
//                FilterTitleListAdapter.tvinvcount.setVisibility(View.VISIBLE);
//                FilterTitleListAdapter.tvinvcount.setText(String.valueOf(filterSelectItems.get(filterCurrentPosition).getFiltercount()));
            } else {
                AppLogger.e("else title position", "------------" + filterCurrentPosition);
                AppLogger.e("else current position", "------------" + getAdapterPosition());
                itemArrayList.get(getAdapterPosition()).setSelected(true);
                notifyItemChanged(getAdapterPosition());
                ((FilterAct) activity).updateFilterData(getAdapterPosition(), true);
//                FilterTitleListAdapter.tvinvcount.setVisibility(View.VISIBLE);
//                FilterTitleListAdapter.tvinvcount.setText(String.valueOf(filterSelectItems.get(filterCurrentPosition).getFiltercount()));
//                filterSelectItems.get(filterCurrentPosition).getOptionData().get(getAdapterPosition()).setSelected(false);
            }
            ((FilterAct) activity).countFilter();
            notifyItemChanged(getAdapterPosition());
            //through this code I am getting instant update of count shpowing but its display on last filter Item
//            tvinvcount.setVisibility(View.VISIBLE);
//            tvinvcount.setText(String.valueOf(filterSelectItems.get(filterCurrentPosition).getFiltercount()));
////            notifyItemChanged(filterCurrentPosition);
////            ((FilterTitleListAdapter) activity).updatecount();
////           ((FilterAct) activity).bindSelectFilter();
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
