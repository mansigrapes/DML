package com.dealermela.listing_and_detail.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dealermela.R;
import com.dealermela.listing_and_detail.activity.ProductDetailAct;
import com.dealermela.listing_and_detail.model.ProductDetailItem;
import com.dealermela.util.AppLogger;
import com.dealermela.util.ThemePreferences;

import java.util.List;

import static com.dealermela.home.adapter.PopularProductCoverFlowAdapter.screenWidth;
import static com.dealermela.listing_and_detail.activity.ProductDetailAct.ProductDetailScrollView;
import static com.dealermela.listing_and_detail.activity.ProductDetailAct.mBottomDialog_detail;
import static com.dealermela.listing_and_detail.activity.ProductDetailAct.productdetailview;
import static com.dealermela.listing_and_detail.activity.ProductDetailAct.recycleViewDiamondDetail;
import static com.dealermela.listing_and_detail.activity.ProductDetailAct.recycleViewGemstoneDetail;
import static com.dealermela.listing_and_detail.activity.ProductDetailAct.relDiamondDetailTotal;
import static com.dealermela.listing_and_detail.activity.ProductDetailAct.relGemstoneDetailTotal;
import static com.dealermela.listing_and_detail.activity.ProductDetailAct.relMetalPurity;
import static com.dealermela.listing_and_detail.activity.ProductDetailAct.relMetalTotal;
import static com.dealermela.listing_and_detail.activity.ProductDetailAct.relMetalWeight;
import static com.dealermela.listing_and_detail.activity.ProductDetailAct.tvDiamondDetailLabel;
import static com.dealermela.listing_and_detail.activity.ProductDetailAct.tvGemStoneDetailTitle;
import static com.dealermela.listing_and_detail.activity.ProductDetailAct.tvMetalDetailTitle;

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
        final ImageView btnInfo;
//        final LinearLayout btnInfo;

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
            btnInfo = itemView.findViewById(R.id.btnInfo);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            btnInfo.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceType")
                @Override
                public void onClick(View v) {
                    AppLogger.e("More Detail button click which display in recyclerview ","-----");
                    ((ViewGroup)productdetailview.getParent()).removeView(productdetailview);
                    if(tvMetalDetailTitle.getVisibility() == View.VISIBLE || recycleViewDiamondDetail.getVisibility() == View.VISIBLE || recycleViewGemstoneDetail.getVisibility() == View.VISIBLE){

                        tvMetalDetailTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_add_24, 0);
                        tvMetalDetailTitle.setTextAppearance(activity, R.attr.transaction_line_color);
                        tvMetalDetailTitle.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/montserrat_bold.ttf"), Typeface.BOLD);
                        relMetalPurity.setVisibility(View.GONE);
                        relMetalWeight.setVisibility(View.GONE);
                        relMetalTotal.setVisibility(View.GONE);

                        tvDiamondDetailLabel.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_add_24, 0);
                        tvDiamondDetailLabel.setTextAppearance(activity, R.attr.transaction_line_color);
                        tvDiamondDetailLabel.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/montserrat_bold.ttf"), Typeface.BOLD);
                        recycleViewDiamondDetail.setVisibility(View.GONE);
                        relDiamondDetailTotal.setVisibility(View.GONE);

                        tvGemStoneDetailTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_add_24, 0);
                        tvGemStoneDetailTitle.setTextAppearance(activity, R.attr.transaction_line_color);
                        tvGemStoneDetailTitle.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/montserrat_bold.ttf"), Typeface.BOLD);
                        recycleViewGemstoneDetail.setVisibility(View.GONE);
                        relGemstoneDetailTotal.setVisibility(View.GONE);
                    }
                    mBottomDialog_detail.setContentView(productdetailview);
                    ProductDetailScrollView.scrollTo(0, 0);
                    mBottomDialog_detail.show();

//                    NestedScrollView.LayoutParams layoutParams = new NestedScrollView.LayoutParams(screenWidth, 395);
//                    ProductDetailScrollView.setLayoutParams(layoutParams);
                }
            });
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
