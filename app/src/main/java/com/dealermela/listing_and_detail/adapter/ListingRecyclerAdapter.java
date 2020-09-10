package com.dealermela.listing_and_detail.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dealermela.R;
import com.dealermela.authentication.myaccount.activity.LoginAct;
import com.dealermela.home.activity.MainActivity;
import com.dealermela.listing_and_detail.activity.ProductDetailAct;
import com.dealermela.listing_and_detail.model.ListingItem;
import com.dealermela.listing_and_detail.model.RTSItem;
import com.dealermela.retrofit.APIClient;
import com.dealermela.retrofit.ApiInterface;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.dealermela.util.CommonUtils;
import com.dealermela.util.SharedPreferences;
import com.google.gson.JsonObject;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dealermela.home.activity.MainActivity.customerId;
import static com.dealermela.listing_and_detail.activity.FilterAct.filterFlag;
import static com.dealermela.other.activity.SplashAct.loginFlag;

public class ListingRecyclerAdapter extends RecyclerView.Adapter<ListingRecyclerAdapter.ViewHolder> {

    private final Activity activity;
    public final List<ListingItem.Datum> itemArrayList;
    private KProgressHUD hud;
    private SharedPreferences sharedPreferences;

    public ListingRecyclerAdapter(Activity activity, List<ListingItem.Datum> itemArrayList) {
        super();
        this.activity = activity;
        this.itemArrayList = itemArrayList;
        sharedPreferences=new SharedPreferences(activity);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.act_list_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {

//        if(!itemArrayList.get(i).getEntityId().equalsIgnoreCase("1430433")) {
            holder.mainLayout.setVisibility(View.VISIBLE);

            //Commented On 02/09/2020  Bcz Qty Label Not displaying from now
//            if (itemArrayList.get(i).getTypeId().equalsIgnoreCase("simple")) {
//                if (Integer.parseInt(itemArrayList.get(i).getQty()) > 1) {
//                    holder.tvQty.setVisibility(View.VISIBLE);
//                    holder.tvQty.setText("QTY " + itemArrayList.get(i).getQty());
//                } else {
//                    holder.tvQty.setVisibility(View.GONE);
//                }
//            } else {
//                holder.tvQty.setVisibility(View.GONE);
//            }

            sharedPreferences = new SharedPreferences(activity);
            if(sharedPreferences.getLoginData().equalsIgnoreCase("")){
                holder.imgDownload.setVisibility(View.GONE);
            }else
            {
                AppLogger.e("DownloadFlag_Sharedpreference","---" + sharedPreferences.getDownloadFlag());
                holder.imgDownload.setVisibility(View.VISIBLE);
            }

            //Temporarly Comment on 09-09-2020 bcz no use of stock parameter in listing page
//            if (itemArrayList.get(i).getStock().equalsIgnoreCase("0")) {
////            holder.tvSoldOut.setVisibility(View.VISIBLE);
//                holder.tvSoldOut.setVisibility(View.GONE);
//                holder.tvStockIn.setVisibility(View.GONE);
//            } else if (itemArrayList.get(i).getStock().equalsIgnoreCase("1")) {
//                holder.tvSoldOut.setVisibility(View.GONE);
//                holder.tvStockIn.setVisibility(View.GONE);
////            holder.tvStockIn.setVisibility(View.VISIBLE);
//            }

            holder.tvName.setText(itemArrayList.get(i).getName());
            String[] sku = itemArrayList.get(i).getSku().split(" ");

            AppLogger.e("sku", "------------" + itemArrayList.get(i).getSku());
            AppLogger.e("sku", "------------" + sku);
            StringBuilder stringBuilder = new StringBuilder();

            for (int j = 0; j <= sku.length - 1; j++) {
                if (j == 1) {
                    stringBuilder.append(sku[j]);
                    stringBuilder.append(" ");
                }
            /*if (j > 1) {
                stringBuilder.append(sku[j].charAt(0));
            }*/
            }

            float price = itemArrayList.get(i).getCustomPrice();
            holder.tvSku.setText(sku[0]);
            holder.tvPrice.setText(AppConstants.RS + CommonUtils.priceFormat(price));
            holder.tvGold.setText(stringBuilder);

            Glide.with(activity)
                    .load(AppConstants.IMAGE_URL + "catalog/product" + itemArrayList.get(i).getThumbnail())
//                .load(itemArrayList.get(i).getImages())
                    .apply(new RequestOptions().placeholder(R.drawable.diamondmela_logo).error(R.drawable.diamondmela_logo))
                    .into(holder.imgProduct);

            AppLogger.e("getDownload_flag()","-----------"+itemArrayList.get(i).getDownloadFlag());

            if (itemArrayList.get(i).getDownloadFlag() == 1) {
                holder.imgDownload.setEnabled(false);
                holder.imgDownload.setColorFilter(ContextCompat.getColor(activity, R.color.download_disabled), android.graphics.PorterDuff.Mode.SRC_IN);
            } else if (itemArrayList.get(i).getDownloadFlag() == 0) {
                holder.imgDownload.setEnabled(true);
                holder.imgDownload.setColorFilter(ContextCompat.getColor(activity, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);
            }

            holder.imgDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sharedPreferences.getLoginData().equalsIgnoreCase("")){
                        Snackbar snackBar = Snackbar
                                .make(v, "Please first login", Snackbar.LENGTH_LONG)
                                .setAction("Login", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        loginFlag = 0;
                                        Intent intent = new Intent(activity, LoginAct.class);
                                        activity.startActivity(intent);
                                    }
                                });
                        snackBar.setActionTextColor(Color.RED);
                        View snackBarView = snackBar.getView();
//                    snackBarView.setBackgroundColor(Color.DKGRAY);
                        TextView textView = snackBarView.findViewById(android.support.design.R.id.snackbar_text);
                        textView.setTextColor(Color.WHITE);
                        snackBar.show();

                    }else {
                        downloadProduct(customerId, itemArrayList.get(i).getEntityId());
                        itemArrayList.get(i).setDownloadFlag(1);
                        notifyItemChanged(i);
                    }
                }
            });
