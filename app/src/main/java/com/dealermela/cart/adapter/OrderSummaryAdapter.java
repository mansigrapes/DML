package com.dealermela.cart.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dealermela.R;
import com.dealermela.cart.model.OrderSummaryItem;
import com.dealermela.util.AppConstants;
import com.dealermela.util.CommonUtils;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.List;


public class OrderSummaryAdapter extends RecyclerView.Adapter<OrderSummaryAdapter.ViewHolder> {

    private final Activity activity;
    private final List<OrderSummaryItem.Datum> itemArrayList;
    private String sourceString = "";
    private KProgressHUD hud;
    private int defaultSelect = 0;

    public OrderSummaryAdapter(Activity activity, List<OrderSummaryItem.Datum> itemArrayList) {
        super();
        this.activity = activity;
        this.itemArrayList = itemArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.act_order_summary_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {
        holder.tvProductName.setText(Html.fromHtml("<b>" + "SKU : " + "</b>" + itemArrayList.get(i).getSku()));

//        itemArrayList.get(i).getCertificate

        float price = itemArrayList.get(i).getPrice();
        holder.tvPrice.setText(AppConstants.RS + CommonUtils.priceFormat(price));
        holder.tvCertificate.setText(Html.fromHtml("<b>" + "Certificate No. : " + "</b>" + itemArrayList.get(i).getCertificateNo()));
        if(itemArrayList.get(i).getStonequality() != null){
            holder.tvStoneQuality.setText(Html.fromHtml("<b>" + "Stone Detail : " + "</b> " + itemArrayList.get(i).getStonequality()));
        }
        else{
            holder.tvStoneQuality.setText(Html.fromHtml("<b>" + "Stone Detail : " + "</b> " + " - "));
        }

        holder.tvMetalQuality.setText(Html.fromHtml("<b>" + "Metal Detail : " + "</b> " + itemArrayList.get(i).getMetaldetails()));
        holder.tvQty.setText(Html.fromHtml("<b>" + "Qty : " + "</b> " + String.valueOf(itemArrayList.get(i).getQty())));
        holder.tvSubPrice.setText(AppConstants.RS + CommonUtils.priceFormat(price));
        if (itemArrayList.get(i).getRingsize() != null) {
            holder.tvSize.setText(Html.fromHtml("<b>" + "Ring Size : " + "</b> " + String.valueOf(itemArrayList.get(i).getRingsize())));
        } else if (itemArrayList.get(i).getPendents() != null) {
//            holder.tvSize.setText(Html.fromHtml("<b>" + "Pendents Type : " + "</b> " + String.valueOf(itemArrayList.get(i).getPendents())));
            holder.tvSize.setVisibility(View.GONE);
        } else if (itemArrayList.get(i).getBangles() != null) {
            holder.tvSize.setText(Html.fromHtml("<b>" + "Bangle Size : " + "</b> " + String.valueOf(itemArrayList.get(i).getBangles())));
        } else if (itemArrayList.get(i).getBracelets() != null) {
            holder.tvSize.setText(Html.fromHtml("<b>" + "Bracelet Size : " + "</b> " + String.valueOf(itemArrayList.get(i).getBracelets())));
        } else {
            holder.tvSize.setVisibility(View.GONE);
        }

        holder.tvtype.setText(Html.fromHtml("<b>" + "Product Type : " + "</b> " + itemArrayList.get(i).getProductCategoryType()));
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        final TextView tvPrice, tvProductName, tvStoneQuality, tvMetalQuality, tvQty, tvSubPrice, tvSize,  tvCertificate, tvtype;

        ViewHolder(View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvStoneQuality = itemView.findViewById(R.id.tvStoneQuality);
            tvMetalQuality = itemView.findViewById(R.id.tvMetalQuality);
            tvQty = itemView.findViewById(R.id.tvQty);
            tvSubPrice = itemView.findViewById(R.id.tvSubPrice);
            tvSize = itemView.findViewById(R.id.tvSize);
            tvCertificate = itemView.findViewById(R.id.tvcertificateNo);
            tvtype = itemView.findViewById(R.id.tvtype);

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
