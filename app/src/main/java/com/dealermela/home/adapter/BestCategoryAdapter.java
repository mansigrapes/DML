package com.dealermela.home.adapter;

import android.app.Activity;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dealermela.R;
import com.dealermela.home.model.HeaderItem;
import com.dealermela.listing_and_detail.activity.ListAct;
import com.dealermela.util.AppConstants;

import java.util.List;

public class BestCategoryAdapter extends RecyclerView.Adapter<BestCategoryAdapter.ViewHolder> {

    private final Activity activity;
    private final List<HeaderItem.Datum> itemArrayList;

    public BestCategoryAdapter(Activity activity, List<HeaderItem.Datum> itemArrayList) {
        super();
        this.activity = activity;
        this.itemArrayList = itemArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.frg_home_item_best_category, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        Glide.with(activity)
                .load(itemArrayList.get(i).getCategoryImg())
                .apply(new RequestOptions().placeholder(R.drawable.dml_logo).error(R.drawable.dml_logo))
                .into(holder.imgThumb);
        holder.tvBestCategoryName.setText(itemArrayList.get(i).getName());
    }

    @Override
    public int getItemCount() {

        return itemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        final TextView tvBestCategoryName;
        final ImageView imgThumb;

        ViewHolder(View itemView) {
            super(itemView);
            tvBestCategoryName = itemView.findViewById(R.id.tvBestCategoryName);
            imgThumb = itemView.findViewById(R.id.imgThumb);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent=new Intent(activity, ListAct.class);
            intent.putExtra(AppConstants.ID, itemArrayList.get(getAdapterPosition()).getEntityId());
            intent.putExtra(AppConstants.NAME, itemArrayList.get(getAdapterPosition()).getName());
            intent.putExtra(AppConstants.bannerListCheck, "");
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        }

        @Override
        public boolean onLongClick(View v) {
//            Toast.makeText(activity, "long Click" + getAdapterPosition(), Toast.LENGTH_SHORT).show();
            return false;
        }


    }


}
