package com.dealermela.authentication.myaccount.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dealermela.R;
import com.dealermela.authentication.myaccount.activity.EditAddressAct;
import com.dealermela.authentication.myaccount.activity.ManageAddressAct;
import com.dealermela.authentication.myaccount.model.AddressManageResponse;
import com.dealermela.retrofit.APIClient;
import com.dealermela.retrofit.ApiInterface;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
import com.dealermela.util.CommonUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ligl.android.widget.iosdialog.IOSDialog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dealermela.home.activity.MainActivity.customerId;

public class AddressRecyclerAdapter extends RecyclerView.Adapter<AddressRecyclerAdapter.ViewHolder> {

    private final Activity activity;
    private final List<AddressManageResponse.Datum> itemArrayList;

    public AddressRecyclerAdapter(Activity activity, List<AddressManageResponse.Datum> itemArrayList) {
        super();
        this.activity = activity;
        this.itemArrayList = itemArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.act_manage_address_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int i) {
        holder.tvBName.setText(itemArrayList.get(i).getFirstname() + " " + itemArrayList.get(i).getLastname());
        holder.tvBAddress1.setText(itemArrayList.get(i).getStreet());
//        holder.tvBAddress2.setText(itemArrayList.get(i).getStreet1());
        holder.tvBCity.setText(itemArrayList.get(i).getCity()+","+itemArrayList.get(i).getRegion()+","+itemArrayList.get(i).getPostcode());
        holder.tvBCountry.setText(itemArrayList.get(i).getCountry());
        holder.tvBTelephone.setText("T: "+itemArrayList.get(i).getTelephone());

        //Edit Address
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                Gson gson = new Gson();
                intent = new Intent(activity, EditAddressAct.class);
                String defaultShipping = gson.toJson(itemArrayList.get(i));
                intent.putExtra("addressStatus", "3");
                intent.putExtra("defaultShipping", defaultShipping);
                activity.startActivity(intent);
            }
        });

        //Delete Address
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new IOSDialog.Builder(activity)
                        .setTitle(activity.getString(R.string.delete))
                        .setMessage(activity.getString(R.string.delete_msg))
                        .setPositiveButton(activity.getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                // continue with delete
                                AppLogger.e("id","-------"+itemArrayList.get(i).getEntityId());
                                deleteAddress(itemArrayList.get(i).getEntityId(), customerId);
                                itemArrayList.remove(i);
                                notifyItemRemoved(i);
                                notifyItemRangeChanged(i, itemArrayList.size());
                                if(itemArrayList.size() == 0){
                                    ManageAddressAct.tvNoAddress.setVisibility(View.VISIBLE);
                                }
                                CommonUtils.showSuccessToast(activity,"Deleted successfully");
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
                        AppLogger.e("id","-------"+itemArrayList.get(i).getEntityId());
                        deleteAddress(itemArrayList.get(i).getEntityId(), customerId);
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
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        final TextView tvBName;
        final TextView tvBAddress1;
        final TextView tvBAddress2;
        final TextView tvBCity;
        final TextView tvBCountry;
        final TextView tvBTelephone;
        final Button btnEdit;
        final Button btnDelete;

        ViewHolder(View itemView) {
            super(itemView);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            tvBName = itemView.findViewById(R.id.tvBName);
            tvBAddress1 = itemView.findViewById(R.id.tvBAddress1);
            tvBAddress2 = itemView.findViewById(R.id.tvBAddress2);
            tvBCity = itemView.findViewById(R.id.tvBCity);
            tvBCountry = itemView.findViewById(R.id.tvBCountry);
            tvBTelephone = itemView.findViewById(R.id.tvBTelephone);
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

    private void deleteAddress(String address_id, String customer_id) {
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<JsonObject> callApi = apiInterface.deleteAddress(address_id, customer_id);
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
