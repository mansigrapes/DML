package com.dealermela.listing_and_detail.activity;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dealermela.DealerMelaBaseActivity;
import com.dealermela.R;
import com.dealermela.listing_and_detail.adapter.FullImageSliderAdapter;

import java.util.ArrayList;

public class FullProductDetailImageAct extends DealerMelaBaseActivity {

    private ViewPager viewPagerFullImage;
    //using slider images horizontal
    private HorizontalScrollView hsvSlider;

    //using slider images horizontal item and progress loader
    private LinearLayout linContainer;
    //using check current position
    private static int c_position = 0;

    ArrayList<String> myList;
    private int position=0;
    private ImageView imgClose;


    @Override
    protected int getLayoutResourceId() {
        return R.layout.acti_full_product_detail_image;
    }

    @Override
    public void init() {
        myList = (ArrayList<String>) getIntent().getSerializableExtra("imageList");
        position = getIntent().getIntExtra("position",0);
    }

    @Override
    public void initView() {
        hsvSlider = findViewById(R.id.hsvSlider);
        linContainer = findViewById(R.id.linContainer);
        viewPagerFullImage = findViewById(R.id.viewPagerFullImage);
        imgClose = findViewById(R.id.imgClose);
    }

    @Override
    public void postInitView() {
        FullImageSliderAdapter imageSliderAdapter = new FullImageSliderAdapter(FullProductDetailImageAct.this, myList);
        viewPagerFullImage.setAdapter(imageSliderAdapter);
        viewPagerFullImage.setCurrentItem(position);
        inflateThumbnails();
    }

    @Override
    public void addListener() {
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        viewPagerFullImage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                c_position = position;
                inflateThumbnails();
                if (position == 0) {
                    hsvSlider.scrollTo(0, 0);
                } else {
                    int x = 160 * position;
                    hsvSlider.scrollTo(x, 0);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void loadData() {
    }

    //add horizontal slide image
    private void inflateThumbnails() {
        linContainer.removeAllViews();
        View imageLayout;
        ImageView imageView;
        ConstraintLayout main_layout;
        for (int i = 0; i < myList.size(); i++) {
            imageLayout = getLayoutInflater().inflate(R.layout.act_product_detail_item_image, null);
            imageView = imageLayout.findViewById(R.id.image_linear);
            main_layout = imageLayout.findViewById(R.id.main_layout);

            imageView.setOnClickListener(
                    onChagePageClickListener(i)
            );

            if (c_position == i) {
                imageView.setBackground(getResources().getDrawable(R.drawable.product_item_select));
            } else {
                imageView.setBackground(getResources().getDrawable(R.drawable.product_item_un_select));
            }

            Glide.with(FullProductDetailImageAct.this)
                    .load(myList.get(i))
                    .apply(new RequestOptions().placeholder(R.drawable.dml_logo).error(R.drawable.dml_logo))
                    .into(imageView);
//            imageView.setImageResource(images.get(i).getFile());
            linContainer.addView(imageLayout);
        }
    }


    //horizontal slide image click
    private View.OnClickListener onChagePageClickListener(final int i) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPagerFullImage.setCurrentItem(i);
                c_position = i;
                inflateThumbnails();
            }
        };
    }
}
