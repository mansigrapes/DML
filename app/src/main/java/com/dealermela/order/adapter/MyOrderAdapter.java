package com.dealermela.order.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dealermela.R;
import com.dealermela.authentication.myaccount.model.LoginResponse;
import com.dealermela.listing_and_detail.activity.ProductDetailAct;
import com.dealermela.order.activity.OrderDetailAct;
import com.dealermela.order.activity.OrderPrintAct;
import com.dealermela.order.model.OrderItem;
import com.dealermela.retrofit.APIClient;
import com.dealermela.retrofit.ApiInterface;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.dealermela.util.CommonUtils;
import com.dealermela.util.SharedPreferences;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.ligl.android.widget.iosdialog.IOSDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dealermela.home.activity.MainActivity.GroupId;
import static com.dealermela.home.activity.MainActivity.customerId;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {

    private final Activity activity;
    private final List<OrderItem.Datum> itemArrayList;
    private KProgressHUD hud;

    public MyOrderAdapter(Activity activity, List<OrderItem.Datum> itemArrayList) {
        super();
        this.activity = activity;
        this.itemArrayList = itemArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.frg_my_order_item_adapter, viewGroup, false);
        return new ViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int i) {

//        AppLogger.e("item", "----------------" + itemArrayList.get(i).getOrderItems().get(0).getProductName());
//        AppLogger.e("position", "----------------" + i);

        if (itemArrayList.get(i).getOrderItems().isEmpty()) {
            holder.tvTitle.setText(Html.fromHtml("<b>" + "Title : " + "</b> "));
            holder.tvSku.setText(Html.fromHtml("<b>" + "Sku : " + "</b> "));
            holder.tvMetalDetail.setText(Html.fromHtml("<b>" + "Metal Detail : " + "</b> "));
            holder.tvStoneDetail.setText(Html.fromHtml("<b>" + "Stone Detail : " + "</b> "));
            holder.imgProduct.setImageURI("http://123.108.51.11/media/catalog/product/cache/1/small_image/9df78eab33525d08d6e5fb8d27136e95/placeholder/default/def_2.png");
        } else {
            holder.tvTitle.setText(itemArrayList.get(i).getOrderItems().get(0).getProductName());

            String[] strings = itemArrayList.get(i).getOrderItems().get(0).getProductSku().split(" ");
            for (int j = 0; j < strings.length; j++) {
                if (j == 0) {
                    holder.tvSku.setText(Html.fromHtml("<b>" + "Sku : " + "</b> " + strings[j]));
                }
            }
//            holder.tvSku.setText(Html.fromHtml("<b>" + "Sku : " + "</b> " + itemArrayList.get(i).getOrderItems().get(0).getProductSku()));
            holder.tvMetalDetail.setText(Html.fromHtml("<b>" + "Metal Detail : " + "</b> " + itemArrayList.get(i).getOrderItems().get(0).getProductMetalquality() + "<b> | </b>" + itemArrayList.get(i).getOrderItems().get(0).getProductMetalweight() ));

            if(itemArrayList.get(i).getOrderItems().get(0).getProductStonequality() != null &&  itemArrayList.get(i).getOrderItems().get(0).getProductStoneweight() != null)
            {
                holder.tvStoneDetail.setText(Html.fromHtml("<b>" + "Stone Detail : " + "</b> " + itemArrayList.get(i).getOrderItems().get(0).getProductStonequality() + "<b> | </b>" + itemArrayList.get(i).getOrderItems().get(0).getProductStoneweight()));
            } else if (itemArrayList.get(i).getOrderItems().get(0).getProductStonequality() != null &&  itemArrayList.get(i).getOrderItems().get(0).getProductStoneweight() == null){
                holder.tvStoneDetail.setText(Html.fromHtml("<b>" + "Stone Detail : " + "</b> " + itemArrayList.get(i).getOrderItems().get(0).getProductStonequality() + "<b> | </b>" + " N/A " ));
            } else if (itemArrayList.get(i).getOrderItems().get(0).getProductStonequality() == null &&  itemArrayList.get(i).getOrderItems().get(0).getProductStoneweight() != null){
                holder.tvStoneDetail.setText(Html.fromHtml("<b>" + "Stone Detail : " + "</b> " + " N/A " + "<b> | </b>" + itemArrayList.get(i).getOrderItems().get(0).getProductStoneweight() ));
            } else {
                holder.tvStoneDetail.setText(Html.fromHtml("<b>" + "Stone Detail : " + "</b> " + " N/A " + "<b> | </b>" + " N/A " ));
            }

            holder.tvtotalItem.setText(Html.fromHtml("<b>" + "Total Qty : " + "</b>" + itemArrayList.get(i).getTotalCount()));

//            if(!itemArrayList.get(i).getOrderItems().get(0).getProductStonequality().isEmpty())
//            {
//                holder.tvStoneDetail.setText(Html.fromHtml("<b>" + "Stone Detail : " + "</b> " + itemArrayList.get(i).getOrderItems().get(0).getProductStonequality() + "<b> | </b>" + itemArrayList.get(i).getOrderItems().get(0).getProductStoneweight() ));
//            } else {
//                holder.tvStoneDetail.setText(Html.fromHtml("<b>" + "Stone Detail : " + "</b> " + " N/A "));
//            }

            AppLogger.e("image url","----"+itemArrayList.get(i).getOrderItems().get(0).getImage());

//            if(itemArrayList.get(i).getOrderItems().get(0).getImage().isEmpty()){
//                holder.imgProduct.setImageResource(R.drawable.dml_logo);
//            }else {
//                holder.imgProduct.setImageURI(itemArrayList.get(i).getOrderItems().get(0).getImage());
//            }

            holder.imgProduct.setImageURI(itemArrayList.get(i).getOrderItems().get(0).getImage());

            if(!itemArrayList.get(i).getOrderItems().get(0).getCertificateNo().isEmpty()){
                holder.tvcertificate.setText(Html.fromHtml("<b>" + "Certificate : " + "</b> " + itemArrayList.get(i).getOrderItems().get(0).getCertificateNo()));
            }else {
                holder.tvcertificate.setText(Html.fromHtml("<b>" + "Certificate : " + " N/A " + "</b>"));
            }
        }

        holder.tvOrderNo.setText(Html.fromHtml("<b>" + "Order No : " + "</b> " + itemArrayList.get(i).getOrderno()));
        float price = Float.parseFloat(itemArrayList.get(i).getGrandTotal());

        //Add This when user's grpid-6 And Invoicesetting is 0 so display subtotal value of order
        if(itemArrayList.get(i).getInvoiceSetting() == "0" && GroupId == "6"){
            holder.tvGrandTotal.setText(Html.fromHtml("<b>" + "Sub Total : " + "</b> " + AppConstants.RS + CommonUtils.priceFormat(price)));
        }else if(itemArrayList.get(i).getInvoiceSetting() == null){
            holder.tvGrandTotal.setText(Html.fromHtml("<b>" + "Grand Total : " + "</b> " + AppConstants.RS + CommonUtils.priceFormat(price)));
        }else {
            holder.tvGrandTotal.setText(Html.fromHtml("<b>" + "Grand Total : " + "</b> " + AppConstants.RS + CommonUtils.priceFormat(price)));
        }

        holder.tvStatus.setText(itemArrayList.get(i).getOrderStatus());

        if (itemArrayList.get(i).getOrderStatus().equalsIgnoreCase("Canceled")) {
            holder.imgCancel.setVisibility(View.GONE);
            holder.imgPrint.setVisibility(View.GONE);
            holder.imgView.setVisibility(View.VISIBLE);
            AppLogger.e("canceled", "------");
        }

        if (itemArrayList.get(i).getOrderStatus().equalsIgnoreCase("Pending")) {
            holder.imgPrint.setVisibility(View.GONE);
            holder.imgCancel.setVisibility(View.VISIBLE);
            holder.imgView.setVisibility(View.VISIBLE);
            AppLogger.e("pending", "------");
        }

        if (itemArrayList.get(i).getOrderStatus().equalsIgnoreCase("Completed")) {
            holder.imgPrint.setVisibility(View.GONE);
            holder.imgCancel.setVisibility(View.GONE);
            holder.imgView.setVisibility(View.VISIBLE);
            AppLogger.e("Completed", "------");
        }

//        if (itemArrayList.get(i).getOrderItems().size() > 1) {
//            holder.cardViewMore.setVisibility(View.VISIBLE);
//        } else {
//            holder.cardViewMore.setVisibility(View.GONE);
//        }
        AppLogger.e("size", "----------------" + itemArrayList.get(i).getOrderItems().size());

        holder.imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, OrderDetailAct.class);
                intent.putExtra(AppConstants.ORDER_ID, itemArrayList.get(i).getOrderid());
                intent.putExtra("status", itemArrayList.get(i).getOrderStatus());
                intent.putExtra("position",i);
                activity.startActivity(intent);
            }
        });

        holder.imgPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppLogger.e("order id", "------" + itemArrayList.get(i).getOrderid());
                printOrder(itemArrayList.get(i).getOrderid());
            }
        });

        holder.imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new IOSDialog.Builder(activity)
                        .setTitle("Cancel Order")
                        .setMessage("Are you sure to cancel order?")
                        .setCancelable(false)
                        .setPositiveButton(activity.getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                cancelOrder(itemArrayList.get(i).getOrderid(), i);
                            }
                        })
                        .setNegativeButton(activity.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });

