package com.dealermela.download.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dealermela.R;
import com.dealermela.download.activity.DownloadAct;
import com.dealermela.download.model.DownloadItem;
import com.dealermela.listing_and_detail.activity.ProductDetailAct;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dealermela.home.activity.MainActivity.customerId;

public class DownloadProductAdapter extends RecyclerView.Adapter<DownloadProductAdapter.ViewHolder> {
    private final Activity activity;
    public List<DownloadItem.Detail> itemArrayList;
    private KProgressHUD hud;

    public DownloadProductAdapter(Activity activity, List<DownloadItem.Detail> itemArrayList) {
        super();
        this.activity = activity;
        this.itemArrayList = itemArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.act_download_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int i) {

        holder.tvProductPrice.setText(AppConstants.RS + CommonUtils.priceFormat((float) Math.ceil(Float.parseFloat(itemArrayList.get(i).getPrice()))));
        holder.imgDownloadProduct.setImageURI(itemArrayList.get(i).getImage());
//        holder.tvSku.setText(itemArrayList.get(i).getSku());

        String[] strings1 = itemArrayList.get(i).getSku().split(" ");
        for (int j = 0; j < strings1.length; j++) {
            if (j == 0) {
                holder.tvSku.setText(strings1[j]);
            }
        }

        holder.checkDownloadProduct.setChecked(itemArrayList.get(i).isSelected() ? true : false);

        if (itemArrayList.get(i).getFlag().equalsIgnoreCase("1")) {
            holder.imgDeleteFlag.setVisibility(View.VISIBLE);
        } else {
            holder.imgDeleteFlag.setVisibility(View.GONE);
        }

        holder.imgDeleteFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.showWarningToast(activity, "Already Downloaded.");
            }
        });

        //using for download product image
        holder.imgDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= 23) {
                    if (((DownloadAct) activity).checkPermission()) {
                        // Code for above or equal 23 API Oriented Device
                        // Your Permission granted already .Do next code
                        AppLogger.e("init if ", "----call");
                        downloadProduct(itemArrayList.get(i).getProductId(), itemArrayList.get(i).getSku(), i);
                    } else {
                        AppLogger.e("init if else ", "----call");
                        ((DownloadAct) activity).requestPermission(); // Code for permission
                    }
                } else {
                    AppLogger.e("init else ", "----call");
                    // Code for Below 23 API Oriented Device
                    // Do next code
                    downloadProduct(itemArrayList.get(i).getProductId(), itemArrayList.get(i).getSku(), i);
                }
            }
        });

        //using for download product delete
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
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
                                deleteProduct(customerId, itemArrayList.get(i).getProductId());
                                itemArrayList.remove(i);
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


               /* AlertDialog.Builder alert = new AlertDialog.Builder(activity, R.style.AppCompatAlertDialogStyle);
                alert.setTitle(activity.getString(R.string.delete));
                alert.setMessage(activity.getString(R.string.delete_msg));
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        dialog.dismiss();
                        deleteProduct(customerId, itemArrayList.get(i).getProductId());
                        itemArrayList.remove(i);
                        notifyItemRemoved(i);
                        notifyItemRangeChanged(i, itemArrayList.size());
                    }
                });
                alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // close dialog
                        dialog.cancel();
                    }
                });
                alert.show();*/
            }
        });

        holder.checkDownloadProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                itemArrayList.get(i).setSelected(!itemArrayList.get(i).isSelected());

                holder.checkDownloadProduct.setChecked(itemArrayList.get(i).isSelected() ? true : false);

                if (itemArrayList.get(i).isSelected()) {
                } else {
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        final ImageView imgDownload, imgDelete, imgDeleteFlag;
        final SimpleDraweeView imgDownloadProduct;
        final TextView tvProductPrice, tvSku;
        final CheckBox checkDownloadProduct;

        ViewHolder(View itemView) {
            super(itemView);
            imgDownload = itemView.findViewById(R.id.imgDownload);
            imgDeleteFlag = itemView.findViewById(R.id.imgDeleteFlag);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            imgDownloadProduct = itemView.findViewById(R.id.imgDownloadProduct);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            tvSku = itemView.findViewById(R.id.tvSku);
            checkDownloadProduct = itemView.findViewById(R.id.checkDownloadProduct);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onClick(View v) {

            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.act_download_dialog_popup);
            dialog.show();
            SimpleDraweeView imgProductImage = dialog.findViewById(R.id.imgProductImage);
            ImageView imgClose = dialog.findViewById(R.id.imgClose);

            imgClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            TextView tvSkuDialog = dialog.findViewById(R.id.tvSkuDialog);
            TextView tvGoldType = dialog.findViewById(R.id.tvGoldType);
            TextView tvMetalDetail = dialog.findViewById(R.id.tvMetalDetail);
            TextView tvStoneDetail = dialog.findViewById(R.id.tvStoneDetail);
            Button btnViewDetail = dialog.findViewById(R.id.btnViewDetail);

            btnViewDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, ProductDetailAct.class);
                    intent.putExtra(AppConstants.NAME, itemArrayList.get(getAdapterPosition()).getProductId());
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                }
            });

