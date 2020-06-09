package com.dealermela.transaction.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dealermela.R;
import com.dealermela.download.model.DownloadItem;
import com.dealermela.listing_and_detail.activity.ProductDetailAct;
import com.dealermela.transaction.model.TransactionItem;
import com.dealermela.util.AppConstants;
import com.dealermela.util.CommonUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;
import java.util.Objects;

public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.ViewHolder> {
    private final Activity activity;
    private final List<TransactionItem.Datum> itemArrayList;

    public TransactionListAdapter(Activity activity, List<TransactionItem.Datum> itemArrayList) {
        super();
        this.activity = activity;
        this.itemArrayList = itemArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.act_transaction_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int i) {

//        holder.tvOrderId.setText(itemArrayList.get(i).getIncrementId());
//        holder.tvCrDr.setText(itemArrayList.get(i).getDescription());
//        float price=itemArrayList.get(i).getTransctionPrice();
//        holder.tvAmount.setText(AppConstants.RS + CommonUtils.priceFormat(price));

        holder.tvOrderId.setText(itemArrayList.get(i).getOrderId());
        holder.tvCrDr.setText(itemArrayList.get(i).getCrDr());
        if(String.valueOf(itemArrayList.get(i).getAmount())=="null"){
            holder.tvAmount.setText("\u20B9 " + 0);
        }else
        {
            holder.tvAmount.setText("\u20B9 " + CommonUtils.rupeeFormat(String.valueOf(itemArrayList.get(i).getAmount())));
        }
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        final TextView tvOrderId, tvAmount, tvCrDr;


        ViewHolder(View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tvOrderId);
            tvAmount = itemView.findViewById(R.id.tvAmount);
            tvCrDr = itemView.findViewById(R.id.tvCrDr);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onClick(View v) {

            if (!itemArrayList.get(getAdapterPosition()).getOrderItem().isEmpty()) {
                final Dialog dialog = new Dialog(activity);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                Objects.requireNonNull(dialog.getWindow()).setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
                dialog.setContentView(R.layout.act_transaction_item_dialog_popup);
                dialog.show();

                RecyclerView recycleViewTransactionItemPopop = dialog.findViewById(R.id.recycleViewTransactionItemPopop);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
                recycleViewTransactionItemPopop.setLayoutManager(linearLayoutManager);

                TransactionPopupRecyclerAdapter transactionPopupRecyclerAdapter = new TransactionPopupRecyclerAdapter(activity, itemArrayList.get(getAdapterPosition()).getOrderItem());
                recycleViewTransactionItemPopop.setAdapter(transactionPopupRecyclerAdapter);

                Button btnClose = dialog.findViewById(R.id.btnClose);
                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                Objects.requireNonNull(dialog.getWindow()).setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            else
            {
                CommonUtils.showInfoToastShort(activity,  "No Transaction Detail");
            }
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }


    }





}
