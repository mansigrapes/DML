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
import com.dealermela.interfaces.RecyclerViewClickListener;
import com.dealermela.listing_and_detail.activity.ProductDetailAct;
import com.dealermela.listing_and_detail.model.ProductDetailItem;
import com.dealermela.util.AppLogger;
import com.dealermela.util.ThemePreferences;

import java.util.List;

public class BraceletsAdapter extends RecyclerView.Adapter<BraceletsAdapter.ViewHolder> {
    private RecyclerViewClickListener recyclerViewClickListener;
    private final Activity activity;
    private final List<ProductDetailItem.BraceletsSize> itemArrayList;
    public static String braceletProductId = "";
    private ThemePreferences themePreferences;

    public BraceletsAdapter(Activity activity, List<ProductDetailItem.BraceletsSize> itemArrayList) {
        super();
        this.activity = activity;
        this.itemArrayList = itemArrayList;
        themePreferences=new ThemePreferences(activity);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.act_product_detail_item_bracelets_adapter, viewGroup, false);
        return new ViewHolder(v, recyclerViewClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        holder.tvName.setText(itemArrayList.get(i).getLabel());

        if (themePreferences.getTheme().equalsIgnoreCase("black")) {

            if (itemArrayList.get(i).isSelected()) {
                holder.linBracelet.setBackground(activity.getResources().getDrawable(R.drawable.pro_detail_customise_pro_select_black));
                holder.tvName.setTextColor(activity.getResources().getColor(R.color.dml_logo_color));
//            ((ProductDetailAct)activity).recycleViewRingSize.scrollToPosition(i);

            } else {
                holder.linBracelet.setBackground(activity.getResources().getDrawable(R.drawable.pro_detail_customise_pro_unselect_black));
                holder.tvName.setTextColor(activity.getResources().getColor(R.color.white));
            }
        } else if (themePreferences.getTheme().equalsIgnoreCase("white")) {

            if (itemArrayList.get(i).isSelected()) {
                holder.linBracelet.setBackground(activity.getResources().getDrawable(R.drawable.pro_detail_customise_pro_select));
                holder.tvName.setTextColor(activity.getResources().getColor(R.color.dml_logo_color));
//            ((ProductDetailAct)activity).recycleViewRingSize.scrollToPosition(i);
//                ((ProductDetailAct) activity).cBracelet=itemArrayList.get(i).getLabel();
            } else {
                holder.linBracelet.setBackground(activity.getResources().getDrawable(R.drawable.pro_detail_customise_pro_unselect));
                holder.tvName.setTextColor(activity.getResources().getColor(R.color.black));
            }
        } else {
            if (itemArrayList.get(i).isSelected()) {
                holder.linBracelet.setBackground(activity.getResources().getDrawable(R.drawable.pro_detail_customise_pro_select));
                holder.tvName.setTextColor(activity.getResources().getColor(R.color.dml_logo_color));
//            ((ProductDetailAct)activity).recycleViewRingSize.scrollToPosition(i);
//                ((ProductDetailAct) activity).cBracelet=itemArrayList.get(i).getLabel();  //set this value in refresh API call
            } else {
                holder.linBracelet.setBackground(activity.getResources().getDrawable(R.drawable.pro_detail_customise_pro_unselect));
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
        final LinearLayout linBracelet;

        ViewHolder(View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            linBracelet = itemView.findViewById(R.id.linBracelet);

            recyclerViewClickListener = listener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            AppLogger.e("click", "-------" + getAdapterPosition());
            int pos = getAdapterPosition();
            for (int i = 0; i < itemArrayList.size(); i++) {
                ProductDetailItem.BraceletsSize braceletsSize = itemArrayList.get(i);
                if (i != pos) {
                    braceletsSize.setSelected(false);
                } else {
                    braceletsSize.setSelected(true);
                }
            }
            braceletProductId=itemArrayList.get(getAdapterPosition()).getProductId();
            ((ProductDetailAct) activity).cBracelet=itemArrayList.get(getAdapterPosition()).getLabel();
//            ((ProductDetailAct) activity).filterClick(itemArrayList.get(getAdapterPosition()).getProductId(),"");
            notifyDataSetChanged();
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }

    }

}
