package com.dealermela.home.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dealermela.R;
import com.dealermela.home.model.SubcategoryItem;
import com.dealermela.listing_and_detail.activity.ListAct;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.dealermela.util.Utils;

import java.util.List;

import static com.dealermela.home.activity.MainActivity.drawer;

public class MainCategoryAdapter extends RecyclerView.Adapter<MainCategoryAdapter.ViewHolder> {
    private  Activity activity;
    private   List<SubcategoryItem.Datum> maincategories;
    public static String mainCategoryId,categoryName;

//    public static TextView tvCollectionItem;
//    public static  RecyclerView tvCollectionSubCategoryRecycleview;

    public MainCategoryAdapter(Activity activity, List<SubcategoryItem.Datum> maincategories) {
        super();
        this.activity = activity;
        this.maincategories = maincategories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawer_collection_maincategory_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MainCategoryAdapter.ViewHolder holder, final int position) {
//        mainCategoryId = String.valueOf(maincategories.get(position).getId());

       holder.tvCollectionItem.setText(maincategories.get(position).getName());
//        holder.imgcategory.setImageResource(maincategories.get(position).getIcon());

//        Utils.fetchSvg(activity, maincategories.get(position).getIcon(), holder.imgcategory);

        Glide.with(activity)
                .load(maincategories.get(position).getIcon())
                .apply(new RequestOptions().placeholder(R.drawable.ic_collection_new).error(R.drawable.ic_collection_new))
                .into(holder.imgcategory);

        AppLogger.e("Icon URL ","----" + maincategories.get(position).getIcon());

        if (!maincategories.get(position).getSubcategories().isEmpty()) {
            if(maincategories.get(position).getSubcategories().size() > 1) {
                holder.tvCollectionSubCategoryRecycleview.setVisibility(View.GONE);
                holder.tvCollectionItem.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/montserrat_regular.ttf"), Typeface.NORMAL);
                holder.tvCollectionItem.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_expand_more_24, 0);
            }else {
               holder.tvCollectionItem.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            }
        } else {
           holder.tvCollectionItem.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }

//        holder.tvCollectionItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                maincategories.get(position).setSelected(!maincategories.get(position).isSelected());
//                mainCategoryId = String.valueOf(maincategories.get(position).getId());
//                for (int j = 0; j < maincategories.size(); j++) {
//                    if (maincategories.get(j).isSelected() && j != position) {
//                        holder.tvCollectionSubCategoryRecycleview.setVisibility(View.GONE);
//                        maincategories.get(j).setSelected(false);
//                        AppLogger.e("Flag is updated or not ","-----"+ maincategories.get(j).isSelected());
//                        holder.tvCollectionItem.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/montserrat_regular.ttf"), Typeface.NORMAL);
//                        holder.tvCollectionItem.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_expand_more_24, 0);
//                        notifyItemChanged(j);
//                        AppLogger.e("Inside for loop Ifany category is selected : ","----j :- " + j);
//                    }
//                }
//
//                if(maincategories.get(position).getSubcategories().size() >  1){
//                    if(!maincategories.get(position).isSelected()){
//                        maincategories.get(position).setSelected(true);
//                        holder.tvCollectionSubCategoryRecycleview.setVisibility(View.VISIBLE);
//                        SubCategoryAdapter subCatgeoryAdapter = new SubCategoryAdapter(activity, maincategories.get(position).getSubcategories());
//                        holder.tvCollectionSubCategoryRecycleview.setAdapter(subCatgeoryAdapter);
//                        holder.tvCollectionItem.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/montserrat_semibold.ttf"), Typeface.BOLD);
//                        holder.tvCollectionItem.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_expand_less_24, 0);
//                        subCatgeoryAdapter.notifyItemChanged(position);
//                    }else {
//                        holder.tvCollectionSubCategoryRecycleview.setVisibility(View.GONE);
//                        maincategories.get(position).setSelected(false);
//                        holder.tvCollectionItem.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/montserrat_regular.ttf"), Typeface.NORMAL);
//                        holder.tvCollectionItem.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_expand_more_24, 0);
//                    }
//                } else {
//                    Intent intent = new Intent(activity, ListAct.class);
//                    intent.putExtra(AppConstants.ID, String.valueOf(maincategories.get(position).getId()));
//                    intent.putExtra(AppConstants.NAME, maincategories.get(position).getName());
//                    intent.putExtra(AppConstants.bannerListCheck, "");
//                    activity.startActivity(intent);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return maincategories.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public  final TextView tvCollectionItem;
        public  final RecyclerView tvCollectionSubCategoryRecycleview;

