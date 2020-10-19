package com.dealermela.my_stock.adapter;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dealermela.R;
import com.dealermela.listing_and_detail.model.FilterItem;
import com.dealermela.my_stock.activity.MyStockAct;

import java.util.List;


public class SortByMyStockRecyclerAdapter extends RecyclerView.Adapter<SortByMyStockRecyclerAdapter.ViewHolder> {

    private final Activity activity;
    private final List<FilterItem.SortBy> itemArrayList;

    public SortByMyStockRecyclerAdapter(Activity activity, List<FilterItem.SortBy> itemArrayList) {
        super();
        this.activity = activity;
        this.itemArrayList = itemArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.act_list_item_sort_by, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        holder.tvSortTitle.setText(itemArrayList.get(i).getLabel());
    }

    @Override
    public int getItemCount() {

        return itemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        final TextView tvSortTitle;
        ViewHolder(View itemView) {
            super(itemView);
            tvSortTitle=itemView.findViewById(R.id.tvSortTitle);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ((MyStockAct)activity).sortValueGetAndDialogClose(itemArrayList.get(getAdapterPosition()).getValue());
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }


    }

}
