package com.dealermela.inventary.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dealermela.R;
import com.dealermela.interfaces.RecyclerViewClickListener;
import com.dealermela.inventary.model.InventoryInvoiceItem;

import java.util.List;

public class InventoryInvoiceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Activity activity;
    private final int VIEW_TYPE_ITEM = 0;
    private final List<InventoryInvoiceItem.Datum> itemArrayList;
    private RecyclerViewClickListener clickListener;


    public InventoryInvoiceAdapter(Activity activity, List<InventoryInvoiceItem.Datum> itemArrayList, RecyclerViewClickListener clickListener) {
        super();
        this.activity = activity;
        this.itemArrayList = itemArrayList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.act_invoice_list_item, parent, false);
            return new ItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ItemViewHolder) {
            populateItemRows((ItemViewHolder) viewHolder, position);
        } else if (viewHolder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) viewHolder, position);
        }
    }

    @Override
    public int getItemCount() {
        return itemArrayList == null ? 0 : itemArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        int VIEW_TYPE_LOADING = 1;
        return itemArrayList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView tvInvoiceNo, tvCustomerName, tvInvoiceDate, tvAmount, tvStatus;

        ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvInvoiceNo = itemView.findViewById(R.id.tvInvoiceNo);
            tvCustomerName = itemView.findViewById(R.id.tvCustomerName);
            tvInvoiceDate = itemView.findViewById(R.id.tvInvoiceDate);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setData(InventoryInvoiceItem.Datum datum) {
            tvInvoiceNo.setText(datum.getInvoiceNumber());
            tvCustomerName.setText(datum.getCustomerFirstname() + " " + datum.getCustomerLastname());
            tvInvoiceDate.setText(datum.getInvoiceCreatedDate());
            tvAmount.setText(datum.getGrandTotal());
            tvStatus.setText(datum.getStatus());
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onLongClick(v, getAdapterPosition());
            return true;
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed

    }

    private void populateItemRows(final ItemViewHolder holder, final int position) {
        final InventoryInvoiceItem.Datum datum = itemArrayList.get(position);
        holder.setData(datum);
    }
}
