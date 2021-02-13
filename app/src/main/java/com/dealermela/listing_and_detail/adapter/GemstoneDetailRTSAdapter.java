package com.dealermela.listing_and_detail.adapter;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dealermela.R;
import com.dealermela.listing_and_detail.model.RTSItem;
import com.dealermela.util.CommonUtils;

import java.util.List;

public class GemstoneDetailRTSAdapter extends RecyclerView.Adapter<GemstoneDetailRTSAdapter.ViewHolder> {
    private final Activity activity;
    private final List<RTSItem.Gemstonedetail> itemArrayList;

    public GemstoneDetailRTSAdapter(Activity activity, List<RTSItem.Gemstonedetail> itemArrayList) {
        super();
        this.activity = activity;
        this.itemArrayList = itemArrayList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.act_product_detail_gemstone_detail, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GemstoneDetailRTSAdapter.ViewHolder holder, int i) {
        if (itemArrayList.size()>1){
            holder.tvGemstoneIndex.setVisibility(View.VISIBLE);
            holder.tvGemstoneIndex.setText("Gemstone "+(i+1));
        }else{
            holder.tvGemstoneIndex.setVisibility(View.GONE);
        }
        holder.tvGemstoneType.setText(itemArrayList.get(i).getType());
        holder.tvGemstoneShape.setText(itemArrayList.get(i).getShape());
        holder.tvGemstoneSetting.setText(itemArrayList.get(i).getSetting());
        holder.tvGemstoneSize.setText(itemArrayList.get(i).getApproxSize()+"(mm)");
        holder.tvGemstonePieces.setText(itemArrayList.get(i).getPieces());
        holder.tvGemstoneEstimatedValue.setText("Rs. "+ CommonUtils.rupeeFormat(itemArrayList.get(i).getGemstoneprice()));
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
    {
        final TextView tvGemstoneIndex, tvGemstoneType, tvGemstoneShape, tvGemstoneSetting, tvGemstoneSize, tvGemstonePieces,tvGemstoneEstimatedValue;
        final LinearLayout linDiamond;

        ViewHolder(View itemView) {
            super(itemView);
            tvGemstoneIndex = itemView.findViewById(R.id.tvGemstoneIndex);
            tvGemstoneType = itemView.findViewById(R.id.tvGemstoneType);
            tvGemstoneShape = itemView.findViewById(R.id.tvGemstoneShape);
            tvGemstoneSetting = itemView.findViewById(R.id.tvGemstoneSetting);
            tvGemstoneSize = itemView.findViewById(R.id.tvGemstoneSize);
            tvGemstonePieces = itemView.findViewById(R.id.tvGemstonePieces);
            tvGemstoneEstimatedValue = itemView.findViewById(R.id.tvGemstoneEstimatedValue);
            linDiamond = itemView.findViewById(R.id.linDiamond);
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