//            tvSkuDialog.setText(itemArrayList.get(getAdapterPosition()).getSku());
            StringBuilder stringBuilder = new StringBuilder();

            String[] strings = itemArrayList.get(getAdapterPosition()).getSku().split(" ");
            for (int i = 0; i < strings.length; i++) {
                if (i == 0) {
                    tvSkuDialog.setText(strings[i]);
                }else {
                    stringBuilder.append(strings[i]);
                    stringBuilder.append(" ");
                }
            }
            tvGoldType.setVisibility(View.VISIBLE);
            tvGoldType.setText(stringBuilder);
//            tvMetalDetail.setText(itemArrayList.get(getAdapterPosition()).getMetalDeatil() + " " + itemArrayList.get(getAdapterPosition()).getCarat());
            tvMetalDetail.setText(itemArrayList.get(getAdapterPosition()).getCarat() + " ("+ itemArrayList.get(getAdapterPosition()).getMetalDeatil()+ ")" );
            tvStoneDetail.setText(itemArrayList.get(getAdapterPosition()).getStoneDetail() + " (" + itemArrayList.get(getAdapterPosition()).getDiamondWeight() + ")" );

            imgProductImage.setImageURI(itemArrayList.get(getAdapterPosition()).getImage());

            Objects.requireNonNull(dialog.getWindow()).setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }

    private void downloadProduct(String productId, final String sku, final int position) {
        //show progress
        hud = KProgressHUD.create(activity)
                .setLabel("Please wait")
                .setDetailsLabel("Downloading product")
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
        hud.show();

        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<JsonObject> callApi = apiInterface.downloadProductImage(customerId, productId, ((DownloadAct) activity).flag);
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
                            String url = jsonObject.getString("image");
                            String fileExtension = MimeTypeMap.getFileExtensionFromUrl(url);
                            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                            request.setDescription(sku);
                            request.setTitle(sku);
                            File folder;
                            String state = Environment.getExternalStorageState();
                            if (state.contains(Environment.MEDIA_MOUNTED)) {
                                folder = new File(Environment
                                        .getExternalStorageDirectory() + "/Diamond Mela/Download Product");
                            } else {
                                folder = new File(Environment
                                        .getExternalStorageDirectory() + "/Diamond Mela/Download Product");
                            }
                            request.allowScanningByMediaScanner();
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            @SuppressLint("SimpleDateFormat") String fileName = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
                            fileName += "."+fileExtension;
                            request.setDestinationInExternalPublicDir(folder.getAbsolutePath(), sku + fileName);

                            DownloadManager manager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
                            manager.enqueue(request);
                            CommonUtils.showSuccessToast(activity, "Image Download in Gallery");

                            itemArrayList.get(position).setFlag("0");
                            notifyItemChanged(position);

//                            itemArrayList.remove(position);
//                            notifyItemRemoved(position);
//                            notifyItemRangeChanged(position, itemArrayList.size());

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

    private void deleteProduct(String customersId, String productsId) {
        AppLogger.e("customerId", customersId);
        AppLogger.e("productId", productsId);
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<JsonObject> callApi = apiInterface.deleteProductImage(customersId, productsId);
        callApi.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                assert response.body() != null;
                AppLogger.e(AppConstants.RESPONSE, "---------" + response.body());
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    if (jsonObject.getString("status").equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
                        if (itemArrayList.isEmpty()) {
                            activity.finish();
                        }
                        CommonUtils.showSuccessToast(activity, "Product deleted successfully");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                AppLogger.e("error", "------------" + t.getMessage());
            }
        });
    }

    public void removeAt(int position) {
//        itemArrayList.get(position).setFlag("1");
//        itemArrayList.get(position).setSelected(false);
        itemArrayList.remove(position);
        if (itemArrayList.isEmpty()) {
            activity.finish();
        }
//        notifyItemChanged(position);
//        notifyItemRangeChanged(position, itemArrayList.size());
    }


   /* public void removeAt(int position) {
        itemArrayList.remove(position);
        notifyItemRemoved(position);
//        notifyItemRangeChanged(position, itemArrayList.size());
    }*/


    public void updateData() {
        for (int i = 0; i <= itemArrayList.size() - 1; i++) {
            itemArrayList.get(i).setSelected(false);
            notifyItemChanged(i);
        }
    }

    public void updateDownloadLoad(int position) {
        itemArrayList.get(position).setFlag("0");
        notifyItemChanged(position);
    }
}
