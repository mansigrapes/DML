package com.dealermela.listing_and_detail.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dealermela.R;
import com.dealermela.listing_and_detail.model.ProductDetailItem;

import java.util.List;

public class DiamondDetailAdapter extends RecyclerView.Adapter<DiamondDetailAdapter.ViewHolder> {
    private final Activity activity;
    private final List<ProductDetailItem.Diamonddetail> itemArrayList;
    public static String diamondValue = "SI-IJ";

    public DiamondDetailAdapter(Activity activity, List<ProductDetailItem.Diamonddetail> itemArrayList) {
        super();
        this.activity = activity;
        this.itemArrayList = itemArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.act_product_detail_item_diamond_detail, viewGroup, false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        if (itemArrayList.size()>1){
            holder.tvDiamondIndex.setText("Diamond "+(i+1));
        }else{
            holder.tvDiamondIndex.setVisibility(View.GONE);
        }
        holder.tvDiamondShape.setText(itemArrayList.get(i).getShape());
        holder.tvDiamondSetting.setText(itemArrayList.get(i).getSetting());
        holder.tvDiamondQuality.setText(itemArrayList.get(i).getQuality());
        holder.tvDiamondPieces.setText(itemArrayList.get(i).getPieces());
        holder.tvDiamondTotalWeight.setText(itemArrayList.get(i).getTotalweight()+"ct");
//        holder.tvDiamondCaratRate.setText(itemArrayList.get(i).getCaratrate());
//        holder.tvDiamondEstimatedPrice.setText(itemArrayList.get(i).getEstimatedprice());
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        final TextView tvDiamondShape, tvDiamondSetting, tvDiamondQuality, tvDiamondPieces, tvDiamondTotalWeight,tvDiamondIndex;
//        final TextView  tvDiamondCaratRate,tvDiamondEstimatedPrice;
        final LinearLayout linDiamond;

        ViewHolder(View itemView) {
            super(itemView);
            tvDiamondShape = itemView.findViewById(R.id.tvDiamondShape);
            tvDiamondSetting = itemView.findViewById(R.id.tvDiamondSetting);
            tvDiamondQuality = itemView.findViewById(R.id.tvDiamondQuality);
            tvDiamondPieces = itemView.findViewById(R.id.tvDiamondPieces);
            tvDiamondTotalWeight = itemView.findViewById(R.id.tvDiamondTotalWeight);
//            tvDiamondCaratRate = itemView.findViewById(R.id.tvDiamondCaratRate);
            tvDiamondIndex = itemView.findViewById(R.id.tvDiamondIndex);
//            tvDiamondEstimatedPrice = itemView.findViewById(R.id.tvDiamondEstimatedPrice);
            linDiamond = itemView.findViewById(R.id.linDiamond);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
        }

        @Override
        public boolean onLongClick(View v) {
//            Toast.makeText(activity, "long Click" + getAdapterPosition(), Toast.LENGTH_SHORT).show();
            return false;
        }


    }

}
