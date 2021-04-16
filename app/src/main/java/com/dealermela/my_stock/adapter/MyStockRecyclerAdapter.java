package com.dealermela.my_stock.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dealermela.R;
import com.dealermela.my_stock.model.MyStockItem;
import com.dealermela.retrofit.APIClient;
import com.dealermela.retrofit.ApiInterface;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.dealermela.util.CommonUtils;
import com.google.gson.JsonObject;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dealermela.home.activity.MainActivity.customerId;

public class MyStockRecyclerAdapter extends RecyclerView.Adapter<MyStockRecyclerAdapter.ViewHolder> {

    private final Activity activity;
    private final List<MyStockItem.Datum> itemArrayList;
    private KProgressHUD hud;

    public MyStockRecyclerAdapter(Activity activity, List<MyStockItem.Datum> itemArrayList) {
        super();
        this.activity = activity;
        this.itemArrayList = itemArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.act_mystock_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {


        holder.tvName.setText(itemArrayList.get(i).getSku());

//        float price = itemArrayList.get(i).getPrice();
        holder.tvSku.setText(itemArrayList.get(i).getSku());
        holder.tvPrice.setText(AppConstants.RS + CommonUtils.priceFormat(itemArrayList.get(i).getPrice()));
        holder.tvGold.setText(itemArrayList.get(i).getMetalQuality());

        Glide.with(activity)
                .load(itemArrayList.get(i).getImage())
                .apply(new RequestOptions().placeholder(R.drawable.dml_logo).error(R.drawable.dml_logo))
                .into(holder.imgProduct);


        holder.btnSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saleProduct(itemArrayList.get(i).getProduct_id());
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        final TextView tvQty;
        final TextView tvName;
        final TextView tvPrice;
        final TextView tvSku;
        final TextView tvGold;
        final ImageView imgDownload;
        final ImageView imgProduct;
        final Button btnSale;

        ViewHolder(View itemView) {
            super(itemView);

            tvQty = itemView.findViewById(R.id.tvQty);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvSku = itemView.findViewById(R.id.tvSku);
            tvGold = itemView.findViewById(R.id.tvGold);
            imgDownload = itemView.findViewById(R.id.imgDownload);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            btnSale = itemView.findViewById(R.id.btnSale);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View v) {

//            filterFlag = 0;
            /*Intent intent = new Intent(activity, ProductDetailAct.class);
            intent.putExtra(AppConstants.NAME, itemArrayList.get(getAdapterPosition()).getEntityId());
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);*/

        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }


    }


    private void saleProduct(String product) {
        //show progress
        hud = KProgressHUD.create(activity)
                .setCancellable(false)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
        hud.show();
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<JsonObject> callApi = apiInterface.MyStockplaceOrder(product, customerId);
        callApi.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                assert response.body() != null;
                hud.dismiss();
                AppLogger.e(AppConstants.RESPONSE, "-----------" + response.body());
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    if (jsonObject.getString("status").equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
                        CommonUtils.showSuccessToast(activity, jsonObject.getString("message"));
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

}
