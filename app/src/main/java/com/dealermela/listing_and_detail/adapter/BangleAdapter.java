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
import static com.dealermela.listing_and_detail.activity.ProductDetailAct.SizeValue;

import java.util.List;

public class BangleAdapter extends RecyclerView.Adapter<BangleAdapter.ViewHolder> {
    private RecyclerViewClickListener recyclerViewClickListener;
    private final Activity activity;
    private final List<ProductDetailItem.BangleSize> itemArrayList;
    public static String bangleProductId = "";

    private ThemePreferences themePreferences;

    public BangleAdapter(Activity activity, List<ProductDetailItem.BangleSize> itemArrayList) {
        super();
        this.activity = activity;
        this.itemArrayList = itemArrayList;
        themePreferences=new ThemePreferences(activity);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.act_product_detail_item_bangle_adapter, viewGroup, false);
        return new ViewHolder(v, recyclerViewClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {

        holder.tvName.setText(itemArrayList.get(i).getLabel());

        if (themePreferences.getTheme().equalsIgnoreCase("black")) {
            if (itemArrayList.get(i).isSelected()) {
                holder.linBangle.setBackground(activity.getResources().getDrawable(R.drawable.pro_detail_customise_pro_select_black));
                holder.tvName.setTextColor(activity.getResources().getColor(R.color.dml_logo_color));
//            ((ProductDetailAct)activity).recycleViewRingSize.scrollToPosition(i);
                bangleProductId  = itemArrayList.get(i).getProductId();
                SizeValue = itemArrayList.get(i).getValue();
            } else {
                holder.linBangle.setBackground(activity.getResources().getDrawable(R.drawable.pro_detail_customise_pro_unselect_black));
                holder.tvName.setTextColor(activity.getResources().getColor(R.color.white));
            }

        } else if (themePreferences.getTheme().equalsIgnoreCase("white")) {
            if (itemArrayList.get(i).isSelected()) {
                holder.linBangle.setBackground(activity.getResources().getDrawable(R.drawable.pro_detail_customise_pro_select));
                holder.tvName.setTextColor(activity.getResources().getColor(R.color.dml_logo_color));
//            ((ProductDetailAct)activity).recycleViewRingSize.scrollToPosition(i);
                bangleProductId  = itemArrayList.get(i).getProductId();
                SizeValue = itemArrayList.get(i).getValue();
            } else {
                holder.linBangle.setBackground(activity.getResources().getDrawable(R.drawable.pro_detail_customise_pro_unselect));
                holder.tvName.setTextColor(activity.getResources().getColor(R.color.black));
            }
        } else {
            if (itemArrayList.get(i).isSelected()) {
                holder.linBangle.setBackground(activity.getResources().getDrawable(R.drawable.pro_detail_customise_pro_select));
                holder.tvName.setTextColor(activity.getResources().getColor(R.color.dml_logo_color));
//            ((ProductDetailAct)activity).recycleViewRingSize.scrollToPosition(i);
                bangleProductId  = itemArrayList.get(i).getProductId();
                SizeValue = itemArrayList.get(i).getValue();
            } else {
                holder.linBangle.setBackground(activity.getResources().getDrawable(R.drawable.pro_detail_customise_pro_unselect));
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
        final LinearLayout linBangle;

        ViewHolder(View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            linBangle = itemView.findViewById(R.id.linBangle);

            recyclerViewClickListener = listener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            AppLogger.e("Bangle_Adapter_click", "-------" + getAdapterPosition());
            int pos = getAdapterPosition();
            for (int i = 0; i < itemArrayList.size(); i++) {
                ProductDetailItem.BangleSize bangleSize = itemArrayList.get(i);
                if (i != pos) {
                    bangleSize.setSelected(false);
                } else {
                    bangleSize.setSelected(true);
                }
            }
            bangleProductId=itemArrayList.get(getAdapterPosition()).getProductId();
            SizeValue=itemArrayList.get(getAdapterPosition()).getValue();
            ((ProductDetailAct) activity).cBangle=itemArrayList.get(getAdapterPosition()).getLabel();
            ((ProductDetailAct) activity).filterClick(itemArrayList.get(getAdapterPosition()).getProductId(),"Bangle");
            notifyDataSetChanged();
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