        ImageView imgcategory;
        LinearLayout linSubContainer;
        //        final View subcategoryView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCollectionItem = itemView.findViewById(R.id.tvCollectionItem);
            imgcategory = itemView.findViewById(R.id.imgcategory);
//            linSubContainer = itemView.findViewById(R.id.linSubContainer);
            tvCollectionItem.setOnClickListener(this);

//            subcategoryView = activity.getLayoutInflater().inflate(R.layout.drawer_new_view_subcategory, null);
            tvCollectionSubCategoryRecycleview = itemView.findViewById(R.id.tvCollectionSubCategoryRecycleview);
//            linSubContainer.addView(subcategoryView);
//            tvCollectionSubCategoryRecycleview = itemView.findViewById(R.id.tvCollectionSubCategoryRecycleview);

            GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 1);
            gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            tvCollectionSubCategoryRecycleview.setLayoutManager(gridLayoutManager);
        }

        @Override
        public void onClick(View v) {
            mainCategoryId = String.valueOf(maincategories.get(getAdapterPosition()).getId());
            categoryName = String.valueOf(maincategories.get(getAdapterPosition()).getName());

 /*           if (!maincategories.get(getAdapterPosition()).getSubcategories().isEmpty()) {
                if (maincategories.get(getAdapterPosition()).isSelected()) {
                    maincategories.get(getAdapterPosition()).setSelected(false);
                    tvCollectionSubCategoryRecycleview.setVisibility(View.GONE);
                    tvCollectionItem.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/montserrat_regular.ttf"), Typeface.NORMAL);
                    tvCollectionItem.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_expand_more_24, 0);

                } else {
                    for(int i = 0 ; i < maincategories.size() ; i++){
                        if(maincategories.get(i).isSelected()){
                            maincategories.get(i).setSelected(false);
                            tvCollectionSubCategoryRecycleview.setVisibility(View.GONE);
                            tvCollectionItem.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/montserrat_regular.ttf"), Typeface.NORMAL);
                            tvCollectionItem.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_expand_more_24, 0);
//                            notifyDataSetChanged();
                            notifyItemChanged(i);
                        }   else {
                        }
                    }

//                linSubContainer.setVisibility(View.VISIBLE);
                    tvCollectionSubCategoryRecycleview.setVisibility(View.VISIBLE);
                    maincategories.get(getAdapterPosition()).setSelected(true);
                    //   linSubContainer.removeView(v);
                    //   linSubContainer.addView(subcategoryView);
                    SubCategoryAdapter subCatgeoryAdapter = new SubCategoryAdapter(activity, maincategories.get(getAdapterPosition()).getSubcategories());
                    tvCollectionSubCategoryRecycleview.setAdapter(subCatgeoryAdapter);
                    tvCollectionItem.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/montserrat_semibold.ttf"), Typeface.BOLD);
                    tvCollectionItem.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_expand_less_24, 0);
                }
            } */
//
            if (!maincategories.get(getAdapterPosition()).getSubcategories().isEmpty()) {
                if(maincategories.get(getAdapterPosition()).getSubcategories().size() > 1) {
////                    if (maincategories.get(getAdapterPosition()).isSelected()) {
////                        maincategories.get(getAdapterPosition()).setSelected(false);
////                        tvCollectionSubCategoryRecycleview.setVisibility(View.GONE);
////                        tvCollectionItem.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/montserrat_regular.ttf"), Typeface.NORMAL);
////                        tvCollectionItem.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_expand_more_24, 0);
////
////                    } else {
//
//////                linSubContainer.setVisibility(View.VISIBLE);
////                        tvCollectionSubCategoryRecycleview.setVisibility(View.VISIBLE);
////                        maincategories.get(getAdapterPosition()).setSelected(true);
//////                                            linSubContainer.removeView(v);
//////                                            linSubContainer.addView(subcategoryView);
////                        SubCategoryAdapter subCatgeoryAdapter = new SubCategoryAdapter(activity, maincategories.get(getAdapterPosition()).getSubcategories());
////                        tvCollectionSubCategoryRecycleview.setAdapter(subCatgeoryAdapter);
////                        tvCollectionItem.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/montserrat_semibold.ttf"), Typeface.BOLD);
////                        tvCollectionItem.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_expand_less_24, 0);
//////                    notifyDataSetChanged();
//
////                        for(int i = 0 ; i < maincategories.size() ; i++){
//////                            if(maincategories.get(i).isSelected()){
////                            AppLogger.e("adapterPosition","-----"+ getAdapterPosition());
////                                if(i == getAdapterPosition()){
////                                    if(!maincategories.get(i).isSelected()) {
////                                        tvCollectionSubCategoryRecycleview.setVisibility(View.VISIBLE);
////                                        maincategories.get(getAdapterPosition()).setSelected(true);
////                                        SubCategoryAdapter subCatgeoryAdapter = new SubCategoryAdapter(activity, maincategories.get(getAdapterPosition()).getSubcategories());
////                                        tvCollectionSubCategoryRecycleview.setAdapter(subCatgeoryAdapter);
////                                        tvCollectionItem.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/montserrat_semibold.ttf"), Typeface.BOLD);
////                                        tvCollectionItem.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_expand_less_24, 0);
////                                        notifyItemChanged(getAdapterPosition());
////                                        AppLogger.e("If adapter position & I is same ", "---" + i);
////                                    }else {
////                                        maincategories.get(i).setSelected(false);
////                                        tvCollectionSubCategoryRecycleview.setVisibility(View.GONE);
////                                        tvCollectionItem.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/montserrat_regular.ttf"), Typeface.NORMAL);
////                                        tvCollectionItem.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_expand_more_24, 0);
////                                        notifyItemChanged(i);
////                                    }
////                                }else {
////                                    if(maincategories.get(i).isSelected()) {
////                                        maincategories.get(i).setSelected(false);
////                                        tvCollectionSubCategoryRecycleview.setVisibility(View.GONE);
////                                        tvCollectionItem.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/montserrat_regular.ttf"), Typeface.NORMAL);
////                                        tvCollectionItem.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_expand_more_24, 0);
////                                        notifyItemChanged(i);
////                                    }
////                                    AppLogger.e("If adapter position & I is different","---"+ i);
////                                }
//////                            }   else {
//////                            }
////                        }
////                    }
//
                    if (maincategories.get(getAdapterPosition()).isSelected()) {

                        maincategories.get(getAdapterPosition()).setSelected(false);
                        tvCollectionSubCategoryRecycleview.setVisibility(View.GONE);
                        tvCollectionItem.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/montserrat_regular.ttf"), Typeface.NORMAL);
                        tvCollectionItem.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_expand_more_24, 0);
//                        notifyItemChanged(getAdapterPosition());
                    }else {
                        for (int j = 0; j < maincategories.size(); j++) {
                            if (maincategories.get(j).isSelected()) {
                                maincategories.get(j).setSelected(false);
                                tvCollectionSubCategoryRecycleview.setVisibility(View.GONE);
                                tvCollectionItem.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/montserrat_regular.ttf"), Typeface.NORMAL);
                                tvCollectionItem.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_expand_more_24, 0);
                                notifyItemChanged(j);
//                                notify();
                            }
                        }
                        maincategories.get(getAdapterPosition()).setSelected(true);
                        tvCollectionSubCategoryRecycleview.setVisibility(View.VISIBLE);
                        SubCategoryAdapter subCatgeoryAdapter = new SubCategoryAdapter(activity, maincategories.get(getAdapterPosition()).getSubcategories());
                        tvCollectionSubCategoryRecycleview.setAdapter(subCatgeoryAdapter);
                        tvCollectionItem.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/montserrat_semibold.ttf"), Typeface.BOLD);
                        tvCollectionItem.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_expand_less_24, 0);
//                        notifyItemChanged(getAdapterPosition());
                        subCatgeoryAdapter.notifyItemChanged(getAdapterPosition());
                    }
                }  else {
                    Intent intent = new Intent(activity, ListAct.class);
                    intent.putExtra(AppConstants.ID, String.valueOf(maincategories.get(getAdapterPosition()).getId()));
                    intent.putExtra(AppConstants.NAME, maincategories.get(getAdapterPosition()).getName());
                    intent.putExtra(AppConstants.bannerListCheck, "");
                    activity.startActivity(intent);
                    drawer.closeDrawer(GravityCompat.START);
                }
            }else {
                Intent intent = new Intent(activity, ListAct.class);
                intent.putExtra(AppConstants.ID, String.valueOf(maincategories.get(getAdapterPosition()).getId()));
                intent.putExtra(AppConstants.NAME, maincategories.get(getAdapterPosition()).getName());
                intent.putExtra(AppConstants.bannerListCheck, "");
                activity.startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
            }

