package com.dealermela.listing_and_detail.adapter;

import android.content.Context;

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
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;

import java.util.List;

public class FullImageSliderAdapter extends androidx.viewpager.widget.PagerAdapter {

    private final Context mContext;
    private final LayoutInflater layoutInflater;
    private final List<String> imageItemArrayList;

    public FullImageSliderAdapter(Context context, List<String> imageItemArrayList) {
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
        View itemView = layoutInflater.inflate(R.layout.act_product_detail_item_full_image_slider, container, false);

        final ImageView imgBanner = itemView.findViewById(R.id.imgBanner);
        final ProgressBar progressBar = itemView.findViewById(R.id.progress);

        AppLogger.e("image url", "--------" + AppConstants.IMAGE_URL + imageItemArrayList.get(position));
        progressBar.setVisibility(View.GONE);
        Glide.with(mContext)
                .load(imageItemArrayList.get(position))
                .apply(new RequestOptions().placeholder(R.drawable.dml_logo).error(R.drawable.dml_logo))
                .into(imgBanner);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}
