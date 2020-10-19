package com.dealermela.cart.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.dealermela.R;
import com.dealermela.cart.activity.ShippingChargeAct;
import com.dealermela.cart.model.SelectPaymentItem;
import com.dealermela.util.AppConstants;
import com.dealermela.util.CommonUtils;

import java.util.List;


public class ShippingChargeAdapter extends RecyclerView.Adapter<ShippingChargeAdapter.ViewHolder> {

    private final Activity activity;
    private final List<SelectPaymentItem.ShippingCharge> itemArrayList;

    public ShippingChargeAdapter(Activity activity, List<SelectPaymentItem.ShippingCharge> itemArrayList) {
        super();
        this.activity = activity;
        this.itemArrayList = itemArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.frg_payment_item_shipping_charge, viewGroup, false);
        return new ViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {

        if (itemArrayList.get(i).getMethod().equalsIgnoreCase("freeshipping")){
            holder.radioBtnSelectShippingCharge.setText(itemArrayList.get(i).getMethodTitle());
        }else{
            float price= Float.parseFloat(itemArrayList.get(i).getPrice());
            holder.radioBtnSelectShippingCharge.setText(itemArrayList.get(i).getMethodTitle()+" "+ AppConstants.RS + CommonUtils.priceFormat(price));
        }

        if (i==0){
            holder.linLeft.setVisibility(View.VISIBLE);
            holder.linRight.setVisibility(View.GONE);
        }else{
            holder.linLeft.setVisibility(View.GONE);
            holder.linRight.setVisibility(View.VISIBLE);
        }

        if (itemArrayList.get(i).isSelected()){
            holder.radioBtnSelectShippingCharge.setChecked(true);
        }else{
            holder.radioBtnSelectShippingCharge.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        final RadioButton radioBtnSelectShippingCharge;
        final LinearLayout linLeft,linRight;
        ViewHolder(View itemView) {
            super(itemView);
            radioBtnSelectShippingCharge=itemView.findViewById(R.id.radioBtnSelectShippingCharge);
            linLeft=itemView.findViewById(R.id.linLeft);
            linRight=itemView.findViewById(R.id.linRight);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            for (int i = 0; i < itemArrayList.size(); i++) {
                SelectPaymentItem.ShippingCharge charge = itemArrayList.get(i);
                if (i != pos) {
                    charge.setSelected(false);
                } else {
                    charge.setSelected(true);
                }
            }
            ((ShippingChargeAct) activity).getSelectShipping(itemArrayList.get(getAdapterPosition()).getCode(),itemArrayList.get(getAdapterPosition()).getPrice());

//            paymentFrg.getSelectPayment(itemArrayList.get(getAdapterPosition()).getValue());
            notifyDataSetChanged();
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
