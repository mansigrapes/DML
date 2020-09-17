package com.dealermela.listing_and_detail.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dealermela.R;
import com.dealermela.interfaces.RecyclerViewClickListener;
import com.dealermela.listing_and_detail.activity.ProductDetailAct;
import com.dealermela.listing_and_detail.model.ProductDetailItem;
import com.dealermela.util.AppLogger;
import com.dealermela.util.ThemePreferences;

import java.util.List;

public class RingAdapter extends RecyclerView.Adapter<RingAdapter.ViewHolder> {
    private RecyclerViewClickListener recyclerViewClickListener;
    private final Activity activity;
    private final List<ProductDetailItem.Ringsize> itemArrayList;
    public static String ringValue = "12";

    public String ringOptionId="",ringOptionTypeId="";
    private ThemePreferences themePreferences;

    public RingAdapter(Activity activity, List<ProductDetailItem.Ringsize> itemArrayList) {
        super();
        this.activity = activity;
        this.itemArrayList = itemArrayList;
        themePreferences=new ThemePreferences(activity);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.act_product_detail_item_ring_adapter, viewGroup, false);
        return new ViewHolder(v, recyclerViewClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        holder.tvName.setText(itemArrayList.get(i).getTitle());

        if (themePreferences.getTheme().equalsIgnoreCase("black")) {

            if (itemArrayList.get(i).getTitle().equalsIgnoreCase(ringValue)) {
                holder.linRing.setBackground(activity.getResources().getDrawable(R.drawable.pro_detail_customise_pro_select_black));
                holder.tvName.setTextColor(activity.getResources().getColor(R.color.dml_logo_color));
                ringOptionId=itemArrayList.get(i).getOptionId();
                ringOptionTypeId=itemArrayList.get(i).getOptionTypeId();
                ((ProductDetailAct) activity).filterClick(itemArrayList.get(i).getTitle(),"");

            } else {
                holder.linRing.setBackground(activity.getResources().getDrawable(R.drawable.pro_detail_customise_pro_unselect_black));
                holder.tvName.setTextColor(activity.getResources().getColor(R.color.white));
            }

        } else if (themePreferences.getTheme().equalsIgnoreCase("white")) {
            if (itemArrayList.get(i).getTitle().equalsIgnoreCase(ringValue)) {
                holder.linRing.setBackground(activity.getResources().getDrawable(R.drawable.pro_detail_customise_pro_select));
                holder.tvName.setTextColor(activity.getResources().getColor(R.color.dml_logo_color));
                ringOptionId=itemArrayList.get(i).getOptionId();
                ringOptionTypeId=itemArrayList.get(i).getOptionTypeId();

            } else {
                holder.linRing.setBackground(activity.getResources().getDrawable(R.drawable.pro_detail_customise_pro_unselect));
                holder.tvName.setTextColor(activity.getResources().getColor(R.color.black));
            }
        } else {
            if (itemArrayList.get(i).getTitle().equalsIgnoreCase(ringValue)) {
                holder.linRing.setBackground(activity.getResources().getDrawable(R.drawable.pro_detail_customise_pro_select));
                holder.tvName.setTextColor(activity.getResources().getColor(R.color.dml_logo_color));
                ringOptionId=itemArrayList.get(i).getOptionId();
                ringOptionTypeId=itemArrayList.get(i).getOptionTypeId();

            } else {
                holder.linRing.setBackground(activity.getResources().getDrawable(R.drawable.pro_detail_customise_pro_unselect));
                holder.tvName.setTextColor(activity.getResources().getColor(R.color.black));
            }
        }
            AppLogger.e("Ring_OptionIdFromAdapter","-----" + ringOptionId);
            AppLogger.e("Ring_OptionTypeIdFromAdapter","-----" + ringOptionTypeId);
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        final TextView tvName;
        final LinearLayout linRing;

        ViewHolder(View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            linRing = itemView.findViewById(R.id.linRing);

            recyclerViewClickListener = listener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            AppLogger.e("click", "-------" + getAdapterPosition());

//          linRing.setBackground(activity.getResources().getDrawable(R.drawable.ten_size_logo_color_round_border));

//            AppLogger.e("Pendent_Adapter_click", "-------" + getAdapterPosition());
//            int pos = getAdapterPosition();
//            for (int i = 0; i < itemArrayList.size(); i++) {
//                ProductDetailItem.PendentEarring pendentEarring = itemArrayList.get(i);
//                if (i != pos) {
//                    pendentEarring.setSelected(false);
//                } else {
//                    pendentEarring.setSelected(true);
//                }
//            }

            int pos  = getAdapterPosition();
            for(int i = 0; i < itemArrayList.size(); i++){
                ProductDetailItem.Ringsize ringsize = itemArrayList.get(i);
                if(i != pos){
                    ringsize.setSelected(false);
                }else {
                    ringsize.setSelected(true);
                }
            }

            ringValue = itemArrayList.get(getAdapterPosition()).getTitle();

            ringOptionId=itemArrayList.get(getAdapterPosition()).getOptionId();
            ringOptionTypeId=itemArrayList.get(getAdapterPosition()).getOptionTypeId();

            ((ProductDetailAct) activity).filterClick(itemArrayList.get(getAdapterPosition()).getTitle(),"");
            notifyDataSetChanged();
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
