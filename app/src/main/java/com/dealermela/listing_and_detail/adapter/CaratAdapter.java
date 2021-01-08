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
import com.dealermela.util.ThemePreferences;

import static com.dealermela.listing_and_detail.activity.ProductDetailAct.CustomizeClick;
import static com.dealermela.listing_and_detail.adapter.MetalAdapter.metalValue;

import java.util.List;

public class CaratAdapter extends RecyclerView.Adapter<CaratAdapter.ViewHolder> {

    private final Activity activity;
    private final List<String> itemArrayList;
    public static String caratValue="14K";

    private ThemePreferences themePreferences;

    public CaratAdapter(Activity activity, List<String> itemArrayList) {
        super();
        this.activity = activity;
        this.itemArrayList = itemArrayList;
        themePreferences=new ThemePreferences(activity);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.act_product_detail_item_carat_adapter, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        holder.tvName.setText(itemArrayList.get(i));

        if (themePreferences.getTheme().equalsIgnoreCase("black")) {
            if (itemArrayList.get(i).equalsIgnoreCase(caratValue)){
                holder.linCarat.setBackground(activity.getResources().getDrawable(R.drawable.pro_detail_customise_pro_select_black));
                holder.tvName.setTextColor(activity.getResources().getColor(R.color.dml_logo_color));
            }else{
                holder.linCarat.setBackground(activity.getResources().getDrawable(R.drawable.pro_detail_customise_pro_unselect_black));
                holder.tvName.setTextColor(activity.getResources().getColor(R.color.white));
            }
        } else if (themePreferences.getTheme().equalsIgnoreCase("white")) {
            if (itemArrayList.get(i).equalsIgnoreCase(caratValue)){
                holder.linCarat.setBackground(activity.getResources().getDrawable(R.drawable.pro_detail_customise_pro_select));
                holder.tvName.setTextColor(activity.getResources().getColor(R.color.dml_logo_color));
            }else{
                holder.linCarat.setBackground(activity.getResources().getDrawable(R.drawable.pro_detail_customise_pro_unselect));
                holder.tvName.setTextColor(activity.getResources().getColor(R.color.black));
            }
        } else {
            if (itemArrayList.get(i).equalsIgnoreCase(caratValue)){
                holder.linCarat.setBackground(activity.getResources().getDrawable(R.drawable.pro_detail_customise_pro_select));
                holder.tvName.setTextColor(activity.getResources().getColor(R.color.dml_logo_color));
            }else{
                holder.linCarat.setBackground(activity.getResources().getDrawable(R.drawable.pro_detail_customise_pro_unselect));
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
        final LinearLayout linCarat;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            linCarat = itemView.findViewById(R.id.linCarat);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            linCarat.setBackground(activity.getResources().getDrawable(R.drawable.ten_size_logo_color_round_border));
            caratValue=itemArrayList.get(getAdapterPosition());
            if (caratValue.equalsIgnoreCase("Platinum(950)")){
                metalValue="Platinum(950)";
                ((ProductDetailAct) activity).refreshAdapter();
            }else{
                metalValue="Yellow Gold";
                ((ProductDetailAct) activity).refreshAdapter();
            }

            CustomizeClick = 1;    //Add this flag for fun of Addtocart press than not need to again call refresh API

            ((ProductDetailAct) activity).filterClick(itemArrayList.get(getAdapterPosition()),"carat");
            notifyDataSetChanged();
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
