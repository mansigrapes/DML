package com.dealermela.cart.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dealermela.DealerMelaBaseActivity;
import com.dealermela.R;
import com.dealermela.cart.fragment.ShoppingFrg;
import com.dealermela.cart.model.CartLocalDataItem;
import com.dealermela.cart.model.CartServerDataItem;
import com.dealermela.download.activity.DownloadAct;
import com.dealermela.retrofit.APIClient;
import com.dealermela.retrofit.ApiInterface;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.dealermela.util.CommonUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.JsonObject;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.ligl.android.widget.iosdialog.IOSDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dealermela.DealerMelaBaseActivity.cartCount;
import static com.dealermela.home.activity.MainActivity.customerId;


public class CartServerAdapter extends RecyclerView.Adapter<CartServerAdapter.ViewHolder> {

    private final Activity activity;
    private final ShoppingFrg shoppingFrg;
    private final List<CartServerDataItem.Datum> itemArrayList;
    private KProgressHUD hud;

    public CartServerAdapter(Activity activity, List<CartServerDataItem.Datum> itemArrayList, ShoppingFrg shoppingFrg) {
        super();
        this.activity = activity;
        this.itemArrayList = itemArrayList;
        this.shoppingFrg = shoppingFrg;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.frg_shopping_cart_item_list, viewGroup, false);
        return new ViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {

        holder.tvSku.setText(Html.fromHtml("<b>" + "SKU : " + "</b> " + itemArrayList.get(i).getSku()));
        holder.tvMetalDetail.setText(Html.fromHtml("<b>" + "Metal Detail : " + "</b> " + itemArrayList.get(i).getMetaldetails()));
        holder.tvStoneDetail.setText(Html.fromHtml("<b>" + "Stone Detail : " + "</b> " + itemArrayList.get(i).getStonedetails()));
        holder.tvPrice.setText(Html.fromHtml("<b>" + AppConstants.RS + CommonUtils.priceFormat(Float.parseFloat(itemArrayList.get(i).getPrice())) + "</b> "));
        holder.tvQuantity.setText(itemArrayList.get(i).getQty());

        String sourceString = "";
        if (itemArrayList.get(i).getRingsize() != null) {
            sourceString = "<b>" + "Ring Size : " + "</b> " + itemArrayList.get(i).getRingsize();
            holder.tvRingSize.setText(Html.fromHtml(sourceString));

        } else if (itemArrayList.get(i).getBracelets() != null) {
            sourceString = "<b>" + "Bracelet Size : " + "</b> " + itemArrayList.get(i).getBracelets();
            holder.tvRingSize.setText(Html.fromHtml(sourceString));

        } else if (itemArrayList.get(i).getBangles() != null) {
            sourceString = "<b>" + "Bangle Size : " + "</b> " + itemArrayList.get(i).getBangles();
            holder.tvRingSize.setText(Html.fromHtml(sourceString));

        } else if (itemArrayList.get(i).getPendents() != null) {
            sourceString = "<b>" + "Pendent Size : " + "</b> " + itemArrayList.get(i).getPendents();
            holder.tvRingSize.setText(Html.fromHtml(sourceString));
        } else {
            holder.tvRingSize.setVisibility(View.GONE);
        }

        if (itemArrayList.get(i).getProduct_type().equalsIgnoreCase("simple")) {
            holder.imgPlus.setVisibility(View.GONE);
            holder.imgMinus.setVisibility(View.GONE);
        } else {
            holder.imgPlus.setVisibility(View.VISIBLE);
            holder.imgMinus.setVisibility(View.VISIBLE);
        }

        holder.imgProduct.setImageURI(itemArrayList.get(i).getImage());

        holder.imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qty = Integer.parseInt(itemArrayList.get(i).getQty());
                qty++;
                holder.tvQuantity.setText(String.valueOf(qty));
                itemArrayList.get(i).setQty(String.valueOf(qty));
                cartCount++;             //Added this line to update cartcount for every item  But count not updated from API SIDE.
                AppLogger.e("CountUpdated ","-----"+cartCount);
                notifyItemChanged(i);
                notifyItemRangeChanged(i, itemArrayList.size());
                updateProductQty(customerId, itemArrayList.get(i).getItemid(), String.valueOf(qty));
            }
        });

        holder.imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qty = Integer.parseInt(itemArrayList.get(i).getQty());
                if (String.valueOf(qty).equalsIgnoreCase("1")) {
                    holder.tvQuantity.setText(String.valueOf(qty));
                    AppLogger.e("minus old price", "----------" + itemArrayList.get(i).getPrice());
                    itemArrayList.get(i).setQty(String.valueOf(qty));
                    notifyItemChanged(i);
                    notifyItemRangeChanged(i, itemArrayList.size());
//                    updateProductQty(customerId, itemArrayList.get(i).getItemid(), String.valueOf(qty));
                    cartCount--;         //Added this line to update cartcount for every item
                } else {
                    qty--;
                    holder.tvQuantity.setText(String.valueOf(qty));
                    AppLogger.e("minus old price", "----------" + itemArrayList.get(i).getPrice());
//                    float updatePrice = Float.parseFloat(itemArrayList.get(i).getPrice()) * qty;
//                    itemArrayList.get(i).setPrice(String.valueOf(updatePrice));
                    itemArrayList.get(i).setQty(String.valueOf(qty));
                    notifyItemChanged(i);
                    notifyItemRangeChanged(i, itemArrayList.size());
                    updateProductQty(customerId, itemArrayList.get(i).getItemid(), String.valueOf(qty));
                    cartCount--;   //Added this line to update cartcount for every item
                    AppLogger.e("CountUpdated ","-----"+cartCount);
                }
            }
        });

        holder.tvRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new IOSDialog.Builder(activity)
                        .setTitle(activity.getString(R.string.delete))
                        .setMessage(activity.getString(R.string.delete_msg))
                        .setCancelable(false)
                        .setPositiveButton(activity.getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                deleteProduct(itemArrayList.get(i).getItemid());
                                itemArrayList.remove(i);

                                if (itemArrayList.isEmpty()) {
//                                    activity.finish();
                                }
                                notifyItemRemoved(i);
                                notifyItemRangeChanged(i, itemArrayList.size());
                            }
                        })
                        .setNegativeButton(activity.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();

              /* new AlertDialog.Builder(activity, R.style.AppCompatAlertDialogStyle)

                        .setTitle(CommonUtils.capitalizeString(activity.getString(R.string.delete)))

                        .setMessage(activity.getString(R.string.delete_msg))
                        .setCancelable(false)
                        .setPositiveButton(activity.getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                deleteProduct(itemArrayList.get(i).getItemid());
                                itemArrayList.remove(i);
                                if (itemArrayList.isEmpty()) {
//                                    activity.finish();
                                }
                                notifyItemRemoved(i);
                                notifyItemRangeChanged(i, itemArrayList.size());
                            }
                        })

                        .setNegativeButton(activity.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        final TextView tvRemove, tvSku, tvRingSize, tvMetalDetail, tvStoneDetail, tvPrice, tvQuantity;
        final ImageView imgPlus, imgMinus;
        final SimpleDraweeView imgProduct;

        ViewHolder(View itemView) {
            super(itemView);
            tvRemove = itemView.findViewById(R.id.tvRemove);
            tvSku = itemView.findViewById(R.id.tvSku);
            tvRingSize = itemView.findViewById(R.id.tvRingSize);
            tvMetalDetail = itemView.findViewById(R.id.tvMetalDetail);
            tvStoneDetail = itemView.findViewById(R.id.tvStoneDetail);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            imgPlus = itemView.findViewById(R.id.imgPlus);
            imgMinus = itemView.findViewById(R.id.imgMinus);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

    private void deleteProduct(String itemId) {

        //show progress
        hud = KProgressHUD.create(activity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
        hud.show();

        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<JsonObject> callApi = apiInterface.removeCartItem(customerId, itemId);
        callApi.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                assert response.body() != null;
                AppLogger.e(AppConstants.RESPONSE, "---------" + response.body());
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        if (jsonObject.getString("status").equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
                            if (cartCount == 0) {

                            } else {
                                cartCount--;
                            }

                            hud.dismiss();
                            shoppingFrg.updateCart(customerId);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                AppLogger.e("error", "------------" + t.getMessage());
            }
        });
    }

    private void updateProductQty(final String customersId, String itemId, String qty) {
        //show progress
        hud = KProgressHUD.create(activity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
        hud.show();
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<JsonObject> callApi = apiInterface.updateCartQty(customersId, itemId, qty);
        callApi.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                assert response.body() != null;
                AppLogger.e(AppConstants.RESPONSE, "---------" + response.body());
                hud.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    if (jsonObject.getString("status").equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
                        shoppingFrg.updateCart(customersId);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                hud.dismiss();
                AppLogger.e("error", "------------" + t.getMessage());
            }
        });
    }
}
