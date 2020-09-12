package com.dealermela.referral.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dealermela.R;
import com.dealermela.referral.activity.ManageReferralAct;
import com.dealermela.referral.model.ReferralResponse;
import com.dealermela.retrofit.APIClient;
import com.dealermela.retrofit.ApiInterface;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.google.gson.JsonObject;
import com.ligl.android.widget.iosdialog.IOSDialog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dealermela.home.activity.MainActivity.customerId;

public class ReferralListRecyclerAdapter extends RecyclerView.Adapter<ReferralListRecyclerAdapter.ViewHolder> {

    private final Activity activity;
    private final List<ReferralResponse.Datum> itemArrayList;


    public ReferralListRecyclerAdapter(Activity activity, List<ReferralResponse.Datum> itemArrayList) {
        super();
        this.activity = activity;
        this.itemArrayList = itemArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.act_manage_referral_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int i) {

        holder.tvName.setText(itemArrayList.get(i).getFirstname() + " " + itemArrayList.get(i).getLastname());
        holder.tvDiscount.setText(itemArrayList.get(i).getReferralComission());
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new IOSDialog.Builder(activity)
                        .setTitle(activity.getString(R.string.delete))
                        .setMessage("Are you sure to delete?")
                        .setCancelable(false)
                        .setPositiveButton(activity.getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                deleteReferral(itemArrayList.get(i).getEntityId());
                                itemArrayList.remove(i);
                                notifyItemRemoved(i);
                                notifyItemRangeRemoved(i, itemArrayList.size());
                                if (itemArrayList.isEmpty()){
                                    ManageReferralAct.constraintNoData.setVisibility(View.VISIBLE);
                                }
                            }
                        })
                        .setNegativeButton(activity.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();

              /*  AlertDialog.Builder alert = new AlertDialog.Builder(activity,R.style.AppCompatAlertDialogStyle);
                alert.setTitle(activity.getString(R.string.delete));
                alert.setMessage(activity.getString(R.string.delete_msg));
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        deleteReferral(itemArrayList.get(i).getEntityId());
                        itemArrayList.remove(i);
                        notifyItemRemoved(i);
                        notifyItemRangeRemoved(i, itemArrayList.size());
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
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        final TextView tvName;
        final TextView tvDiscount;
        final ImageView imgDelete;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDiscount = itemView.findViewById(R.id.tvDiscount);
            imgDelete = itemView.findViewById(R.id.imgDelete);
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

    private void deleteReferral(String referralId) {
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<JsonObject> callApi = apiInterface.deleteReferral(referralId);
        callApi.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                AppLogger.e(AppConstants.RESPONSE, "-----------------" + response.body());
                assert response.body() != null;
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                AppLogger.e("", "------------" + t.getMessage());
            }
        });
    }
}