//        }else {
//            itemArrayList.remove(i);
////            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
////            notifyItemRemoved(i);
////            notifyItemChanged(i);
//            holder.mainLayout.setVisibility(View.GONE);
//        }
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        final TextView tvQty;
        final TextView tvSoldOut;
        final TextView tvStockIn;
        final TextView tvName;
        final TextView tvPrice;
        final TextView tvSku;
        final TextView tvGold;
        final ImageView imgDownload;
        final ImageView imgProduct;
        final RelativeLayout mainLayout;

        ViewHolder(View itemView) {
            super(itemView);

            mainLayout = itemView.findViewById(R.id.mainLayout);
            tvQty = itemView.findViewById(R.id.tvQty);
            tvStockIn = itemView.findViewById(R.id.tvStockIn);
            tvSoldOut = itemView.findViewById(R.id.tvSoldOut);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvSku = itemView.findViewById(R.id.tvSku);
            tvGold = itemView.findViewById(R.id.tvGold);
            imgDownload = itemView.findViewById(R.id.imgDownload);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
//            filterFlag = 0;
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

    private void downloadProduct(String customerId, String productId) {
        //show progress
        hud = KProgressHUD.create(activity)
                .setCancellable(false)
                .setLabel("Please wait")
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);

        hud.show();
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);

        AppLogger.e("customerId", "--------" + customerId);
        AppLogger.e("productId", "--------" + productId);

        Call<JsonObject> callApi = apiInterface.downloadProduct(customerId, productId);
        callApi.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                assert response.body() != null;
                hud.dismiss();
                AppLogger.e(AppConstants.RESPONSE, "-----------" + response.body());
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    if (jsonObject.getString("status").equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
                        CommonUtils.showSuccessToast(activity, "Product added in download list");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                AppLogger.e("error", "-----------" + t.getMessage());
                hud.dismiss();
            }
        });
    }

    public void updateDownloadFlag(){
        for (int i=0;i<=itemArrayList.size()-1;i++){
            itemArrayList.get(i).setDownloadFlag(1);
            notifyItemChanged(i);
        }
    }
}
