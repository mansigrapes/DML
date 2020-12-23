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
import static com.dealermela.listing_and_detail.activity.ProductDetailAct.TypeValue;

import java.util.List;

public class PendentSetsAdapter extends RecyclerView.Adapter<PendentSetsAdapter.ViewHolder> {
    private RecyclerViewClickListener recyclerViewClickListener;
    private final Activity activity;
    private final List<ProductDetailItem.PendentEarring> itemArrayList;
    private int flag=0;
    public static String pendentProId = "";
    private ThemePreferences themePreferences;

    public PendentSetsAdapter(Activity activity, List<ProductDetailItem.PendentEarring> itemArrayList) {
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

//        if (flag==0) {
//                if (itemArrayList.get(i).getLabel().equalsIgnoreCase("PENDANT WITH EARRINGS")) {
//                    flag=1;
//                    pendentProId=itemArrayList.get(i).getProductId();
//                    ((ProductDetailAct) activity).cPendentSet=itemArrayList.get(i).getLabel();
////                    ((ProductDetailAct) activity).filterClick(itemArrayList.get(i).getProductId(),"");
//
//                }else if(itemArrayList.get(i).getLabel().equalsIgnoreCase("ONLY EARRINGS")){
//                    flag=1;
//                    pendentProId=itemArrayList.get(i).getProductId();
//                    ((ProductDetailAct) activity).cPendentSet=itemArrayList.get(i).getLabel();
////                    ((ProductDetailAct) activity).filterClick(itemArrayList.get(i).getProductId(),"");
//                }else{
//                    flag=1;
//                    pendentProId=itemArrayList.get(i).getProductId();
//                    ((ProductDetailAct) activity).cPendentSet=itemArrayList.get(i).getLabel();
////                    ((ProductDetailAct) activity).filterClick(itemArrayList.get(i).getProductId(),"");
//                }
//        }

        holder.tvName.setText(itemArrayList.get(i).getLabel());

        if (themePreferences.getTheme().equalsIgnoreCase("black")) {
            if (itemArrayList.get(i).isSelected()) {
                holder.linBangle.setBackground(activity.getResources().getDrawable(R.drawable.pro_detail_customise_pro_select_black));
                holder.tvName.setTextColor(activity.getResources().getColor(R.color.dml_logo_color));
//            ((ProductDetailAct)activity).recycleViewRingSize.scrollToPosition(i);
                pendentProId=itemArrayList.get(i).getProductId();
                TypeValue=itemArrayList.get(i).getValue();
                ((ProductDetailAct) activity).cPendentSet=itemArrayList.get(i).getLabel();
            } else {
                holder.linBangle.setBackground(activity.getResources().getDrawable(R.drawable.pro_detail_customise_pro_unselect_black));
                holder.tvName.setTextColor(activity.getResources().getColor(R.color.white));
            }
        }else{

            if (itemArrayList.get(i).isSelected()) {
                holder.linBangle.setBackground(activity.getResources().getDrawable(R.drawable.pro_detail_customise_pro_select));
                holder.tvName.setTextColor(activity.getResources().getColor(R.color.dml_logo_color));
//            ((ProductDetailAct)activity).recycleViewRingSize.scrollToPosition(i);
                pendentProId=itemArrayList.get(i).getProductId();
                TypeValue=itemArrayList.get(i).getValue();
                ((ProductDetailAct) activity).cPendentSet=itemArrayList.get(i).getLabel();
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
            AppLogger.e("Pendent_Adapter_click", "-------" + getAdapterPosition());
            int pos = getAdapterPosition();
            for (int i = 0; i < itemArrayList.size(); i++) {
                ProductDetailItem.PendentEarring pendentEarring = itemArrayList.get(i);
                if (i != pos) {
                    pendentEarring.setSelected(false);
                } else {
                    pendentEarring.setSelected(true);
                }
            }
            pendentProId=itemArrayList.get(getAdapterPosition()).getProductId();
            TypeValue=itemArrayList.get(getAdapterPosition()).getValue();
            ((ProductDetailAct) activity).cPendentSet=itemArrayList.get(getAdapterPosition()).getLabel();
//            ((ProductDetailAct) activity).filterClick(itemArrayList.get(getAdapterPosition()).getProductId(),"");
            notifyDataSetChanged();
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
