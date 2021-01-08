package com.dealermela.listing_and_detail.adapter;

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
import com.dealermela.listing_and_detail.activity.FullProductDetailImageAct;
import com.dealermela.util.AppLogger;

import java.io.Serializable;
import java.util.List;

public class ImageSliderAdapter extends androidx.viewpager.widget.PagerAdapter {

    private final Context mContext;
    private final LayoutInflater layoutInflater;
    private final List<String> imageItemArrayList;

    public ImageSliderAdapter(Context context, List<String> imageItemArrayList) {
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
        View itemView = layoutInflater.inflate(R.layout.act_product_detail_item_image_slider, container, false);

        final ImageView imgBanner = itemView.findViewById(R.id.imgBanner);
        final ProgressBar progressBar = itemView.findViewById(R.id.progress);

        AppLogger.e("image url", "--------" + imageItemArrayList.get(position));
        progressBar.setVisibility(View.GONE);
        Glide.with(mContext)
                .load(imageItemArrayList.get(position))
                .apply(new RequestOptions().placeholder(R.drawable.dml_logo).error(R.drawable.dml_logo))
                .into(imgBanner);

        container.addView(itemView);

        imgBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, FullProductDetailImageAct.class);
                intent.putExtra("imageList", (Serializable) imageItemArrayList);
                intent.putExtra("position", position);
                mContext.startActivity(intent);
            }
        });

        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }
}
