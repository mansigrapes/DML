package com.dealermela.cart.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.dealermela.R;
import com.dealermela.cart.fragment.PaymentFrg;
import com.dealermela.cart.fragment.ShoppingFrg;
import com.dealermela.cart.model.CartServerDataItem;
import com.dealermela.cart.model.SelectPaymentItem;
import com.dealermela.retrofit.APIClient;
import com.dealermela.retrofit.ApiInterface;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.dealermela.util.CommonUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.JsonObject;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dealermela.home.activity.MainActivity.customerId;


public class PaymentSelectAdapter extends RecyclerView.Adapter<PaymentSelectAdapter.ViewHolder> {

    private final Activity activity;
    private final PaymentFrg paymentFrg;
    private final List<SelectPaymentItem.Date> itemArrayList;
    private String sourceString = "";
    private KProgressHUD hud;
    private int defaultSelect=0;

    public PaymentSelectAdapter(Activity activity, List<SelectPaymentItem.Date> itemArrayList, PaymentFrg paymentFrg) {
        super();
        this.activity = activity;
        this.itemArrayList = itemArrayList;
        this.paymentFrg = paymentFrg;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.frg_payment_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {

        holder.radioButton.setText(itemArrayList.get(i).getLabel());

        if (defaultSelect==0){
            itemArrayList.get(i).setSelected(true);
            paymentFrg.getSelectPayment(itemArrayList.get(i).getValue());
            defaultSelect++;
        }

        if (itemArrayList.get(i).isSelected()){
            holder.radioButton.setChecked(true);
        }else{
            holder.radioButton.setChecked(false);
        }

        if (i == 0) {
            holder.tvInfo.setVisibility(View.VISIBLE);
            holder.imgPaymentLogo.setVisibility(View.GONE);
            holder.tvInfo.setText(String.valueOf(itemArrayList.get(i).getInfo()));
        } else if (i == 1){
            holder.tvInfo.setVisibility(View.GONE);
            holder.imgPaymentLogo.setVisibility(View.GONE);
            holder.radioButton.setVisibility(View.GONE);
            holder.defaulttxt.setVisibility(View.GONE);
        }
        else  {
            holder.tvInfo.setVisibility(View.GONE);
            holder.imgPaymentLogo.setVisibility(View.VISIBLE);
            holder.imgPaymentLogo.setImageURI(itemArrayList.get(i).getImage());
        }
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        final TextView tvInfo,defaulttxt;
        final SimpleDraweeView imgPaymentLogo;
        final RadioButton radioButton;

        ViewHolder(View itemView) {
            super(itemView);
            radioButton = itemView.findViewById(R.id.radioButton);
            tvInfo = itemView.findViewById(R.id.tvInfo);
            defaulttxt = itemView.findViewById(R.id.defaulttxt);
            imgPaymentLogo = itemView.findViewById(R.id.imgPaymentLogo);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            defaultSelect=1;
            int pos = getAdapterPosition();
            for (int i = 0; i < itemArrayList.size(); i++) {
                SelectPaymentItem.Date date = itemArrayList.get(i);
                if (i != pos) {
                    date.setSelected(false);
                } else {
                    date.setSelected(true);
                }
            }
            paymentFrg.getSelectPayment(itemArrayList.get(getAdapterPosition()).getValue());
            notifyDataSetChanged();
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
