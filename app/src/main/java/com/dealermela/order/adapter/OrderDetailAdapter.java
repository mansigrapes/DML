package com.dealermela.order.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dealermela.R;
import com.dealermela.order.activity.OrderDetailAct;
import com.dealermela.order.model.OrderDetailItem;
import com.dealermela.order.model.OrderItem;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.dealermela.util.CommonUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {

    private final Activity activity;
    private final List<OrderDetailItem.OrderItem> itemArrayList;

    public OrderDetailAdapter(Activity activity, List<OrderDetailItem.OrderItem> itemArrayList) {
        super();
        this.activity = activity;
        this.itemArrayList = itemArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.act_order_detail_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {
        holder.tvProductName.setText(itemArrayList.get(i).getProductName());
        holder.tvSku.setText(Html.fromHtml("<b>" + "Sku : " + "</b> " + itemArrayList.get(i).getProductSku()));
        holder.tvMetalWeight.setText(Html.fromHtml("<b>" + "Metal Detail : " + "</b> " + itemArrayList.get(i).getProductMetalquality() + "<b>" + " | "+ "</b>" + itemArrayList.get(i).getProductMetalweight()));

//        if(itemArrayList.get(i).getProductStoneweight() != null){
//            holder.tvStoneWeight.setText(Html.fromHtml("<b>" + "Stone Detail : " + "</b> " + itemArrayList.get(i).getProductStonequality() + "<b>" + " | "+ "</b>" + itemArrayList.get(i).getProductStoneweight()));
//        }else{
//            holder.tvStoneWeight.setText(Html.fromHtml("<b>" + "Stone Detail : " + "</b> " + " - "));
//        }

        if(itemArrayList.get(i).getProductStonequality() != null && itemArrayList.get(i).getProductStoneweight() != null){
            holder.tvStoneWeight.setText(Html.fromHtml("<b>" + "Stone Detail : " + "</b> " + itemArrayList.get(i).getProductStonequality() + "<b>" + " | "+ "</b>" + itemArrayList.get(i).getProductStoneweight()));
        }else if(itemArrayList.get(i).getProductStonequality() != null && itemArrayList.get(i).getProductStoneweight() == null){
            holder.tvStoneWeight.setText(Html.fromHtml("<b>" + "Stone Detail : " + "</b> " + itemArrayList.get(i).getProductStonequality() + "<b>" + " | "+ "</b>" + " N/A "));
        }else if(itemArrayList.get(i).getProductStonequality() == null && itemArrayList.get(i).getProductStoneweight() != null){
            holder.tvStoneWeight.setText(Html.fromHtml("<b>" + "Stone Detail : " + "</b> " + " N/A " + "<b>" + " | "+ "</b>" + itemArrayList.get(i).getProductStoneweight()));
        }else {
            holder.tvStoneWeight.setText(Html.fromHtml("<b>" + "Stone Detail : " + "</b> " + " N/A " + "<b>" + " | "+ "</b>" + " N/A "));
        }

        holder.tvStoneQuality.setText(Html.fromHtml("<b>" + "Product Type : " + "</b> " + itemArrayList.get(i).getProductType()));

        if(!itemArrayList.get(i).getCertificateNo().isEmpty()){
            holder.tvcertificate.setText(Html.fromHtml("<b>" + "Certificate : " + "</b> " + itemArrayList.get(i).getCertificateNo()));
        }else {
            holder.tvcertificate.setText(Html.fromHtml("<b>" + "Certificate : " + " N/A " + "</b>"));
        }

        holder.tvQty.setText(Html.fromHtml("<b>" + "QTY : " + "</b> " + Math.round(Float.parseFloat(itemArrayList.get(i).getProductQty()))));
//        holder.tvSubPrice.setText(AppConstants.RS + CommonUtils.priceFormat(Float.parseFloat(itemArrayList.get(i).getProductPrice())));
        holder.tvPrice.setText(AppConstants.RS + CommonUtils.priceFormat(Float.parseFloat(itemArrayList.get(i).getProductRawtotal())));
        holder.imgProduct.setImageURI(itemArrayList.get(i).getProductImg());

        if(itemArrayList.get(i).getRingsize() != null){
            holder.tvproductsize.setVisibility(View.VISIBLE);
            holder.tvproductsize.setText(Html.fromHtml("<b>" + "Ring Size : " + "</b>" + itemArrayList.get(i).getRingsize()));
        }else if(itemArrayList.get(i).getBangleSize() != null){
            holder.tvproductsize.setVisibility(View.VISIBLE);
            holder.tvproductsize.setText(Html.fromHtml("<b>" + "Bangle Size : " + "</b>" + itemArrayList.get(i).getBangleSize()));
        }else if (itemArrayList.get(i).getBraceletsSize() != null){
            holder.tvproductsize.setVisibility(View.VISIBLE);
            holder.tvproductsize.setText(Html.fromHtml("<b>" + "Bracelet Size : " + "</b>" + itemArrayList.get(i).getBraceletsSize()));
        }
//        else if(itemArrayList.get(i).getPendentSize() != null){
//            holder.tvproductsize.setText(Html.fromHtml("<b>" + "Pendent Type : " + "</b>" + itemArrayList.get(i).getPendentSize()));
//        }

//        int height = holder.linProductContent.getMeasuredHeight();
//        AppLogger.e("height","-------"+height);
//        ViewGroup.LayoutParams layoutParams = holder.viewHori.getLayoutParams();
//        layoutParams.width = 1;
//        layoutParams.height = height;
//        holder.viewHori.setLayoutParams(layoutParams);

//        holder.viewHori.setLayoutParams(new ViewGroup.LayoutParams(1, height));

    }

    @Override
    public int getItemCount() {

        return itemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        final TextView tvProductName, tvSku, tvMetalWeight, tvStoneWeight, tvStoneQuality, tvQty, tvSubPrice, tvPrice, tvcertificate, tvproductsize;
        final SimpleDraweeView imgProduct;
        LinearLayout linProductContent;
        View viewHori;

        ViewHolder(View itemView) {
            super(itemView);
            viewHori = itemView.findViewById(R.id.viewHori);
            linProductContent = itemView.findViewById(R.id.linProductContent);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvSku = itemView.findViewById(R.id.tvSku);
            tvMetalWeight = itemView.findViewById(R.id.tvMetalWeight);
            tvStoneWeight = itemView.findViewById(R.id.tvStoneWeight);
            tvStoneQuality = itemView.findViewById(R.id.tvStoneQuality);
            tvQty = itemView.findViewById(R.id.tvQty);
            tvSubPrice = itemView.findViewById(R.id.tvSubPrice);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvcertificate = itemView.findViewById(R.id.tvCertificateno);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            tvproductsize = itemView.findViewById(R.id.tvproductsize);

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
