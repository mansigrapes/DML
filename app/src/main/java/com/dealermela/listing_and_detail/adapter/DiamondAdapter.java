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
import com.dealermela.listing_and_detail.activity.ProductDetailAct;
import com.dealermela.listing_and_detail.model.ProductDetailItem;
import com.dealermela.util.AppLogger;
import com.dealermela.util.ThemePreferences;

import java.util.List;

import static com.dealermela.listing_and_detail.activity.ProductDetailAct.btnapply;
import static com.dealermela.listing_and_detail.activity.ProductDetailAct.diamondValue;
import static com.dealermela.listing_and_detail.activity.ProductDetailAct.CustomizeClick;

public class DiamondAdapter extends RecyclerView.Adapter<DiamondAdapter.ViewHolder> {
    private final Activity activity;
    private final List<ProductDetailItem.StoneClarity> itemArrayList;

    public String stoneOptionId="",stoneOptionTypeId="";
    private ThemePreferences themePreferences;

    public DiamondAdapter(Activity activity, List<ProductDetailItem.StoneClarity> itemArrayList) {
        super();
        this.activity = activity;
        this.itemArrayList = itemArrayList;
        themePreferences=new ThemePreferences(activity);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.act_product_detail_item_diamond_adapter, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        holder.tvName.setText(itemArrayList.get(i).getTitle());

        if (themePreferences.getTheme().equalsIgnoreCase("black")) {
            if (itemArrayList.get(i).getTitle().equalsIgnoreCase(diamondValue)){
                holder.linDiamond.setBackground(activity.getResources().getDrawable(R.drawable.pro_detail_customise_pro_select_black));
                holder.tvName.setTextColor(activity.getResources().getColor(R.color.dml_logo_color));
                stoneOptionId=itemArrayList.get(i).getOptionId();
                stoneOptionTypeId=itemArrayList.get(i).getOptionTypeId();
            }else{
                holder.linDiamond.setBackground(activity.getResources().getDrawable(R.drawable.pro_detail_customise_pro_unselect_black));
                holder.tvName.setTextColor(activity.getResources().getColor(R.color.white));
            }
        } else if (themePreferences.getTheme().equalsIgnoreCase("white")) {
            if (itemArrayList.get(i).getTitle().equalsIgnoreCase(diamondValue)){
                holder.linDiamond.setBackground(activity.getResources().getDrawable(R.drawable.pro_detail_customise_pro_select));
                holder.tvName.setTextColor(activity.getResources().getColor(R.color.dml_logo_color));
                stoneOptionId=itemArrayList.get(i).getOptionId();
                stoneOptionTypeId=itemArrayList.get(i).getOptionTypeId();
            }else{
                holder.linDiamond.setBackground(activity.getResources().getDrawable(R.drawable.pro_detail_customise_pro_unselect));
                holder.tvName.setTextColor(activity.getResources().getColor(R.color.black));
            }
        } else {
            if (itemArrayList.get(i).getTitle().equalsIgnoreCase(diamondValue)){
                holder.linDiamond.setBackground(activity.getResources().getDrawable(R.drawable.pro_detail_customise_pro_select));
                holder.tvName.setTextColor(activity.getResources().getColor(R.color.dml_logo_color));
                stoneOptionId=itemArrayList.get(i).getOptionId();
                stoneOptionTypeId=itemArrayList.get(i).getOptionTypeId();
            }else{
                holder.linDiamond.setBackground(activity.getResources().getDrawable(R.drawable.pro_detail_customise_pro_unselect));
                holder.tvName.setTextColor(activity.getResources().getColor(R.color.black));
            }
        }
        AppLogger.e("stoneOptionId","------------"+ stoneOptionId);
        AppLogger.e("stoneOptionTypeId","------------"+ stoneOptionTypeId);
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        final TextView tvName;
        final LinearLayout linDiamond;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            linDiamond = itemView.findViewById(R.id.linDiamond);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

//            btnapply.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(!diamondValue.equalsIgnoreCase(itemArrayList.get(getAdapterPosition()).getTitle())) {
//                        diamondValue = itemArrayList.get(getAdapterPosition()).getTitle();
//                        stoneOptionId = itemArrayList.get(getAdapterPosition()).getOptionId();
//                        stoneOptionTypeId = itemArrayList.get(getAdapterPosition()).getOptionTypeId();
//                        ((ProductDetailAct) activity).filterClick(itemArrayList.get(getAdapterPosition()).getTitle(), "");
//                        AppLogger.e("stoneOptionId", "------------" + stoneOptionId);
//                        AppLogger.e("stoneOptionTypeId", "------------" + stoneOptionTypeId);
//                    }
//                }
//            });
        }

        @Override
        public void onClick(View v) {
//            Toast.makeText(activity, "on Click" + getAdapterPosition(), Toast.LENGTH_SHORT).show();
            linDiamond.setBackground(activity.getResources().getDrawable(R.drawable.pro_detail_customise_pro_select));
            diamondValue=itemArrayList.get(getAdapterPosition()).getTitle();
            stoneOptionId=itemArrayList.get(getAdapterPosition()).getOptionId();
            stoneOptionTypeId=itemArrayList.get(getAdapterPosition()).getOptionTypeId();

            CustomizeClick = 1;    //Add this flag for fun of Addtocart press than not need to again call refresh API

            AppLogger.e("stoneOptionId","------------"+ stoneOptionId);
            AppLogger.e("stoneOptionTypeId","------------"+ stoneOptionTypeId);
            ((ProductDetailAct) activity).filterClick(itemArrayList.get(getAdapterPosition()).getTitle(),"");
            notifyDataSetChanged();
        }

        @Override
        public boolean onLongClick(View v) {
//            Toast.makeText(activity, "long Click" + getAdapterPosition(), Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