//        holder.cardViewMore.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(activity, OrderDetailAct.class);
//                intent.putExtra(AppConstants.ORDER_ID, itemArrayList.get(i).getOrderid());
//                intent.putExtra("status", itemArrayList.get(i).getOrderStatus());
//                activity.startActivity(intent);
//            }
//        });

        holder.imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ProductDetailAct.class);
                intent.putExtra(AppConstants.NAME,itemArrayList.get(i).getOrderItems().get(0).getProductId());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        final TextView tvTitle, tvOrderNo, tvSku, tvMetalDetail, tvStoneDetail, tvGrandTotal, tvStatus, tvcertificate, tvtotalItem;
        final ImageView imgView, imgPrint, imgCancel;
        final SimpleDraweeView imgProduct;
//        final CardView cardViewMore;

        ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOrderNo = itemView.findViewById(R.id.tvOrderNo);
            tvSku = itemView.findViewById(R.id.tvSku);
            tvMetalDetail = itemView.findViewById(R.id.tvMetalDetail);
            tvStoneDetail = itemView.findViewById(R.id.tvStoneDetail);
            tvGrandTotal = itemView.findViewById(R.id.tvGrandTotal);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvcertificate = itemView.findViewById(R.id.tvcertificate);
            imgView = itemView.findViewById(R.id.imgView);
            imgPrint = itemView.findViewById(R.id.imgPrint);
            imgCancel = itemView.findViewById(R.id.imgCancel);
            imgProduct = itemView.findViewById(R.id.imgProduct);
