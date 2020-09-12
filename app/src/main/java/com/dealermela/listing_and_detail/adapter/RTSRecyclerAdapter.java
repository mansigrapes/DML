package com.dealermela.listing_and_detail.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dealermela.R;
import com.dealermela.listing_and_detail.activity.ProductDetailAct;
import com.dealermela.listing_and_detail.model.ListingItem;
import com.dealermela.listing_and_detail.model.ProductDetailItem;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.dealermela.util.CommonUtils;
import com.dealermela.util.ThemePreferences;

import java.util.List;

public class RTSRecyclerAdapter extends RecyclerView.Adapter<RTSRecyclerAdapter.ViewHolder> {

    private final Activity activity;
    private final List<ProductDetailItem.RtsSlider> itemArrayList;
    private ProductDetailItem.RtsSlider rtsSlider;
    public static int Rtsflag = 0;
    private ThemePreferences themePreferences;

    public RTSRecyclerAdapter(Activity activity, List<ProductDetailItem.RtsSlider> itemArrayList) {
        super();
        this.activity = activity;
        this.itemArrayList = itemArrayList;
        themePreferences=new ThemePreferences(activity);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.act_product_detail_item_rts_adapter, viewGroup, false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {
        holder.tvMetal.setText(itemArrayList.get(i).getMetalQualityValue());

        if(itemArrayList.get(i).getRtsStoneQuality() != null){
            holder.tvStone.setText(itemArrayList.get(i).getRtsStoneQuality());
        }else {
            holder.tvStone.setText("-");
        }

        if(itemArrayList.get(i).getDiamondWeight() != null){
            holder.tvDiamondWeight.setText(String.valueOf(itemArrayList.get(i).getDiamondWeight()) + " cts");
        }else {
            holder.tvDiamondWeight.setText("-");
        }

        if(itemArrayList.get(i).getRtsRingSize() != null) {
            holder.tvSize.setText(itemArrayList.get(i).getRtsRingSize());
        }else if(itemArrayList.get(i).getRtsBangleSize() != null){
            holder.tvSize.setText( itemArrayList.get(i).getRtsBangleSize().toString());
        } else {
            holder.tvSize.setText("-");
        }

        rtsSlider = itemArrayList.get(i);

        if (Rtsflag == 0) {
            if (((ProductDetailAct) activity).productType.equalsIgnoreCase("simple")) {
                if (rtsSlider.getEntityId().equalsIgnoreCase(((ProductDetailAct) activity).productId)) {
                    rtsSlider.setSelected(true);
                    Rtsflag = 1;
                    AppLogger.e("selected item", "----------" + rtsSlider.getEntityId());

                    ((ProductDetailAct) activity).cProductId = rtsSlider.getEntityId();
                    ((ProductDetailAct) activity).productType = rtsSlider.getTypeId();
                    ((ProductDetailAct) activity).cSku = rtsSlider.getOriginalSku();
                    ((ProductDetailAct) activity).cRingSize = rtsSlider.getRtsRingSize();
                    ((ProductDetailAct) activity).cBangle = String.valueOf(rtsSlider.getRtsBangleSize());
                    ((ProductDetailAct) activity).cBracelet = String.valueOf(rtsSlider.getRtsBraceletSize());
                    ((ProductDetailAct) activity).cMetalDetail = rtsSlider.getMetalQualityValue();
                    ((ProductDetailAct) activity).cStoneDetail = rtsSlider.getRtsStoneQuality();
                    ((ProductDetailAct) activity).cStoneWeight = String.valueOf(rtsSlider.getDiamondWeight());
                    ((ProductDetailAct) activity).cPrice = rtsSlider.getCustomPrice();
                    ((ProductDetailAct) activity).rtsClick(rtsSlider.getEntityId());
                    ((ProductDetailAct) activity).tvColorGold.setText(itemArrayList.get(i).getMetalQualityValue());

                }
            } else{  // Add this condition bcz when product is customize than also any 1 RTS slider is selected at page load time so thats why give this condition on listing's product id
                if (rtsSlider.getEntityId().equalsIgnoreCase(((ProductDetailAct) activity).productId)) {
                    rtsSlider.setSelected(true);
                    Rtsflag = 1;
                    AppLogger.e("selected item", "----------" + rtsSlider.getEntityId());

                    ((ProductDetailAct) activity).cProductId = rtsSlider.getEntityId();
                    ((ProductDetailAct) activity).productType = rtsSlider.getTypeId();
                    ((ProductDetailAct) activity).cSku = rtsSlider.getOriginalSku();
                    ((ProductDetailAct) activity).cRingSize = rtsSlider.getRtsRingSize();
                    ((ProductDetailAct) activity).cBangle = String.valueOf(rtsSlider.getRtsBangleSize());
                    ((ProductDetailAct) activity).cBracelet = String.valueOf(rtsSlider.getRtsBraceletSize());
                    ((ProductDetailAct) activity).cMetalDetail = rtsSlider.getMetalQualityValue();
                    ((ProductDetailAct) activity).cStoneDetail = rtsSlider.getRtsStoneQuality();
                    ((ProductDetailAct) activity).cStoneWeight = String.valueOf(rtsSlider.getDiamondWeight());
                    ((ProductDetailAct) activity).cPrice = rtsSlider.getCustomPrice();
                    ((ProductDetailAct) activity).rtsClick(rtsSlider.getEntityId());
                    ((ProductDetailAct) activity).tvColorGold.setText(itemArrayList.get(i).getMetalQualityValue());

                }
            }
        }

        if (themePreferences.getTheme().equalsIgnoreCase("black")) {
            if (rtsSlider.isSelected()) {
                holder.linRts.setBackground(activity.getResources().getDrawable(R.drawable.rts_selected_item));
                holder.tvMetal.setTextColor(activity.getResources().getColor(R.color.white));
                holder.tvStone.setTextColor(activity.getResources().getColor(R.color.white));
                holder.tvDiamondWeight.setTextColor(activity.getResources().getColor(R.color.white));
                holder.tvSize.setTextColor(activity.getResources().getColor(R.color.white));
                holder.tvMetalTitle.setTextColor(activity.getResources().getColor(R.color.white));
                holder.tvStoneTitle.setTextColor(activity.getResources().getColor(R.color.white));
                holder.tvDiamondWeightTitle.setTextColor(activity.getResources().getColor(R.color.white));
                holder.tvSizeTitle.setTextColor(activity.getResources().getColor(R.color.white));

            } else {
                holder.linRts.setBackground(activity.getResources().getDrawable(R.drawable.ten_rts_border_black));
                holder.tvMetal.setTextColor(activity.getResources().getColor(R.color.white));
                holder.tvStone.setTextColor(activity.getResources().getColor(R.color.white));
                holder.tvDiamondWeight.setTextColor(activity.getResources().getColor(R.color.white));
                holder.tvSize.setTextColor(activity.getResources().getColor(R.color.white));
                holder.tvMetalTitle.setTextColor(activity.getResources().getColor(R.color.white));
                holder.tvStoneTitle.setTextColor(activity.getResources().getColor(R.color.white));
                holder.tvDiamondWeightTitle.setTextColor(activity.getResources().getColor(R.color.white));
                holder.tvSizeTitle.setTextColor(activity.getResources().getColor(R.color.white));

            }
        } else if (themePreferences.getTheme().equalsIgnoreCase("white")) {
            if (rtsSlider.isSelected()) {
                holder.linRts.setBackground(activity.getResources().getDrawable(R.drawable.rts_selected_item));
                holder.tvMetal.setTextColor(activity.getResources().getColor(R.color.white));
                holder.tvStone.setTextColor(activity.getResources().getColor(R.color.white));
                holder.tvDiamondWeight.setTextColor(activity.getResources().getColor(R.color.white));
                holder.tvSize.setTextColor(activity.getResources().getColor(R.color.white));
                holder.tvMetalTitle.setTextColor(activity.getResources().getColor(R.color.white));
                holder.tvStoneTitle.setTextColor(activity.getResources().getColor(R.color.white));
                holder.tvDiamondWeightTitle.setTextColor(activity.getResources().getColor(R.color.white));
                holder.tvSizeTitle.setTextColor(activity.getResources().getColor(R.color.white));

            } else {
                holder.linRts.setBackground(activity.getResources().getDrawable(R.drawable.ten_rts_border));
                holder.tvMetal.setTextColor(activity.getResources().getColor(R.color.black));
                holder.tvStone.setTextColor(activity.getResources().getColor(R.color.black));
                holder.tvDiamondWeight.setTextColor(activity.getResources().getColor(R.color.black));
                holder.tvSize.setTextColor(activity.getResources().getColor(R.color.black));
                holder.tvMetalTitle.setTextColor(activity.getResources().getColor(R.color.black));
                holder.tvStoneTitle.setTextColor(activity.getResources().getColor(R.color.black));
                holder.tvDiamondWeightTitle.setTextColor(activity.getResources().getColor(R.color.black));
                holder.tvSizeTitle.setTextColor(activity.getResources().getColor(R.color.black));

            }
        } else {
            if (rtsSlider.isSelected()) {
                holder.linRts.setBackground(activity.getResources().getDrawable(R.drawable.rts_selected_item));
                holder.tvMetal.setTextColor(activity.getResources().getColor(R.color.white));
                holder.tvStone.setTextColor(activity.getResources().getColor(R.color.white));
                holder.tvDiamondWeight.setTextColor(activity.getResources().getColor(R.color.white));
                holder.tvSize.setTextColor(activity.getResources().getColor(R.color.white));
                holder.tvMetalTitle.setTextColor(activity.getResources().getColor(R.color.white));
                holder.tvStoneTitle.setTextColor(activity.getResources().getColor(R.color.white));
                holder.tvDiamondWeightTitle.setTextColor(activity.getResources().getColor(R.color.white));
                holder.tvSizeTitle.setTextColor(activity.getResources().getColor(R.color.white));

            } else {
                holder.linRts.setBackground(activity.getResources().getDrawable(R.drawable.ten_rts_border));
                holder.tvMetal.setTextColor(activity.getResources().getColor(R.color.black));
                holder.tvStone.setTextColor(activity.getResources().getColor(R.color.black));
                holder.tvDiamondWeight.setTextColor(activity.getResources().getColor(R.color.black));
                holder.tvSize.setTextColor(activity.getResources().getColor(R.color.black));
                holder.tvMetalTitle.setTextColor(activity.getResources().getColor(R.color.black));
                holder.tvStoneTitle.setTextColor(activity.getResources().getColor(R.color.black));
                holder.tvDiamondWeightTitle.setTextColor(activity.getResources().getColor(R.color.black));
                holder.tvSizeTitle.setTextColor(activity.getResources().getColor(R.color.black));
            }
        }
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        final TextView tvMetal;
        final TextView tvMetalTitle;
        final TextView tvStone;
        final TextView tvStoneTitle;
        final TextView tvDiamondWeight;
        final TextView tvDiamondWeightTitle;
        final TextView tvSize;
        final TextView tvSizeTitle;
        final LinearLayout linRts;

        ViewHolder(View itemView) {
            super(itemView);
            tvMetal = itemView.findViewById(R.id.tvMetal);
            tvMetalTitle = itemView.findViewById(R.id.tvMetalTitle);
            tvStone = itemView.findViewById(R.id.tvStone);
            tvStoneTitle = itemView.findViewById(R.id.tvStoneTitle);
            tvDiamondWeight = itemView.findViewById(R.id.tvDiamondWeight);
            tvDiamondWeightTitle = itemView.findViewById(R.id.tvDiamondWeightTitle);
            tvSize = itemView.findViewById(R.id.tvSize);
            tvSizeTitle = itemView.findViewById(R.id.tvSizeTitle);
            linRts = itemView.findViewById(R.id.linRts);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            for (int i = 0; i < itemArrayList.size(); i++) {
                ProductDetailItem.RtsSlider slider = itemArrayList.get(i);
                if (i != pos) {
                    slider.setSelected(false);
                } else {
                    slider.setSelected(true);
                }
            }
            ((ProductDetailAct) activity).cProductId = itemArrayList.get(getAdapterPosition()).getEntityId();
            ((ProductDetailAct) activity).cSku = itemArrayList.get(getAdapterPosition()).getOriginalSku();
            ((ProductDetailAct) activity).cRingSize =itemArrayList.get(getAdapterPosition()).getRtsRingSize();
            ((ProductDetailAct) activity).cBangle = String.valueOf(itemArrayList.get(getAdapterPosition()).getRtsBangleSize());
            ((ProductDetailAct) activity).cBracelet = String.valueOf(itemArrayList.get(getAdapterPosition()).getRtsBraceletSize());
            ((ProductDetailAct) activity).cMetalDetail = itemArrayList.get(getAdapterPosition()).getMetalQualityValue();
            ((ProductDetailAct) activity).cStoneDetail = itemArrayList.get(getAdapterPosition()).getRtsStoneQuality();
            ((ProductDetailAct) activity).cStoneWeight = String.valueOf(itemArrayList.get(getAdapterPosition()).getDiamondWeight());
            ((ProductDetailAct) activity).cPrice = itemArrayList.get(getAdapterPosition()).getCustomPrice();
            ((ProductDetailAct) activity).rtsClick(itemArrayList.get(getAdapterPosition()).getEntityId());
            ((ProductDetailAct) activity).tvColorGold.setText(itemArrayList.get(getAdapterPosition()).getMetalQualityValue());
            ((ProductDetailAct) activity).productType = itemArrayList.get(getAdapterPosition()).getTypeId();

            notifyDataSetChanged();
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
