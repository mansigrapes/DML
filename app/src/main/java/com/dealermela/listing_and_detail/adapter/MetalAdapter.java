package com.dealermela.listing_and_detail.adapter;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dealermela.R;
import com.dealermela.listing_and_detail.activity.ProductDetailAct;
import com.dealermela.util.ThemePreferences;

import java.util.List;

import static com.dealermela.listing_and_detail.activity.ProductDetailAct.CustomizeClick;

public class MetalAdapter extends RecyclerView.Adapter<MetalAdapter.ViewHolder> {
    private final Activity activity;
    private final List<String> itemArrayList;
    private ThemePreferences themePreferences;
    public static String metalValue="Yellow Gold";

    public MetalAdapter(Activity activity, List<String> itemArrayList) {
        super();
        this.activity = activity;
        this.itemArrayList = itemArrayList;
        themePreferences = new ThemePreferences(activity);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.act_product_detail_item_metal_adapter, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {

        holder.tvName.setText(itemArrayList.get(i));

        if (itemArrayList.get(i).contains("Rose")) {
            holder.cardMetalColor.setCardBackgroundColor(activity.getResources().getColor(R.color.rose));
        } else if (itemArrayList.get(i).contains("Tone")) {
            holder.cardMetalColor.setCardBackgroundColor(activity.getResources().getColor(R.color.tone));
        } else if (itemArrayList.get(i).contains("Yellow")) {
            holder.cardMetalColor.setCardBackgroundColor(activity.getResources().getColor(R.color.yellow));
        } else if (itemArrayList.get(i).contains("White")) {
            holder.cardMetalColor.setCardBackgroundColor(activity.getResources().getColor(R.color.OffWhite));
        } else {
            holder.cardMetalColor.setCardBackgroundColor(activity.getResources().getColor(R.color.platinum));
        }

// Add this for perfect display Platinum & (950) On different line 28/01/2021
        if(itemArrayList.get(i).contains("Platinum")){
            holder.tvName.setText("Platinum (950)");
        }

        if (themePreferences.getTheme().equalsIgnoreCase("black")) {
            if (itemArrayList.get(i).equalsIgnoreCase(metalValue)) {
                holder.linMetal.setBackground(activity.getResources().getDrawable(R.drawable.pro_detail_customise_pro_select_black));
                holder.tvName.setTextColor(activity.getResources().getColor(R.color.dml_logo_color));

            } else {
                holder.linMetal.setBackground(activity.getResources().getDrawable(R.drawable.pro_detail_customise_pro_unselect_black));
                holder.tvName.setTextColor(activity.getResources().getColor(R.color.white));
            }
        } else if (themePreferences.getTheme().equalsIgnoreCase("white")) {
            if (itemArrayList.get(i).equalsIgnoreCase(metalValue)) {
                holder.linMetal.setBackground(activity.getResources().getDrawable(R.drawable.pro_detail_customise_pro_select));
                holder.tvName.setTextColor(activity.getResources().getColor(R.color.dml_logo_color));

            } else {
                holder.linMetal.setBackground(activity.getResources().getDrawable(R.drawable.pro_detail_customise_pro_unselect));
                holder.tvName.setTextColor(activity.getResources().getColor(R.color.black));
            }
        } else {
            if (itemArrayList.get(i).equalsIgnoreCase(metalValue)) {
                holder.linMetal.setBackground(activity.getResources().getDrawable(R.drawable.pro_detail_customise_pro_select));
                holder.tvName.setTextColor(activity.getResources().getColor(R.color.dml_logo_color));

            } else {
                holder.linMetal.setBackground(activity.getResources().getDrawable(R.drawable.pro_detail_customise_pro_unselect));
                holder.tvName.setTextColor(activity.getResources().getColor(R.color.black));
            }
        }
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        final TextView tvName;
        final LinearLayout linMetal;
        final CardView cardMetalColor;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            linMetal = itemView.findViewById(R.id.linMetal);
            cardMetalColor = itemView.findViewById(R.id.cardMetalColor);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            linMetal.setBackground(activity.getResources().getDrawable(R.drawable.ten_size_logo_color_round_border));
            metalValue = itemArrayList.get(getAdapterPosition());

            CustomizeClick = 1;    //Add this flag for fun of Addtocart press than not need to again call refresh API

            ((ProductDetailAct) activity).filterClick(itemArrayList.get(getAdapterPosition()), "");
            notifyDataSetChanged();
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