//            cardViewMore = itemView.findViewById(R.id.cardViewMore);
            tvtotalItem = itemView.findViewById(R.id.tvtotalItem);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(activity, OrderDetailAct.class);
            intent.putExtra(AppConstants.ORDER_ID, itemArrayList.get(getAdapterPosition()).getOrderid());
            intent.putExtra("status", itemArrayList.get(getAdapterPosition()).getOrderStatus());
            activity.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

    private void cancelOrder(String orderId, final int cPosition) {

        //show progress
        hud = KProgressHUD.create(activity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait");

        hud.show();

        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<JsonObject> callApi = apiInterface.cancelOrder(orderId);
        callApi.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                assert response.body() != null;
                AppLogger.e(AppConstants.RESPONSE, "---------" + response.body());
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        if (jsonObject.getString("status").equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
                            hud.dismiss();
                            itemArrayList.get(cPosition).setOrderStatus("Canceled");
                            notifyItemChanged(cPosition);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        hud.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                AppLogger.e("error", "------------" + t.getMessage());
            }
        });
    }

    private void printOrder(String orderId) {
        LoginResponse loginResponse;
        SharedPreferences sharedPreferences = new SharedPreferences(activity);
        Gson gson = new Gson();
        loginResponse = gson.fromJson(sharedPreferences.getLoginData(), LoginResponse.class);

        //show progress
        hud = KProgressHUD.create(activity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait");
        hud.show();

        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<JsonObject> callApi = apiInterface.printOrder(orderId, customerId, loginResponse.getData().getGroupId());
        callApi.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                assert response.body() != null;
                AppLogger.e(AppConstants.RESPONSE, "---------" + response.body());
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        if (jsonObject.getString("status").equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
                            hud.dismiss();
                            String pdfUrl = jsonObject.getString("pdf");
                            Intent intent = new Intent(activity, OrderPrintAct.class);
                            intent.putExtra(AppConstants.NAME, pdfUrl);
                            activity.startActivity(intent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        hud.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                AppLogger.e("error", "------------" + t.getMessage());
            }
        });
    }

////   Not working this
//    public void onBackPressed(){
//        AppLogger.e("MyOrderAdapter ","Back Pressed---");
//        if(Orderflag == 1){
//            Orderflag = 0;
////            startNewActivity(MainActivity.class);
//        }else {
////            startNewActivity(MainActivity.class);
//        }
//    }
}
