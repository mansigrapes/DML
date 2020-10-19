package com.dealermela.home.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dealermela.R;
import com.dealermela.home.model.MostSellingItem;
import com.dealermela.listing_and_detail.activity.ProductDetailAct;
import com.dealermela.util.AppConstants;
import com.dealermela.util.CommonUtils;

import java.util.List;

public class MostSellingAdapter extends RecyclerView.Adapter<MostSellingAdapter.ViewHolder> {

    private final Activity activity;
    private final List<MostSellingItem.Datum> itemArrayList;

    public MostSellingAdapter(Activity activity, List<MostSellingItem.Datum> itemArrayList) {
        super();
        this.activity = activity;
        this.itemArrayList = itemArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.frg_home_item_most_selling, viewGroup, false);


        int widths = screenWidth();
        Log.e("width", "-----------------" + widths);

//        v.getLayoutParams().width = (widths / 3); /// THIS LINE WILL DIVIDE OUR VIEW INTO NUMBERS OF PARTS
//        v.getLayoutParams().height = widths / 3; /// THIS LINE WILL DIVIDE OUR VIEW INTO NUMBERS OF PARTS
        return new ViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {

//        int index = itemArrayList.get(i).getPrice().indexOf(".");
        float price=Float.parseFloat(itemArrayList.get(i).getCustomPrice());

        holder.tvPrice.setText(AppConstants.RS + CommonUtils.priceFormat(price));
        holder.tvProductName.setText(itemArrayList.get(i).getName());

        Glide.with(activity)
                .load(AppConstants.IMAGE_URL+"catalog/product"+itemArrayList.get(i).getSmallImage())
                .apply(new RequestOptions().placeholder(R.drawable.dml_logo).error(R.drawable.dml_logo))
                .into(holder.imgMostSelling);
    }

    @Override
    public int getItemCount() {

        return itemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        final ImageView imgMostSelling;
        final TextView tvPrice;
        final TextView tvProductName;

        ViewHolder(View itemView) {
            super(itemView);
            imgMostSelling=itemView.findViewById(R.id.imgMostSelling);
            tvPrice=itemView.findViewById(R.id.tvPrice);
            tvProductName=itemView.findViewById(R.id.tvProductName);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(activity, ProductDetailAct.class);
            intent.putExtra(AppConstants.NAME, itemArrayList.get(getAdapterPosition()).getEntityId());
            intent.putExtra(AppConstants.ID,itemArrayList.get(getAdapterPosition()).getAttributeSetId());
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }


    private int screenWidth() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int height = displayMetrics.heightPixels;

        return displayMetrics.widthPixels;
    }
}
