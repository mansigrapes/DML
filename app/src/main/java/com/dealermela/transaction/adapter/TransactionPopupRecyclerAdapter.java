package com.dealermela.transaction.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dealermela.R;
import com.dealermela.transaction.model.TransactionItem;
import com.dealermela.util.CommonUtils;

import java.util.List;

public class TransactionPopupRecyclerAdapter extends RecyclerView.Adapter<TransactionPopupRecyclerAdapter.ViewHolder> {

    private final Activity activity;
    private final List<TransactionItem.OrderItem> itemArrayList;


    public TransactionPopupRecyclerAdapter(Activity activity, List<TransactionItem.OrderItem> itemArrayList) {
        super();
        this.activity = activity;
        this.itemArrayList = itemArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.act_transaction_item_dialog_popup_sub_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int i) {
        holder.tvCustomerName.setText(itemArrayList.get(i).getCustomerName());
        holder.tvAmt.setText("\u20B9 " + CommonUtils.rupeeFormat(String.valueOf(itemArrayList.get(i).getAmount())));
        holder.tvRemainingAmt.setText("\u20B9 " + CommonUtils.rupeeFormat(String.valueOf(itemArrayList.get(i).getRemaingAmount())));
        holder.tvPaidDate.setText(CommonUtils.convert_dateformate(itemArrayList.get(i).getPaidDate()));

    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        final TextView tvCustomerName, tvAmt, tvRemainingAmt, tvPaidDate;

        ViewHolder(View itemView) {
            super(itemView);
            tvCustomerName = itemView.findViewById(R.id.tvCustomerName);
            tvAmt = itemView.findViewById(R.id.tvAmt);
            tvRemainingAmt = itemView.findViewById(R.id.tvRemainingAmt);
            tvPaidDate = itemView.findViewById(R.id.tvPaidDate);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }

    }


}
