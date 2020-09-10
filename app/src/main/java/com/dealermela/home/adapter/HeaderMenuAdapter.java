package com.dealermela.home.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
import com.dealermela.home.model.HeaderItem;
import com.dealermela.listing_and_detail.activity.ListAct;
import com.dealermela.util.AppConstants;

import java.util.List;

public class HeaderMenuAdapter extends RecyclerView.Adapter<HeaderMenuAdapter.ViewHolder> {

    private final Activity activity;
    private final List<HeaderItem.Datum> itemArrayList;

    public HeaderMenuAdapter(Activity activity, List<HeaderItem.Datum> itemArrayList) {
        super();
        this.activity = activity;
        this.itemArrayList = itemArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.frg_home_item_header, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {

        Glide.with(activity)
                .load(itemArrayList.get(i).getIconImg())
                .apply(new RequestOptions().placeholder(R.drawable.dml_logo).error(R.drawable.dml_logo))
                .into(holder.imgMenuIcon);

        holder.tvMenuIconName.setText(itemArrayList.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        final ImageView imgMenuIcon;
        final TextView tvMenuIconName;

        ViewHolder(View itemView) {
            super(itemView);
            imgMenuIcon = itemView.findViewById(R.id.imgMenuIcon);
            tvMenuIconName = itemView.findViewById(R.id.tvMenuIconName);
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
            return false;
        }
    }
}
