package com.dealermela.home.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.dealermela.R;
import com.dealermela.home.model.SubcategoryItem;
import com.dealermela.listing_and_detail.activity.ListAct;
import com.dealermela.util.AppConstants;

import java.util.List;

import static com.dealermela.home.activity.MainActivity.drawer;
import static com.dealermela.home.adapter.MainCategoryAdapter.categoryName;
import static com.dealermela.home.adapter.MainCategoryAdapter.mainCategoryId;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder> {
    private final Activity activity;
    private final List<SubcategoryItem.Subcategory> subcategories;

    public SubCategoryAdapter(Activity activity, List<SubcategoryItem.Subcategory> subcategories) {
        super();
        this.activity = activity;
        this.subcategories = subcategories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_collection_subcategory_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvCollectionSubCategoryItem.setText(subcategories.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return subcategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        final TextView tvCollectionSubCategoryItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCollectionSubCategoryItem = itemView.findViewById(R.id.tvCollectionSubCategoryItem);
            tvCollectionSubCategoryItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(activity, ListAct.class);
            intent.putExtra(AppConstants.ID,mainCategoryId);
            intent.putExtra(AppConstants.SubCategory_ID, subcategories.get(getAdapterPosition()).getId());
            intent.putExtra(AppConstants.NAME, subcategories.get(getAdapterPosition()).getName());
            intent.putExtra(AppConstants.MAIN_NAME, categoryName);
            intent.putExtra(AppConstants.bannerListCheck, "");
            activity.startActivity(intent);
            drawer.closeDrawer(GravityCompat.START);
//            MainCategoryAdapter.tvCollectionSubCategoryRecycleview.setVisibility(View.GONE);
//            MainCategoryAdapter.tvCollectionItem.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/montserrat_regular.ttf"), Typeface.NORMAL);
//            MainCategoryAdapter.tvCollectionItem.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_expand_more_24, 0);
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}