//            for (int j = 0; j < maincategories.size(); j++) {
//                if (maincategories.get(j).isSelected() && j != getAdapterPosition()) {
//                    tvCollectionSubCategoryRecycleview.setVisibility(View.GONE);
//                    maincategories.get(j).setSelected(false);
//                    AppLogger.e("Flag is updated or not ","-----"+ maincategories.get(j).isSelected());
//                    tvCollectionItem.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/montserrat_regular.ttf"), Typeface.NORMAL);
//                    tvCollectionItem.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_expand_more_24, 0);
//                    notifyItemChanged(j);
//                    AppLogger.e("Inside for loop Ifany category is selected : ","----j :- " + j);
//                }
//            }
//
//            if(maincategories.get(getAdapterPosition()).getSubcategories().size() >  1)
//            {
//
//                if(!maincategories.get(getAdapterPosition()).isSelected()){
//                    maincategories.get(getAdapterPosition()).setSelected(true);
//                    tvCollectionSubCategoryRecycleview.setVisibility(View.VISIBLE);
//                    SubCategoryAdapter subCatgeoryAdapter = new SubCategoryAdapter(activity, maincategories.get(getAdapterPosition()).getSubcategories());
//                    tvCollectionSubCategoryRecycleview.setAdapter(subCatgeoryAdapter);
//                    tvCollectionItem.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/montserrat_semibold.ttf"), Typeface.BOLD);
//                    tvCollectionItem.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_expand_less_24, 0);
//                    subCatgeoryAdapter.notifyItemChanged(getAdapterPosition());
//                }else {
//                    tvCollectionSubCategoryRecycleview.setVisibility(View.GONE);
//                    maincategories.get(getAdapterPosition()).setSelected(false);
//                    tvCollectionItem.setTypeface(Typeface.createFromAsset(activity.getAssets(), "fonts/montserrat_regular.ttf"), Typeface.NORMAL);
//                    tvCollectionItem.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_expand_more_24, 0);
//                }
//
//            } else {
//                Intent intent = new Intent(activity, ListAct.class);
//                intent.putExtra(AppConstants.ID, String.valueOf(maincategories.get(getAdapterPosition()).getId()));
//                intent.putExtra(AppConstants.NAME, maincategories.get(getAdapterPosition()).getName());
//                intent.putExtra(AppConstants.bannerListCheck, "");
//                activity.startActivity(intent);
//            }
        }

        @Override
        public boolean onLongClick(View v) {
//            Intent intent = new Intent(activity, ListAct.class);
//            intent.putExtra(AppConstants.ID, String.valueOf(maincategories.get(getAdapterPosition()).getId()));
//            intent.putExtra(AppConstants.NAME, maincategories.get(getAdapterPosition()).getName());
//            intent.putExtra(AppConstants.bannerListCheck, "");
//            activity.startActivity(intent);
            return false;
        }
    }
}

