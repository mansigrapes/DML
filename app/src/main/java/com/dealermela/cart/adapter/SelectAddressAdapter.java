package com.dealermela.cart.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dealermela.R;
import com.dealermela.authentication.myaccount.model.AddressManageResponse;
import com.dealermela.cart.activity.SelectAddressAct;
import com.dealermela.retrofit.APIClient;
import com.dealermela.retrofit.ApiInterface;
import com.dealermela.util.AppConstants;
import com.dealermela.util.AppLogger;
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


public class SelectAddressAdapter extends RecyclerView.Adapter<SelectAddressAdapter.ViewHolder> {

    private final Activity activity;
    private final List<AddressManageResponse.Datum> itemArrayList;
    KProgressHUD hud;

    public SelectAddressAdapter(Activity activity, List<AddressManageResponse.Datum> itemArrayList) {
        super();
        this.activity = activity;
        this.itemArrayList = itemArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.act_select_address_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int i) {

        holder.tvName.setText(itemArrayList.get(i).getFirstname() + " " + itemArrayList.get(i).getLastname());
        holder.tvAddress.setText(itemArrayList.get(i).getStreet() + ",\n" + itemArrayList.get(i).getCity() + ", " + itemArrayList.get(i).getRegion() + ", " + itemArrayList.get(i).getPostcode() + ",\n" + itemArrayList.get(i).getCountryId() + "\nT: " + itemArrayList.get(i).getTelephone());

        holder.btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((SelectAddressAct) activity).addressFlag.equalsIgnoreCase("Billing")) {
                    SharedPreferences sharedPreferences = new SharedPreferences(activity);
                    sharedPreferences.saveOrderBillingAddress(itemArrayList.get(i).getFirstname() + " " + itemArrayList.get(i).getLastname() + ",\n" + itemArrayList.get(i).getStreet() + ",\n" + itemArrayList.get(i).getCity() + ", " + itemArrayList.get(i).getRegion() + ", " + itemArrayList.get(i).getPostcode() + ",\n" + itemArrayList.get(i).getCountryId() + "\nT: " + itemArrayList.get(i).getTelephone());
                    setAddress(itemArrayList.get(i).getEntityId(), "billing");
                } else if (((SelectAddressAct) activity).addressFlag.equalsIgnoreCase("Shipping")) {
                    SharedPreferences sharedPreferences = new SharedPreferences(activity);
                    sharedPreferences.saveOrderShipping(itemArrayList.get(i).getFirstname() + " " + itemArrayList.get(i).getLastname() + ",\n" + itemArrayList.get(i).getStreet() + ",\n" + itemArrayList.get(i).getCity() + ", " + itemArrayList.get(i).getRegion() + ", " + itemArrayList.get(i).getPostcode() + ",\n" + itemArrayList.get(i).getCountryId() + "\nT: " + itemArrayList.get(i).getTelephone());
                    setAddress(itemArrayList.get(i).getEntityId(), "shipping");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        final TextView tvName, tvAddress;
        final Button btnSelect;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            btnSelect = itemView.findViewById(R.id.btnSelect);
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

    private void setAddress(String address_id, String flag_shipping) {
        //show progress
        hud = KProgressHUD.create(activity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE);
        hud.show();
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<JsonObject> callApi = apiInterface.setAddresses(customerId, address_id, flag_shipping);
        callApi.enqueue(new Callback<JsonObject>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                Log.e(AppConstants.RESPONSE, "-----------------" + response.body());
                assert response.body() != null;
                hud.dismiss();
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        if (jsonObject.getString("status").equalsIgnoreCase(AppConstants.STATUS_CODE_SUCCESS)) {
                            activity.finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {
                AppLogger.e("", "------------" + t.getMessage());
                hud.dismiss();
            }
        });
    }
}
