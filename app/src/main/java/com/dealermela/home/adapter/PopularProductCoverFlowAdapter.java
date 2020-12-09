package com.dealermela.home.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dealermela.R;
import com.dealermela.home.model.PopularProductItem;
import com.dealermela.listing_and_detail.activity.ProductDetailAct;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.dealermela.util.CommonUtils;

import java.util.List;

public class PopularProductCoverFlowAdapter extends androidx.viewpager.widget.PagerAdapter  {

    private List<PopularProductItem.ProductImg> data;
    private Activity activity;
    private LayoutInflater layoutInflater;

    private int screenWidth;
    private int screenHeight;

    public PopularProductCoverFlowAdapter(Activity context, List<PopularProductItem.ProductImg> objects) {
        this.activity = context;
        this.data = objects;
        getWidthAndHeight();
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
//        int count = 0;
//        try {
//            count = data.size() * 2;
//            AppLogger.e("AdapterSize","----"+ count);
//        } catch (Exception e) {
//            // TODO: handle exception
//            e.printStackTrace();
//        }
//        return count;

        return data.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == object);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

//        TextView view = new TextView(activity);
//        view.setText("Item " + position);
//        view.setGravity(Gravity.CENTER);
//        view.setBackgroundColor(Color.argb(255, position * 50, position * 10, position * 50));
//        container.addView(view);

//        ViewHolder viewHolder;
//        if (container == null) {
//            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            container = inflater.inflate(R.layout.frg_home_item_cover_flow_slider, null, false);
//
//            viewHolder = new ViewHolder(container);
//            container.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) container.getTag();
//        }

        View itemview = layoutInflater.inflate(R.layout.frg_home_item_cover_flow_slider,container,false);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(screenWidth / 2, screenHeight / 2);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((screenWidth / 3) + 100 , screenHeight / 2);

        LinearLayout linearlayout_Popularproduct = itemview.findViewById(R.id.linearlayout_Popularproduct);
        ImageView imgPopularProduct = itemview.findViewById(R.id.imgPopularProduct);
        TextView tvPrice = itemview.findViewById(R.id.tvPrice);

        linearlayout_Popularproduct.setLayoutParams(layoutParams);
        imgPopularProduct.setLayoutParams(layoutParams);

        Glide.with(activity)
//                .load("http://123.108.51.11/media/wysiwyg/application_banner1.jpg")
//                .load("https://storage.googleapis.com/media.nacjewellers.com/resources/dist/jewellery/gold/rings/elegant-golden-curl-ring-l.png")
//                .load(AppConstants.IMAGE_URL+"catalog/product"+data.get(position).getThumbnail())
                .load(AppConstants.IMAGE_URL + "catalog/product"+ data.get(position).getThumbnail())
                .apply(new RequestOptions().placeholder(R.drawable.dml_logo).error(R.drawable.dml_logo))
                .into(imgPopularProduct);

//        container.setOnClickListener(onClickListener(position));

        float price = data.get(position).getCustomPrice();
        tvPrice.setText(AppConstants.RS + CommonUtils.priceFormat(price));
        container.addView(itemview);

        imgPopularProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ProductDetailAct.class);
                intent.putExtra(AppConstants.NAME, data.get(position).getEntityId());
                intent.putExtra(AppConstants.ID, data.get(position).getAttributeSetId());
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            }
        });

        return itemview;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
//        container.removeView((FrameLayout) object);
    }

    private void getWidthAndHeight() {

        DisplayMetrics displaymetrics = new DisplayMetrics();

        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        screenHeight = displaymetrics.heightPixels;
        screenWidth = displaymetrics.widthPixels;

        AppLogger.e("HomeFrg ","--- Screen Width -------" + screenWidth);
        AppLogger.e("HomeFrg ","--- Screen Height -------" + screenHeight);
    }

}


//public class PopularProductCoverFlowAdapter extends BaseAdapter {
//
//    private List<PopularProductItem.ProductImg> data;
//    private Activity activity;
//
//    public PopularProductCoverFlowAdapter(Activity context, List<PopularProductItem.ProductImg> objects) {
//        this.activity = context;
//        this.data = objects;
//    }
//
//    @Override
//    public int getCount() {
//        return data.size();
//    }
//
//    @Override
//    public PopularProductItem.ProductImg getItem(int position) {
//        return data.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.N)
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        ViewHolder viewHolder;
//
//        if (convertView == null) {
//            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = inflater.inflate(R.layout.frg_home_item_cover_flow_slider, null, false);
//
//            viewHolder = new ViewHolder(convertView);
//            convertView.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//
//
//        Glide.with(activity)
////                .load("http://123.108.51.11/media/wysiwyg/application_banner1.jpg")
////                .load("https://storage.googleapis.com/media.nacjewellers.com/resources/dist/jewellery/gold/rings/elegant-golden-curl-ring-l.png")
//                .load(AppConstants.IMAGE_URL+"catalog/product"+data.get(position).getThumbnail())
//                .apply(new RequestOptions().placeholder(R.drawable.dml_logo).error(R.drawable.dml_logo))
//                .into(viewHolder.imgPopularProduct);
//        convertView.setOnClickListener(onClickListener(position));
//
//        float price = data.get(position).getCustomPrice();
//        viewHolder.tvPrice.setText(AppConstants.RS + CommonUtils.priceFormat(price));
//
//        return convertView;
//    }
//
//    private View.OnClickListener onClickListener(final int position) {
//        return new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(activity, ProductDetailAct.class);
//                intent.putExtra(AppConstants.NAME, data.get(position).getEntityId());
//                intent.putExtra(AppConstants.ID, data.get(position).getAttributeSetId());
//                activity.startActivity(intent);
//                activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
//            }
//        };
//    }
//
//    private static class ViewHolder {
//        private ImageView imgPopularProduct;
//        private TextView tvPrice;
//
//        public ViewHolder(View v) {
//            imgPopularProduct = (ImageView) v.findViewById(R.id.imgPopularProduct);
//            tvPrice =  v.findViewById(R.id.tvPrice);
//        }
//    }
//}
