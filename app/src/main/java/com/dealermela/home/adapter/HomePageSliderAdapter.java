package com.dealermela.home.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dealermela.R;
import com.dealermela.home.model.BannerSliderItem;
import com.dealermela.listing_and_detail.activity.ListAct;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;

import java.util.HashMap;
import java.util.List;

public class HomePageSliderAdapter extends 	androidx.viewpager.widget.PagerAdapter {

    private final Context mContext;
    private final LayoutInflater layoutInflater;
    private final List<BannerSliderItem.SliderImage> imageItemArrayList;

    public HomePageSliderAdapter(Context context, List<BannerSliderItem.SliderImage> imageItemArrayList) {
        mContext = context;
        this.imageItemArrayList = imageItemArrayList;
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return imageItemArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.frg_home_item_home_slider, container, false);

        final ImageView imgBanner = itemView.findViewById(R.id.imgBanner);
        final ProgressBar progressBar = itemView.findViewById(R.id.progress);

        AppLogger.e("image url", "--------" + AppConstants.IMAGE_URL + imageItemArrayList.get(position).getImageUrl());
        progressBar.setVisibility(View.GONE);
        Glide.with(mContext)
//                .load("http://123.108.51.11/media/wysiwyg/application_banner1.jpg")
                .load(AppConstants.IMAGE_URL + imageItemArrayList.get(position).getImageUrl())
                .apply(new RequestOptions().placeholder(R.drawable.dml_logo).error(R.drawable.dml_logo))
                .into(imgBanner);

//        imgBanner.setImageResource(imageItemArrayList.get(position));
        container.addView(itemView);

        imgBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppLogger.e("data attr", "--------------" + imageItemArrayList.get(position).getDataAttr());

//                if(imageItemArrayList.get(position).getDataAttr().equalsIgnoreCase("")){
//
//                }else if(imageItemArrayList.get(position).getDataAttr().equalsIgnoreCase("")) {
//
//                }

                if (!imageItemArrayList.get(position).getDataAttr().equalsIgnoreCase("")) {

                    HashMap<String, String> bannerFilter = new HashMap<>();
                    String[] str = imageItemArrayList.get(position).getDataAttr().split("\\|");

                    AppLogger.e("array", "-----------" + str);
                    AppLogger.e("array1", "-----------" + str[0]);
                    AppLogger.e("array2", "-----------" + str[1]);
                    AppLogger.e("size", "-----------" + str.length);

//                    for (int j = 0; j < str.length; j++) {
//                        String[] keyValue = str[j].split("#");
//                        AppLogger.e("key value", "-----------" + keyValue);
//                        AppLogger.e("key value", "-----------" + keyValue[0]);
//                        AppLogger.e("key value", "-----------" + keyValue[1]);
//                        bannerFilter.put(str[0], str[1]);
//                        if (j == str.length - 1) {
                            Intent intent = new Intent(mContext, ListAct.class);
//                            if (bannerFilter.containsKey("category_id")) {
//                                intent.putExtra(AppConstants.ID, bannerFilter.get("category_id"));
//                            } else {
//                                intent.putExtra(AppConstants.ID, "");
//                            }
                            intent.putExtra(AppConstants.ID,str[0] );
                            if(str[1].equalsIgnoreCase("pendentsets")){
                                intent.putExtra(AppConstants.NAME, "Pendants & Sets");
                            }else {
                                intent.putExtra(AppConstants.NAME, str[1]);
                            }
                            intent.putExtra(AppConstants.bannerListCheck, "Banner");
                            intent.putExtra("map", bannerFilter);
                            mContext.startActivity(intent);
//                        }
//                    }
                }
            }
        });
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}
